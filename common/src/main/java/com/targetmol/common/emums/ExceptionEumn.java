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
    OBJECT_VALUE_ERROR(400,"请求参数不正确"),
    CONTACTNAME_ALREADY_EXISTS(400,"修改的联系人名已存在"),
    FAIIL_TO_DELETE(500,"删除失败"),
    SUCCESS_TO_DELETE(200,"删除成功"),
    CONTACTID_CANNOT_BE_NULL(400,"联系人ID不能为空"),
    ADDRESS_ISNOT_FOUND(404,"该地址不存在"),
    USERS_ISNOT_FOUND(404,"用户不存在"),
    USERNAME_PASSWORD_CANNOT_BE_NULL(400,"用户名及密码不能为空"),
    EMAIL_CANNOT_BE_NULL(400,"邮箱不能为空"),
    USERNAME_ALREADY_EXISTS(400,"用户名已存在"),
    USERNAMEANDPASSWORD_ISNOT_MATCH(400,"用户名和密码不匹配"),
    DEPARTMENTID_IS_NOT_FOUND(404,"部门ID不存在"),
    DEPARTMENTNAME_ALREADY_EXISTS(400,"部门名称已存在"),
    FOUND_SUB_DEPARTMENT(400,"包含子部门，不能删除"),
    FOUND_USERS(400,"包含用户,不能删除"),
    PERMISSION_IS_NOT_FOUND(404,"权限不存在"),
    ROLENAME_ALREADY_EXISTS(400,"角色名已存在"),
    ROLE_IS_NOT_FOUND(404,"角色不存在"),
    ROLE_IS_BOUNDBY_USERS(400,"用户已绑定该角色"),
    PERMISSION_IS_BOUNDBY_THE_ROLE(400,"角色已绑定该权限"),
    PERMISSION_GRANT_FAILED(420,"授权失效"),
    SALESMAN_CANNOT_BE_NULL(400,"所属销售不能为空"),
    FAIIL_TO_APPLY_FOR_TOKEN(400,"申请令牌失败"),
    LOGIN_USERNAME_IS_NULL(400,"用户名不能为空"),
    LOGIN_PASSWORD_IS_NULL(400,"密码不能为空"),
    NETWOK_IS_BUSY(500,"网络繁忙，请稍后再试"),
    LOGIN_USERNAME_IS_FAIL(400,"错误的用户名"),
    PERMESSION_DENIED(401,"权限不足"),
    PERMESSION_PID_IS_NOT_FOUND(404,"权限父ID不存在"),
    SUB_PERMISSION_ALREADY_EXISTS(400,"该权限存在子权限，不能删除"),
    AUTO_LOGIN_FAILD(401,"自动登录失败"),
    GROUPID_IS_NULL(400,"组ID不能为空"),
    GROUPNAME_IS_NULL(400,"组名不能为空"),
    GROUP_IS_NOT_FOUND(404,"组不存在"),
    FIND_SUB_GROUP(400,"该组存在子组，不能删除"),
    GROUPNAME_ALREADY_EXISTS(400,"组名已存在，保存失败"),
    GROUP_PID_IS_NOT_FOUND(400,"父ID不存在，保存失败"),
    BIND_GROUP_FAILD(400,"绑定组失败"),
    ;
    private Integer code;
    private String msg;
}
