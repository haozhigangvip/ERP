package com.targetmol.system.dao;

import com.targetmol.common.mapper.BaseMapper;
import com.targetmol.domain.system.User_ROLE;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRoleDao extends BaseMapper<User_ROLE> {
    @Select("select rid from user_role where user_id=#{uid}")
    List<User_ROLE> findByUid(Integer uid);

    @Select("select count(*) from user_role where role_id=#{rid}")
    Integer countByRid(Integer rid);

}
