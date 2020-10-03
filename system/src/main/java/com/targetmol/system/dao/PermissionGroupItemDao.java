package com.targetmol.system.dao;

import com.targetmol.parent.common.mapper.BaseMapper;
import com.targetmol.domain.system.PermissionGroupItem;
import com.targetmol.domain.system.ext.PermissionGroupUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionGroupItemDao extends BaseMapper<PermissionGroupItem> {
    @Select("select * from permission_group_item where uid=#{uid} order by pid asc")
    List<PermissionGroupItem> findByUid(Integer uid);

    @Select("select a.gid as groupid ,a.uid as userid,b.name from permission_group_item as a left join `user` as b on a.uid=b.uid where a.pid=#{pid}")
    List<PermissionGroupUser> findSubUserByPid(Integer pid);

    @Select("select a.gid as groupid ,a.uid as userid,b.name from permission_group_item as a left join `user` as b on a.uid=b.uid where a.gid=#{gid}")
    List<PermissionGroupUser> findSubUserByGid(Integer gid);}
