package com.targetmol.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.targetmol.parent.common.emums.ExceptionEumn;
import com.targetmol.parent.common.utils.JsonUtils;
import com.targetmol.parent.common.utils.JwtUtils;
import com.targetmol.parent.common.vo.ExceptionResult;
import com.targetmol.domain.auth.ErpAuthToken;
import com.targetmol.domain.system.ext.AuthUser;
import com.targetmol.gateway.config.DataFilterConfig;
import com.targetmol.gateway.service.AuthService;
import com.targetmol.gateway.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.util.StringUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@Component
public class LoginFilter extends ZuulFilter {
    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;
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

        //从hearder中取jwt_token
        String userJwt=authService.getJwtHeard(request);
        if(StringUtil.isEmpty(userJwt)){
            responseError(ctx,ExceptionEumn.PERMISSION_GRANT_FAILED);
            return null;
        }

         AuthUser user=jwtUtils.getUserInfo(userJwt);
        if(user==null){
            responseError(ctx,ExceptionEumn.PERMISSION_GRANT_FAILED);
            return null;
        }


        //从redis取出JWT令牌
        ErpAuthToken erpAuthToken=authService.getRedisInfo(user.getJti());
        if(erpAuthToken==null ||StringUtil.isEmpty(erpAuthToken.getJwt_token())||!userJwt.equals(erpAuthToken.getJwt_token())){
            responseError(ctx,ExceptionEumn.PERMISSION_GRANT_FAILED);
            return null;
        }
        //再次保存到Redis中，重新刷新过期时间
        String jsonString = JSON.toJSONString(erpAuthToken);
        authService.saveToken(user.getJti(),jsonString,tokenValiditySeconds);
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


