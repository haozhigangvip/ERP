package com.targetmol.sales.service.Account;

import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.sales.dao.Account.AreaDao;
import com.targetmol.domain.sales.Account.Area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
@Transactional(rollbackFor = {Exception.class, ErpExcetpion.class})
public class AreaService {
    @Autowired
    private AreaDao areaDao;

    //查找所有国家
    public List<Area> findAllCountry(String key) {
        Example example=new Example(Area.class);
        Example.Criteria criteria1=example.createCriteria();
        Example.Criteria criteria2=example.createCriteria();
        if(StringUtil.isNotEmpty(key)){
            criteria1.orLike("name","%"+key.trim()+"%")
                    .orLike("pingyin","%"+key.toUpperCase().trim()+"%")
                    .orEqualTo("citycode",key.trim());
            example.and(criteria1);
        }
        criteria2.andEqualTo("level",0);
        example.and(criteria2);
        example.setOrderByClause("pingyin asc");
        return areaDao.selectByExample(example);
    }

    //查找所有地区
    public List<Area> findAllState(Integer cid,String key) {
        Example example=new Example(Area.class);
        Example.Criteria criteria1=example.createCriteria();
        Example.Criteria criteria2=example.createCriteria();
        if(StringUtil.isNotEmpty(key)){
            criteria1.orLike("name","%"+key.trim()+"%")
                    .orLike("pingyin","%"+key.toUpperCase().trim()+"%")
                    .orEqualTo("citycode",key.trim());
            example.and(criteria1);
        }
        criteria2.andEqualTo("level",1).andEqualTo("pid",cid);
        example.and(criteria2);
        example.setOrderByClause("pingyin asc");
        return areaDao.selectByExample(example);
    }
    //查找所有地区
    public List<Area> findAllCity(Integer sid,String key) {
        Example example=new Example(Area.class);
        Example.Criteria criteria1=example.createCriteria();
        Example.Criteria criteria2=example.createCriteria();
        if(StringUtil.isNotEmpty(key)){
            criteria1.orLike("name","%"+key.trim()+"%")
                    .orLike("pingyin","%"+key.toUpperCase().trim()+"%")
                    .orEqualTo("citycode",key.trim());
            example.and(criteria1);
        }
        criteria2.andEqualTo("level",2).andEqualTo("pid",sid);
        example.and(criteria2);
        example.setOrderByClause("pingyin asc");
        return areaDao.selectByExample(example);
    }
}
