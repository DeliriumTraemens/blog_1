package com.nick.blog_1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer  {
	
	@Value("${upload.path}")
	private String uploadPath;
	@Value("${upload.path2}")
	private String uploadPath2;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/picsUp/**").addResourceLocations("file:///"+uploadPath+"/");
	
	}
}
