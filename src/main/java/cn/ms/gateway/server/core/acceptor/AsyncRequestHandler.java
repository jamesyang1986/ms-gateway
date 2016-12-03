package cn.ms.gateway.server.core.acceptor;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.timeout.ReadTimeoutException;
import cn.ms.gateway.server.common.RequestMethod;
import cn.ms.gateway.server.core.NestyServerMonitor;
import cn.ms.gateway.server.core.rest.ExecutorTask;
import cn.ms.gateway.server.core.rest.HttpContext;
import cn.ms.gateway.server.core.rest.URLResource;
import cn.ms.gateway.server.core.rest.controller.URLController;
import cn.ms.gateway.server.core.rest.entity.HttpResponseBuilder;
import cn.ms.gateway.server.core.rest.entity.NettyHttpRequestVisitor;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

public class AsyncRequestHandler extends AsyncRequestReceiver {

    // Http context
    private volatile HttpContext httpContext;
    // channel context
    private ChannelHandlerContext context;

    public static AsyncRequestHandler build() {
        return new AsyncRequestHandler();
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final FullHttpRequest httpRequest) throws Exception {
        this.context = ctx;

        // build context
        httpContext = HttpContext.build(new NettyHttpRequestVisitor(context.channel(), httpRequest));

        // execute async logic code block
        doRun();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof ReadTimeoutException) {
            // httpcode 504
            if (context.channel().isOpen())
                sendResponse(httpContext, HttpResponseBuilder.create(httpContext, HttpResponseStatus.GATEWAY_TIMEOUT));
        }
    }

    private void doRun() {

        /**
         * 1. checking phase. http method, param, url
         *
         */
        if (!checkup())
            return;

        /**
         * 2. according to URL to search the URLController
         *
         */
        URLController controller = findController();

        /**
         * 3. execute controller logic to async executor thread pool
         *
         */
        if (controller != null)
            execute(controller);
    }

    private boolean checkup() {
        // check http method
        if (httpContext.getRequestMethod() == RequestMethod.UNKOWN) {
            // httpcode 405
            sendResponse(httpContext, HttpResponseBuilder.create(httpContext, HttpResponseStatus.METHOD_NOT_ALLOWED));
            return false;
        }

        return true;
    }

    private URLController findController() {
        // build URLResource from incoming http request
        URLResource resource = URLResource.fromHttp(httpContext.getUri(), httpContext.getRequestMethod());
        URLController controller;
        if ((controller = controllerRouter.findURLController(resource)) == null) {
            // httpcode 404
            sendResponse(httpContext, HttpResponseBuilder.create(httpContext, HttpResponseStatus.NOT_FOUND));
            NestyServerMonitor.incrRequestMiss();
            return null;
        }

        if (!controller.isInternal()) {
            NestyServerMonitor.incrRequestHit();
            controller.hit();
        }

        return controller;
    }

    private void execute(URLController controller) {
        if (!controller.isInternal()) {
            NestyServerMonitor.setLastServTime(System.currentTimeMillis());
            NestyServerMonitor.setLastServID(httpContext.getRequestId());
        }

        // if IOWorker pool is empty. we use this same thread directly.
        // no more context switch (queue pop/push cause) happen here !
        if (ioWorker == null) {
            try {
                sendResponse(httpContext, new ExecutorTask(httpContext, interceptor, controller).call());
            } catch (Throwable e) {
                e.printStackTrace();
                sendResponse(httpContext, failure());
            }
            return;
        }

        executeAsyncTask(new ExecutorTask(httpContext, interceptor, controller));
    }

    private void executeAsyncTask(ExecutorTask task) {
        final HttpContext currentContext = httpContext;

        Futures.addCallback(ioWorker.submit(task), new FutureCallback<DefaultFullHttpResponse>() {
            @Override
            public void onSuccess(DefaultFullHttpResponse resp) {
                sendResponse(currentContext, resp);
            }

            @Override
            public void onFailure(Throwable e) {
                e.printStackTrace();
                sendResponse(currentContext, failure());
            }
        });
    }

    private DefaultFullHttpResponse failure() {
        NestyServerMonitor.setLastServFailID(httpContext.getRequestId());
        // httpcode 503
        return HttpResponseBuilder.create(httpContext, HttpResponseStatus.SERVICE_UNAVAILABLE);
    }

    private void sendResponse(HttpContext ctx, DefaultFullHttpResponse response) {
        ChannelFuture future = context.channel().writeAndFlush(response);

        // http short connection
        if (!ctx.isKeepAlive())
            future.addListener(ChannelFutureListener.CLOSE);
    }
}
