package com.targetmol.system.dao;

import com.targetmol.common.mapper.BaseMapper;
import com.targetmol.common.utils.PermissionConstants;
import com.targetmol.domain.system.Permission;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PermissionDao extends BaseMapper<Permission> {
    @Select("select id from permission where  p_id=#{pid}")
    List<Integer> findbyPid(Integer pid);
    @Select("select id,code,p_id as pId,menu_name as menuName,url,type ,level,sort,status,icon," +
            "create_time as creatime from permission where id in(select permission_id from role_permission " +
            "where role_id in(select role_id from user_role where user_id=#{userId}))")
    List<Permission> findByUserId(Integer userId);
    @Select("selct max(level) as levels from permission")
    Integer getMaxLevel();
    @Select("selct *  from permission where level=#{level}")
    List<Permission> findByLevel(int level);
}
