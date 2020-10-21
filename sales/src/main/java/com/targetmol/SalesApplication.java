package com.targetmol;
import com.targetmol.common.filter.FeignClientFilter;
import com.targetmol.common.utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages= {"com.targetmol.sales.dao"})
@EnableDiscoveryClient
@EnableFeignClients
public class SalesApplication {
    public static void main(String[] args) {
        SpringApplication.run(SalesApplication.class,args);
    }

    @Bean
    public JwtUtils jwtUtils(){
        return new JwtUtils();
    }

    @Bean
    public FeignClientFilter feignClientFilter(){
        return new FeignClientFilter();
    }

}

