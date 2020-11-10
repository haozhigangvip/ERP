package com.targetmol.domain.sales.Account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Table(name="company_info")
@JsonIgnoreProperties(value = {"handler"})
public class Company  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "companyid")
    private Integer companyid;
    private String companyname;
    private String areagroup;
    private String country;
    private String state;
    private String city;
    private String street;
    private String phone;
    private String zipcode;
    private String note;
    @Column(insertable = false,updatable = false)
    private Date creatime;
    private Integer saleid;
    private Integer activated;
    private String fileurl;
    @Transient
    private Integer contid;
    @Transient
    private Integer def;


    private List<Contact> contacts;

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public String getAreagroup() {
        return areagroup;
    }

    public void setAreagroup(String areagroup) {
        this.areagroup = areagroup;
    }

    public Integer getContid() {
        return contid;
    }

    public void setContid(Integer contid) {
        this.contid = contid;
    }

    public Integer getDef() {
        return def;
    }

    public void setDef(Integer def) {
        this.def = def;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getNonte() {
        return note;
    }

    public void setNonte(String nonte) {
        this.note = nonte;
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }

    public Integer getSaleid() {
        return saleid;
    }

    public void setSaleid(Integer saleid) {
        this.saleid = saleid;
    }

    public Integer getActivated() {
        return activated;
    }

    public void setActivated(Integer activated) {
        this.activated = activated;
    }
}
