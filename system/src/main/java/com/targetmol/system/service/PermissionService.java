package com.targetmol.system.service;

import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.domain.system.*;
import com.targetmol.system.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = {Exception.class,ErpExcetpion.class})
public class PermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private PermissionApiDao permissionApiDao;
    @Autowired
    private PermissionMenuDao permissionMenuDao;
    @Autowired
    private PermissionPointDao permissionPointDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;


    //添加权限
    public void addPermission(Permission permission) throws Exception {
        int result=1;
        //判断对象参数是否正确
        permission=checkPermssion(permission);
        //检查PID是否存在
        Permission p_permission=findById(permission.getpId());
        if(p_permission==null){
            throw new ErpExcetpion(ExceptionEumn.PERMESSION_PID_IS_NOT_FOUND);
        }
        //设置父code
        permission.setpCode(p_permission.getCode());
        //设置层级
        permission.setLevel(p_permission.getLevel()+1);

        //保存Permission表
        result =result*permissionDao.insert(permission);

        //判断保存是否成功
        if(result!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
    }



    //修改权限
    public void updatePermission(Permission permission) throws Exception{
        int result=1;
        //判断对象是否为空
        permission=checkPermssion(permission);

        //通过传递的权限ID查询权限是否存在

        Permission data=permissionDao.selectByPrimaryKey(permission.getpId());
        if(data==null){
            throw new ErpExcetpion(ExceptionEumn.PERMISSION_IS_NOT_FOUND);
        }
        //传递值赋给Permission
        data.setPermissions(permission.getPermissions());
        data.setpCode(permission.getpCode());
        data.setSort(permission.getSort());
        data.setIcon(permission.getIcon());
        data.setMenuName(permission.getMenuName());
        data.setNote(permission.getNote());
        data.setType(permission.getType());
        data.setUrl(permission.getUrl());
        data.setStatus(permission.getStatus());
        //保存permission
        result=result*permissionDao.updateByPrimaryKeySelective(data);

        //判断保存是否成功
        if(result!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

    }
//
//
    //删除权限
    public void deletePermission(Integer id) {
       Integer result=1;
        if(id==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }

        //判断权限是否存在
        Permission permission=permissionDao.selectByPrimaryKey(id);
        if(permission==null){
            throw new ErpExcetpion(ExceptionEumn.PERMISSION_IS_NOT_FOUND);
        }

        //查询权限是否有下级权限
        List<Integer> lst=permissionDao.findbyPid(id);
        if(lst!=null && lst.size()>0){
            throw new ErpExcetpion(ExceptionEumn.SUB_PERMISSION_ALREADY_EXISTS);
        }

        //查找角色是否绑定权限
        if(rolePermissionDao.countByPid(id)>0){
            throw new ErpExcetpion(ExceptionEumn.PERMISSION_IS_BOUNDBY_THE_ROLE);
        }
        //删除权限
        result=permissionDao.deleteByPrimaryKey(id);

        if(result!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }
    }
//
//
//
    //查询所有权限
    public List<Permission> findByAll() {
        //搜索所有权限
        List<Permission> data=permissionDao.selectAll();
        List<Permission> lst=new ArrayList<>();
        for (Permission permissionExt:data){
            if(permissionExt.getLevel()!=null && permissionExt.getLevel()==0){
                lst.add(permissionExt);
            }
        }
        for(Permission item:lst){
            item.setPermissions(getChilde(item.getId(),data));
        }
        return lst;
    }

    private List<Permission>getChilde(Integer id,List<Permission> rootList){
        //子菜单
        List<Permission> childList=new ArrayList<>();
        if(rootList==null||rootList.size()<=0){
            return null;
        }
        for(Permission item:rootList){
            if(item!=null&&item.getpId()!=null &&item.getpId().equals(id)){
                childList.add(item);
            }
        }
        for(Permission item:childList){
            item.setPermissions(getChilde(item.getId(),rootList));
        }
        if(childList.size()==0){
            return null;
        }
        return childList;
    }


    //根据用户ID查询权限
    public List<Permission> getByUserId(Integer userId){
        if(userId!=null){
            return permissionDao.findByUserId(userId);
        }else
        {
            return null;
        }
    }


    //根据权限ID查询权限
    public Permission findById(Integer id){
        return permissionDao.selectByPrimaryKey(id);
    }

    //检查权限参数
    private Permission checkPermssion(Permission permission){
        if(permission==null|| permission.getCode()==null||permission.getMenuName()==null||permission.getpId()==null|| StringUtil.isEmpty(permission.getType())){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        //设置默认值

        if(permission.getSort()==null){
            permission.setSort(1);
        }

        return permission;
    }

}
