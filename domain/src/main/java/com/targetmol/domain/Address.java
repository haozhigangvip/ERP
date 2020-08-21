package com.targetmol.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name="address")
@Data
public class Address  implements Serializable {
    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "autoid")
    private Integer autoid;
    @Column(insertable = false,updatable = false)
    private String adrid;
    private String attention;
    private String Street;
    private String Street2;
    private String city;
    private String state;
    private String country;
    private String zipcode;
    private String company;
    private String dept;
    private String phone;
    private String email;
    private String label;
    @Column(insertable = false,updatable = false)
    private Date creatime;
    private String adrcontact;
    private Integer active;

}