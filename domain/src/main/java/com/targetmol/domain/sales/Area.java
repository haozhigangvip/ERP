package com.targetmol.domain.sales;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="area")
public class Area implements Serializable {
    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "id")
    private Integer id;
    private String name;
    private Integer pid;
    private String sname;
    private Integer level;
    private String citycode;
    private String yzode;
    private String mername;
    private Float lng;
    private Float lat;
    private String pingyin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getYzode() {
        return yzode;
    }

    public void setYzode(String yzode) {
        this.yzode = yzode;
    }

    public String getMername() {
        return mername;
    }

    public void setMername(String mername) {
        this.mername = mername;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public String getPingyin() {
        return pingyin;
    }

    public void setPingyin(String pingyin) {
        this.pingyin = pingyin;
    }
}