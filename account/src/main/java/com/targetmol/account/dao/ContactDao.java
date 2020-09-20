package com.targetmol.account.dao;

import com.targetmol.common.mapper.BaseMapper;

import com.targetmol.domain.account.Contact;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;



public interface ContactDao extends BaseMapper<Contact> {
    //查询重复联系人名称
    @Select("select * from contact where contactid<>#{contactid} and name=#{name}")
    Contact  findRepeatName(@Param("contactid") Integer autoid,@Param("name") String name);

    //查询所有联系人
    @SelectProvider(type=contactDaoProvider.class,method = "findAllByAnyPara")
    @Results({
            @Result(id=true,column = "contactid",property = "contactid"),
            @Result(property = "saleid",column = "saleid"),
            @Result(property = "note",column = "note"),
            @Result(property = "contvip",column = "contvip"),
            @Result(property = "companys",column = "contactid",many =
                     @Many(select = "com.targetmol.account.dao.CompanyDao.searchByContactIdDef",fetchType = FetchType.LAZY)),
            @Result(property = "salesname",column = "saleid",one = @One(select = "com.targetmol.account.dao.ContactDao.getUserNameByUid"))

    })
    List<Contact> findAllByAnyPara(
            @Param("key") String key,@Param("showUnActived") Boolean showUnActived,
            @Param("softby") String softby,@Param("desc") Boolean desc);

    class contactDaoProvider{
        public String findAllByAnyPara(String key,Boolean showUnActived,String softby,Boolean desc){
            String sqlstr="select * from contact ";
            if(key!=null){
                sqlstr +="where (contactid like CONCAT('%',#{key},'%') or name like CONCAT('%',#{key},'%'))";
            }
              if(showUnActived==null||showUnActived==false){
                if( key!=null){
                    sqlstr +=" and (activated<>0)";
                }else{
                    sqlstr +="where (activated<>0)";
                }
            }
            if(softby!=null){
                 sqlstr+=" order by " + softby+( desc?" DESC":" ASC");
            }

            return sqlstr;
        }

    }


    //根据公司ID查询联系人
    @Select("select * from  contact where contid in (select  contactid from contact_company where companyid=#{companyid}) order by creatime desc")
    List<Contact> searchByCompanyId(Integer companyid );
    //根据用户ID查询用户名
    @Select("select name from user where  uid=#{uid}")
    String getUserNameByUid(@Param("uid") Integer uid);
}
