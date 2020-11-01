package com.targetmol.sales.dao.Order;

import com.targetmol.common.mapper.BaseMapper;
import com.targetmol.domain.sales.Order.InquiryOrderItem;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface InquiryOrderItemDao extends BaseMapper<InquiryOrderItem> {
    //根据orderid查询所有明细
    @Select("select * from inquiry_order_item where orderid=#{orderid}")
    List<InquiryOrderItem> findByOrderId(@RequestParam("orderid") String orderid);

    //根据orderid删除明细
    @Select("delete  from inquiry_order_item where orderid=#{orderid}")
    Integer delByOrderId(@RequestParam("orderid") String orderid);
}
