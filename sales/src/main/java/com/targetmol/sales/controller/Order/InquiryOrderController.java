package com.targetmol.sales.controller.Order;

import com.targetmol.common.controller.BaseController;
import com.targetmol.common.utils.Oauth2Util;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.sales.Account.PiInfo;
import com.targetmol.domain.sales.Order.InquiryOrder;
import com.targetmol.domain.system.ext.XcMenuExt;
import com.targetmol.sales.service.Account.PiInfoService;
import com.targetmol.sales.service.Order.InquiryOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.http11.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Map;

/**
 * 询价单
 */
@Slf4j
@RequestMapping("/inquiryorder")
@RestController
public class InquiryOrderController  extends BaseController {
    @Autowired
    private InquiryOrderService inquiryOrderService;


    //查找所有
    @GetMapping
    public ResponseEntity<ResultMsg> findAll(@RequestParam("key") String key,
                                             @RequestParam("page") Integer page,
                                             @RequestParam("pagesize") Integer pageSize,
                                             @RequestParam("softby") String softBy,
                                             @RequestParam("desc") Boolean desc){
        Integer uid=Oauth2Util.getUserid(request);
        return ResponseEntity.ok(ResultMsg.success(inquiryOrderService.findAll(key,page,pageSize,softBy,desc)));
    }
    //根据ID查找
    @GetMapping("{id}")
    public ResponseEntity<ResultMsg> findById(@PathVariable("id") Integer id){

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
    public ResponseEntity<ResultMsg> update(@PathVariable("id") Integer id, @RequestBody InquiryOrder inquiryOrder){
        inquiryOrder.setId(id);
        inquiryOrderService.update(inquiryOrder);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //修改订单状态
    @PutMapping("{id}/state")
    public ResponseEntity<ResultMsg> modifyState(@PathVariable("id") Integer id,String state){
        inquiryOrderService.modifyState(id,state);
        return  ResponseEntity.ok(ResultMsg.success());
    }



    //删除
    @DeleteMapping("{id}")
    public ResponseEntity<ResultMsg> delete(@PathVariable("id") Integer id){
        inquiryOrderService.delete(id);
        return ResponseEntity.ok(ResultMsg.success());
    }


}
