package cn.ms.gateway.core.container;

import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

public interface IContainerCallback {

	GatewayRES callback(GatewayREQ req, Object... args) throws Throwable;
	
}
