package com.targetmol.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.utils.JsonUtils;
import com.targetmol.common.utils.JwtUtils;
import com.targetmol.common.vo.ExceptionResult;
import com.targetmol.gateway.config.DataFilterConfig;
import com.targetmol.gateway.service.AuthService;
import com.targetmol.gateway.utils.PathUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@Component
public class LoginFilter extends ZuulFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private DataFilterConfig dataFilterConfig;
    @Autowired
    private AuthService authService;
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
        //读取白名单
        for (String pathPattern : dataFilterConfig.getUserLoginPath()) {
            System.out.println(pathPattern);
            if (PathUtil.isPathMatch(pathPattern,request.getRequestURI())) {
                return false;
            }
        }
        return true;
//        return false;
    }
    //过滤器内容
    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        //获取request对象
        HttpServletRequest request = ctx.getRequest();
        //取cookie中的身份令牌
        String token=authService.getTokenFromCookie(request);
        if(StringUtil.isEmpty(token)){
//            responseError(ctx,ExceptionEumn.PERMISSION_GRANT_FAILED);
            return null;
        }
        //从hearder中取access_token
        String userJwt=authService.getJwtHeard(request);
        if(StringUtil.isEmpty(userJwt)){
            responseError(ctx,ExceptionEumn.PERMISSION_GRANT_FAILED);
            return null;
        }
        //从redis取出JWT令牌
        String redisJwt=authService.getRedisJwt(token);
        if(StringUtil.isEmpty(redisJwt)||!userJwt.equals(redisJwt)){
            responseError(ctx,ExceptionEumn.PERMISSION_GRANT_FAILED);
            return null;
        }


        return null;
    }

    //拒绝访问
    private void responseError(RequestContext ctx,ExceptionEumn eumn) {
        ctx.setSendZuulResponse(false);
        HttpServletResponse response = ctx.getResponse();
        ExceptionResult result=new ExceptionResult(eumn);
        ctx.setResponseBody(JsonUtils.serialize(result));
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json;charset=utf-8");
    }


}


