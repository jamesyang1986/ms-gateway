package org.xdemo.example.springmvc.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author <a href="http://www.xdemo.org">xdemo.org</a>
 */
@SuppressWarnings("deprecation")
@EnableWebMvc// 启用SpringMVC
@ComponentScan(basePackages = "org.xdemo.example.springmvc")// 配置包扫描路径
@Configuration// 启用注解式配置
//继承WebMvcConfigurerAdapter可以是我们可以重写一些资源或者一些处理器
public class AppConfig extends WebMvcConfigurerAdapter {

	/**
	 * 设置资源路径
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/").setCachePeriod(31556926);
	}

	/**
	 * 设置默认的Servlet请求处理器
	 */
	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * 设置视图解析器，以及页面路径
	 * 
	 * @return
	 */
	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	/**
	 * 配置消息转换器
	 */
	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		converters.add(converter());
		
	}
	
	/**
	 * JSON格式的支持，这个很重要，只有加上这个JSON的消息转换器，才能够支持JSON格式数据的绑定
	 * @return
	 */
	@Bean
	public MappingJacksonHttpMessageConverter converter() {
		MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
		return converter;
	}

}
