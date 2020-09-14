package com.targetmol.domain.system.ext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.targetmol.domain.system.Permission;
import com.targetmol.domain.system.PermissionMenu;
import com.targetmol.domain.system.User;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by admin on 2018/3/20.
 */
@Data
@ToString
public class AuthUserExt extends User {

    //权限信息
    private List<Permission> permissions;

    //企业信息
    private String companyId;

}
