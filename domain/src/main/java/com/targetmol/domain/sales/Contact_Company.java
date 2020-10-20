package com.targetmol.domain.sales;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name="contact_company")
public class Contact_Company implements Serializable {
    @Id
    private Integer contactid;
    private Integer companyid;
    private Integer def;


    public Integer getDef() {
        return def;
    }

    public void setDef(Integer def) {
        this.def = def;
    }

    public Integer getContactid() {
        return contactid;
    }

    public void setContactid(Integer contactid) {
        this.contactid = contactid;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }
}
