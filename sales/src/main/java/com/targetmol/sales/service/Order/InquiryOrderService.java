package com.targetmol.sales.service.Order;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.vo.PageResult;
import com.targetmol.domain.sales.Account.Address;
import com.targetmol.domain.sales.Order.InquiryOrder;
import com.targetmol.domain.sales.Order.InquiryOrderItem;
import com.targetmol.sales.dao.Order.InquiryOrderDao;
import com.targetmol.sales.dao.Order.InquiryOrderItemDao;
import com.targetmol.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service()
@Transactional(rollbackFor = {Exception.class,ErpExcetpion.class})
public class InquiryOrderService {
    @Autowired
    private InquiryOrderDao inquiryOrderDao;
    @Autowired
    private InquiryOrderItemDao inquiryOrderItemDao;

    //查找所有询价单
    public PageResult<InquiryOrder> findAll(String key,Integer page,Integer pageSize,String softBy,Boolean desc) {

        //分页
        Page pg= PageHelper.startPage(page,pageSize);
        //过滤
        Example example=new Example(Address.class);
        Example.Criteria criteria1=example.createCriteria();
        Example.Criteria criteria2=example.createCriteria();
        Example.Criteria criteria3=example.createCriteria();

        if(StringUtil.isNotEmpty(key)){
            criteria1.orLike("companyname","%"+key.trim()+"%")
                    .orLike("orderid","%"+key.toUpperCase().trim()+"%");
            example.and(criteria1);
        }
        //排序
        if(StringUtil.isNotEmpty(softBy)) {
            String orderByClause=softBy+(desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        //进行查询
        List<InquiryOrder>  list=inquiryOrderDao.selectByExample(example);
        //封装到pageHelper
        PageInfo<InquiryOrder> pageInfo=new PageInfo<InquiryOrder>(pg.getResult());
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(), list);
    }

    //根据ID查找
    public InquiryOrder findById(Integer id) {
        InquiryOrder result=inquiryOrderDao.findInquiryOrderById(id);
        return result;
    }

    //添加询价单
    public void addnew(InquiryOrder inquiryOrder) {
        //检查参数
        checkInquiryOrder(inquiryOrder);
        //添加询价单
        if(inquiryOrderDao.insertSelective(inquiryOrder)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
        //设置订单ID
        inquiryOrder.setOrderid("INQ"+inquiryOrder.getId());

        //添加询价单明细
        inquiryOrder=addItem(inquiryOrder);

        //设置订单ID，并保存
        if(inquiryOrderDao.updateByPrimaryKeySelective(inquiryOrder)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
    }

    //修改
    public void update(InquiryOrder inquiryOrder) {
        //检查参数
        checkInquiryOrder(inquiryOrder);
        //查询该询价单是否存在
        InquiryOrder oldInquiryOrder=inquiryOrderDao.selectByPrimaryKey(inquiryOrder.getId());
        if(oldInquiryOrder==null){
            throw new ErpExcetpion(ExceptionEumn.PI_IS_NOT_FOUND);
        }
        //设置ID及ORDERID不可修改
        inquiryOrder.setId(oldInquiryOrder.getId());
        inquiryOrder.setOrderid(oldInquiryOrder.getOrderid());
        //删除明细
        if(inquiryOrderItemDao.delByOrderId(inquiryOrder.getOrderid())<1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

        //添加明细
        inquiryOrder=addItem(inquiryOrder);
        //保存询价单
        if(inquiryOrderDao.updateByPrimaryKey(inquiryOrder)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }





    }

    private  InquiryOrder addItem(InquiryOrder inquiryOrder){
        Double sumAmount=0.00;
        Double sumtax=0.00;
        Double sumTaxAmount=0.00;
        String orderid=inquiryOrder.getOrderid();
        List<InquiryOrderItem> items=inquiryOrder.getInquiryOrderItemList();
        for (InquiryOrderItem item:items) {
            //判断参数是否齐全
            checkInquiryOrderItem(item);
            //计算金额
            //判断是否为赠品
            Double price=item.getPrice();       //单价
            if(item.getGifit()!=null&& item.getGifit()==1) {
                price=0.00;
            }
            Double quantiy=item.getQuantiy();   //数量
            Double itemtotal=price*quantiy;      //商品金额；
            Double discountRate=item.getDiscountrate();//扣率
            discountRate=discountRate==null?0:discountRate;
            Double discount=itemtotal*(discountRate/100);//商品折扣金额
            itemtotal=itemtotal-discount;
            Double taxrate=item.getTaxrate();
            Double tax=NumberUtils.round(itemtotal*(taxrate/100),2);//税额
            Double amount=NumberUtils.round(itemtotal+tax,2);       //商品税价合计
            item.setOrderid(orderid);
            item.setPrice(price);
            item.setAmount(amount);
            sumAmount +=itemtotal;
            sumTaxAmount+=tax;

            //保存明细
            if(inquiryOrderItemDao.insert(item)!=1){
                throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
            }
        }
        inquiryOrder.setAmount(sumAmount);
        inquiryOrder.setItemtotal(sumAmount);
        inquiryOrder.setItemtotaltax(sumTaxAmount);
        Double deliveryfree=inquiryOrder.getDeliveryfee();
        deliveryfree=deliveryfree==null?0:deliveryfree;
        Double adjustment=inquiryOrder.getAdjustment();
        adjustment=adjustment==null?0:adjustment;
        inquiryOrder.setAmount(sumAmount+sumTaxAmount+deliveryfree+adjustment);//计算订单总金额（含税）

        return inquiryOrder;
    }



    //删除
    public void delete(Integer id) {
        //查询询价单ID是否存在
        if(inquiryOrderDao.selectByPrimaryKey(id)==null){
            throw new ErpExcetpion(ExceptionEumn.INQURIYORDER_IS_NOT_FOUND);
        }
        //删除询价单
        if(inquiryOrderDao.deleteByPrimaryKey(id)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }
    }

    //检查订单参数
    private void checkInquiryOrder(InquiryOrder inquiryOrder){
        if(inquiryOrder.getCompanyid()==null||inquiryOrder.getContactid()==null||
                StringUtil.isEmpty(inquiryOrder.getCompanyname())||inquiryOrder.getInquiryOrderItemList()==null||
                inquiryOrder.getInquiryOrderItemList().size()<1){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
    }

    //检查订单明细参数
    private void checkInquiryOrderItem(InquiryOrderItem inquiryOrderItem){
        if(inquiryOrderItem==null||inquiryOrderItem.getQuantiy()==null||
                inquiryOrderItem.getQuantiy()<0||StringUtil.isEmpty(inquiryOrderItem.getName())||
                StringUtil.isEmpty(inquiryOrderItem.getTsid())){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
    }
}
