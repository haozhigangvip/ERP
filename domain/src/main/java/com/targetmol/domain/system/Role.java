package com.targetmol.domain.system;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import java.util.Map;

@Table(name="role")
public class Role {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(insertable = false,updatable =false,name = "rid")
    private Integer rid;
    private String rolename;
    private String note;
    private String companyid;
    @Transient
    private List<Integer> perIds;

    @Transient
    private List<Map<String ,Object>> permissions;


    public List<Map<String ,Object>> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Map<String ,Object>> permissions) {
        this.permissions = permissions;
    }

    public List<Integer> getPerIds() {
        return perIds;
    }

    public void setPerIds(List<Integer> perIds) {
        this.perIds = perIds;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
