package com.targetmol.domain.sales.Account;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.util.StringUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name="address")
public class Address  implements Serializable {
    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "addrid")
    private Integer addrid;
    private String linkman;
    private String street;
    private String street2;
    private String city;
    private String phone;
    private String phone1;
    private String state;
    private String country;
    private String zipcode;
    private String label;
    @Column(insertable = false,updatable = false)
    private Date creatime;
    @Column(name = "defa")
    private Integer def;
    private Integer activated;
    @Transient
    private String detaAddress;
    @Transient
    private String detaAddress2;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getDetaAddress() {
        if(StringUtil.isEmpty(street)==false){
            return ((country.toUpperCase().equals("CHINA")?"中国":country) +  state + city + street);
        }else
        {
            return street;
        }
    }

    public String getDetaAddress2() {
        if(StringUtil.isEmpty(street2)==false){
            return ((country.toUpperCase().equals("CHINA")?"中国":country) +  state + city + street2);
        }else
        {
            return street2;
        }
    }




    public Integer getDef() {
        return def;
    }

    public void setDef(Integer def) {
        this.def = def;
    }

    public Integer getAddrid() {
        return addrid;
    }

    public void setAddrid(Integer addrid) {
        this.addrid = addrid;
    }

    public String getLinkman() {
        return linkman;
    }
    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public void setDetaAddress(String detaAddress) {
        this.detaAddress = detaAddress;
    }

    public void setDetaAddress2(String detaAddress2) {
        this.detaAddress2 = detaAddress2;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }

    public Integer getActivated() {
        return activated;
    }

    public void setActivated(Integer activated) {
        this.activated = activated;
    }
}