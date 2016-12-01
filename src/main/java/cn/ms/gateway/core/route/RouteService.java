package cn.ms.gateway.core.route;

/**
 * 路由服务
 * 
 * @author lry
 */
public class RouteService {

	// 服务ID
	private String serviceId;
	// 服务版本号
	private String version;
	// 场景ID
	private String sceneId;

	RouteService(String serviceId, String version, String sceneId) {
		this.serviceId = serviceId;
		this.version = version;
		this.sceneId = sceneId;
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

	public String getSceneId() {
		return sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	//$NON-NLS-扩展$
	public String buildKey() {
		return getServiceId() + RouteContext.SVS_SEQ + getVersion() + RouteContext.SVS_SEQ + getSceneId();
	}
	
	public static RouteService build(String serviceId, String version, String sceneId) {
		return new RouteService(serviceId, version, sceneId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sceneId == null) ? 0 : sceneId.hashCode());
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
		RouteService other = (RouteService) obj;
		if (sceneId == null) {
			if (other.sceneId != null)
				return false;
		} else if (!sceneId.equals(other.sceneId))
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
				+ ", sceneId=" + sceneId + "]";
	}

}
