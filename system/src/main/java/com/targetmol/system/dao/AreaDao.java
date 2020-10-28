package com.targetmol.system.dao;

import com.targetmol.common.mapper.BaseMapper;
import com.targetmol.domain.system.Area;
import org.apache.ibatis.annotations.*;
import java.util.List;


public interface AreaDao extends BaseMapper<Area> {

    @SelectProvider(type=areaDaoProvider.class,method = "findAreaByAnyParam")
    List<Area> findAllByAnyPara(@Param("code") String Code,@Param("key") String key,@Param("isChinese") Boolean isChinese);


    class areaDaoProvider{
        public String findAreaByAnyParam(String code,String key,boolean isChinese){
            String sqlstr="select * from area where ";
            if(isChinese==true){
                sqlstr+=" language='zh_CN' ";
            }else{
                sqlstr+=" language='en_US' ";
            }
            if(code!=null){
                sqlstr+=" and code like '%"+code+"%'  and LENGTH(trim(code))="+((Integer)code.trim().length()+2);
            }else{
                sqlstr+=" and LENGTH(trim(code))=8";
            }

            if(key!=null){
                sqlstr +=" and name like '%"+key+"%'";
            }
            sqlstr +=" order by id";
            return sqlstr;
        }

    }




}
