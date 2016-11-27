package cn.ms.gateway.neural.route.entity;

import java.net.InetSocketAddress;

import cn.ms.gateway.common.ConcurrentHashSet;

/**
 * 服务实例
 * 
 * @author lry
 */
public class ServiceApp {

	private String serviceIdVersion;
	private ConcurrentHashSet<InetSocketAddress> apps;

	public ServiceApp() {
	}

	public ServiceApp(String serviceIdVersion) {
		this.serviceIdVersion = serviceIdVersion;
		this.apps = new ConcurrentHashSet<InetSocketAddress>();
	}
	
	public ServiceApp(String serviceIdVersion, ConcurrentHashSet<InetSocketAddress> apps) {
		this.serviceIdVersion = serviceIdVersion;
		this.apps = apps;
	}

	public String getServiceIdVersion() {
		return serviceIdVersion;
	}

	public void setServiceIdVersion(String serviceIdVersion) {
		this.serviceIdVersion = serviceIdVersion;
	}

	public ConcurrentHashSet<InetSocketAddress> getApps() {
		return apps;
	}

	public void setApps(ConcurrentHashSet<InetSocketAddress> apps) {
		this.apps = apps;
	}

	@Override
	public String toString() {
		return "ServiceApp [serviceIdVersion=" + serviceIdVersion + ", apps="
				+ apps + "]";
	}

}
