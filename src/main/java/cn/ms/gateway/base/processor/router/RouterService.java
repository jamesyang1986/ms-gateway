package cn.ms.gateway.base.processor.router;

/**
 * 路由服务
 * 
 * @author lry
 */
public class RouterService {

	/** 服务ID **/
	private String serviceId;
	/** 服务版本号 **/
	private String version;
	/** 服务组ID(相同服务相同版本的不同组) **/
	private String group;

	RouterService(String serviceId, String version, String group) {
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

	//$NON-NLS-扩展$
	public String buildKey() {
		return getServiceId() + RouterContext.SVG_SEQ + getVersion() + RouterContext.SVG_SEQ + getGroup();
	}

	public static RouterService build(String serviceId, String version,
			String sceneId) {
		return new RouterService(serviceId, version, sceneId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result
				+ ((serviceId == null) ? 0 : serviceId.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RouterService other = (RouterService) obj;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RouteService [serviceId=" + serviceId + ", version=" + version
				+ ", group=" + group + "]";
	}

}
