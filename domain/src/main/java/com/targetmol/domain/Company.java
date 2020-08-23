package com.targetmol.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="[company_info]")
@JsonIgnoreProperties(value = {"handler"})
public class Company  implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(insertable = false,updatable =false,name = "autoid")
    private Integer autoid;
    @Column(insertable = false,updatable = false,name = "comid")
    private String  comid;
    private String companyname;
    private String comptype;
    private String comadrid;
    private String phone;
    private String abbre;
    private String note;
    @Column(insertable = false,name = "creatime")
    private Date creatime;
    private String csalesman;
    private Integer deltag;


}
