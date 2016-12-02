package cn.ms.gateway.server.core.rest.interceptor.internal;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import cn.ms.gateway.server.common.writer.DailyRollFileWriter;
import cn.ms.gateway.server.common.writer.FileWriter;
import cn.ms.gateway.server.common.writer.GenericFileWriter;
import cn.ms.gateway.server.core.NestyOptionProvider;
import cn.ms.gateway.server.core.NestyOptions;
import cn.ms.gateway.server.core.rest.HttpContext;
import cn.ms.gateway.server.core.rest.interceptor.Interceptor;

import java.io.IOException;
import java.util.Date;

@cn.ms.gateway.server.common.annotations.Interceptor
public class AccessLog extends Interceptor {

    /**
     * A switch of logging. This value is configured by nesty-server options. Represent with a full path of log file name when logging is opend.
     */
    private GenericFileWriter accessLogAppender;

    @SuppressWarnings("deprecation")
	@Override
    public DefaultFullHttpResponse handler(HttpContext context, DefaultFullHttpResponse response) {
        if (accessLogAppender != null) {
            String accessOnce = new AccessLogGenerator()
                    .setIp(context.getRemoteAddress())
                    .setTime(new Date(context.getCreationTime()))
                    .setHttpMethod(context.getRequestMethod().name())
                    .setURL(context.getUri())
                    .setHttpCode(response.getStatus().hashCode())
                    .setConsume(System.currentTimeMillis() - context.getCreationTime())
                    .setTransitionSize(response.content().readableBytes())
                    .setReqeustID(context.getRequestId())
                    .makeAccess();

            accessLogAppender.writeLine(accessOnce);
        }

        return super.handler(context, response);
    }

    @Override
    public boolean install(NestyOptionProvider nesty) {
        String useLog = nesty.option(NestyOptions.ACCESS_LOG);
        if (useLog != null && !useLog.isEmpty()) {
            try {
                // default buffer is 4k
                GenericFileWriter appender = new DailyRollFileWriter(nesty.option(NestyOptions.ACCESS_LOG_BUFFER_IO));
                if (appender.open(useLog, FileWriter.WriteMode.APPEND))
                    accessLogAppender = appender;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return super.install(nesty);
    }


    class AccessLogGenerator {

        private String ip;
        private Date time;
        private String httpMethod;
        private String URL;
        private int httpCode;
        private long consume;
        private int transitionSize;
        private String reqeustID;

        AccessLogGenerator setIp(String ip) {
            this.ip = ip;
            return this;
        }

        AccessLogGenerator setTime(Date time) {
            this.time = time;
            return this;
        }

        AccessLogGenerator setHttpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        AccessLogGenerator setURL(String URL) {
            this.URL = URL;
            return this;
        }

        AccessLogGenerator setHttpCode(int httpCode) {
            this.httpCode = httpCode;
            return this;
        }

        AccessLogGenerator setTransitionSize(int transitionSize) {
            this.transitionSize = transitionSize;
            return this;
        }

        AccessLogGenerator setReqeustID(String reqeustID) {
            this.reqeustID = reqeustID;
            return this;
        }

        AccessLogGenerator setConsume(long consume) {
            this.consume = consume;
            return this;
        }

        String makeAccess() {
            return String.format("%s [%s] %s %s %d %d %d %s", ip, time.toString(), httpMethod, URL, httpCode, consume, transitionSize, reqeustID);
        }
    }
}
