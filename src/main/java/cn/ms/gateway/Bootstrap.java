package cn.ms.gateway;


/**
 * 微服务网关
 * 
 * @author lry
 */
public enum Bootstrap {

	INSTANCE;

	public void init() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void start() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void destory() throws Exception {
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
				INSTANCE.destory();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
