package com.targetmol.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.vo.PageResult;
import com.targetmol.dao.CompanyDao;
import com.targetmol.domain.Company;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyDao companyDao;

    public Company findByComid(String comid){
        Company newcom=new Company();
        newcom.setComid(comid);
        Company result=companyDao.selectOne(newcom);
        if(result==null ){
            throw new ErpExcetpion(ExceptionEumn.COMPANY_ISNOT_FOUND);
        }
        return result;
    }

    //查询所有Company
    public PageResult<Company> findByAll(Integer page, Integer pageSize, String softBy, Boolean desc, String key,Boolean showDelete) {
        //分页
        PageHelper.startPage(page,pageSize);
        //过滤
        Example example=new Example(Company.class);
        Example.Criteria criteria1=example.createCriteria();
        Example.Criteria criteria2=example.createCriteria();
        if(StringUtils.isNotEmpty(key)){
            criteria1.orLike("companyname","%"+key.trim()+"%")
                    .orEqualTo("comid",key.toUpperCase().trim());
            example.and(criteria1);
        }
        if(!showDelete){
            criteria2.orEqualTo("deltag",0).orEqualTo("deltag" ,null);
            example.and(criteria2);
        }
        //排序
        if(StringUtils.isNotBlank(softBy)) {
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


    //添加
    @Transactional
    public Company save(Company company) {
        //判断是否为空
        if(company==null || company.getCompanyname()==null ){
            throw new ErpExcetpion(ExceptionEumn.COMPANYNAME_CANNOT_BE_NULL);
        }

        //判断公司名是否存在
        Company c1=new Company();
        c1.setCompanyname(company.getCompanyname());
        Company com=companyDao.selectOne(c1);
        System.out.println(com);
        if(com != null){
            throw new ErpExcetpion(ExceptionEumn.COMPANYNAME_ALREADY_EXISTS);
        }
        company.setCreatime( new Timestamp(new Date().getTime()));
        //保存
        Integer rs=companyDao.insert(company);
        if (rs!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

        return company;
    }
    //修改

    // 删除
}
