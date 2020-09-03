package com.targetmol.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix="data-filter") //接收application.yml中的myProps下面的属性
public class DataFilterConfig {
    private List<String> authPath=new ArrayList<>();
    private  List<String> userLoginPath=new ArrayList<>();

    public List<String> getAuthPath() {
        return authPath;
    }

    public void setAuthPath(List<String> authPath) {
        this.authPath = authPath;
    }

    public List<String> getUserLoginPath() {
        return userLoginPath;
    }

    public void setUserLoginPath(List<String> userLoginPath) {
        this.userLoginPath = userLoginPath;
    }
}
