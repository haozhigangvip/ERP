package com.targetmol.common.utils;


import com.targetmol.domain.system.ext.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@ConfigurationProperties("jwt.config")  //读取配置文件中key与ttl值
public class JwtUtils {
    //设置签名的私钥
    private String key;

    //设置签名的实效时间
    private Long ttl;

    private static final String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhViTZjVzOIDNpApClDcK\n" +
            "+/sBmDLteiiLXVK1E7aMsHaj0fed/KVjTSq24oKPvvvffQ6HC1Q7i3d49oJ9cEp5\n" +
            "aBz3hSVHnCkbWOYWZgDHPozEOv4tBBNqJevjBuCz7+Tm0TrjLXxHabPZjxKjrMf9\n" +
            "j3QVLZNGVCFP+HjxiJIx0ZU7gKMGtPuSXFgxDw0Syv6lVT2ECvthoabSHeETTFSX\n" +
            "KfztOIu5zCB5eKlzSuOCxLvXh+C7S6ePDv/j9PdJ8hmOwWiOLNffmP8I3xQ7WBDU\n" +
            "2c19yiSECZn+8vHtkGq2SoPl//mHoL95aQYk7sa+g/4Bo0JU6M/nKxLsWnIbhreL\n" +
            "+wIDAQAB\n" +
            "-----END PUBLIC KEY-----";


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
        result= Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();//设置私钥信息
        return result;
    }
    //读取公钥
    public  String getPubKey(){
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
            InputStreamReader inputStreamReader = new
                    InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            return null;
        }

    }

    //获取用户信息
    public  AuthUser getUserInfo(String jwtToken){
        if(StringUtil.isEmpty(jwtToken)==false){
            try {
                Jwt jwt=  JwtHelper.decodeAndVerify(jwtToken, new RsaVerifier(PUBLIC_KEY));
                String claims = jwt.getClaims();
                    AuthUser user=JsonUtils.parse(claims, AuthUser.class);
                user.setJwtToken(jwtToken);
                return user;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }
        return null;

    }

}
