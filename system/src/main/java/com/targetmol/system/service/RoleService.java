package com.targetmol.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.vo.PageResult;
import com.targetmol.domain.system.Role;
import com.targetmol.system.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    //查询角色
    public PageResult<Role> findByAll(Integer page, Integer pageSize, String softBy, Boolean desc, String key) {
        PageHelper.startPage(page,pageSize);

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
        PageInfo<Role> pageInfo=new PageInfo<Role>(list);
        return new PageResult<Role>(pageInfo.getTotal(),pageInfo.getPages(), list);

    }

    //添加角色
    public Role addRole(Role role) {
        //检查role参数是否为空
        checkRole(role);
        //检查role名称是否重复
        checkRepeatRoleName(role);
        //保存
        roleDao.insert(role);
        return (findById(role.getRid()));
    }

    //修改角色
    public Role updateRole(Role role) {
        //检查role参数是否为空
        checkRole(role);
        //检查role名称是否重复
        checkRepeatRoleName(role);
        //保存
        roleDao.updateByPrimaryKeySelective(role);
        return (findById(role.getRid()));
    }

    //根据ID查询角色
    public Role findById(Integer rid) {
        if(rid==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        return (roleDao.selectByPrimaryKey(rid));
    }

    //根据ID删除角色
    public void deleteById(Integer rid) {
       Role role =findById(rid);
       if(role==null){
            throw new ErpExcetpion(ExceptionEumn.ROLE_IS_NOT_FOUND);
       }else{
            //查找角色是否绑定用户
           //查找角色是否绑定权限
           roleDao.delete(role);
       }

    }


    //判断角色参数
    private void checkRole(Role role){
        if(role==null|| StringUtil.isEmpty( role.getRollname())){
            throw  new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
    }

    //判断角色名是否存在
    private void checkRepeatRoleName(Role role){

        Example example=new Example(Role.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("rolename",role.getRollname());
        if(role.getRid()!=null){
            criteria.andNotEqualTo("rid",role.getRid());
        }
        example.and(criteria);
        if(roleDao.selectByExample(example)!=null){
            throw new ErpExcetpion(ExceptionEumn.ROLENAME_ALREADY_EXISTS);
        }
    }
}
