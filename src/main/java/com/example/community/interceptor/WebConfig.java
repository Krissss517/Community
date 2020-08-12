package com.example.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    //实现注入自定义的
    @Autowired
    LocalInterceptor localInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //实现WebMvcConfigurer接口修改
        registry.addInterceptor(localInterceptor).addPathPatterns("/**")
                .excludePathPatterns(Arrays.asList("/static/**","/templates/**"));

    }


}
