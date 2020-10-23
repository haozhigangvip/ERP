package com.targetmol.sales.controller.Account;

import com.targetmol.sales.service.Account.AccountTypeService;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.sales.Account.AccountType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * 客户类型
 */
@Slf4j
@RequestMapping("/accounttype")
@RestController
public class AccountTypeController {
    @Autowired
    private AccountTypeService accountTypeService;


    //查找所有
    @GetMapping
    public ResponseEntity<ResultMsg> findAll(){

        return ResponseEntity.ok(ResultMsg.success(accountTypeService.findAll()));
    }
    //根据ID查找
    @GetMapping("{id}")
    public ResponseEntity<ResultMsg> findById(@PathParam("id") Integer id){

        return ResponseEntity.ok(ResultMsg.success(accountTypeService.findById(id)));
    }
    //添加
    @PostMapping
    public ResponseEntity<ResultMsg> addNew(@RequestBody AccountType accountType){
        accountTypeService.addnew(accountType);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //修改
    @PutMapping("{id}")
    public ResponseEntity<ResultMsg> update(@PathParam("id") Integer id, @RequestBody AccountType accountType){
        accountType.setId(id);
        accountTypeService.update(accountType);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //删除
    @DeleteMapping("{id}")
    public ResponseEntity<ResultMsg> delete(@PathParam("id") Integer id){
        accountTypeService.delete(id);
        return ResponseEntity.ok(ResultMsg.success());
    }


}
