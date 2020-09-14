package com.targetmol.domain.system;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Date;

@Table(name = "role_permission")
public class Role_Permission {
    @Id
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "id")
    private Integer id;
    @Column(name="role_id")
    private Integer roleId;
    @Column(name="permission_id")
    private Integer pid;
    @Column(insertable = false,updatable = false)
    private Date creatime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }
}
