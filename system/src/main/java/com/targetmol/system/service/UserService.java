package com.targetmol.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.vo.PageResult;
import com.targetmol.domain.system.User;
import com.targetmol.system.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private DepartmentService departmentService;

    //查询所有用
    public PageResult<User> findByAll(Integer page, Integer pageSize, String softBy, Boolean desc, String key,Integer active,Boolean showsales){
        PageHelper.startPage(page,pageSize);

            Example example=new Example(User.class);
            Example.Criteria criteria1=example.createCriteria();
            Example.Criteria criteria2=example.createCriteria();
            Example.Criteria criteria3=example.createCriteria();
            if(StringUtil.isNotEmpty(key)){
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
        if(StringUtil.isNotEmpty(softBy)) {
            String orderByClause=softBy+(desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        //进行查询
        List<User> list=userDao.selectByExample(example);
        //遍历用户集，获取部门和权限
        for (User user:list) {
            //查询部门
            user.setDepartment(departmentService.findById(user.getDepartmentid()));
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
            result.setDepartment(departmentService.findById(result.getDepartmentid()));
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
             throw new ErpExcetpion(ExceptionEumn.USERNAME_ALREADY_EXISTS);
         }

        //检查部门是否存在
        checkDerprtmentId(user.getDepartmentid());

        //保存
        if(userDao.insert(user)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

        return findById(user.getUid());
    }


    //判断部门是否存在
    private void  checkDerprtmentId(Integer depaid){
        if(depaid!=null && departmentService.findById(depaid)==null){
            throw new ErpExcetpion(ExceptionEumn.DEPARTMENTID_IS_NOT_FOUND);
        }
    }

    //判断USER数据是否齐全
    private  void checkUserProperty(User user){
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
        //检查用户名是否已存在
        if(findByUsername(user).size()>0){
            throw new ErpExcetpion(ExceptionEumn.USERNAME_ALREADY_EXISTS);
        }
        //检查部门是否存在
        checkDerprtmentId(user.getDepartmentid());
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

    //查询指定部门用户
    public List<User> findByDepaId(Integer depaid){
        List<User> list=new ArrayList<User>();
        if(depaid!=null){
            User user=new User();
            user.setActived(1);
            user.setDepartmentid(depaid);
            list=userDao.select(user);
        }
        return list;
    }
}

