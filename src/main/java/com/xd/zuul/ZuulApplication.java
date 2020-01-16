package com.xd.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by 张立军 on 2020/1/14.
 * Project Name : SpringCloud
 * Package Name : com.xd.zuul
 */
@SpringBootApplication
@EnableZuulProxy //开启Zuul功能
@EnableEurekaClient //表示向服务端进行服务注册
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class,args);
    }
}
