package com.targetmol.auth.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.targetmol.auth.service.AuthService;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.utils.CookieUtil;
import com.targetmol.common.utils.JwtUtils;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.auth.ErpAuthToken;
import com.targetmol.domain.auth.LoginRequest;
import com.targetmol.domain.system.ext.AuthUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
@RequestMapping("/")
public class  AuthController {

    @Value("${auth.clientId}")
    String clientId;
    @Value("${auth.clientSecret}")
    String clientSecret;
    @Value("${auth.cookieDomain}")
    String cookieDomain;
    @Value("${auth.cookieMaxAge}")
    int cookieMaxAge;

    @Autowired
    private AuthService authService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/login")
    public ResponseEntity<ResultMsg> login(@RequestBody LoginRequest loginRequest) {
        if(loginRequest == null || StringUtils.isEmpty(loginRequest.getUsername())){
            throw new ErpExcetpion(ExceptionEumn.LOGIN_USERNAME_IS_NULL);
        }
        if(loginRequest == null || StringUtils.isEmpty(loginRequest.getPassword())){
            throw new ErpExcetpion(ExceptionEumn.LOGIN_PASSWORD_IS_NULL);
        }
        //账号
        String username = loginRequest.getUsername();
        //密码
        String password = loginRequest.getPassword();

        //申请令牌
        ErpAuthToken erpAuthToken =  authService.login(username,password,clientId,clientSecret);

        //用户身份令牌
        String access_token = erpAuthToken.getAccess_token();
        //将令牌存储到cookie
        this.saveCookie(access_token);
        Map<String,String> mp=new HashMap<>();
        mp.put("access_token",erpAuthToken.getAccess_token());
        mp.put("jwt_token",erpAuthToken.getJwt_token());
        return ResponseEntity.ok(ResultMsg.success(mp));
    }

    @GetMapping("/autologin/dingTalk")
    public ResponseEntity<ResultMsg> login(@RequestParam("code") String code) {

        authService.autoLoginByDD(code);

        return ResponseEntity.ok(ResultMsg.success());
    }


    //将令牌存储到cookie
    private void saveCookie(String token){

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //HttpServletResponse response,String domain,String path, String name, String value, int maxAge,boolean httpOnly
        CookieUtil.addCookie(response,cookieDomain,"/","uid",token,cookieMaxAge,false);
    }

    //从cookie中删除token
    private void clearCookie(String token){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //HttpServletResponse response,String domain,String path, String name, String value, int maxAge,boolean httpOnly
        //将cookie有效期改为0
        CookieUtil.addCookie(response,cookieDomain,"/","uid",token,0,false);
    }

    //登出
    @GetMapping("/logout")
    public ResponseEntity<ResultMsg> logout() {
        //从cookie中获取token
        String uid=getTokenFormCookie();
        //删除redis中的token
        authService.delToken(uid);
        //清除token
        clearCookie(uid);
        //返回成功
        return ResponseEntity.ok(ResultMsg.success());
    }



    //获取JWT令牌
    @GetMapping("/ujwt")
    public ResponseEntity<ResultMsg> getUserJwt(HttpServletRequest request){
        String jwt_token=null;
        //从hearder中取jwt_token
        String userJwt=authService.getJwtHeard(request);
        AuthUser user=jwtUtils.getUserInfo(userJwt);
        if(user==null){
            throw  new ErpExcetpion(ExceptionEumn.PERMISSION_GRANT_FAILED);
        }

        if(user.getJti()!=null){
            //拿身份令牌从redis中取出jwt令牌
            ErpAuthToken erpAuthToken=authService.getUserToken(user.getJti());
            if(erpAuthToken==null){
                throw new ErpExcetpion(ExceptionEumn.PERMISSION_GRANT_FAILED);
            }
             //重新更新cookie
             saveCookie(user.getJti());
           }

        //返回JWT_TOKEN获取用户信息
        return ResponseEntity.ok(ResultMsg.success(user));
    }


    //取出cookie中的身份令牌
    private String getTokenFormCookie(){
        HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String,String> map=CookieUtil.readCookie(request,"uid");
        if(map!=null&&map.get("uid")!=null){
            String uid=map.get("uid");
            return uid;
        }
        return null;

    }
}
