package cn.ms.gateway.server.common.utils;

import java.lang.reflect.Constructor;

public class ClassUtil {

	public static boolean hasDefaultConstructor(Class<?> clazz) {
		for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
			if (!constructor.isVarArgs()) {
				return true;
			}
		}

		return false;
	}
}
