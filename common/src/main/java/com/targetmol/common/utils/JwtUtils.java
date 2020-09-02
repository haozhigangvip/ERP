package com.targetmol.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
@Component
@ConfigurationProperties("jwt.config")  //读取配置文件中key与ttl值
public class JwtUtils {
    //设置签名的私钥
    private String key;

    //设置签名的实效时间
    private Long ttl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    //设置认证Token
    public String createJwt(String id, String username, Map<String ,Object> map){
        //1.设置实效时间
        Long now=System.currentTimeMillis();    //当前毫秒数
        Long exp=now+ttl;

        //2.创建JwtBuilder
        JwtBuilder jwtBuilder= Jwts.builder()
                .setId(id)    //设置ID，ID为用户ID
                .setSubject(username)      //设置项目名为用户名，
                .setIssuedAt(new Date()) //设置签名时间
                .signWith(SignatureAlgorithm.HS256,key); //加密算法, key为自定义私钥信息
        //3.根据map设置claims
        for(Map.Entry<String,Object> entry:map.entrySet()){
            jwtBuilder.claim(entry.getKey(),entry.getValue());
        }
        //jwtBuilder.setClaims(map);
        jwtBuilder.setExpiration(new Date(exp));  //设置失效时间

        //4. 创建token
        return jwtBuilder.compact();
    }

    //解析Token
    public Claims parseJwt(String token){
        Claims result=null;
        try {
           result= Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();//设置私钥信息
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

}
