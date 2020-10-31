package com.targetmol.sales.dao.Account;

import com.targetmol.common.mapper.BaseMapper;
import com.targetmol.domain.sales.Account.AccountGroup;
import org.apache.ibatis.annotations.Select;

public interface AccountGroupDao extends BaseMapper<AccountGroup> {
    @Select("select max(right(code,2)) as maxcode from account_group where code like concat('%',#{pcode},'%') and length(code)=length(#{pcode})+2 ")
    Integer getMaxCode(String pcode);
    @Select("select * from account_group where code=#{code}")
    AccountGroup  getBycode(String code);
}
