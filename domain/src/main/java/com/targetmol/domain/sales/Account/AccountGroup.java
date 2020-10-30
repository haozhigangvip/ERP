package com.targetmol.domain.sales.Account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

/**
        * 客户分组
        */

@Table(name="account_goup")
public class AccountGroup {
    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "id")
    private Integer id;
    private Integer code;    //组代码
    private String name;    //组名
    @Transient
    @JsonIgnore
    private String pcode;     //组的父代码

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getCode() {
                return code;
            }

            public void setCode(Integer code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
