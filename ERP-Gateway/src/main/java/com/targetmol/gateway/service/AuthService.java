package com.targetmol.gateway.service;

import com.targetmol.common.utils.CookieUtil;
import com.targetmol.common.utils.JsonUtils;
import com.targetmol.domain.auth.ErpAuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    //从头信息取出JWT令牌
    public String getJwtHeard(HttpServletRequest request){
        //取出头信息
        String auth=request.getHeader("Authorization");
        if(StringUtil.isEmpty(auth)==false && auth.startsWith("Bearer ")){
            auth=auth.replace("Bearer ","");
            return auth;
        }
         return null;
    }


    //cookie取出token
    //查询身份令牌
    public String getTokenFromCookie(HttpServletRequest request){
        Map<String, String> cookieMap = CookieUtil.readCookie(request, "uid");
        String access_token = cookieMap.get("uid");
        if(StringUtil.isEmpty(access_token)){
            return null;
        }
        return access_token;
    }

    //从redis取出JWT令牌
    //查询令牌的有效期
    public String getRedisJwt(String access_token){
        //key
        String key = "user_token:"+access_token;
        String result=stringRedisTemplate.opsForValue().get(key);
        if(result==null){
            return null;
        }
        ErpAuthToken erpAuthToken=JsonUtils.parse(result, ErpAuthToken.class);
        if(erpAuthToken!=null){
            return erpAuthToken.getJwt_token();
        }
        return null;
    }

}
