package com.capstone.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.capstone.global.logger.LoggerInterceptor;



@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	private String address ="http://172.26.30.76:3000";
	private String localhost = "http://localhost:3000";
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
       	.allowedOrigins(localhost,address)
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
        
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor())
                .addPathPatterns("/**") // 해당 경로에 접근하기 전에 인터셉터가 가로챈다.
       
                ;
    }
    
}
