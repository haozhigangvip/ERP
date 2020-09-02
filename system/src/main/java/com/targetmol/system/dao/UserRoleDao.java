package com.targetmol.system.dao;

import com.targetmol.common.mapper.BaseMapper;
import com.targetmol.domain.system.User_ROLE;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserRoleDao extends BaseMapper<User_ROLE> {
    @Select("select rid from user_role where uid=#{uid}")
    List<Integer> findByUid(Integer uid);

    @Select("select count(*) from user_role where rid-#{rid}")
    Integer countByRid(Integer rid);

}
