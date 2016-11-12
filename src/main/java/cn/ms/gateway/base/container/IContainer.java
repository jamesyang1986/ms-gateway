package cn.ms.gateway.base.container;

import cn.ms.gateway.base.adapter.IAdapter;

/**
 * 网关运行容器 <br>
 * <br>
 * 用于启动网关服务，对外接收请求，将请求转接人微服务网关核心通道中
 * 
 * @author lry
 *
 * @param <REQ>
 * @param <RES>
 */
public interface IContainer<REQ, RES> extends IAdapter {

}
