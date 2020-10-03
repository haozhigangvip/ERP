package com.targetmol.account.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.targetmol.account.dao.AddressDao;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.vo.PageResult;
import com.targetmol.domain.account.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class AddressServcie {
    @Autowired
    private AddressDao addressDao;

    public PageResult<Address> findByAll(Integer page, Integer pageSize, String softBy, Boolean desc, String key,Integer contid,Integer def) {
        //分页
        Page pg=PageHelper.startPage(page,pageSize);
        //过滤
        Example example=new Example(Address.class);
        Example.Criteria criteria1=example.createCriteria();
        Example.Criteria criteria2=example.createCriteria();
        Example.Criteria criteria3=example.createCriteria();

        if(StringUtil.isNotEmpty(key)){
            criteria1.orLike("street","%"+key.trim()+"%")
                    .orLike("adrid","%"+key.toUpperCase().trim()+"%");
            example.and(criteria1);
        }
        if(def!=null && def==1){
            criteria2.andEqualTo("def",def);
            example.and(criteria2);
        }
        if(contid!=null){
            criteria3.andEqualTo("contactid",contid);
            example.and(criteria3);
        }

        //排序
        if(StringUtil.isNotEmpty(softBy)) {
            String orderByClause=softBy+(desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        //进行查询
        List<Address> list=addressDao.selectByExample(example);
//        if(list ==null ||list.size()==0){
//            throw new ErpExcetpion(ExceptionEumn.ADDRESS_ISNOT_FOUND);
//        }
        //封装到pageHelper
        PageInfo<Address> pageInfo=new PageInfo<Address>(pg.getResult());
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(), list);
    }

    //按contid查询所有Address
    public List<Address> findByContId(Integer contID){
        Example example=new Example(Address.class);
        example.and(example.createCriteria().andEqualTo("contactid",contID)
                .andEqualTo("activated",1));
        return addressDao.selectByExample(example);
    }

    //按id查询address
    public Address findbyAddrId(Integer addrid){

        return (addressDao.selectByPrimaryKey(addrid));

    }

    //添加地址
    public Address addAddress(Address address) {
        checkAddress(address);
        address.setActivated(1);
        address=setDefault(address);
        if(addressDao.insert(address)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
        return findbyAddrId(address.getAddrid());
    }

    //修改地址
    public Address updateAddress(Address address) {
        checkAddress(address);
        address=setDefault(address);
        if(addressDao.updateByPrimaryKeySelective(address)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
        return findbyAddrId(address.getAddrid());
    }

    //设置默认值
    public Address setDefault(Address address){
       if(address!=null){
           List<Address> lst=findByContId(address.getContactid());
           if(lst==null || lst.size()<=0){
               address.setDef(1);
           }else if(address.getDef()!=null && address.getDef()==1){
               for (Address add:lst) {
                    add.setDef(0);
                    addressDao.updateByPrimaryKey(add);
               }
           }else{
               address.setDef(0);
           }
       }
       return address;
    }

    //判断地址参数是否齐全
    public void checkAddress(Address address){
        if( StringUtil.isEmpty(address.getStreet())
                || StringUtil.isEmpty(address.getCountry())
                || StringUtil.isEmpty(address.getCity())
                || StringUtil.isEmpty(address.getState())
                || address.getContactid()==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
    }
    //冻结地址
    public void unActiveAddress(Integer addrid) {
        Address address=findbyAddrId(addrid);
        if(address==null){
            throw new ErpExcetpion(ExceptionEumn.ADDRESS_ISNOT_FOUND);
        }
        address.setActivated(0);

        if(addressDao.updateByPrimaryKeySelective(address)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }
    }
}
