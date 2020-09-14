package com.targetmol.domain.system;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Date;

@Table(name="`user_role`")
public class User_ROLE {
    @Id
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "id")
    private Integer id;
    @Column(name="user_id")
    private Integer userId;
    @Column(name="role_id")
    private Integer roleId;

    @Column(insertable = false,updatable = false)
    private Date creatime;
    private String creator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
