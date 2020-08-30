package com.targetmol.domain.system;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="permissionapi")
public class PermissionApi {
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(insertable = false,updatable =false,name = "id")
    private Integer id;
    private Integer preid;      //主权限表ID
    private String apiurl;     //请求路径
    private String apimethod; //请求方法，get\post\put\delete
    private Integer apilevel; //权限等级，1为通用接口权限，2为需校验接口权限


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApiurl() {
        return apiurl;
    }

    public void setApiurl(String apiurl) {
        this.apiurl = apiurl;
    }

    public String getApimethod() {
        return apimethod;
    }

    public void setApimethod(String apimethod) {
        this.apimethod = apimethod;
    }

    public Integer getApilevel() {
        return apilevel;
    }

    public void setApilevel(Integer apilevel) {
        this.apilevel = apilevel;
    }

    public Integer getPreid() {
        return preid;
    }

    public void setPreid(Integer preid) {
        this.preid = preid;
    }
}
