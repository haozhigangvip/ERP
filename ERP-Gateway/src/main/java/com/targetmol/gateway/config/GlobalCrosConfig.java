package com.targetmol.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCrosConfig{
    @Bean
    public CorsFilter corsFilter(){
        //1.添加CORS配置信息
        CorsConfiguration config=new CorsConfiguration();
        //1.1 允许的域，不要写*，否则cookie无法使用
        config.addAllowedOrigin("http://127.0.0.1");
        config.addAllowedOrigin("http://localhost");
        config.addAllowedOrigin("http://192.168.3.245");
        config.addAllowedOrigin("http://192.168.200.199");


        //1.2 是否发送cookie信息
        config.setAllowCredentials(true);
        //1.3 允许请求方式
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATH");
        //1.4 允许的头信息
        config.addAllowedHeader("*");
        //1.5 有效时长(1小时）
        config.setMaxAge(3600L);

        //2 添加映射路径，我们拦截一切信息
        UrlBasedCorsConfigurationSource configSource=new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**",config);

        //3 返回新的CorsFilter
        return  new CorsFilter(configSource);

    }
}