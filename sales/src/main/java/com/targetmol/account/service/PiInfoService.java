package com.targetmol.account.service;

import com.targetmol.account.dao.AccountGradeDao;
import com.targetmol.account.dao.PiInfoDao;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.domain.sales.AccountGrade;
import com.targetmol.domain.sales.PiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

public class PiInfoService {
    @Autowired
    private PiInfoDao piInfoDao;
    //查找所有
    public List<PiInfo> findAll() {
        return piInfoDao.selectAll();
    }

    //根据ID查找
    public PiInfo findById(Integer id) {
        return piInfoDao.selectByPrimaryKey(id);
    }
    //添加
    public void addnew(PiInfo piInfo) {
        if(StringUtil.isEmpty(piInfo.getPi())){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        if(piInfoDao.insert(piInfo)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
    }
    //修改
    public void update(PiInfo piInfo) {
        if(StringUtil.isEmpty(piInfo.getPi())){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        if(piInfoDao.selectByPrimaryKey(piInfo.getId())==null){
            throw new ErpExcetpion(ExceptionEumn.PI_IS_NOT_FOUND);
        }
        if(piInfoDao.updateByPrimaryKey(piInfo)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

    }
    //删除
    public void delete(Integer id) {
        if(piInfoDao.selectByPrimaryKey(id)==null){
            throw new ErpExcetpion(ExceptionEumn.PI_IS_NOT_FOUND);
        }
        if(piInfoDao.deleteByPrimaryKey(id)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }
    }
}
