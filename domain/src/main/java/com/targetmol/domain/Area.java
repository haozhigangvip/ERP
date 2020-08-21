package com.targetmol.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="area")
@Data
public class Area implements Serializable {
    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "autoid")
    private Integer id;
    private String name;
    private Integer pid;
    private String sname;
    private Integer level;
    private String citycode;
    private String yzode;
    private String mername;
    private Float lng;
    private Float lat;
    private String pingyin;
}