package com.targetmol.account.service;

import com.targetmol.account.dao.AccountGradeDao;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.domain.sales.AccountGrade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AccountGradeService {
    @Autowired
    private AccountGradeDao accountGradeDao;
    //查找所有
    public List<AccountGrade> findAll() {
        return accountGradeDao.selectAll();
    }

    //根据ID查找
    public AccountGrade findById(Integer id) {
        return accountGradeDao.selectByPrimaryKey(id);
    }
    //添加
    public void addnew(AccountGrade accountGrade) {
        if(accountGrade.getContvip()==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        if(accountGradeDao.insert(accountGrade)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
    }
    //修改
    public void update(AccountGrade accountGrade) {
        if(accountGrade.getContvip()==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        if(accountGradeDao.selectByPrimaryKey(accountGrade.getId())==null){
            throw new ErpExcetpion(ExceptionEumn.ACCOUNT_GRADE_IS_NOT_FOUND);
        }
        if(accountGradeDao.updateByPrimaryKey(accountGrade)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

    }
    //删除
    public void delete(Integer id) {
        if(accountGradeDao.selectByPrimaryKey(id)==null){
            throw new ErpExcetpion(ExceptionEumn.ACCOUNT_GRADE_IS_NOT_FOUND);
        }
        if(accountGradeDao.deleteByPrimaryKey(id)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }
    }
}
