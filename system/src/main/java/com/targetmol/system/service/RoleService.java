package com.targetmol.system.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.utils.PermissionConstants;
import com.targetmol.common.vo.PageResult;
import com.targetmol.domain.system.*;
import com.targetmol.system.dao.PermissionDao;
import com.targetmol.system.dao.RoleDao;
import com.targetmol.system.dao.RolePermissionDao;
import com.targetmol.system.dao.UserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private PermissionDao permissionDao;
//
//    @Autowired
//    private PermissionService permissionService;

    //查询角色
    public PageResult<Role> findByAll(Integer page, Integer pageSize, String softBy, Boolean desc, String key) {
        Page pg=PageHelper.startPage(page,pageSize);

        Example example=new Example(Role.class);
        Example.Criteria criteria1=example.createCriteria();

        if(StringUtil.isNotEmpty(key)){
            criteria1.orLike("rolename","%"+key.trim()+"%")
                    .orEqualTo("rid",key.toUpperCase().trim());
            example.and(criteria1);
        }
        //排序
        if(StringUtil.isNotEmpty(softBy)) {
            String orderByClause=softBy+(desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        //进行查询
        List<Role> list=roleDao.selectByExample(example);

        //封装到pageHelper
        PageInfo<Role> pageInfo=new PageInfo<Role>(pg.getResult());
        return new PageResult<Role>(pageInfo.getTotal(),pageInfo.getPages(), list);

    }

    //添加角色
    public void addRole(Role role) {
        //检查role参数是否为空
        checkRole(role);
        //检查role名称是否重复
        checkRepeatRoleName(role);
        //保存
        if(roleDao.insert(role)!=1){
         throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
        //return (findById(role.getRid()));
    }

    //修改角色
    public void updateRole(Role role) {
        //检查role参数是否为空
        checkRole(role);
        //检查role名称是否重复
        checkRepeatRoleName(role);
        //保存
        if(roleDao.updateByPrimaryKeySelective(role)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
//        return (findById(role.getRid()));
    }

    //根据ID查询角色
    public Role findById(Integer rid)  throws Exception{
        if(rid==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        Role role=new Role();
        role.setRid(rid);
        role=roleDao.selectOne(role);
        if(role!=null){
            //查询权限集合
           List<Integer> pids=rolePermissionDao.findByRid(rid);
           List<Map<String,Object>> permissions=new ArrayList<Map<String, Object>>();
//            for (Integer pid:pids) {
//               permissions.add(permissionService.findById(pid));
//            }
            role.setPermissions(permissions);

        }

        return (role);
    }

    //根据ID删除角色
    public void deleteById(Integer rid) {
       Role role =roleDao.selectByPrimaryKey(rid);
       if(role==null){
            throw new ErpExcetpion(ExceptionEumn.ROLE_IS_NOT_FOUND);
       }else{
            //查找角色是否绑定用户
           if(userRoleDao.countByRid(rid)>0){
               throw new ErpExcetpion(ExceptionEumn.ROLE_IS_BOUNDBY_USERS);
           }

           roleDao.delete(role);
       }

    }


    //判断角色参数
    private void checkRole(Role role){
        if(role==null|| StringUtil.isEmpty( role.getRolename())){
            throw  new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
    }

    //判断角色名是否存在
    private void checkRepeatRoleName(Role role){

        Example example=new Example(Role.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("rolename",role.getRolename());
            if(role.getRid()!=null){
            criteria.andNotEqualTo("rid",role.getRid());
        }
        example.and(criteria);
        if(roleDao.selectByExample(example).size()>0){
            throw new ErpExcetpion(ExceptionEumn.ROLENAME_ALREADY_EXISTS);
        }
    }
    //绑定权限
    public void assignRoles(Integer rid, List<Integer> permsids) {
        if(rid==null || permsids==null||permsids.size()<=0){
            throw  new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        //1、根据id查询用户
        Role role =roleDao.selectByPrimaryKey(rid);
        if(role==null){
            throw new ErpExcetpion(ExceptionEumn.ROLE_IS_NOT_FOUND);
        }

        //2.删除role_permisssion中间表中所有的该用户角色
        Example example=new Example(Role_Permission.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("rid",rid);
        example.and(criteria);
        rolePermissionDao.deleteByExample(example);

        //3. 更新role_permisssion中间表

        for (Integer pid:permsids) {
            Permission permission= permissionDao.selectByPrimaryKey(pid);
            if(permission==null){
                throw new ErpExcetpion(ExceptionEumn.PERMISSION_IS_NOT_FOUND);
            }
            Role_Permission role_permission=new Role_Permission();
            role_permission.setRoleId(rid);
            role_permission.setPid(pid);
            //保存角色权限到中间表
            if(rolePermissionDao.insert(role_permission)!=1){
                throw  new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
            }
            //自动将对应权限的API权限绑定到中间表
            List<Integer>  apiPermissions=permissionDao.findbyPid(pid);
            for (Integer  apipid:apiPermissions) {
                Role_Permission api_role_permission=new Role_Permission();
                api_role_permission.setRoleId(rid);
                api_role_permission.setPid(apipid);
                if(rolePermissionDao.insert(api_role_permission)!=1){
                    throw  new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
                }
            }

        }
    }
}
