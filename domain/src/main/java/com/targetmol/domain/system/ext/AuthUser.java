package com.targetmol.domain.system.ext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class AuthUser {
    public AuthUser(){

    }
    private Integer uid;
    private String username;
    private String name;
    private String userpic;
    private String[] scope;
    private String jwtToken;
    private String jti;

    @JsonIgnore
    private String utype;
    @JsonIgnore
    private Integer exp;
    @JsonIgnore
    private String client_id;

}
