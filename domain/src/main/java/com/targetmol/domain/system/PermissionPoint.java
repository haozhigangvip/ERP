package com.targetmol.domain.system;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="permissionpoint")
public class PermissionPoint {
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(insertable = false,updatable =false,name = "id")
    private Integer id;
    private Integer preid;      //主权限表ID
    private String pointclass;   //按钮样式
    private String pointicon;    //按钮图标
    private String pointstatus;  //按钮状态


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPointclass() {
        return pointclass;
    }

    public void setPointclass(String pointclass) {
        this.pointclass = pointclass;
    }

    public String getPointicon() {
        return pointicon;
    }

    public void setPointicon(String pointicon) {
        this.pointicon = pointicon;
    }

    public String getPointstatus() {
        return pointstatus;
    }

    public void setPointstatus(String pointstatus) {
        this.pointstatus = pointstatus;
    }

    public Integer getPreid() {
        return preid;
    }

    public void setPreid(Integer preid) {
        this.preid = preid;
    }
}
