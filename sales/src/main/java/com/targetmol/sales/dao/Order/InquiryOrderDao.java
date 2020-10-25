package com.targetmol.sales.dao.Order;

import com.targetmol.common.mapper.BaseMapper;
import com.targetmol.domain.sales.Account.Contact;
import com.targetmol.domain.sales.Account.PiInfo;
import com.targetmol.domain.sales.Order.InquiryOrder;
import com.targetmol.sales.dao.Account.ContactDao;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface InquiryOrderDao extends BaseMapper<InquiryOrder> {
    @Select("select * from inquiry_order where id=#{id}")
    @Results({
            @Result(id=true,column = "id",property = "id"),
            @Result(property = "inquiryOrderItemList",column = "orderid",many =
            @Many(select = "com.targetmol.sales.dao.Order.InquiryOrderItemDao.findByOrderId",fetchType = FetchType.LAZY)),
    })
    InquiryOrder findInquiryOrderById(@RequestParam("id") Integer id);

}
