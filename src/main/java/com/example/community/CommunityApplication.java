package com.example.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication：标注这个类是SpringBoot的应用：启动类下的所有资源被导入
@SpringBootApplication
@MapperScan("com.example.community.mapper")
public class CommunityApplication {

    public static void main(String[] args) {
        //将SpringBoot应用启动
        //SpringApplication 应用入口的类，参数类：命令行参数、
        //run方法
        SpringApplication.run(CommunityApplication.class, args);
    }

}
