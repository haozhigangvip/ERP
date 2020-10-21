package com.targetmol.sales.controller;

import com.targetmol.sales.service.OrderSourceService;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.sales.OrderSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * 订单来源
 */
@Slf4j
@RequestMapping("/ordersource")
@RestController
public class OrderSourceController {
    @Autowired
    private OrderSourceService orderSourceService;


    //查找所有
    @GetMapping
    public ResponseEntity<ResultMsg> findAll(){

        return ResponseEntity.ok(ResultMsg.success(orderSourceService.findAll()));
    }
    //根据ID查找
    @GetMapping("{id}")
    public ResponseEntity<ResultMsg> findById(@PathParam("id") Integer id){

        return ResponseEntity.ok(ResultMsg.success(orderSourceService.findById(id)));
    }
    //添加
    @PostMapping
    public ResponseEntity<ResultMsg> addNew(@RequestBody OrderSource orderSource){
        orderSourceService.addnew(orderSource);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //修改
    @PutMapping("{id}")
    public ResponseEntity<ResultMsg> update(@PathParam("id") Integer id, @RequestBody OrderSource orderSource){
        orderSource.setId(id);
        orderSourceService.update(orderSource);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //删除
    @DeleteMapping("{id}")
    public ResponseEntity<ResultMsg> delete(@PathParam("id") Integer id){
        orderSourceService.delete(id);
        return ResponseEntity.ok(ResultMsg.success());
    }


}
