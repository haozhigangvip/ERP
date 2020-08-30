package com.targetmol.account.dao;

import com.targetmol.common.mapper.BaseMapper;
import com.targetmol.domain.account.Company;
import org.apache.ibatis.annotations.Select;



public interface CompanyDao extends BaseMapper<Company> {
    @Select("select * from company_info where autoid<>#{autoid} and companyname=#{cname}")
    Company findRepeatCompanyName(Integer autoid, String cname);
//    @Select("select * from company_info where comid=#{comid}")
//    Company getCompanyBycomId(String comid);
}
