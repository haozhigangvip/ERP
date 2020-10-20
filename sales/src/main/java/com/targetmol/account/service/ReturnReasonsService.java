package com.targetmol.account.service;

import com.targetmol.account.dao.ReturnReasonsDao;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.domain.sales.ReturnReasons;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

public class ReturnReasonsService {
    @Autowired
    private ReturnReasonsDao returnReasonsDao;
    //查找所有
    public List<ReturnReasons> findAll() {
        return returnReasonsDao.selectAll();
    }

    //根据ID查找
    public ReturnReasons findById(Integer id) {
        return returnReasonsDao.selectByPrimaryKey(id);
    }
    //添加
    public void addnew(ReturnReasons returnReasons) {
        if(StringUtil.isEmpty(returnReasons.getReasons())){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        if(returnReasonsDao.insert(returnReasons)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
    }
    //修改
    public void update(ReturnReasons returnReasons) {
        if(StringUtil.isEmpty(returnReasons.getReasons())){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        if(returnReasonsDao.selectByPrimaryKey(returnReasons.getId())==null){
            throw new ErpExcetpion(ExceptionEumn.RETURN_REASON_IS_NOT_FOUND);
        }
        if(returnReasonsDao.updateByPrimaryKey(returnReasons)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

    }
    //删除
    public void delete(Integer id) {
        if(returnReasonsDao.selectByPrimaryKey(id)==null){
            throw new ErpExcetpion(ExceptionEumn.RETURN_REASON_IS_NOT_FOUND);
        }
        if(returnReasonsDao.deleteByPrimaryKey(id)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }
    }
}
