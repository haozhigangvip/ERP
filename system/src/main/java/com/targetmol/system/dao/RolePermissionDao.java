package com.targetmol.system.dao;

import com.targetmol.common.mapper.BaseMapper;
import com.targetmol.domain.system.Role_Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RolePermissionDao  extends BaseMapper<Role_Permission> {
    @Select("select pid from role_permission where rid=#{rid}")
    List<Integer> findByRid(Integer rid);
}
