/**
 * Written by xdemo.org
 */
package org.xdemo.example.springmvc.utils;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Goofy <a href="http://www.xdemo.org">xdemo.org</a>
 * 
 */
public class CookieUtils {

	public static void writeCookie(HttpServletResponse response, int maxAge,
			String key,String value) throws NoSuchAlgorithmException {

		Cookie cookie = new Cookie(key, value);

		cookie.setMaxAge(maxAge);

		cookie.setPath("/");

		response.addCookie(cookie);

	}

	public static void writeCookie(HttpServletResponse response, String key,
			String value) {
		Cookie cookie = new Cookie(key, value);

		cookie.setMaxAge(-1);

		cookie.setPath("/");

		response.addCookie(cookie);
	}

	public static Cookie[] readCookie(HttpServletRequest request) {
		return request.getCookies();
	}

}
