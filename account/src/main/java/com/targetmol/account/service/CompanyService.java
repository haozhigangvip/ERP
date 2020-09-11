package com.targetmol.account.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.targetmol.account.dao.CompanyDao;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.vo.PageResult;
import com.targetmol.domain.account.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyDao companyDao;

    //按id查询company
    public Company findById(Integer companyid){

        Company result=companyDao.selectByPrimaryKey(companyid);
        if(result==null ){
            throw new ErpExcetpion(ExceptionEumn.COMPANY_ISNOT_FOUND);
        }
        return result;
    }



//    //按comid查询company
//    public Company findByComId(Integer comid){
//        Company company=new Company();
//        company.setCompanyid(comid);
//        Company result=companyDao.selectOne(company);
//        if(result==null ){
//            throw new ErpExcetpion(ExceptionEumn.COMPANY_ISNOT_FOUND);
//        }
//        return result;
//    }

    //查询所有Company
    public PageResult<Company> findByAll(Integer page, Integer pageSize, String softBy, Boolean desc, String key,Integer actived) {
        //分页
        PageHelper.startPage(page,pageSize);
        //过滤
        Example example=new Example(Company.class);
        Example.Criteria criteria1=example.createCriteria();
        Example.Criteria criteria2=example.createCriteria();
        if(StringUtil.isNotEmpty(key)){
            criteria1.orLike("companyname","%"+key.trim()+"%")
                    .orEqualTo("comid",key.toUpperCase().trim());
            example.and(criteria1);
        }
        if(actived==1){
            criteria2.orEqualTo("activated",1);
            example.and(criteria2);
        }
        //排序
        if(StringUtil.isNotEmpty(softBy)) {
            String orderByClause=softBy+(desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        //进行查询
        List<Company> list=companyDao.selectByExample(example);
        if(list ==null ||list.size()==0){
            throw new ErpExcetpion(ExceptionEumn.COMPANYS_ISNOT_FOUND);
        }
        //封装到pageHelper
        PageInfo<Company> pageInfo=new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(), list);
    }


    //添加Company
    @Transactional
    public void addCompany(Company company) {
        //判断是否为空
        checkCompany(company);

        //判断公司名是否存在
        Company c1=new Company();
        c1.setCompanyname(company.getCompanyname());
        Company com=companyDao.selectOne(c1);
        if(com != null){
            throw new ErpExcetpion(ExceptionEumn.COMPANYNAME_ALREADY_EXISTS);
        }
        company.setCreatime( new Timestamp(new Date().getTime()));
        company.setActivated(1);
        //保存

        Integer rs=companyDao.insert(company);
        if (rs!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
    }

    //修改Company
    public void updateCompany(Company company) {
        //检查company数据是否为空
        checkCompany(company);
        //检查company是否存在
        if(companyDao.findRepeatCompanyName(company.getCompanyid(),company.getCompanyname())!=null){
            throw new ErpExcetpion(ExceptionEumn.COMPANYNAME_ALREADY_EXISTS);
        }
        if(companyDao.updateByPrimaryKeySelective(company)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
//        return companyDao.selectByPrimaryKey(company.getAutoid());
    }

    //检查要保存的COMPANY
    public void checkCompany(Company company){
        if(company==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        if(company.getCompanyname()==null ||company.getCompanyname()=="") {
            throw  new ErpExcetpion(ExceptionEumn.NAME_CANNOT_BE_NULL);
        }
        if(company.getSaleid()==null){
            throw new ErpExcetpion(ExceptionEumn.SALESMAN_CANNOT_BE_NULL);
        }

    }


    //冻结单位 0为冻结，1为激活
    public void setactive(Integer companyid, int active) {
        Company company=    companyDao.selectByPrimaryKey(companyid);
        if (company==null){
            throw  new ErpExcetpion(ExceptionEumn.COMPANY_ISNOT_FOUND);
        }
        company.setActivated(active);
        if(companyDao.updateByPrimaryKeySelective(company)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }
    }
}
