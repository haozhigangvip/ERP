package com.targetmol.domain.sales;

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
    private String attention;
    private String street;
    private String street2;
    private String city;
    private String state;
    private String country;
    private String zipcode;
    private String label;
    @Column(insertable = false,updatable = false)
    private Date creatime;
    private Integer contactid;
    @Column(name = "defa")
    private Integer def;
    private Integer activated;
    @Transient
    private String detaAddress;
    @Transient
    private String detaAddress2;

    public String getDetaAddress() {
        if(StringUtil.isEmpty(street)==false){
            return (country=="China"?"中国":country + state + city + street);
        }else
        {
            return street;
        }
    }

    public String getDetaAddress2() {
        if(StringUtil.isEmpty(street2)==false){
            return (country=="China"?"中国":country +  state + city + street2);
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

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
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

    public Integer getContactid() {
        return contactid;
    }

    public void setContactid(Integer contactid) {
        this.contactid = contactid;
    }

    public Integer getActivated() {
        return activated;
    }

    public void setActivated(Integer activated) {
        this.activated = activated;
    }
}