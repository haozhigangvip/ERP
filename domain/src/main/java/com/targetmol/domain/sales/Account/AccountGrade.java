package com.targetmol.domain.sales.Account;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

/**
 * 客户等级表
 */
@Table(name="account_grade")
public class AccountGrade {
    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "id")
    private Integer id;
    private Integer contvip;    //客户等级
    private String vipname;       //等级名称
    private Double discount;   //扣率

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContvip() {
        return contvip;
    }

    public void setContvip(Integer contvip) {
        this.contvip = contvip;
    }

    public String getVipname() {
        return vipname;
    }

    public void setVipname(String vipname) {
        this.vipname = vipname;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
