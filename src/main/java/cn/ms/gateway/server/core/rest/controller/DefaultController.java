package cn.ms.gateway.server.core.rest.controller;

import cn.ms.gateway.server.common.RequestMethod;
import cn.ms.gateway.server.common.annotations.Controller;
import cn.ms.gateway.server.common.annotations.RequestMapping;
import cn.ms.gateway.server.core.NestyServerMonitor;
import cn.ms.gateway.server.core.rest.URLResource;

import java.util.HashMap;
import java.util.Map;

@Controller
public class DefaultController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RootResponse root() {
        RootResponse response = new RootResponse();
        response.REQUESTS_MISS = NestyServerMonitor.getRequestMiss();
        response.REQUEST_HITS = NestyServerMonitor.getRequestHit();
        response.CONNECTIONS = NestyServerMonitor.getConnections();
        response.LAST_SERV_TIME = NestyServerMonitor.getLastServTime();
        response.LAST_SERV_ID = NestyServerMonitor.getLastServID();
        response.LAST_SERV_FAIL_ID = NestyServerMonitor.getLastServFailID();

        for (Map.Entry<URLResource, URLController> entry : NestyServerMonitor.getResourcesMap().entrySet())
            response.RESOURCES_HITS.put(entry.getKey().toString(), entry.getValue().count());

        return response;
    }

    public static class RootResponse {
        public long REQUESTS_MISS;
        public long REQUEST_HITS;
        public long CONNECTIONS;
        public long LAST_SERV_TIME = System.currentTimeMillis();
        public String LAST_SERV_ID;
        public String LAST_SERV_FAIL_ID;
        public Map<String, Long> RESOURCES_HITS = new HashMap<>();
    }
}
