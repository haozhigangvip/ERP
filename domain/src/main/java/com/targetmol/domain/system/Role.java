package com.targetmol.domain.system;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Table(name="role")
public class Role {

    @Id
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "rid")
    private Integer rid;
    @Column(name="role_name")
    private String roleName;
    @Column(name="role_code")
    private String roleCode;
    private String note;
    @Column(insertable = false,updatable = false)
    private Date creatime;
    private Integer status;

    @Transient
    private List<Integer> perIds;
    @Transient
    private List<Map<String ,Object>> permissions;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Integer> getPerIds() {
        return perIds;
    }

    public void setPerIds(List<Integer> perIds) {
        this.perIds = perIds;
    }

    public List<Map<String, Object>> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Map<String, Object>> permissions) {
        this.permissions = permissions;
    }
}
