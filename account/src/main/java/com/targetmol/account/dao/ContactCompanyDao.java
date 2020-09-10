package com.targetmol.account.dao;

import com.targetmol.common.mapper.BaseMapper;
import com.targetmol.domain.account.Contact;
import com.targetmol.domain.account.Contact_Company;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactCompanyDao  extends BaseMapper<Contact_Company> {


}
