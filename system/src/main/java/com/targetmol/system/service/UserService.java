package com.targetmol.system.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.utils.BCryptUtil;
import com.targetmol.common.vo.PageResult;
import com.targetmol.domain.system.Role;
import com.targetmol.domain.system.User;
import com.targetmol.domain.system.User_ROLE;
import com.targetmol.domain.system.ext.AuthUserExt;
import com.targetmol.domain.system.ext.UserExt;
import com.targetmol.system.dao.RoleDao;
import com.targetmol.system.dao.UserDao;
import com.targetmol.system.dao.UserRoleDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionService permissionService;

    //查询所有用
    public PageResult<UserExt> findByAll(Integer page, Integer pageSize, String softBy, Boolean desc, String key,Integer active,Boolean showsales){
        Page pg=PageHelper.startPage(page,pageSize);

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
        List<UserExt> nlist=new ArrayList<UserExt>();
        //遍历用户集，获取部门
        for (User user:list) {
            //查询部门
            UserExt userExt=new UserExt();
            BeanUtils.copyProperties(user,userExt);
            userExt.setPassword(null);
            userExt.setDepartment(departmentService.findById(user.getDepartmentid()));
            nlist.add(userExt);
        }
        Integer totalSize=nlist.size();
        //封装到pageHelper
        PageInfo<UserExt> pageInfo=new PageInfo<>(pg.getResult());
        return new PageResult<UserExt>((long)pageInfo.getPageNum(),pageInfo.getPages(), nlist);
    }

    //按ID查询用户
    public  User findById(Integer uid) throws Exception{
        User result=userDao.selectByPrimaryKey(uid);
        UserExt user=new UserExt();
        if(result!=null){
            BeanUtils.copyProperties(result,user);
            //查询部门
            user.setDepartment(departmentService.findById(result.getDepartmentid()));
            //查询权限
            User_ROLE u=new User_ROLE();
            u.setUid(uid);
            List<User_ROLE> rids=userRoleDao.select(u);
            List<Role> roles=new ArrayList<Role>();
            for(User_ROLE ur1:rids) {
                Role role=roleService.findById(ur1.getRid());
                if(role!=null){
                    roles.add(role);
                }
            }
            user.setRoles(roles);

        }
        return  user;
    }

    //新增用户
    public void addUser(User user) {
        //检查用户数据
        checkUserProperty(user);
        //设置用户状态，1为在职，0为离职
        user.setActivated(1);
        if(user.getOnsales()==null){
            user.setOnsales(0);
        }

        //检查用户名是否存在
         if(findByUsername(user).size()>0){
             throw new ErpExcetpion(ExceptionEumn.USERNAME_ALREADY_EXISTS);
         }

        //检查部门是否存在
        checkDerprtmentId(user.getDepartmentid());
        //加密密码
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String npassword=bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(npassword);
        //保存
        if(userDao.insert(user)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
//        return findById(user.getUid());
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
        if(user==null|| StringUtil.isEmpty(user.getUsername())==true||StringUtil.isEmpty(user.getPassword())==true){
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
    public void updateUser(User user) {

        //检查用户数据
        checkUserProperty(user);

        if(userDao.selectByPrimaryKey(user.getUid())==null){
            throw new ErpExcetpion(ExceptionEumn.USERS_ISNOT_FOUND);
        }

        //检查部门是否存在
        checkDerprtmentId(user.getDepartmentid());

        //加密密码
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String npassword=bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(npassword);


        //不允许修改用户名和激活标记
        user.setUsername(null);
        user.setActivated(null);
        checkDerprtmentId(user.getDepartmentid());

        //保存
        if(userDao.updateByPrimaryKeySelective(user)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
//        return findById(user.getUid());

    }

    //更新激活标记
    public void updateActive(Integer uid, Integer active) {
        if (uid == null || active == null){
             throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        User user=userDao.selectByPrimaryKey(uid);
        if(user==null){
            throw new ErpExcetpion(ExceptionEumn.USERS_ISNOT_FOUND);
        }
        if(active>1 || active<0){
            throw  new ErpExcetpion((ExceptionEumn.OBJECT_VALUE_ERROR));
        }
        user.setActivated(active);
        if(userDao.updateByPrimaryKey(user)!=1){
            throw new  ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

    }

    //登录
    public AuthUserExt login(String username){
        if(StringUtil.isEmpty(username)){
            return null;
        }
        //查询用户
        User user=new User();
        user.setUsername(username);
        user=userDao.selectOne(user);
        if(user==null){
            throw new ErpExcetpion(ExceptionEumn.USERNAMEANDPASSWORD_ISNOT_MATCH);
        }
        AuthUserExt resultUser=new AuthUserExt();
        BeanUtils.copyProperties(user,resultUser);
        //查询权限
        resultUser.setPermissions(permissionService.getByUserId(user.getUid()));

        return resultUser;
    }

    //修改密码
    public void updatePassword(Integer uid, String password) {
        if(uid==null || password==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        User user=userDao.selectByPrimaryKey(uid);

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
            user.setActivated(1);
            user.setDepartmentid(depaid);
            list=userDao.select(user);
        }
        return list;
    }
    //分配角色
    public void assignRoles(Integer uid, List<Integer> rolesids) {
        //1、根据id查询用户
        User user =userDao.selectByPrimaryKey(uid);
        if(user==null){
            throw new ErpExcetpion(ExceptionEumn.USERS_ISNOT_FOUND);
        }

        //2.删除user_role中间表中所有的该用户角色
        Example example=new Example(User_ROLE.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("uid",uid);
        example.and(criteria);
        userRoleDao.deleteByExample(example);

        //3、更新USER_ROLE中间表
        for (Integer rid:rolesids) {
           Role role= roleDao.selectByPrimaryKey(rid);
           if(role==null){
               throw new ErpExcetpion(ExceptionEumn.ROLE_IS_NOT_FOUND);
           }
           User_ROLE user_role=new User_ROLE();
           user_role.setRid(rid);
           user_role.setUid(uid);
           //保存用户角色到中间表
           if(userRoleDao.insert(user_role)!=1){
              throw  new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
           }

        }
    }
    //查找所有在职销售
    public List<Map<String,Object>> findAllSales() {

        return userDao.getSales();
    }

    public User findByDdId(String ddid,String code) {
        Example example=new Example(User.class);
        example.and(example.createCriteria()
                .andEqualTo("ddid",ddid)
                    .andEqualTo("activated",true));
        User user=  userDao.selectOneByExample(example);
        if(user!=null){
            user.setDcode(BCryptUtil.encode(code));
            userDao.updateByPrimaryKey(user);
        }
        return user;
    }
    //动态根据用户名将密码传递给dcode
    public void refreshCode(String username) {
        User u1=new User();
        u1.setUsername(username);
        User user=userDao.selectOne(u1);
        if(user!=null){
            user.setDcode(user.getPassword());
            userDao.updateByPrimaryKeySelective(user);
        }

    }
}

