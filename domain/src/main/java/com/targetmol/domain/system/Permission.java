package com.targetmol.domain.system;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="permission")
public class Permission {
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(insertable = false,updatable =false,name = "id")
    private  int id;
    private String pername;  //权限名称
    private Integer type;       //权限类型，1为菜单，2为功能，3为API
    private String code;    //权限编码，根据code比较权限
    private Integer pid;    //父ID
    private String note;    //备注

    public Permission(String pername,Integer type,String code,String note){
        this.pername=pername;
        this.type=type;
        this.code=code;
        this.note=note;
    }
    public Permission(String pername,Integer type,String code){
        this.pername=pername;
        this.type=type;
        this.code=code;
    }
    public Permission(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPername() {
        return pername;
    }

    public void setPername(String pername) {
        this.pername = pername;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", pername='" + pername + '\'' +
                ", type=" + type +
                ", code='" + code + '\'' +
                ", pid=" + pid +
                ", note='" + note + '\'' +
                '}';
    }
}
