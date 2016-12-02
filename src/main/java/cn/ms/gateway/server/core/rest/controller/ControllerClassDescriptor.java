package cn.ms.gateway.server.core.rest.controller;

public class ControllerClassDescriptor {

    private Class<?> clazz;

    public ControllerClassDescriptor(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
