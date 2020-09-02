package com.targetmol.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginFilter extends ZuulFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public String filterType() {
        //路由请求之前触发拦截
        return "pre";
    }
    //过滤器序号，越小越优先执行
    @Override
    public int filterOrder() {
        return 0;
    }

    //判断过滤器是否要执行，true执行过滤器，false过滤器无效
    @Override
    public boolean shouldFilter() {
        //共享RequestContext，上下文对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        System.out.println(request.getRequestURI());
        //需要权限校验URL
        if ("/api/sys/user/login".equalsIgnoreCase(request.getRequestURI())) {
            return false;
        }
        return true;
    }
    //过滤器内容
    @Override
    public Object run() throws ZuulException {
        //获取请求上下文对象
        RequestContext ctx = RequestContext.getCurrentContext();
        //获取request对象
        HttpServletRequest request = ctx.getRequest();
        //获取response对象
        HttpServletResponse response = ctx.getResponse();

        String authorization=request.getHeader("Authorization");
        System.out.println(authorization);
        //判断请求头是否为空或者开头是否是Bearer开头
        if(authorization!=null && authorization.startsWith("Bearer")){
            //获取token数据
            String token=authorization.replace("Bearer ","");
            //3解析token，获取claims
            Claims claims=jwtUtils.parseJwt(token);
            if(claims!=null){
                //绑定到request域
                request.setAttribute("user_claims",claims);
                return null;
            }
        }
        throw new ErpExcetpion(ExceptionEumn.PERMISSION_GRANT_FAILED);
    }

//    private void access_denied(){
//        RequestContext requestContext=RequestContext.getCurrentContext();
//        requestContext.setSendZuulResponse(false);
//        HttpServletResponse responseContent=requestContext.getResponse();
//        requestContext.setResponseStatusCode(200);
//
//    }
}
