package com.targetmol.domain.sales;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

/**
 * 客户信用等级
 */
@Table(name="account_credit")
public class AccountCredit {
    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "id")
    private Integer id;
    private Integer level;          //信用等级
    private String levelname;       //等级名称
    private Double limit;       //信用额度
    private Integer allotteddays; //授信天数
    private String note;     //备注

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public Integer getAllotteddays() {
        return allotteddays;
    }

    public void setAllotteddays(Integer allotteddays) {
        this.allotteddays = allotteddays;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
