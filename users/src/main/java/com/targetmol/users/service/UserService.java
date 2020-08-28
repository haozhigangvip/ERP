package com.targetmol.users.service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.vo.PageResult;
import com.targetmol.domain.User;
import com.targetmol.users.dao.UserDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserDao userDao;
    public PageResult<User> findByAll(Integer page, Integer pageSize, String softBy, Boolean desc, String key,Integer active,Boolean showsales){
        PageInfo info=new PageInfo();

        Example example=new Example(User.class);
        Example.Criteria criteria1=example.createCriteria();
        Example.Criteria criteria2=example.createCriteria();
        Example.Criteria criteria3=example.createCriteria();
        if(StringUtils.isNotEmpty(key)){
            criteria1.orLike("username","%"+key.trim()+"%")
                    .orLike("name","%"+key.trim()+"%")
                    .orEqualTo("uid",key.toUpperCase().trim());
            example.and(criteria1);
        }

        //是否激活
        switch (active){
            case 0:

                break;
            case 1:
                criteria2.orEqualTo("actived",1)
                        .orEqualTo("actived",null);
                example.and(criteria2);
                break;
            case 2:
                criteria2.orEqualTo("actived",0)
                        .orEqualTo("actived",null);
                example.and(criteria2);
                break;
            default:
                throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);

        }

        //是否销售
        if(showsales==true){
            criteria3.orEqualTo("issales",1);
            example.and(criteria3);
        }

        //排序
        if(StringUtils.isNotBlank(softBy)) {
            String orderByClause=softBy+(desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        //进行查询
        List<User> list=userDao.selectByExample(example);
        for (User user:list) {
            //查询部门

            //查询权限
        }

        //封装到pageHelper
        PageInfo<User> pageInfo=new PageInfo<User>(list);
        return new PageResult<User>(pageInfo.getTotal(),pageInfo.getPages(), list);
    }

    //按ID查询用户
    public  User findById(Integer uid){
        User result=userDao.selectByPrimaryKey(uid);
        if(result!=null){
            //查询部门

            //查询权限

        }
        return  result;
    }

    //新增用户
    public User addUser(User user) {
        //检查用户数据
        checkUserProperty(user);

        //设置用户状态，1为在职，0为离职
        user.setActived(1);

        //检查用户名是否存在
        if(findByUsername(user).size()>0){
            throw new ErpExcetpion(ExceptionEumn.USERNAME_ALREADY_EXISTS) ;
        }

        //保存
        if(userDao.insert(user)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

        return findById(user.getUid());
    }




    //判断USER数据是否齐全
    public  void checkUserProperty(User user){
        //判断参数是否齐全
        if(user==null|| StringUtil.isEmpty(user.getName())==true||StringUtil.isEmpty(user.getPassword())){
            throw new ErpExcetpion(ExceptionEumn.USERNAME_PASSWORD_CANNOT_BE_NULL);
        }
        if(StringUtil.isEmpty(user.getEmail())){
            throw new ErpExcetpion(ExceptionEumn.EMAIL_CANNOT_BE_NULL);
        }




    }

    //查询按用户名查询
    public List<User> findByUsername(User user){
       return( userDao.findUserbyUsername(user.getUid(),user.getUsername()));
    }


    //更新用户
    public User updateUser(User user) {
        checkUserProperty(user);
        //检查用户名及邮箱是否已存在

        //检查部门是否存在

        //保存
        if(userDao.updateByPrimaryKeySelective(user)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
        return findById(user.getUid());

    }

    //更新激活标记
    public void updateActive(Integer uid, Integer active) {
        if (uid == null || active == null){
             throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        User user=findById(uid);
        if(user==null){
            throw new ErpExcetpion(ExceptionEumn.USERS_ISNOT_FOUND);
        }
        if(active>1 || active<0){
            throw  new ErpExcetpion((ExceptionEumn.OBJECT_VALUE_ERROR));
        }
        user.setActived(active);
        if(userDao.updateByPrimaryKey(user)!=1){
            throw new  ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

    }

    //登录
    public void login(String username,String password){
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        if(userDao.selectOne(user)==null){
            throw new ErpExcetpion(ExceptionEumn.USERNAMEANDPASSWORD_ISNOT_MATCH);
        }
    }

    //修改密码
    public void updatePassword(Integer uid, String password) {
        if(uid==null || password==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        User user=findById(uid);
        if(user==null){
            throw new ErpExcetpion(ExceptionEumn.USERS_ISNOT_FOUND);
        }
        user.setPassword(password);
        if(userDao.updateByPrimaryKeySelective(user)!=1){
            throw  new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

    }
}

