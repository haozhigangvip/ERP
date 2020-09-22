package com.targetmol.domain.system;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="permission_group")
public class PermissionGroup {
    @Id
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "id")
    private Integer id;
    private Integer pid;
    private  String groupname;
    private String note;

    @Transient
    private List<PermissionGroup> permissionGroupList;


    public List<PermissionGroup> getPermissionGroupList() {
        return permissionGroupList;
    }

    public void setPermissionGroupList(List<PermissionGroup> permissionGroupList) {
        this.permissionGroupList = permissionGroupList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
