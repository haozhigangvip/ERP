package com.targetmol.common.vo;

import lombok.Data;

@Data
public class ResultMsg<T> {
    private int code;
    private String msg;
    private Long timestamp;
    private T data;

    public static <T> ResultMsg<T> success() {
        return new ResultMsg<T>();
    }

    public static <T> ResultMsg<T> success(T data) {
        return new ResultMsg<>(data);
    }

    public static <T> ResultMsg<T> error(int code, String msg) {
        return new ResultMsg<>(code, msg);
    }

    public ResultMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.timestamp=System.currentTimeMillis();
        this.data = null;
    }

    public ResultMsg(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
        this.timestamp=System.currentTimeMillis();
    }

    public ResultMsg() {
        this.code = 0;
        this.msg = "success";
        this.data = null;
        this.timestamp=System.currentTimeMillis();
    }


}
