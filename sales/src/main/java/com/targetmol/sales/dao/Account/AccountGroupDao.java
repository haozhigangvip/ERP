package com.targetmol.sales.dao.Account;

import com.targetmol.common.mapper.BaseMapper;
import com.targetmol.domain.sales.Account.AccountGroup;
import org.apache.ibatis.annotations.Select;

public interface AccountGroupDao extends BaseMapper<AccountGroup> {
    @Select("SELECT max(right(code,2)) FROM account_group where code like #{pcode} and length(code)=length#{pcode}")
    Integer getMaxCode(String pcode);
}
