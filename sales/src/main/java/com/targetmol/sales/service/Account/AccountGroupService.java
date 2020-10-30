package com.targetmol.sales.service.Account;

import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.domain.sales.Account.AccountGroup;
import com.targetmol.sales.dao.Account.AccountGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
@Transactional(rollbackFor = {Exception.class, ErpExcetpion.class})
public class AccountGroupService {
    @Autowired
    private AccountGroupDao accountGroupDao;


    //查找所有
    public List<AccountGroup> findAll() {
        return accountGroupDao.selectAll();
    }

    //根据ID查找
    public AccountGroup findById(Integer id) {
        return accountGroupDao.selectByPrimaryKey(id);
    }

    //添加
    public void addnew(AccountGroup accountGroup) {
        if(StringUtil.isEmpty(accountGroup.getName())){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }

        accountGroup.setCode( getNewCode(accountGroup.getPcode()));

        if(accountGroupDao.insert(accountGroup)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

    }

    private String getNewCode(String pcode){
        if(pcode==null){
            pcode="AG-";
        }
         return  pcode+(String.valueOf( accountGroupDao.getMaxCode(pcode)+1));
    }

    //修改
    public void update(AccountGroup accountGroup) {
        if(StringUtil.isEmpty(accountGroup.getName())){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        if(accountGroupDao.selectByPrimaryKey(accountGroup.getId())==null){
            throw new ErpExcetpion(ExceptionEumn.ACCOUNT_GROUP_IS_NOT_FOUND);
        }
        if(accountGroupDao.updateByPrimaryKey(accountGroup)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

    }


    //删除
    public void delete(Integer id) {
        if(accountGroupDao.selectByPrimaryKey(id)==null){
            throw new ErpExcetpion(ExceptionEumn.ACCOUNT_GROUP_IS_NOT_FOUND);
        }
        if(accountGroupDao.deleteByPrimaryKey(id)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }
    }


}
