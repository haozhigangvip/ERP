package com.targetmol.account.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.targetmol.account.dao.AddressDao;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.vo.PageResult;
import com.targetmol.domain.account.Address;
import com.targetmol.domain.account.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class AddressServcie {
    @Autowired
    private AddressDao addressDao;

    //按contid查询所有Address
    public PageResult<Address> findByAll(Integer page, Integer pageSize, String softBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page,pageSize);
        //过滤
        Example example=new Example(Address.class);
        Example.Criteria criteria1=example.createCriteria();
        Example.Criteria criteria2=example.createCriteria();
        if(StringUtil.isNotEmpty(key)){
            criteria1.orLike("street","%"+key.trim()+"%")
                    .orLike("adrid","%"+key.toUpperCase().trim()+"%").
                    orLike("adrcontact","%"+key.toUpperCase().trim()+"%");
            example.and(criteria1);
        }

        //排序
        if(StringUtil.isNotEmpty(softBy)) {
            String orderByClause=softBy+(desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        //进行查询
        List<Address> list=addressDao.selectByExample(example);
        if(list ==null ||list.size()==0){
            throw new ErpExcetpion(ExceptionEumn.ADDRESS_ISNOT_FOUND);
        }
        //封装到pageHelper
        PageInfo<Address> pageInfo=new PageInfo<Address>(list);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(), list);
    }

    public List<Address> findByContId(String contID){
        Example example=new Example(Address.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("adrcontact",contID.toUpperCase());
        example.and(criteria);
        return addressDao.selectByExample(example);
    }

    //按id查询address
    public Address findbyAutoid(Integer autoid){

        return (addressDao.selectByPrimaryKey(autoid));

    }

    //添加地址
    public void addAddress(Address address) {
        checkAddress(address);
        address.setActive(1);
        if(addressDao.insert(address)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
//        return findbyAutoid(address.getAutoid());
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
        Address address=findbyAutoid(autoid);
        if(address==null){
            throw new ErpExcetpion(ExceptionEumn.ADDRESS_ISNOT_FOUND);
        }
        address.setActive(0);

        if(addressDao.updateByPrimaryKeySelective(address)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }
    }
}
