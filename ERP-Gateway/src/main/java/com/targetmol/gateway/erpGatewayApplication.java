package com.targetmol.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class erpGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(erpGatewayApplication.class,args);
    }
}
