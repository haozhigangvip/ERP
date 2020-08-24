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
    COMPANYNAME_CANNOT_BE_NULL(400,"公司名不能为空"),
    COMPANYNAME_ALREADY_EXISTS (400,"公司名已存在"),
    CONTACT_ISNOT_FOUND(404,"没有找到任何联系人"),
    FAIIL_TO_SAVE(500,"保存失败"),
    CONTACT_ALREADY_EXISTS (400,"联系人已存在"),
    OBJECT_VALUE_ERROR(400,"错误的对象"),
    CONTACTNAME_ALREADY_EXISTS(400,"修改的联系人名已存在"),
    FAIIL_TO_DELETE(500,"删除失败"),
    SUCCESS_TO_DELETE(200,"删除成功"),
    CONTACTID_CANNOT_BE_NULL(400,"联系人ID不能为空"),
    ;

    private Integer code;
    private String msg;
}
