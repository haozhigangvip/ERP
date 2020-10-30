package com.targetmol.sales.service.Account;

import com.targetmol.sales.dao.Account.AccountTypeDao;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.domain.sales.Account.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
@Service
@Transactional(rollbackFor = {Exception.class,ErpExcetpion.class})
public class AccountTypeService {
    @Autowired
    private AccountTypeDao accountTypeDao;
    //查找所有
    public List<AccountType> findAll() {
        return accountTypeDao.selectAll();
    }

    //根据ID查找
    public AccountType findById(Integer id) {
        return accountTypeDao.selectByPrimaryKey(id);
    }
    //添加
    public void addnew(AccountType accountType) {
        if(StringUtil.isEmpty(accountType.getType())){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }

        if(accountTypeDao.insert(accountType)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
    }
    //修改
    public void update(AccountType accountType) {
        if(StringUtil.isEmpty(accountType.getType())){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        if(accountTypeDao.selectByPrimaryKey(accountType.getId())==null){
            throw new ErpExcetpion(ExceptionEumn.ACCOUNT_TYPE_IS_NOT_FOUND);
        }
        if(accountTypeDao.updateByPrimaryKey(accountType)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

    }
    //删除
    public void delete(Integer id) {
        if(accountTypeDao.selectByPrimaryKey(id)==null){
            throw new ErpExcetpion(ExceptionEumn.ACCOUNT_TYPE_IS_NOT_FOUND);
        }
        if(accountTypeDao.deleteByPrimaryKey(id)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }
    }
}
