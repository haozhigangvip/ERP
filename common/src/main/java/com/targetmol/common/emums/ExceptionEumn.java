package com.targetmol.common.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  ExceptionEumn {
    NAME_CANNOT_BE_NULL(400,"名字不能为空"),
    USERNAME_CANNOT_BE_NULL(400,"用户名不能为空"),
    ;
    private Integer code;
    private String msg;
}
