package com.targetmol.sales.dao.Account;

import com.targetmol.common.mapper.BaseMapper;
import com.targetmol.domain.sales.Account.Contact_Company;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactCompanyDao  extends BaseMapper<Contact_Company> {
    //根据contid，设置所有公司为非默认公司
    @Update("update contact_company set def=0 where contactid=#{contid}")
    Integer updateDef20CompanyByContId(Integer contid);

    //根据contid和companyid查询
    @Select("select * from contact_company where  contactid=#{contid} and companyid=#{companyid}")
    Contact_Company findByContidAndCompanyId(Integer contid,Integer companyid);

    //根据contid，更新最后的绑定日期的公司为默认公司
    @Update("update  contact_company set def=1 where  contactid=#{contid}   order by creatime desc limit 1")
    Integer updateDefa21CompanyContid(Integer contid);
}
