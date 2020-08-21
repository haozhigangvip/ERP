package com.targetmol.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name="users")
@Data
public class Users implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "uid")
    private Integer uid;
    private String username;
    private String password;
    private Integer depaid;
    private Boolean issales;
    @Column(insertable = false)
    private Date creatime;
    private Date lastlogtime;
    private String companyid;
    private String lastlogip;
    private String note;

}
