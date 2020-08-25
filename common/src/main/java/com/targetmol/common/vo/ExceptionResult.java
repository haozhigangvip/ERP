package com.targetmol.common.vo;

import com.targetmol.common.emums.ExceptionEumn;
import lombok.Data;

@Data
public class ExceptionResult {
    private Integer  code;
    private String message;
    private Long timestamp;

    public  ExceptionResult(ExceptionEumn em){
        this.code=em.getCode();
        this.message=em.getMsg();
        this.timestamp=System.currentTimeMillis();
    }
}
