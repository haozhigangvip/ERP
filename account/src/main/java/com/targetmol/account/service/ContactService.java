package com.targetmol.account.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.targetmol.account.dao.ContactCompanyDao;
import com.targetmol.account.dao.ContactDao;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.vo.PageResult;
import com.targetmol.domain.account.Company;
import com.targetmol.domain.account.Contact;
import com.targetmol.domain.account.Contact_Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

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



    //按id查询联系人
    public Contact findByAutoId(Integer contid){
        Contact res=contactDao.selectByPrimaryKey(contid);
        if(res==null ){
            throw new ErpExcetpion(ExceptionEumn.CONTACT_ISNOT_FOUND);
        }else{
            res.setCompanys(findAllCompanyByContId(res.getContactid()));

//            res.setAddressList(addressServcie.findByContId(res.getContactid()));
        }
        return res;
    }

    //按contid查询联系人
    public Contact findByContId(Integer contid){
        Contact cont=new Contact();
        cont.setContactid(contid);
        Contact res=contactDao.selectOne(cont);
        if(res==null ){
            throw new ErpExcetpion(ExceptionEumn.CONTACT_ISNOT_FOUND);
        }else{

            res.setAddressList(addressServcie.findByContId(res.getContactid()));
        }
        return res;
    }


    //查询所有Contact
    public PageResult<Contact> findByAll(Integer page, Integer pageSize, String softBy, Boolean desc, String key, Boolean showUnActivated) {
        //分页
        PageHelper.startPage(page,pageSize);
        //过滤
        Example example=new Example(Contact.class);
        Example.Criteria criteria1=example.createCriteria();
        Example.Criteria criteria2=example.createCriteria();
        if(StringUtil.isNotEmpty(key)){
            criteria1.orLike("name","%"+key.trim()+"%")
                    .orEqualTo("contid",key.toUpperCase().trim());
            example.and(criteria1);
        }
        if(showUnActivated==false){
            criteria2.orEqualTo("activated",1);
            example.and(criteria2);
        }
        //排序
        if(StringUtil.isNotEmpty(softBy)==true) {
            String orderByClause=softBy+(desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        //进行查询
        List<Contact> list=contactDao.selectByExample(example);
//        if(list ==null ||list.size()==0){
//            throw new ErpExcetpion(ExceptionEumn.CONTACT_ISNOT_FOUND);
//        }
//        loadCompanys(list);
        //封装到pageHelper
        PageInfo<Contact> pageInfo=new PageInfo<Contact>(list);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(), list);
    }



    //添加联系人
    @Transactional
    public void add(Contact contact) {
        //设置默认值
        if(contact.getContvip()==null){
            contact.setContvip(0);
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
        //绑定中间表
        updateContact_Company(contact.getContactid(),contact.getCompanys());


//        return contactDao.selectByPrimaryKey(contact.getAutoid());
    }

    //修改联系人
    public void update(Contact contact) {
        //检查联系人数据是否为空
        CheckContact(contact);
        //设置默认值
        contact.setActivated(1);
        //检查联系人名称是否存在
        if(contactDao.findRepeatName(contact.getContactid(),contact.getName())!=null){
            throw new ErpExcetpion(ExceptionEumn.CONTACTNAME_ALREADY_EXISTS);
        }

       if(contactDao.updateByPrimaryKeySelective(contact)!=1){
           throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
       }
       //绑定中间表
        updateContact_Company(contact.getContactid(),contact.getCompanys());

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


    //设置激活状态 0 冻结，1 激活

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


    //根据联系人查询公司集合
    public List<Company> findAllCompanyByContId(Integer contId){
        List<Company> result=new ArrayList<Company>();

        if(contId!=null){
            Contact_Company contact_company=new Contact_Company();
            contact_company.setContactid(contId);
           List<Contact_Company> ls=contactCompanyDao.select(contact_company);
            for (Contact_Company cc:ls) {
               result.add(companyService.findByComId(cc.getCompanyid())) ;
            }
        }
        return result;
    }
    //根据联系人ID清除contact_company中间表
    public void deleteContact_Company(Integer contid){
        //根据contid 删除contact_company中间表
        if(contid!=null  ){
            Example example=new Example(Contact_Company.class);
            Example.Criteria criteria=example.createCriteria();
            criteria.andEqualTo("contactid",contid);
            example.and(criteria);
            if(contactCompanyDao.deleteByPrimaryKey(contid)<0){
                throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
            }
        }
    }

    //根据联系人绑定中间表
    public  void updateContact_Company(Integer contid,List<Company> companys){
        //清除联系人
        deleteContact_Company(contid);
        //将新的company集合绑定至中间表
        if(companys!=null&& companys.size()>0){
            for (Company company: companys) {
                 if(companyService.findByComId(company.getCompanyid())==null){
                     throw new ErpExcetpion(ExceptionEumn.COMPANY_ISNOT_FOUND);
                 }
                Contact_Company contact_company=new Contact_Company();
                contact_company.setContactid(contid);
                contact_company.setCompanyid(company.getCompanyid());
                if(contactCompanyDao.insert(contact_company)!=1){
                    throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
                }
            }
        }
    }

}
