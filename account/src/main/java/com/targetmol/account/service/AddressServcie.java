package com.targetmol.account.service;

import com.targetmol.account.dao.AddressDao;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.domain.Address;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServcie {
    @Autowired
    private AddressDao addressDao;

    //按contid查询所有Address
    public List<Address> findByAll(String contid) {
        if(StringUtils.isEmpty(contid)==true){
            throw new ErpExcetpion(ExceptionEumn.CONTACTID_CANNOT_BE_NULL);
        }
        Address address=new Address();
        address.setAdrcontact(contid);
        return addressDao.select(address);
    }
}
