package com.targetmol.account.service;

import com.targetmol.account.dao.AddressDao;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.domain.account.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class AddressServcie {
    @Autowired
    private AddressDao addressDao;

    //按contid查询所有Address
    public List<Address> findByAll(String contid) {
        if(StringUtil.isEmpty(contid)==true){
            throw new ErpExcetpion(ExceptionEumn.CONTACTID_CANNOT_BE_NULL);
        }
        Address address=new Address();
        address.setAdrcontact(contid);
        return addressDao.select(address);
    }
    //按id查询address
    public Address findbyAutoid(Integer autoid){

        return (addressDao.selectByPrimaryKey(autoid));

    }

    //添加地址
    public Address addAddress(Address address) {
        checkAddress(address);
        if(addressDao.insert(address)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
        return findbyAutoid(address.getAutoid());
    }

    //保存
    public Address updateAddress(Address address) {
        checkAddress(address);
        if(addressDao.updateByPrimaryKeySelective(address)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
        return findbyAutoid(address.getAutoid());
    }

    //判断地址参数是否齐全
    public void checkAddress(Address address){
        if(address==null||StringUtil.isEmpty(address.getAdrcontact())
                || StringUtil.isEmpty(address.getStreet())
                || StringUtil.isEmpty(address.getCountry())
                || StringUtil.isEmpty(address.getCity())
                || StringUtil.isEmpty(address.getState())){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
    }
    //删除地址
    public void delAddress(Integer autoid) {
        if(findbyAutoid(autoid)==null){
            throw new ErpExcetpion(ExceptionEumn.ADDRESS_ISNOT_FOUND);
        }
        if(addressDao.deleteByPrimaryKey(autoid)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }
    }
}
