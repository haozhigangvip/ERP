package com.targetmol.system.dao;

import com.targetmol.common.mapper.BaseMapper;
import com.targetmol.common.utils.PermissionConstants;
import com.targetmol.domain.system.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionDao extends BaseMapper<Permission> {
    @Select("select id from permission where type=#{type} and pid=#{pid}")
    List<Integer> findbyTypeandPid(Integer type,Integer pid);
}
