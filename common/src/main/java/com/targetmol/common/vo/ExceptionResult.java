package com.targetmol.common.vo;

import com.targetmol.common.emums.ExceptionEumn;
import lombok.Data;

import java.util.Date;

@Data
public class ExceptionResult {
    private Integer  code;
    private String message;
    private Date timestamp;
    private Object data;
    public  ExceptionResult(ExceptionEumn em){
        this.code=em.getCode();
        this.message=em.getMsg();
        this.timestamp=new Date(System.currentTimeMillis());
    }
}
