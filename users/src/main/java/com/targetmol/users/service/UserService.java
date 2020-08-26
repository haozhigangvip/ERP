package com.targetmol.users.service;

import com.github.pagehelper.PageInfo;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.vo.PageResult;
import com.targetmol.domain.User;
import com.targetmol.users.dao.UserDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    public PageResult<User> findByAll(Integer page, Integer pageSize, String softBy, Boolean desc, String key,Boolean showUnActive){
        PageInfo info=new PageInfo();

        Example example=new Example(User.class);
        Example.Criteria criteria1=example.createCriteria();
        Example.Criteria criteria2=example.createCriteria();
        if(StringUtils.isNotEmpty(key)){
            criteria1.orLike("username","%"+key.trim()+"%")
                    .orLike("name","%"+key.trim()+"%")
                    .orEqualTo("uid",key.toUpperCase().trim());
            example.and(criteria1);
        }
        if(!showUnActive){
            criteria2.orEqualTo("deltag",0).orEqualTo("deltag" ,null);
            example.and(criteria2);
        }
        //排序
        if(StringUtils.isNotBlank(softBy)) {
            String orderByClause=softBy+(desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        //进行查询
        List<User> list=userDao.selectByExample(example);
        if(list ==null ||list.size()==0){
            throw new ErpExcetpion(ExceptionEumn.USERS_ISNOT_FOUND);
        }
        //封装到pageHelper
        PageInfo<User> pageInfo=new PageInfo<User>(list);
        return new PageResult<User>(pageInfo.getTotal(),pageInfo.getPages(), list);
    }
}
