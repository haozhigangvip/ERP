package com.targetmol.system.dao;

import com.targetmol.common.mapper.BaseMapper;
import com.targetmol.domain.system.Role_Permission;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RolePermissionDao  extends BaseMapper<Role_Permission> {
    @Select("select permission_id from role_permission where role_id=#{rid}")
    List<Integer> findByRid(Integer rid);
    @Select("select count(*) from role_permission where permission_id=#{pid}")
    Integer countByPid(Integer pid);
}
