package com.targetmol.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name="invoice_info")
@Data
public class Invoice implements Serializable {
    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "autoid")
    private Integer autoid;
    private String invoid;
    private String invobank;
    private String invoaccount;
    private String invotaxid;
    private String invonote;
    private String vocompid;
    @Column(insertable = false)
    private Date creatime;
    private String invoaddress;
    private String invotitle;
    private String invophoen;
    private String involock;

}
