package com.targetmol.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name="[company_info]")
@JsonIgnoreProperties(value = {"handler"})
public class Company {
    private Integer autoid;
    private String  comid;
    private String companyname;
    private String comptype;
    private String comadrid;
    private String phone;
    private String abbre;
    private String note;
    private Date creatime;
    private String csalesman;
    private Integer deltag;
}
