package com.targetmol.system.service;

import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.domain.system.Area;

import com.targetmol.system.dao.AreaDao;
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
    public List<Area> findAllArea(String code,String key) {
        Boolean isChinese=true;
        if(code==null||code.indexOf("AREA-101")<0){
            isChinese=false;
        }
        return areaDao.findAllByAnyPara(code,key,isChinese);
    }


}
