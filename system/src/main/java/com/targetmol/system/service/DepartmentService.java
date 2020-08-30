package com.targetmol.system.service;

import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.domain.Department;
import com.targetmol.system.dao.DepartmentDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private  UserService userService;

    //查询所有部门
    public List<Department> findByAll(String softBy, Boolean desc) {

        Example example=new Example(Department.class);

        //排序
        if(StringUtils.isNotBlank(softBy)) {
            String orderByClause=softBy+(desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        return  departmentDao.selectByExample(example);
    }

    //按ID查询部门
    public Department findById(Integer depaid){
        return departmentDao.selectByPrimaryKey(depaid);
    }

    //新增部门
    public Department addDepartment(Department department) {
        //检查部门数据
        checkDepartmentProperty(department);
        //检查部门名称是否存在
        findRepeatName(department);
        //获取部门Level
        department.setLevel(getDepartmentLevel(department));
        //保存
        if(departmentDao.insert(department)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

        return findById(department.getDepaid());
    }
    //查询部门名称是否重复
    private void findRepeatName(Department department){
        if(department!=null && StringUtil.isNotEmpty(department.getName())){
            Example example=new Example(Department.class);
            Example.Criteria criteria1=example.createCriteria();
            criteria1.andEqualTo("name",department.getName())
            .andEqualTo("pid",department.getPid());
            if(department.getDepaid()!=null){
                criteria1.andNotEqualTo("depaid",department.getDepaid());
            }
            example.and(criteria1);
            if(departmentDao.selectByExample(example).size()>0){
                throw new ErpExcetpion(ExceptionEumn.DEPARTMENTNAME_ALREADY_EXISTS);
            }

        }
    }

    //根据pid获取部门LEVEL
    private Integer getDepartmentLevel(Department department){
        Integer level=0;
        if(department!=null){
            while (department.getPid()!=null){
                department=findById(department.getPid());
                if(department!=null){
                    level+=1;
                }else{
                    throw new ErpExcetpion(ExceptionEumn.DEPARTMENTID_IS_NOT_FOUND);
                }
            }
        }
        return  level;
    }

    //检查部门参数
    private void checkDepartmentProperty(Department department){
        if(department==null || StringUtil.isEmpty(department.getName())){
                throw  new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
    }

    //更新部门
    public Department updateDepartment(Department department) {
        //检查部门数据
        checkDepartmentProperty(department);
        //检查部门名称是否存在
        findRepeatName(department);
        //获取部门Level
        department.setLevel(getDepartmentLevel(department));
        //保存
        if(departmentDao.updateByPrimaryKeySelective(department)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

        return findById(department.getDepaid());
    }


    //删除部门
    public void delDepartment(Integer depaid) {
        if(depaid==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        //查询ID是否存在
        if(findById(depaid)==null){
            throw new ErpExcetpion(ExceptionEumn.DEPARTMENTID_IS_NOT_FOUND);
        }
        //是否包含子部门
        if(findSubDepartment(depaid).size()>0){
            throw new ErpExcetpion(ExceptionEumn.FOUND_SUB_DEPARTMENT);
        }
        //查询部门是否包含用户
        if(userService.findByDepaId(depaid).size()>0){
            throw new ErpExcetpion(ExceptionEumn.FOUND_USERS);
        }
        //删除
        if(departmentDao.deleteByPrimaryKey(depaid)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }

    }

    //查找所有子部门
    private List<Department>  findSubDepartment(Integer depaid){
        List<Department> list=new ArrayList<Department>();
        if(depaid!=null){
            Example example=new Example(Department.class);
            Example.Criteria criteria1=example.createCriteria();
            criteria1.andEqualTo("pid",depaid);
            example.and(criteria1);
            list=departmentDao.selectByExample(example);
        }

        return list;
    }
}
