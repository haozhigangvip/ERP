package com.targetmol.sales.service;

import com.targetmol.sales.dao.OrderSourceDao;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.domain.sales.OrderSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
@Service
public class OrderSourceService {
    @Autowired
    private OrderSourceDao orderSourceDao;
    //查找所有
    public List<OrderSource> findAll() {
        return orderSourceDao.selectAll();
    }

    //根据ID查找
    public OrderSource findById(Integer id) {
        return orderSourceDao.selectByPrimaryKey(id);
    }
    //添加
    public void addnew(OrderSource orderSource) {
        if(StringUtil.isEmpty(orderSource.getSourcename())){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        if(orderSourceDao.insert(orderSource)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
    }
    //修改
    public void update(OrderSource orderSource) {
        if(StringUtil.isEmpty(orderSource.getSourcename())){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        if(orderSourceDao.selectByPrimaryKey(orderSource.getId())==null){
            throw new ErpExcetpion(ExceptionEumn.ORDER_SOURCE_IS_NOT_FOUND);
        }
        if(orderSourceDao.updateByPrimaryKey(orderSource)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

    }
    //删除
    public void delete(Integer id) {
        if(orderSourceDao.selectByPrimaryKey(id)==null){
            throw new ErpExcetpion(ExceptionEumn.ORDER_SOURCE_IS_NOT_FOUND);
        }
        if(orderSourceDao.deleteByPrimaryKey(id)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }
    }
}
