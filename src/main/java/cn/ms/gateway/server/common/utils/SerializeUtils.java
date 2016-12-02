package cn.ms.gateway.server.common.utils;

import java.nio.charset.Charset;

import cn.ms.gateway.server.common.exception.SerializeException;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;

public class SerializeUtils {

    private static final Charset CHARSET = Charsets.UTF_8;

    public static byte[] encode(Object stuff) {
        return JSON.toJSONString(stuff).getBytes(CHARSET);
    }

    public static Object decode(String stuff, Class<?> clazz) throws SerializeException {
        try {
            return JSON.parseObject(stuff, clazz);
        } catch (Exception e) {
            throw new SerializeException(String.format("%s decode exception", stuff));
        }
    }
}
