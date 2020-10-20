package com.targetmol.domain.sales;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

/**
 * 客户类型表
 */
@Table(name="account_type")
public class AccountType {
    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "id")
    private Integer id;
    private String type;       //客户类型

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
