package com.targetmol.common.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 通用返回类
 * @param <T>
 */

@Data
public class PageResult<T> {
    private Integer code;
    private String msg;
    private Date timestamp;
    private List<T> data;
    private Long total;
    private Integer totalPage;




    public PageResult(){
    }

    public PageResult(Long total ,List<T> data){
        this.code=200;
        this.msg="success";
        this.timestamp=new Date(System.currentTimeMillis());
        this.total=total;
        this.data=data;


    }
    public PageResult(Long total ,Integer totalPage,List<T> data){
        this.code=200;
        this.msg="success";
        this.total=total;
        this.timestamp=new Date(System.currentTimeMillis());
        this.totalPage=totalPage;
        this.data=data;
    }

}
