package com.targetmol.filemanager.domain.history;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Document(collection = "tsbio_tablehistory")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class TableHistory {

    private String tablename;   //表名
    private Integer tableid;    //表ID
    private String type;        //操作类型（增删改查）
    private String json;        //操作记录集
    private Integer userid;     //用户ID
    private Date creatime;      //创建时间
    private String note;        //备注




    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public Integer getTableid() {
        return tableid;
    }

    public void setTableid(Integer tableid) {
        this.tableid = tableid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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
}
