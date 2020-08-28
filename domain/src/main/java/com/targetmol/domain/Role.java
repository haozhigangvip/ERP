package com.targetmol.domain;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="role")
public class Role {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(insertable = false,updatable =false,name = "rid")
    private Integer rid;

    private String name;
    private String nonte;


    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNonte() {
        return nonte;
    }

    public void setNonte(String nonte) {
        this.nonte = nonte;
    }
}
