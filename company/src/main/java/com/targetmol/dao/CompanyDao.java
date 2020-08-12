package com.targetmol.dao;

import com.targetmol.domain.Company;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface CompanyDao extends Mapper<Company> {
    @Select("select * from [company_info] where comID = #{comID}")
    Company findByComid(String comID);
}
