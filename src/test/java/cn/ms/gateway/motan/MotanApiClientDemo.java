package cn.ms.gateway.motan;

import com.weibo.api.motan.config.ProtocolConfig;
import com.weibo.api.motan.config.RefererConfig;
import com.weibo.api.motan.config.RegistryConfig;

public class MotanApiClientDemo {

    public static void main(String[] args) {
        RefererConfig<MotanDemoService> motanDemoServiceReferer = new RefererConfig<MotanDemoService>();

        // 设置接口及实现类
        motanDemoServiceReferer.setInterface(MotanDemoService.class);

        // 配置服务的group以及版本号
        motanDemoServiceReferer.setGroup("motan-demo-rpc");
        motanDemoServiceReferer.setVersion("1.0");
        motanDemoServiceReferer.setRequestTimeout(300);

        // 配置注册中心直连调用
        // RegistryConfig directRegistry = new RegistryConfig();
        // directRegistry.setRegProtocol("local");
        // motanDemoServiceReferer.setRegistry(directRegistry);

        // 配置ZooKeeper注册中心
        RegistryConfig zookeeperRegistry = new RegistryConfig();
        zookeeperRegistry.setRegProtocol("zookeeper");
        zookeeperRegistry.setAddress("127.0.0.1:2181");
        motanDemoServiceReferer.setRegistry(zookeeperRegistry);

        // 配置RPC协议
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setId("motan");
        protocol.setName("motan");
        motanDemoServiceReferer.setProtocol(protocol);
        // motanDemoServiceReferer.setDirectUrl("localhost:8002");  // 注册中心直连调用需添加此配置

        // 使用服务
        MotanDemoService service = motanDemoServiceReferer.getRef();
        System.out.println(service.hello("motan"));

        System.exit(0);
    }
}
