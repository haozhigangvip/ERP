package com.targetmol.sales.controller.Account;

import com.targetmol.sales.service.Account.ReturnReasonsService;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.sales.Account.ReturnReasons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * 退回原因
 */
@Slf4j
@RequestMapping("/returnreasons")
@RestController
public class ReturnReasonsController {
    @Autowired
    private ReturnReasonsService returnReasonsService;


    //查找所有
    @GetMapping
    public ResponseEntity<ResultMsg> findAll(){

        return ResponseEntity.ok(ResultMsg.success(returnReasonsService.findAll()));
    }
    //根据ID查找
    @GetMapping("{id}")
    public ResponseEntity<ResultMsg> findById(@PathParam("id") Integer id){

        return ResponseEntity.ok(ResultMsg.success(returnReasonsService.findById(id)));
    }
    //添加
    @PostMapping
    public ResponseEntity<ResultMsg> addNew(@RequestBody ReturnReasons returnReasons){
        returnReasonsService.addnew(returnReasons);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //修改
    @PutMapping("{id}")
    public ResponseEntity<ResultMsg> update(@PathParam("id") Integer id,@RequestBody ReturnReasons returnReasons){
        returnReasons.setId(id);
        returnReasonsService.update(returnReasons);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //删除
    @DeleteMapping("{id}")
    public ResponseEntity<ResultMsg> delete(@PathParam("id") Integer id){
        returnReasonsService.delete(id);
        return ResponseEntity.ok(ResultMsg.success());
    }


}
