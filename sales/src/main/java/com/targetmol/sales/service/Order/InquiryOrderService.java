package com.targetmol.sales.service.Order;

import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.domain.sales.Order.InquiryOrder;
import com.targetmol.sales.dao.Order.InquiryOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class InquiryOrderService {
    @Autowired
    private InquiryOrderDao inquiryOrderDao;
    //查找所有
    public List<InquiryOrder> findAll() {
        return inquiryOrderDao.selectAll();
    }

    //根据ID查找
    public InquiryOrder findById(Integer id) {
        return inquiryOrderDao.selectByPrimaryKey(id);
    }

    //添加
    public void addnew(InquiryOrder inquiryOrder) {
      checkInquriyOrder(inquiryOrder);
        if(inquiryOrderDao.insert(inquiryOrder)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
    }
    //修改
    public void update(InquiryOrder inquiryOrder) {
        checkInquriyOrder(inquiryOrder);
        if(inquiryOrderDao.selectByPrimaryKey(inquiryOrder.getId())==null){
            throw new ErpExcetpion(ExceptionEumn.PI_IS_NOT_FOUND);
        }
        if(inquiryOrderDao.updateByPrimaryKey(inquiryOrder)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

    }
    //删除
    public void delete(Integer id) {
        if(inquiryOrderDao.selectByPrimaryKey(id)==null){
            throw new ErpExcetpion(ExceptionEumn.INQURIYORDER_IS_NOT_FOUND);
        }
        if(inquiryOrderDao.deleteByPrimaryKey(id)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }
    }

    //检查参数
    private void checkInquriyOrder(InquiryOrder inquiryOrder){
        if(inquiryOrder.getCompanyid()==null||inquiryOrder.getContactid()==null||StringUtil.isEmpty(inquiryOrder.getCompanyname())){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
    }
}
