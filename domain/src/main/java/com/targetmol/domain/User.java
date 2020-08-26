package com.targetmol.domain;


import tk.mybatis.mapper.annotation.KeySql;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name="users")
public class User implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "uid")
    private Integer uid;
    private String username;
    private String password;
    private String name;
    private Integer groupid;
    private String phone;
    private String email;
    private Boolean issales;
    @Column(insertable = false)
    private Date creatime;
    private Date lastlogtime;
    private String lastlogip;
    private String note;


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Boolean getIssales() {
        return issales;
    }

    public void setIssales(Boolean issales) {
        this.issales = issales;
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }

    public Date getLastlogtime() {
        return lastlogtime;
    }

    public void setLastlogtime(Date lastlogtime) {
        this.lastlogtime = lastlogtime;
    }



    public String getLastlogip() {
        return lastlogip;
    }

    public void setLastlogip(String lastlogip) {
        this.lastlogip = lastlogip;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
