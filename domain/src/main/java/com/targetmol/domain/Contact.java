package com.targetmol.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name="contact")
@Data
public class Contact implements Serializable {
    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "autoid")
    private Integer autoid;
    @Column(insertable = false,updatable = false)
    private String contid;
    private String name;
    private String middlename;
    private String lastname;
    private String firstname;
    private String companyid;
    private String city;
    private String state;
    private String country;
    private String email;
    private String phone;
    private String source;
    private String salesman;
    private String note;
    @Column(insertable = false,updatable = false)
    private Date creatime;
    private String streetadr;
    private String weixin;
    private String lab;

    private Integer contvip;
    private String position;
    private Integer deltag;
    @Transient
    private Company company;
    @Transient
    private List<Address> addressList;

}