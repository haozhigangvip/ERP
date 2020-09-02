package com.targetmol.system.dao;

import com.targetmol.common.mapper.BaseMapper;
import com.targetmol.domain.system.Role_Permission;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RolePermissionDao  extends BaseMapper<Role_Permission> {
    @Select("select pid from role_permission where rid=#{rid}")
    List<Integer> findByRid(Integer rid);
    @Select("select count(*) from role_permission where pid=#{pid}")
    Integer countByPid(Integer pid);
}
