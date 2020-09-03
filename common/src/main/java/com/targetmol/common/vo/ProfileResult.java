package com.targetmol.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.targetmol.domain.Department;
import com.targetmol.domain.system.Permission;
import com.targetmol.domain.system.Role;
import com.targetmol.domain.system.User;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
@Setter
@Getter
public class ProfileResult {
    private String phone;
    private String username;
    private String name;
    private Integer departmentid;
    private String departmentname;
    private Map<String,Object> roles = new HashMap<>();
    @JsonIgnore
    private Department department;


    public ProfileResult(User user, List<Permission> list){
        this.phone=user.getPhone();
        this.username=user.getUsername();
        this.name=user.getName();
        this.department=user.getDepartment();
        this.departmentid=user.getDepartmentid();
        this.departmentname=this.department.getName();
        Set<String> menus=new HashSet<>();
        Set<String> points=new HashSet<>();
        Set<String> apis=new HashSet<>();

        for (Permission perm : list) {
            String code = perm.getCode();
            if(perm.getType() == 1) {
                menus.add(code);
            }else if(perm.getType() == 2) {
                points.add(code);
            }else {
                apis.add(code);
            }
        }
        this.roles.put("menus",menus);
        this.roles.put("points",points);
        this.roles.put("apis",apis);
    }

    public ProfileResult(User user) {
        this.phone = user.getPhone();
        this.username = user.getUsername();
        this.departmentid=user.getDepartmentid();
        this.departmentname=user.getDepartment().getName();
        Set<Role> roles = new HashSet<>(user.getRoles());
        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();

        for (Role role : roles) {
            List<Map<String ,Object>> perms = role.getPermissions();
            for (Map<String,Object> perm: perms) {
                String code = (String)perm.get("code");
                if((Integer)perm.get("type") == 1) {
                    menus.add(code);
                }else if((Integer)perm.get("type") == 2) {
                    points.add(code);
                }else {
                    apis.add(code);
                }
            }
        }

        this.roles.put("menus",menus);
        this.roles.put("points",points);
        this.roles.put("apis",apis);
    }





}




