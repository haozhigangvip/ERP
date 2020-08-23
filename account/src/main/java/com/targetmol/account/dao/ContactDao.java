package com.targetmol.account.dao;

import com.targetmol.common.mapper.BaseMapper;
import com.targetmol.domain.Contact;
import com.targetmol.domain.Invoice;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface ContactDao extends BaseMapper<Contact> {
    @Select("select * from contact where autoid<>#{autoid} and name=#{name} and  companyid=#{comid}")
    Contact  findRepeatName(Integer autoid,String name,String comid);
//    @Select("select * from contact")
//    @Results(value = {
//           @Result(column="companyid",property="company",one = @One(select = "com.targetmol.account.dao.CompanyDao.getCompanyBycomId",fetchType = FetchType.EAGER)),
//
//    })
//    List<Contact> getAll();
}
