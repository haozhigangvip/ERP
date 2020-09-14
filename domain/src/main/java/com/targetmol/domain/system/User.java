package com.targetmol.domain.system;


import tk.mybatis.mapper.annotation.KeySql;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name="`user`")
public class User implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "uid")
    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private String name;
    private Integer departmentid;
    private String phone;
    private String email;
    private Integer onsales;
    @Column(insertable = false)
    private Date creatime;
    private String utype;
    private String note;
    private Integer activated;
    private String userpic;
    private int groupid;




    public int getGroupid() {
        return groupid;
    }
    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }
    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public Integer getActivated() {
        return activated;
    }

    public void setActivated(Integer activated) {
        this.activated = activated;
    }


    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getOnsales() {
        return onsales;
    }

    public void setOnsales(Integer onsales) {
        this.onsales = onsales;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
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
