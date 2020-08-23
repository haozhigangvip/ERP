package com.targetmol.account.dao;

import com.targetmol.common.mapper.BaseMapper;
import com.targetmol.domain.Contact;
import com.targetmol.domain.Invoice;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface ContactDao extends BaseMapper<Contact> {
    @Select("select * from contact where autoid<>#{autoid} and name=#{name} and  companyid=#{comid}")
    Contact  findRepeatName(Integer autoid,String name,String comid);
}
