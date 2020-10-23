package com.targetmol.sales.service.Account;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.targetmol.sales.dao.Account.ContactCompanyDao;
import com.targetmol.sales.dao.Account.ContactDao;
import com.targetmol.common.client.UserFeignClent;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.vo.PageResult;
import com.targetmol.domain.sales.Account.Company;
import com.targetmol.domain.sales.Account.Contact;
import com.targetmol.domain.sales.Account.Contact_Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class ContactService {
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private AddressServcie addressServcie;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ContactCompanyDao contactCompanyDao;
    @Autowired
    private UserFeignClent userFeignClent;
//    //关联查询||特殊查询所有Contact
//    public PageResult<Contact> getByAll(Integer page, Integer pageSize, String softBy, Boolean desc, String key, Boolean showDelete) {
//        //分页
//        PageHelper.startPage(page,pageSize);
//        //过滤
//        //进行查询
//        List<Contact> list=contactDao.findAllByAnyPara(key,showDelete,softBy,desc);
//        if(list ==null ||list.size()==0){
//            throw new ErpExcetpion(ExceptionEumn.CONTACT_ISNOT_FOUND);
//        }
//        //loadCompanys(list);
//        //封装到pageHelper
//        PageInfo<Contact> pageInfo=new PageInfo<Contact>(list);
//        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(), list);
//    }




    //按联系人ID查询联系人
    public Contact findByContId(Integer contid) throws Exception{
        Contact cont=new Contact();
        cont.setContactid(contid);
        Contact res=contactDao.selectOne(cont);
        //查询地址
        if(res!=null ){
            res.setAddressList(addressServcie.findByContId(res.getContactid()));
        }
        //查询联系人ID查询单位
        res.setCompanys(companyService.findAllCompanyByContId(res.getContactid()));

        //查询销售员名字
        if(res.getSaleid()!=null){
            res.setSalesname(contactDao.getUserNameByUid(res.getSaleid()));
        }

        return res;
    }




    //查询所有Contact
    public PageResult<Contact> findByAll(Integer page, Integer pageSize, String softBy, Boolean desc, String key, Boolean showUnActive,Integer pid) throws Exception {
        //分页
        Page pg=PageHelper.startPage(page,pageSize);

        List<Contact> result=contactDao.findAllByAnyPara(key,showUnActive,softBy,desc,pid);

        PageInfo<Contact> pageInfo=new PageInfo<Contact>(pg.getResult());
        return new PageResult<Contact>(pageInfo.getTotal(),pageInfo.getPages(), result);
    }



    //添加联系人
    @Transactional
    public void add(Contact contact) {
        //设置默认值
        if(contact.getContvip()==null){
            contact.setContvip(0);
            contact.setActivated(1);
        }
        //检查联系人数据是否为空
        CheckContact(contact);
        //判断联系人是否存在
        Contact fc=new Contact();
        fc.setName(contact.getName());

        if(contactDao.selectOne(fc)!=null){
            throw new ErpExcetpion(ExceptionEumn.CONTACT_ALREADY_EXISTS);
        }
        //保存，并判断是否成功
        if(contactDao.insert(contact)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

        List<Contact_Company> contact_companies=new ArrayList<>();

        //绑定中间表
        updateContact_Company(contact.getContactid(),contact.getCompanys());


//        return contactDao.selectByPrimaryKey(contact.getAutoid());
    }

    //修改联系人
    public void update(Contact contact) {
        //检查联系人数据是否为空
        CheckContact(contact);
        //检查联系人名称是否存在
        if(contactDao.findRepeatName(contact.getContactid(),contact.getName())!=null){
            throw new ErpExcetpion(ExceptionEumn.CONTACTNAME_ALREADY_EXISTS);
        }

       if(contactDao.updateByPrimaryKeySelective(contact)!=1){
           throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
       }
//       return contactDao.selectByPrimaryKey(contact.getAutoid());
    }

    //检查要保存的联系人
    public void CheckContact(Contact contact){
        if(contact==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        if(contact.getName()==null ||contact.getName()=="") {
            throw  new ErpExcetpion(ExceptionEumn.NAME_CANNOT_BE_NULL);
        }
//        if(contact.getContactid()==null){
//            throw new ErpExcetpion(ExceptionEumn.COMPANYID_CANNOT_BE_NULL);
//        }

    }


    //设置删除标记 0 可用，1 删除

    public void setActived(Integer contid, int i) {
        Contact contact=    contactDao.selectByPrimaryKey(contid);
        if (contact==null){
            throw  new ErpExcetpion(ExceptionEumn.CONTACT_ISNOT_FOUND);
        }
        contact.setActivated(i);
        if(contactDao.updateByPrimaryKeySelective(contact)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }
    }
    public Map<String,String> findNameBycontId(Integer contid){
        Map<String,String> mp=new HashMap<>();
        if(contid!=null){
                Contact cont=contactDao.selectByPrimaryKey(contid);
               if(cont!=null){
                    mp.put("contactName",cont.getName());
               }
               Company company=companyService.searchByContactIdDef(contid);
               if(company!=null){
                   mp.put("companyName",company.getCompanyname());
               }

        }

        return mp;
    }



    //根据联系人绑定公司
    public  void updateContact_Company(Integer contid,List<Company> companys){
        //根据contid 删除contact_company中间表
        if(contid!=null  ){
            Example example=new Example(Contact.class);
            Example.Criteria criteria=example.createCriteria();
            criteria.andEqualTo("contactid",contid);
            example.and(criteria);
            if(contactCompanyDao.deleteByExample(example)<0){
             throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
            }
        }
        //将新的company集合绑定至中间表
        if(companys!=null&& companys.size()>0){
            for (Company company: companys) {
                 if(companyService.findById(company.getCompanyid())==null){
                     throw new ErpExcetpion(ExceptionEumn.COMPANY_ISNOT_FOUND);
                 }
                Contact_Company contact_company=new Contact_Company();
                 contact_company.setContactid(contid);
                contact_company.setCompanyid(company.getCompanyid());
                contact_company.setDef(company.getDef());
                if(contactCompanyDao.insert(contact_company)!=1){
                    throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
                }
            }
        }
    }

    public void bindSubContact(Integer contid, Map<String, Object> map)  throws Exception{
        if(contid==null ||map==null|| map.get("contacts")==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        if(findByContId(contid)==null){
            throw new ErpExcetpion(ExceptionEumn.CONTACT_ISNOT_FOUND);
        }
        List<Integer> contactids=(List<Integer>)map.get("contacts");
        //清除所有绑定联系人
        contactDao.setContactPidIsNull(contid);
        //重新绑定所有子联系人
        for(Integer id:contactids){
             Contact cont=findByContId(id);
             if(cont==null){
                 throw new ErpExcetpion(ExceptionEumn.BIND_CONTACT_IS_NOT_FOUND);
             }
             cont.setPid(contid);
             if(contactDao.updateByPrimaryKeySelective(cont)!=1){
                 throw  new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
             }
        }

    }
}
