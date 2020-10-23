package com.targetmol.domain.sales.Account;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

/**
 * Pi信息
 */

@Table(name="pi_info")
public class PiInfo {
    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "id")
    private Integer id;
    private String pi;       //PI名称


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPi() {
        return pi;
    }

    public void setPi(String pi) {
        this.pi = pi;
    }
}
