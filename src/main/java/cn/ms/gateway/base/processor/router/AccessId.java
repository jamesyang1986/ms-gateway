package cn.ms.gateway.base.processor.router;

/**
 * 访问ID
 * 
 * @author lry
 */
public class AccessId {

	/** 服务ID **/
	private String serviceId;
	/** 服务版本号 **/
	private String version;
	/** 服务场景 **/
	private String group;

	public AccessId() {
	}
	
	public AccessId(String serviceId, String version, String group) {
		this.serviceId = serviceId;
		this.version = version;
		this.group = group;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "AccessId [serviceId=" + serviceId + ", version=" + version
				+ ", group=" + group + "]";
	}

}
