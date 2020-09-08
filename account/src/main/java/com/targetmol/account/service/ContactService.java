package com.targetmol.account.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.targetmol.account.dao.ContactDao;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.vo.PageResult;
import com.targetmol.domain.account.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private AddressServcie addressServcie;
    @Autowired
    private CompanyService companyService;

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



    //按autoid查询联系人
    public Contact findByAutoId(Integer autoid){
        Contact res=contactDao.selectByPrimaryKey(autoid);
        if(res==null ){
            throw new ErpExcetpion(ExceptionEumn.CONTACT_ISNOT_FOUND);
        }else{
           if(res.getCompanyid()!=null){
               res.setCompany(companyService.findByComId(res.getCompanyid()));

           }
            res.setAddressList(addressServcie.findByContId(res.getContid()));
        }
        return res;
    }

    //按contid查询联系人
    public Contact findByContId(String contid){
        Contact cont=new Contact();
        cont.setContid(contid);
        Contact res=contactDao.selectOne(cont);
        if(res==null ){
            throw new ErpExcetpion(ExceptionEumn.CONTACT_ISNOT_FOUND);
        }else{
            if(res.getCompanyid()!=null){
                res.setCompany(companyService.findByComId(res.getCompanyid()));
            }
            res.setAddressList(addressServcie.findByContId(res.getContid()));
        }
        return res;
    }


    //查询所有Contact
    public PageResult<Contact> findByAll(Integer page, Integer pageSize, String softBy, Boolean desc, String key, Boolean showDelete) {
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
        if(showDelete==false){
            criteria2.orEqualTo("deltag",0).orEqualTo("deltag" ,null);
            example.and(criteria2);
        }
        //排序
        if(StringUtil.isNotEmpty(softBy)==true) {
            String orderByClause=softBy+(desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        //进行查询
        List<Contact> list=contactDao.selectByExample(example);
        if(list ==null ||list.size()==0){
            throw new ErpExcetpion(ExceptionEumn.CONTACT_ISNOT_FOUND);
        }
        loadCompanys(list);
        //封装到pageHelper
        PageInfo<Contact> pageInfo=new PageInfo<Contact>(list);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(), list);
    }

    //查询公司
    private void loadCompanys(List<Contact> lst){
        for(Contact cont: lst){
            String comid=cont.getCompanyid();
            if(comid!=null){
                cont.setCompany(companyService.findByComId(comid));
            }
        }
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
        fc.setCompanyid(contact.getCompanyid());
        if(contactDao.selectOne(fc)!=null){
            throw new ErpExcetpion(ExceptionEumn.CONTACT_ALREADY_EXISTS);
        }
        //保存，并判断是否成功
        if(contactDao.insert(contact)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

//        return contactDao.selectByPrimaryKey(contact.getAutoid());
    }

    //修改联系人
    public void update(Contact contact) {
        //检查联系人数据是否为空
        CheckContact(contact);
        //检查联系人名称是否存在
        if(contactDao.findRepeatName(contact.getAutoid(),contact.getName(),contact.getCompanyid())!=null){
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
        if(contact.getCompanyid()==null||contact.getCompanyid()==""){
            throw new ErpExcetpion(ExceptionEumn.COMPANYID_CANNOT_BE_NULL);
        }

    }

    //设置删除标记 0 可用，1 删除
    public void setdelbj(Integer autoid,Integer delbj) {
         Contact contact=    contactDao.selectByPrimaryKey(autoid);
         if (contact==null){
             throw  new ErpExcetpion(ExceptionEumn.CONTACT_ISNOT_FOUND);
         }
         contact.setDeltag(delbj);
         if(contactDao.updateByPrimaryKeySelective(contact)!=1){
             throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
         }
    }
}
