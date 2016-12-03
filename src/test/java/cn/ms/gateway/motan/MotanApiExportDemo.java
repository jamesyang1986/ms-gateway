package cn.ms.gateway.motan;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.config.ProtocolConfig;
import com.weibo.api.motan.config.RegistryConfig;
import com.weibo.api.motan.config.ServiceConfig;
import com.weibo.api.motan.util.MotanSwitcherUtil;

public class MotanApiExportDemo {

    public static void main(String[] args) throws InterruptedException {
        ServiceConfig<MotanDemoService> motanDemoService = new ServiceConfig<MotanDemoService>();

        // 设置接口及实现类
        motanDemoService.setInterface(MotanDemoService.class);
        motanDemoService.setRef(new MotanDemoServiceImpl());

        // 配置服务的group以及版本号
        motanDemoService.setGroup("motan-demo-rpc");
        motanDemoService.setVersion("1.0");

        // 配置注册中心直连调用
        // RegistryConfig directRegistry = new RegistryConfig();
        // directRegistry.setRegProtocol("local");
        // directRegistry.setCheck("false"); //不检查是否注册成功
        // motanDemoService.setRegistry(directRegistry);

        // 配置ZooKeeper注册中心
        RegistryConfig zookeeperRegistry = new RegistryConfig();
        zookeeperRegistry.setRegProtocol("zookeeper");
        zookeeperRegistry.setAddress("127.0.0.1:2181");
        motanDemoService.setRegistry(zookeeperRegistry);

        // 配置RPC协议
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setId("motan");
        protocol.setName("motan");
        motanDemoService.setProtocol(protocol);

        motanDemoService.setExport("motan:8002");
        motanDemoService.export();

        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);

        System.out.println("server start...");
    }

}
