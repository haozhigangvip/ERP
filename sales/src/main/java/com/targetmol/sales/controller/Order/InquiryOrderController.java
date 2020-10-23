package com.targetmol.sales.controller.Order;

import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.sales.Account.PiInfo;
import com.targetmol.domain.sales.Order.InquiryOrder;
import com.targetmol.sales.service.Account.PiInfoService;
import com.targetmol.sales.service.Order.InquiryOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * 询价单
 */
@Slf4j
@RequestMapping("/inquiryorder")
@RestController
public class InquiryOrderController {
    @Autowired
    private InquiryOrderService inquiryOrderService;


    //查找所有
    @GetMapping
    public ResponseEntity<ResultMsg> findAll(){

        return ResponseEntity.ok(ResultMsg.success(inquiryOrderService.findAll()));
    }
    //根据ID查找
    @GetMapping("{id}")
    public ResponseEntity<ResultMsg> findById(@PathParam("id") Integer id){

        return ResponseEntity.ok(ResultMsg.success(inquiryOrderService.findById(id)));
    }
    //添加
    @PostMapping
    public ResponseEntity<ResultMsg> addNew(@RequestBody InquiryOrder inquiryOrder){
        inquiryOrderService.addnew(inquiryOrder);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //修改
    @PutMapping("{id}")
    public ResponseEntity<ResultMsg> update(@PathParam("id") Integer id, @RequestBody InquiryOrder inquiryOrder){
        inquiryOrder.setId(id);
        inquiryOrderService.update(inquiryOrder);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //删除
    @DeleteMapping("{id}")
    public ResponseEntity<ResultMsg> delete(@PathParam("id") Integer id){
        inquiryOrderService.delete(id);
        return ResponseEntity.ok(ResultMsg.success());
    }


}
