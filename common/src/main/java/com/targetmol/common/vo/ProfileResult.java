package com.targetmol.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.targetmol.domain.Department;
import com.targetmol.domain.system.User;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProfileResult {
    private String phone;
    private String username;
    private String name;
    private Integer departmentid;
    private String departmentname;
    private Map roles;
    @JsonIgnore
    private Department department;

    public ProfileResult(User user){
        this.phone=user.getPhone();
        this.username=user.getUsername();
        this.name=user.getName();
        this.department=user.getDepartment();
        this.departmentid=user.getDepartmentid();
        this.departmentname=this.department.getName();
        Set<String> menus=new HashSet<>();
        Set<String> point=new HashSet<>();
        Set<String> api=new HashSet<>();






    }



}
