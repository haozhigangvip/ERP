package com.targetmol.account.service;

import com.targetmol.account.dao.AddreddDao;
import com.targetmol.account.dao.ContactDao;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.domain.Address;
import com.targetmol.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private AddreddDao addreddDao;
    @Autowired
    private CompanyService companyService;


    public Contact findByContId(String contid){
        Contact ct=new Contact();
        ct.setContid(contid);
        Contact res=contactDao.selectOne(ct);
        if(res==null ){
            throw new ErpExcetpion(ExceptionEumn.CONTACT_ISMOT_FOUND);
        }else{
           if(res.getCompanyid()!=null){
               res.setCompany(companyService.findByComid(res.getCompanyid()));

           }
            Address add=new Address();
            add.setAdrcontact(res.getContid());
            res.setAddressList(addreddDao.select(add));
        }
        return res;
    }
}
