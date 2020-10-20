package com.targetmol.domain.sales;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name="contact")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })

public class Contact implements Serializable {
    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "contactid")
    private Integer contactid;
    private String name;
    private String email;
    private String phone;
    private String source;
    @Column(name="note")
    private String note;
    @Column(insertable = false, updatable = false)
    private Date creatime;
    private String qq;
    private String weixin;
    private String lab;
    @Column(name="contvip")
    private Integer contvip;
    private String position;
    private Integer activated;
    private Integer pid;
    @Column(name="saleid")
    private Integer saleid;
    private Integer creatuserid;
    private Integer updateuserid;
    private Integer creditlevel;
    private String conttype;


    @Transient
    private List<Company> companys;
    @Transient
    private List<Address> addressList;
    @Transient
    private String salesname;

    public Integer getContactid() {
        return contactid;
    }

    public void setContactid(Integer contactid) {
        this.contactid = contactid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public Integer getContvip() {
        return contvip;
    }

    public void setContvip(Integer contvip) {
        this.contvip = contvip;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getActivated() {
        return activated;
    }

    public void setActivated(Integer activated) {
        this.activated = activated;
    }

    public Integer getSaleid() {
        return saleid;
    }

    public void setSaleid(Integer saleid) {
        this.saleid = saleid;
    }

    public List<Company> getCompanys() {
        return companys;
    }

    public void setCompanys(List<Company> companys) {
        this.companys = companys;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public String getSalesname() {
        return salesname;
    }

    public void setSalesname(String salesname) {
        this.salesname = salesname;
    }


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCreatuserid() {
        return creatuserid;
    }

    public void setCreatuserid(Integer creatuserid) {
        this.creatuserid = creatuserid;
    }

    public Integer getUpdateuserid() {
        return updateuserid;
    }

    public void setUpdateuserid(Integer updateuserid) {
        this.updateuserid = updateuserid;
    }

    public Integer getCreditlevel() {
        return creditlevel;
    }

    public void setCreditlevel(Integer creditlevel) {
        this.creditlevel = creditlevel;
    }

    public String getConttype() {
        return conttype;
    }

    public void setConttype(String conttype) {
        this.conttype = conttype;
    }
}