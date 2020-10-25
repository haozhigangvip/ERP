package com.targetmol.sales.controller.Account;

import com.targetmol.sales.service.Account.AccountGradeService;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.sales.Account.AccountGrade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * 客户等级
 */
@Slf4j
@RequestMapping("/accountgrade")
@RestController
public class AccountGradeController {
    @Autowired
    private AccountGradeService accountGradeService;


    //查找所有
    @GetMapping
    public ResponseEntity<ResultMsg> findAll(){

        return ResponseEntity.ok(ResultMsg.success(accountGradeService.findAll()));
    }
    //根据ID查找
    @GetMapping("{id}")
    public ResponseEntity<ResultMsg> findById(@PathVariable("id") Integer id){

        return ResponseEntity.ok(ResultMsg.success(accountGradeService.findById(id)));
    }
    //添加
    @PostMapping
    public ResponseEntity<ResultMsg> addNew(@RequestBody AccountGrade accountGrade){
        accountGradeService.addnew(accountGrade);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //修改
    @PutMapping("{id}")
    public ResponseEntity<ResultMsg> update(@PathVariable("id") Integer id, @RequestBody AccountGrade accountGrade){
        accountGrade.setId(id);
        accountGradeService.update(accountGrade);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //删除
    @DeleteMapping("{id}")
    public ResponseEntity<ResultMsg> delete(@PathVariable("id") Integer id){
        accountGradeService.delete(id);
        return ResponseEntity.ok(ResultMsg.success());
    }


}
