package cn.ms.gateway;

import cn.ms.gateway.frm.IAdapter;

/**
 * 微服务网关
 * 
 * @author lry
 */
public enum MsGateway implements IAdapter {

	INSTANCE;

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
	}

	public static void main(String[] args) {
		try {
			// 第一步初始化
			INSTANCE.init();
			// 第二步启动容器
			INSTANCE.start();
		} catch (Exception e) {
			e.printStackTrace();

			try {
				// 异常则销毁
				INSTANCE.destroy();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
