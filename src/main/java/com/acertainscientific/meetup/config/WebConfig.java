package com.acertainscientific.meetup.config;

import com.acertainscientific.meetup.interceptor.HttpResponseInterceptorHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpResponseInterceptorHandler()).addPathPatterns("/**")
                                                                        .excludePathPatterns("/sign-up")
                                                                        .excludePathPatterns("/login")
                                                                        .excludePathPatterns("/swagger-ui.html/**")
                                                                        .excludePathPatterns("/swagger-ui/**")
                                                                        .excludePathPatterns("/swagger-resources/**")
                                                                        .excludePathPatterns("/webjars/**")
                                                                        .excludePathPatterns("/v3/**")
                                                                        .excludePathPatterns("/doc.html")
                                                                        .excludePathPatterns("/doc.html/**");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}