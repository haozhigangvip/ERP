package com.targetmol.auth.service;

import com.alibaba.fastjson.JSON;
import com.targetmol.parent.common.client.UserFeignClent;
import com.targetmol.parent.common.emums.ExceptionEumn;
import com.targetmol.parent.common.exception.ErpExcetpion;
import com.targetmol.domain.auth.ErpAuthToken;
import com.targetmol.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class AuthService {

    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;
    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private UserFeignClent userClient;

    //从头部获取JWT信息
    public String getJwtHeard(HttpServletRequest request){
        //取出头信息
        String auth=request.getHeader("Authorization");
        if(StringUtil.isEmpty(auth)==false && auth.startsWith("Bearer ")){
            auth=auth.replace("Bearer ","");
            return auth;
        }
        return null;

    }

    //用户认证申请令牌，将令牌存储到redis
    public ErpAuthToken login(String username, String password, String clientId, String clientSecret,String code ) {

        //请求spring security申请令牌
        ErpAuthToken erpAuthToken = this.applyToken(username, password, clientId, clientSecret,code);

        if(erpAuthToken == null){
            throw  new ErpExcetpion(ExceptionEumn.USERNAMEANDPASSWORD_ISNOT_MATCH);
        }
        //用户身份令牌
        String access_token = erpAuthToken.getAccess_token();
        //存储到redis中的内容
        String jsonString = JSON.toJSONString(erpAuthToken);
        //将令牌存储到redis
        boolean result = this.saveToken(access_token, jsonString, tokenValiditySeconds);
        if (!result) {
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_APPLY_FOR_TOKEN);
        }
        return erpAuthToken;

    }


    //存储到令牌到redis
    private boolean saveToken(String access_token,String content,long ttl){
        String key = "user_token:" + access_token;
        stringRedisTemplate.boundValueOps(key).set(content,ttl, TimeUnit.SECONDS);
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire>0;
    }

    //删除token
    public boolean delToken(String access_token){
        String key = "user_token:" + access_token;
        stringRedisTemplate.delete(key);
        return true;
    }

    //从redis查询令牌
    public ErpAuthToken getUserToken(String token){
        String key = "user_token:" + token;
        //从redis中取到令牌信息
        String value = stringRedisTemplate.opsForValue().get(key);
        //转成对象
        try {
            ErpAuthToken authToken = JSON.parseObject(value, ErpAuthToken.class);
            //用户身份令牌

            //存储到redis中的内容
            String jsonString = JSON.toJSONString(authToken);
            this.saveToken(token, jsonString, tokenValiditySeconds);
            //返回JWT令牌
            return authToken;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    //申请令牌
    private ErpAuthToken applyToken(String username, String password, String clientId, String clientSecret,String code){

        if(code==null){
            userClient.refreshCode(username);
        }

        //从eureka中获取认证服务的地址（因为spring security在认证服务中）
        //从eureka中获取认证服务的一个实例的地址
        ServiceInstance serviceInstance = loadBalancerClient.choose("AUTH-SERVICE");
        //此地址就是http://ip:port
        if(serviceInstance==null){
            throw new ErpExcetpion(ExceptionEumn.NETWOK_IS_BUSY);
        }
        URI uri = serviceInstance.getUri();
        //令牌申请的地址 http://localhost:10086/auth/oauth/token
        String authUrl = uri+ "/oss/oauth/token";
        //定义header
        LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        String httpBasic = getHttpBasic(clientId, clientSecret);
        header.add("Authorization",httpBasic);

        //定义body
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username",username);
        body.add("password",password);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, header);
        //String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables

        //设置restTemplate远程调用时候，对400和401不让报错，正确返回数据
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if(response.getRawStatusCode()!=400 && response.getRawStatusCode()!=401){
                    super.handleError(response);
                }
            }
        });

        ResponseEntity<Map> exchange = restTemplate.exchange(authUrl, HttpMethod.POST, httpEntity, Map.class);

        //申请令牌信息
        Map bodyMap = exchange.getBody();
        if(bodyMap == null ||
            bodyMap.get("access_token") == null ||
                bodyMap.get("refresh_token") == null ||
                bodyMap.get("jti") == null){
            return null;
        }
        ErpAuthToken erpAuthToken = new ErpAuthToken();
        erpAuthToken.setAccess_token((String) bodyMap.get("jti"));//用户身份令牌
        erpAuthToken.setRefresh_token((String) bodyMap.get("refresh_token"));//刷新令牌
        erpAuthToken.setJwt_token((String) bodyMap.get("access_token"));//jwt令牌
        return erpAuthToken;
    }



    //获取httpbasic的串
    private String getHttpBasic(String clientId,String clientSecret){
        String string = clientId+":"+clientSecret;
        //将串进行base64编码
        byte[] encode = Base64Utils.encode(string.getBytes());
        return "Basic "+new String(encode);
    }

    //钉钉免登
    public User autoLoginByDD(String code) {
        //获取钉钉accessToken
        String corpId="dingfbc7fad2d294d37e";
        String urlDomain="https://oapi.dingtalk.com/";
        String appKey="dingaqxyes7ba9dcou5j";
        String appSecret="YX4o7hOPm_9K8XZdfCPcb2X7zCw2IzyWVNpeXcID6v8u_bt5FHW-H2T48kxFfpxH";
        String url=urlDomain+"gettoken?appkey="+appKey+"&appsecret="+appSecret;
        Map<String,Object> result=restTemplate.getForObject(url,Map.class);
        if(result==null || (Integer)result.get("errcode")!=0){
            return null;
        }
        //获取钉钉ID
        String access_token=(String)result.get("access_token");
        url=urlDomain+"user/getuserinfo?access_token="+access_token+"&code="+code;
        result=restTemplate.getForObject(url,Map.class);
        if(result==null || (Integer)result.get("errcode")!=0){
            return null;
        }
        String userId=(String)result.get("userid");
        //通过钉钉ID查询用户
           User  user= userClient.findByDdId(userId,code);
        return user;
    }


}
