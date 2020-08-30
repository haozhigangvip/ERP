package com.targetmol.system.service;

import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.utils.BeanMapUtils;
import com.targetmol.common.utils.PermissionConstants;
import com.targetmol.domain.system.Permission;
import com.targetmol.domain.system.PermissionApi;
import com.targetmol.domain.system.PermissionMenu;
import com.targetmol.domain.system.PermissionPoint;
import com.targetmol.system.dao.PermissionApiDao;
import com.targetmol.system.dao.PermissionDao;
import com.targetmol.system.dao.PermissionMenuDao;
import com.targetmol.system.dao.PermissionPointDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class PermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private PermissionApiDao permissionApiDao;
    @Autowired
    private PermissionMenuDao permissionMenuDao;
    @Autowired
    private PermissionPointDao permissionPointDao;

    //添加权限
    public void addPermission(Map<String, Object> map) throws Exception {
        int result=1;
        //判断对象是否为空
        if(map==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        //封装到实体类
        Permission permission= BeanMapUtils.mapToBean(map,Permission.class);
        //保存Permission表
        result=result*permissionDao.insert(permission);
        //根据类型构造不同资源对象并保存（菜单，按钮，api)
        int type=permission.getType();
        switch (type){
            case PermissionConstants.PY_MENU:
                PermissionMenu menu=BeanMapUtils.mapToBean(map,PermissionMenu.class);
                menu.setPreid(permission.getId());
                result=result*permissionMenuDao.insert(menu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint point=BeanMapUtils.mapToBean(map,PermissionPoint.class);
                point.setPreid(permission.getId());
                result=result*(permissionPointDao.insert(point));
                break;
            case PermissionConstants.PY_API:
                PermissionApi api=BeanMapUtils.mapToBean(map, PermissionApi.class);
                api.setPreid(permission.getId());
                result=result*permissionApiDao.insert(api);
                break;
            default:
                throw  new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        //判断保存是否成功
        if(result!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
    }



    //修改权限
    public void updatePermission(Map<String, Object> map) throws Exception{
        int result=1;
        //判断对象是否为空
        if(map==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        //将Map封装成permission
        Permission per=BeanMapUtils.mapToBean(map,Permission.class);
        //通过传递的权限ID查询权限是否存在
        Permission permission=permissionDao.selectByPrimaryKey(per.getId());
        if(permission==null){
            throw new ErpExcetpion(ExceptionEumn.PERMISSION_IS_NOT_FOUND);
        }
        //传递值赋给Permission
        permission.setCode(per.getCode());
        permission.setNote(per.getNote());
        permission.setPername(per.getPername());
        permission.setPid(per.getPid());
        permission.setType(per.getType());
        //保存permission
        result=result*permissionDao.updateByPrimaryKeySelective(permission);

        //根据不同类型保存不同资源
        int type =per.getType();
        switch (type){
            case PermissionConstants.PY_MENU:
                PermissionMenu menu= BeanMapUtils.mapToBean(map,PermissionMenu.class);
                menu.setPreid(permission.getId());
                PermissionMenu rmenu=permissionMenuDao.selectOne(menu);
                rmenu.setMenuicon(menu.getMenuicon());
                rmenu.setMenuorder(menu.getMenuorder());
                result=result*permissionMenuDao.updateByPrimaryKeySelective(rmenu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint point= BeanMapUtils.mapToBean(map,PermissionPoint.class);
                point.setPreid(permission.getId());
                PermissionPoint rpoint=permissionPointDao.selectOne(point);
                rpoint.setPointclass(point.getPointclass());
                rpoint.setPointicon(point.getPointicon());
                rpoint.setPointstatus(point.getPointstatus());
                result=result*permissionPointDao.updateByPrimaryKeySelective(rpoint);
                break;
            case PermissionConstants.PY_API:
                PermissionApi api= BeanMapUtils.mapToBean(map,PermissionApi.class);
                api.setPreid(permission.getId());
                PermissionApi rapi=permissionApiDao.selectOne(api);
                rapi.setApilevel(api.getApilevel());
                rapi.setApimethod(api.getApimethod());
                rapi.setApiurl(api.getApiurl());
                result=result*permissionApiDao.updateByPrimaryKeySelective(rapi);
                break;
            default:
                throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }

        //判断保存是否成功
        if(result!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

    }
}
