package com.targetmol.sales.dao;

import com.targetmol.common.mapper.BaseMapper;

import com.targetmol.domain.sales.Contact;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;


public interface ContactDao extends BaseMapper<Contact> {
    //查询重复联系人名称
    @Select("select * from contact where contactid<>#{contactid} and name=#{name}")
    Contact  findRepeatName(@Param("contactid") Integer autoid,@Param("name") String name);

    //查询所有联系人
    @SelectProvider(type=contactDaoProvider.class,method = "findAllByAnyPara")
    @Results({
            @Result(id=true,column = "contactid",property = "contactid"),
            @Result(property = "companys",column = "contactid",many =
                     @Many(select = "com.targetmol.sales.dao.CompanyDao.searchByContactIdDef",fetchType = FetchType.LAZY)),
    })
    List<Contact> findAllByAnyPara(
            @Param("key") String key,@Param("showUnActived") Boolean showUnActived,
            @Param("softby") String softby,@Param("desc") Boolean desc,
            @Param("pid") Integer pid);

    class contactDaoProvider{
        public String findAllByAnyPara(String key,Boolean showUnActived,String softby,Boolean desc,Integer pid){
            String sqlstr="select a.*,b.name as salesname from contact as a left join `user` as b on a.saleid=b.uid  ";
            if(key!=null){
                sqlstr +="where (a.contactid like CONCAT('%',#{key},'%') or a.name like CONCAT('%',#{key},'%'))";
            }
            if(showUnActived==null||showUnActived==false){
                if(StringUtil.isEmpty(key)==false){
                    sqlstr +=" and (a.activated<>0)";
                }else{
                    sqlstr +=" where (a.activated<>0)";
                }
            }
            if(pid!=null){
               if(sqlstr.indexOf("where")>0){
                   sqlstr +=" and (a.pid=#{pid}) ";
               }else{
                   sqlstr +=" where a.pic=#{pid}";
               }
            }

            if(softby!=null){
                 sqlstr+=" order by a." + softby+( desc?" DESC":" ASC");
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

    //根据contiID查找默认联系人名字及公司名
    @Select("select a.contactid,a.name as contactname,d.companyid,d.companyname from contact as a LEFT JOIN(select b.contactid,b.companyid,c.companyname from contact_company as b LEFT JOIN company_info as c on b.companyid=c.companyid where b.def=1) as d on a.contactid=d.contactid where a.contactid=#{contid}")
    Map<String,String> getConNameCompanyName(Integer contid);
    //解绑所有PID等于contid的所有子联系人
    @Update("update contact set pid=null where pid=#{contid}")
    Integer setContactPidIsNull(Integer contid);
}
