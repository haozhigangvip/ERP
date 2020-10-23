package com.targetmol.domain.sales.Account;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

/**
 * 退货原因
 */

@Table(name="return_reasons")
public class ReturnReasons {
    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "id")
    private Integer id;
    private String reasons;       //退回原因


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }
}
