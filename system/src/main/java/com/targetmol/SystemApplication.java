package com.targetmol;

import com.targetmol.common.filter.FeignClientFilter;
import com.targetmol.common.utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages= {"com.targetmol.system.dao"})
@EnableDiscoveryClient
@EnableFeignClients
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class,args);
    }

    @Bean
    public JwtUtils jwtUtils(){
        return new JwtUtils();
    }



    //自动将Header专递到它所调用的其它微服务中去（包括JWT令牌）
    @Bean
    public FeignClientFilter getFeignClientFilter(){
        return new FeignClientFilter();
    }
}
