package com.targetmol.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name="invoice_info")
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


    public Integer getAutoid() {
        return autoid;
    }

    public void setAutoid(Integer autoid) {
        this.autoid = autoid;
    }

    public String getInvoid() {
        return invoid;
    }

    public void setInvoid(String invoid) {
        this.invoid = invoid;
    }

    public String getInvobank() {
        return invobank;
    }

    public void setInvobank(String invobank) {
        this.invobank = invobank;
    }

    public String getInvoaccount() {
        return invoaccount;
    }

    public void setInvoaccount(String invoaccount) {
        this.invoaccount = invoaccount;
    }

    public String getInvotaxid() {
        return invotaxid;
    }

    public void setInvotaxid(String invotaxid) {
        this.invotaxid = invotaxid;
    }

    public String getInvonote() {
        return invonote;
    }

    public void setInvonote(String invonote) {
        this.invonote = invonote;
    }

    public String getVocompid() {
        return vocompid;
    }

    public void setVocompid(String vocompid) {
        this.vocompid = vocompid;
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }

    public String getInvoaddress() {
        return invoaddress;
    }

    public void setInvoaddress(String invoaddress) {
        this.invoaddress = invoaddress;
    }

    public String getInvotitle() {
        return invotitle;
    }

    public void setInvotitle(String invotitle) {
        this.invotitle = invotitle;
    }

    public String getInvophoen() {
        return invophoen;
    }

    public void setInvophoen(String invophoen) {
        this.invophoen = invophoen;
    }

    public String getInvolock() {
        return involock;
    }

    public void setInvolock(String involock) {
        this.involock = involock;
    }
}
