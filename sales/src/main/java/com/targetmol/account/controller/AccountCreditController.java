package com.targetmol.account.controller;

import com.targetmol.account.service.AccountCreditService;
import com.targetmol.account.service.AccountGradeService;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.sales.AccountCredit;
import com.targetmol.domain.sales.AccountGrade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * 信用等级
 */
@Slf4j
@RequestMapping("/accountcredit")
@RestController
public class AccountCreditController {
    @Autowired
    private AccountCreditService accountCreditService;


    //查找所有
    @GetMapping
    public ResponseEntity<ResultMsg> findAll(){

        return ResponseEntity.ok(ResultMsg.success(accountCreditService.findAll()));
    }
    //根据ID查找
    @GetMapping("{id}")
    public ResponseEntity<ResultMsg> findById(@PathParam("id") Integer id){

        return ResponseEntity.ok(ResultMsg.success(accountCreditService.findById(id)));
    }
    //添加
    @PostMapping
    public ResponseEntity<ResultMsg> addNew(@RequestBody AccountCredit accountCredit){
        accountCreditService.addnew(accountCredit);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //修改
    @PutMapping("{id}")
    public ResponseEntity<ResultMsg> update(@PathParam("id") Integer id, @RequestBody AccountCredit accountCredit){
        accountCredit.setId(id);
        accountCreditService.update(accountCredit);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //删除
    @DeleteMapping("{id}")
    public ResponseEntity<ResultMsg> delete(@PathParam("id") Integer id){
        accountCreditService.delete(id);
        return ResponseEntity.ok(ResultMsg.success());
    }


}
