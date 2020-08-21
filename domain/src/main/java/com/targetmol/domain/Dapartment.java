package com.targetmol.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name="contact")
@Data
public class Dapartment  implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(insertable = false,updatable =false,name = "autoid")
    private Integer depaid;
    private Integer pid;
    private String name;
    private String mname;
    private Integer level;
    private String companyid;
    @Column(insertable = false)
    private Date creatime;
}
