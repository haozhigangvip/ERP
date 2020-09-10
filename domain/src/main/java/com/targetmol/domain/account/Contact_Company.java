package com.targetmol.domain.account;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="contact")
public class Contact_Company {
    @Id
    private Integer contactid;
    private Integer companyid;

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
