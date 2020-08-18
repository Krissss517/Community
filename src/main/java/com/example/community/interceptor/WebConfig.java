package com.example.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
//@EnableWebMvc 标注了这个注解SpringMVC配置就被全面接管,原来的WebmvcAutoConfiguration就会失效
//扩展SpringMVC
public class WebConfig implements WebMvcConfigurer {

    //public interface ViewResolver实现了视图解析器接口的类，我们就能把它看做视图解析器

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
