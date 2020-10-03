package com.targetmol.parent.common.controller;


import com.targetmol.parent.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected Claims claims;

    @Autowired
    private JwtUtils jwtUtils;

    @ModelAttribute
    public void setResAnReq(HttpServletRequest request, HttpServletResponse response) {

        this.request = request;
        this.response = response;

        String authorization = request.getHeader("AuthorizationToken");
        if(authorization!=null){
            Claims claims = jwtUtils.parseJwt(authorization);
            if (claims != null) {
                this.claims = claims;
            }
        }


    }

}