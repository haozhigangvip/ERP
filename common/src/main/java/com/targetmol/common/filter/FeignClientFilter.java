package com.targetmol.common.filter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

//微服务之间发送JWT令牌
public class FeignClientFilter  implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        if(servletRequestAttributes!=null){
            //取出当前Header,找打jwt令牌
            HttpServletRequest request=servletRequestAttributes.getRequest();
            Enumeration<String> headersNames = request.getHeaderNames();
            if(headersNames!=null){
                while (headersNames.hasMoreElements()){
                    String headersName=headersNames.nextElement();
                    String headerValue=request.getHeader(headersName);
                    //将header(JWT令牌）向下传递
                    requestTemplate.header(headersName,headerValue);
                }
            }

        }


    }
}
