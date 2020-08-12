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
    COMPANYID_CANNOT_BE_NULL(400,"公司ID不能为空"),
    COMPANY_ISNOT_FOUND(404,"该公司不存在"),
    COMPANYS_ISNOT_FOUND (404,"没有找到任何公司"),
    ;

    private Integer code;
    private String msg;
}
