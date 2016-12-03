package cn.ms.gateway.motan;

public class MotanDemoServiceImpl implements MotanDemoService {

    public String hello(String name) {
        System.out.println(name);
        return "Hello " + name + "!";
    }

}
