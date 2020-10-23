package com.targetmol.domain.sales.Account;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

/**
 * 订单来源
 */
@Table(name="order_source")
public class OrderSource {
    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "id")
    private Integer id;
    private String sourcename;       //来源名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSourcename() {
        return sourcename;
    }

    public void setSourcename(String sourcename) {
        this.sourcename = sourcename;
    }
}
