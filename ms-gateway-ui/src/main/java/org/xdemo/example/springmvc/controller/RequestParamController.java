package org.xdemo.example.springmvc.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xdemo.example.springmvc.bean.C;
import org.xdemo.example.springmvc.bean.User;
import org.xdemo.example.springmvc.utils.CookieUtils;

/**
 * @author <a href="http://www.xdemo.org">xdemo.org</a>
 */
@Controller
@RequestMapping(value="/request")
public class RequestParamController {
	
	/**
	 * 最简的配置，不是用@RequestParam,效果和get2一样，默认required=false
	 * 请求方式不限
	 * @param p1
	 * @param map
	 */
	@RequestMapping(value="get0")
	public void get0(String p1,ModelMap map){
		map.addAttribute("p1", p1);//往页面传递
	}
	
	/**
	 * value="p1"表示参数名称<br>
	 * required=true表示如果没有传递参数"p1",则会报400参数异常<br>
	 * 使用void表示约定的路径，即request/get1.jsp
	 * @param p1
	 * @param map
	 */
	@RequestMapping(value="get1",method=RequestMethod.GET)
	public void get1(@RequestParam(value="p1",required=true)String p1,ModelMap map){
		map.addAttribute("p1", p1);//往页面传递
	}
	
	/**
	 * 和get1不同的是，p1这个参数不一定非得需要，即使不给这个参数，也可以正常运行<br>
	 * 返回String是视图的名称，只要将map赋值，给的值也会带到前抬
	 * @param p1
	 * @param map
	 * @return
	 */
	@RequestMapping(value="get2",method=RequestMethod.GET)
	public String get2(@RequestParam(value="p1",required=false)String p1,ModelMap map){
		map.addAttribute("p1", p1);//往页面传递
		return "request/get2";
	}
	
	/**
	 * 和get2不同的是，返回的对象是ModelAndView
	 * 表示绑定了视图和数据的对象，数据就是ModelMap中的Key-Value
	 * @param p1
	 * @param map
	 * @return
	 */
	@RequestMapping(value="get3",method=RequestMethod.GET)
	public ModelAndView get3(@RequestParam(value="p1",required=false)String p1,ModelMap map){
		map.addAttribute("p1", p1);
		return new ModelAndView("request/get2",map);
	}
	
	/**
	 * 跳转到页面
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping("userForm")
	public String userForm(HttpServletResponse response) throws NoSuchAlgorithmException{
		CookieUtils.writeCookie(response, -1, "x", "dddddddddddddd");
		return "request/userForm";
	}
	
	/**
	 * 绑定数据到User对象，支持Map,Set,List,Array等，但是需要使用下标，不是很灵活
	 * 请查看user2的写法
	 * @param user
	 * @param map
	 */
	@RequestMapping(value="user")
	public void user(User user,ModelMap map){
		map.addAttribute("user", user);
	}
	
	/**
	 * 这里可以接受List，Array，Set等，写法是一样的，注意前端写法<br>
	 * 另外这个必须要使用MappingJacksonHttpMessageConverter这个消息转换器
	 * 请看我上面的配置
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("user2")
	public String user2(@RequestBody List<User> user){
		System.out.println(user.size());
		return "";
	}
	
	/**
	 * 传递简单的字符串数组
	 * 这个方法只支持POST
	 * @param s
	 * @return
	 */
	@ResponseBody
	@RequestMapping("array")
	public String array(@RequestBody String[] s){
		System.out.println(s.length);
		return "";
	}
	
	/**
	 * 这个比较奇葩，来自一位朋友的写法，即.xxx/5,4这样的请求，SpringMVC竟然也是支持的
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="array/{id}",method=RequestMethod.GET)
	public String array2(@PathVariable("id")Long[] id){
		System.out.println(id.length);
		return "array length:"+id.length+"";
	}
	
	/**
	 * 一个表单对应多个Bean对象，这些Bean中有相同的属性，那么需要在分装他们的一个整体的对象
	 * 使之支持object.property的表达式
	 * @param c
	 */
	@ResponseBody
	@RequestMapping("complex")
	public void complexObject(C c){
		System.out.println(c.getA().getX());
		System.out.println(c.getB().getX());
	}
	
	/**
	 * 读取Cookie的值
	 * @param x
	 * @return
	 */
	@ResponseBody
	@RequestMapping("cookie")
	public String cookie(@CookieValue("x")String x){
		return x;
	}
	
}
