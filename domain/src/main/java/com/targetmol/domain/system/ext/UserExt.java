package com.targetmol.domain.system.ext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.targetmol.domain.Department;
import com.targetmol.domain.system.Permission;
import com.targetmol.domain.system.Role;
import com.targetmol.domain.system.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * Created by admin on 2018/3/20.
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserExt extends User {
    private String password;
    private Department department;
    private List<Role> roles;

}
