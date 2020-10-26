package com.targetmol.sales.service.Account;

import com.targetmol.sales.dao.Account.InvoiceinfoDao;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.domain.sales.Account.InvoiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
@Transactional(rollbackFor = {Exception.class,ErpExcetpion.class})
public class InvoiceInfoService {
    @Autowired
    private InvoiceinfoDao invoiceinfoDao;

    //根据联系人ID查开票信息
    public List<InvoiceInfo> findAllByContId(Integer contid) {
        if(contid!=null){
            InvoiceInfo invoiceInfo=new InvoiceInfo();
            invoiceInfo.setContid(contid);
          return   invoiceinfoDao.select(invoiceInfo);
        }
        return null;
    }

    //根据ID查询开票信息
    public InvoiceInfo findById(Integer invoiceid) {
        if(invoiceid!=null){
            return invoiceinfoDao.selectByPrimaryKey(invoiceid);
        }
        return null;
    }

    //添加开票信息
    public void addInvoiceInfo(InvoiceInfo invoiceInfo) {
        //检查发票信息是否齐全
        checkInvoiceInfo(invoiceInfo);
        //保存发票信息
        if(invoiceinfoDao.insert(invoiceInfo)!=1){
            throw  new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
    }

    //根据联系人ID修改开票信息
    public void updateInvoiceInfo(InvoiceInfo invoiceInfo) {
        if(invoiceInfo==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        InvoiceInfo checkInvoice =findById(invoiceInfo.getId());
        if(checkInvoice==null){
            throw  new ErpExcetpion(ExceptionEumn.INVOICE_INFO_IS_NOT_FOUND);
        }
        invoiceInfo.setContid(checkInvoice.getContid());
        //检查发票信息是否齐全
        checkInvoiceInfo(invoiceInfo);
        //保存发票信息
        if(invoiceinfoDao.updateByPrimaryKey(invoiceInfo)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

    }
    //检查发票信息是否齐全
    private void checkInvoiceInfo(InvoiceInfo invoiceInfo) {
        if(invoiceInfo.getContid()==null|| StringUtil.isEmpty(invoiceInfo.getInvoiceTitle())||StringUtil.isEmpty(invoiceInfo.getTaxNumber())){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
    }

    //根据联系人ID删除开票信息
    public void delInvoiceInfo(Integer invoiceid) {
        if(findById(invoiceid)==null){
            throw  new ErpExcetpion(ExceptionEumn.INVOICE_INFO_IS_NOT_FOUND);
        }
        //删除发票信息
        if(invoiceinfoDao.deleteByPrimaryKey(invoiceid)<1){
            throw  new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }
    }



}
