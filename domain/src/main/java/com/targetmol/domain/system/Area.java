package com.targetmol.domain.system;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="area")
public class Area implements Serializable {
    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "id")
    private Integer id;
    private String language;
    private String name;
    private String code;
    private String type;
    private String isedit;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsedit() {
        return isedit;
    }

    public void setIsedit(String isedit) {
        this.isedit = isedit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}