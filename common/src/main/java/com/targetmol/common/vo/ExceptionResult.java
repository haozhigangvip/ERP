package com.targetmol.common.vo;

import com.targetmol.common.emums.ExceptionEumn;

import java.util.Date;


public class ExceptionResult {
    private Integer  code;
    private String message;
    private Date timestamp;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public  ExceptionResult(ExceptionEumn em){
        this.code=em.getCode();
        this.message=em.getMsg();
        this.timestamp=new Date(System.currentTimeMillis());
    }
}
