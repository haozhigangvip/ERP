package com.targetmol.auth.controller;

import com.targetmol.auth.service.AuthService;
import com.targetmol.auth.service.UserJwt;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.utils.CookieUtil;
import com.targetmol.common.utils.JsonUtils;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.auth.ErpAuthToken;
import com.targetmol.domain.auth.LoginRequest;
import com.targetmol.domain.system.ext.AuthUser;
import com.targetmol.domain.system.ext.AuthUserExt;
import com.targetmol.domain.system.ext.UserExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.util.StringUtil;

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
        return ResponseEntity.ok(ResultMsg.success(erpAuthToken.getAccess_token()));
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
    @PostMapping("/logout")
    public ResponseEntity logout() {
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
    @PostMapping("/userjwt")
    public ResponseEntity<ResultMsg> getUserJwt(@RequestBody Map<String,String > mp){
        //取出cookie中的身份令牌
        String uid=null;
        if(mp!=null){
            uid= mp.get("access_token");
        }
        if(uid==null){
            uid=getTokenFormCookie();
        }

        String jwt_token =null;
        if(StringUtil.isEmpty(uid)==false){
            //拿身份令牌从redis中取出jwt令牌
            ErpAuthToken erpAuthToken=authService.getUserToken(uid);
            if(erpAuthToken!=null){
                jwt_token=erpAuthToken.getJwt_token();
                //重新更新cookie
                saveCookie(uid);
            }
           }
        //将JWT令牌返回给用户
//        return ResponseEntity.ok(ResultMsg.success(jwt_token));
        //通过JWT_TOKEN获取用户信息
        Jwt jwt= getUserInfo(jwt_token);
        String claims = jwt.getClaims();
        AuthUser user= JsonUtils.parse(claims, AuthUser.class);
        user.setJwtToken(jwt_token);
        return ResponseEntity.ok(ResultMsg.success(user));

    }

    private Jwt getUserInfo(String jwt_token){
        String publickey="-----BEGIN PUBLIC KEY-----\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhViTZjVzOIDNpApClDcK\n" +
                "+/sBmDLteiiLXVK1E7aMsHaj0fed/KVjTSq24oKPvvvffQ6HC1Q7i3d49oJ9cEp5\n" +
                "aBz3hSVHnCkbWOYWZgDHPozEOv4tBBNqJevjBuCz7+Tm0TrjLXxHabPZjxKjrMf9\n" +
                "j3QVLZNGVCFP+HjxiJIx0ZU7gKMGtPuSXFgxDw0Syv6lVT2ECvthoabSHeETTFSX\n" +
                "KfztOIu5zCB5eKlzSuOCxLvXh+C7S6ePDv/j9PdJ8hmOwWiOLNffmP8I3xQ7WBDU\n" +
                "2c19yiSECZn+8vHtkGq2SoPl//mHoL95aQYk7sa+g/4Bo0JU6M/nKxLsWnIbhreL\n" +
                "+wIDAQAB\n" +
                "-----END PUBLIC KEY-----" ;
        if(StringUtil.isEmpty(jwt_token)){
            throw new ErpExcetpion(ExceptionEumn.PERMISSION_GRANT_FAILED);
        }
        Jwt jwt=  JwtHelper.decodeAndVerify(jwt_token, new RsaVerifier(publickey));
        return jwt ;
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
