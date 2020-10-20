package com.targetmol.account.service;

import com.targetmol.account.dao.AccountCreditDao;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.domain.sales.AccountCredit;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

public class AccountCreditService {
    @Autowired
    private AccountCreditDao accountCreditDao;
    //查找所有
    public List<AccountCredit> findAll() {
        return accountCreditDao.selectAll();
    }

    //根据ID查找
    public AccountCredit findById(Integer id) {
        return accountCreditDao.selectByPrimaryKey(id);
    }
    //添加
    public void addnew(AccountCredit accountCredit) {
        if(accountCredit.getLevel()==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }

        if(accountCreditDao.insert(accountCredit)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
    }
    //修改
    public void update(AccountCredit accountCredit) {
        if(accountCredit.getLevel()==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        if(accountCreditDao.selectByPrimaryKey(accountCredit.getId())==null){
            throw new ErpExcetpion(ExceptionEumn.CREDIT_IS_NOT_FOUND);
        }
        if(accountCreditDao.updateByPrimaryKey(accountCredit)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

    }
    //删除
    public void delete(Integer id) {
        if(accountCreditDao.selectByPrimaryKey(id)==null){
            throw new ErpExcetpion(ExceptionEumn.CREDIT_IS_NOT_FOUND);
        }
        if(accountCreditDao.deleteByPrimaryKey(id)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }
    }
}
