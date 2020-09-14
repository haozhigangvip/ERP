package com.targetmol.system.service;

import com.github.pagehelper.PageInfo;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.utils.BeanMapUtils;
import com.targetmol.common.utils.PermissionConstants;
import com.targetmol.common.vo.PageResult;
import com.targetmol.domain.system.*;
import com.targetmol.system.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
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



//    //修改权限
//    public void updatePermission(Map<String, Object> map) throws Exception{
//        int result=1;
//        //判断对象是否为空
//        if(map==null){
//            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
//        }
//        //将Map封装成permission
//        Permission per=BeanMapUtils.mapToBean(map,Permission.class);
//        //通过传递的权限ID查询权限是否存在
//
//        Permission permission=permissionDao.selectByPrimaryKey(per.getId());
//        if(permission==null){
//            throw new ErpExcetpion(ExceptionEumn.PERMISSION_IS_NOT_FOUND);
//        }
//        //传递值赋给Permission
//        permission.setCode(per.getCode());
//        permission.setNote(per.getNote());
//        permission.setPername(per.getPername());
//        permission.set(per.getpId());
//        permission.setType(per.getType());
//        //保存permission
//        result=result*permissionDao.updateByPrimaryKeySelective(permission);
//
//        //根据不同类型保存不同资源
//        int type =per.getType();
//        switch (type){
//            case PermissionConstants.PY_MENU:
//                PermissionMenu menu= BeanMapUtils.mapToBean(map,PermissionMenu.class);
//                menu.setId(null);
//                menu.setPreid(permission.getId());
//                PermissionMenu rmenu=permissionMenuDao.selectOne(menu);
//                rmenu.setMenuicon(menu.getMenuicon());
//                rmenu.setMenuorder(menu.getMenuorder());
//                result=result*permissionMenuDao.updateByPrimaryKeySelective(rmenu);
//                break;
//            case PermissionConstants.PY_POINT:
//                PermissionPoint point= BeanMapUtils.mapToBean(map,PermissionPoint.class);
//                point.setId(null);
//                point.setPreid(permission.getId());
//                PermissionPoint rpoint=permissionPointDao.selectOne(point);
//                rpoint.setPointclass(point.getPointclass());
//                rpoint.setPointicon(point.getPointicon());
//                rpoint.setPointstatus(point.getPointstatus());
//                result=result*permissionPointDao.updateByPrimaryKeySelective(rpoint);
//                break;
//            case PermissionConstants.PY_API:
//                PermissionApi api= BeanMapUtils.mapToBean(map,PermissionApi.class);
//                api.setId(null);
//                api.setPreid(permission.getId());
//                PermissionApi rapi=permissionApiDao.selectOne(api);
//                rapi.setApilevel(api.getApilevel());
//                rapi.setApimethod(api.getApimethod());
//                rapi.setApiurl(api.getApiurl());
//                result=result*permissionApiDao.updateByPrimaryKeySelective(rapi);
//                break;
//            default:
//                throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
//        }
//
//        //判断保存是否成功
//        if(result!=1){
//            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
//        }
//
//    }
//
//
//    //删除权限
//    public void deletePermission(Integer id) {
//       Integer result=1;
//        if(id==null){
//            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
//        }
//        //查找角色是否绑定权限
//        if(rolePermissionDao.countByPid(id)>0){
//            throw new ErpExcetpion(ExceptionEumn.PERMISSION_IS_BOUNDBY_THE_ROLE);
//        }
//
//        //删除权限表
//        Permission permission=permissionDao.selectByPrimaryKey(id);
//        if(permission==null){
//            throw new ErpExcetpion(ExceptionEumn.PERMISSION_IS_NOT_FOUND);
//        }
//        result=result*permissionDao.deleteByPrimaryKey(id);
//        //根据类型删除子权限表
//        Integer type=permission.getType();
//        switch (type){
//            case PermissionConstants.PY_MENU:
//                PermissionMenu menu=new PermissionMenu();
//                menu.setPreid(id);
//                result=result*permissionMenuDao.delete(menu);
//                break;
//            case PermissionConstants.PY_POINT:
//                PermissionPoint point=new PermissionPoint();
//                point.setPreid(id);
//                result=result*permissionPointDao.delete(point);
//                break;
//            case PermissionConstants.PY_API:
//                PermissionApi api=new PermissionApi();
//                api.setPreid(id);
//                result=result*permissionApiDao.delete(api);
//                break;
//        }
//
//        if(result!=1){
//            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
//        }
//    }
//
//
//
//    //查询权限
//    public PageResult<Permission> findByAll(Integer type,Integer pid) {
//        Example example = new Example(Permission.class);
//        Example.Criteria criteria = example.createCriteria();
//        //判断类型，0菜单+按钮，1菜单，2按钮，3API接口
//        switch (type){
//            case 0:
//                criteria.orEqualTo("type",1).orEqualTo("type",2);
//                break;
//            case 1:
//                criteria.andEqualTo("type",1);
//                break;
//            case 2:
//                criteria.andEqualTo("type",2);
//                break;
//            case 3:
//                criteria.andEqualTo("type",3);
//                break;
//            default:
//                throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
//        }
//
//        example.and(criteria);
//        //判断父ID是否为空
//        if(pid!=null){
//            criteria.andEqualTo("pid",pid);
//        }
//        //进行查询
//        List<Permission> list=permissionDao.selectByExample(example);
//        PageInfo<Permission> pageInfo=new PageInfo<>(list);
//        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(), list);
//    }





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
        if(permission==null|| permission.getCode()==null||permission.getMenuName()==null||permission.getpId()==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        //设置默认值

        if(permission.getSort()==null){
            permission.setSort(1);
        }
        if(permission.getIsMenu()==null){
            permission.setIsMenu(0);
        }
        return permission;
    }

}
