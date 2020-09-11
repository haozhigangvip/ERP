package com.targetmol.account.dao;

import com.targetmol.common.mapper.BaseMapper;

import com.targetmol.domain.account.Contact;
import org.apache.ibatis.annotations.*;

import java.util.List;



public interface ContactDao extends BaseMapper<Contact> {
    @Select("select * from contact where contactid<>#{contactid} and name=#{name}")
    Contact  findRepeatName(@Param("contactid") Integer autoid,@Param("name") String name);

//   //关联查询
//    @Select("select * from contact")
//    @Results(value = {
//           @Result(column="companyid",property="company",one = @One(select = "com.targetmol.account.dao.CompanyDao.getCompanyBycomId",fetchType = FetchType.EAGER)),
//
//    })
//    List<Contact> getAll();
    //
    @SelectProvider(type=contactDaoProvider.class,method = "findAllByAnyPara")
    List<Contact> findAllByAnyPara(
            @Param("key") String key,@Param("showdelete") Boolean showdelete,
            @Param("softby") String softby,@Param("desc") Boolean desc);

    class contactDaoProvider{
        public String findAllByAnyPara(String key,Boolean showdelete,String softby,Boolean desc){
            String sqlstr="select * from contact ";
            if(key!=null){
                sqlstr +="where (contid like CONCAT('%',#{key},'%') or name like CONCAT('%',#{key},'%'))";
            }
              if(showdelete==null||showdelete==false){
                if( key!=null){
                    sqlstr +=" and (deltag=0 or deltag is null)";
                }else{
                    sqlstr +="where (deltag=0 or deltag is null)";
                }
            }
            return sqlstr;
        }

    }
}
