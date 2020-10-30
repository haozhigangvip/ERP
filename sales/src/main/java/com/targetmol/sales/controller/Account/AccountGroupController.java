package com.targetmol.sales.controller.Account;

import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.sales.Account.AccountGroup;
import com.targetmol.sales.service.Account.AccountGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 客户分组
 */
@Slf4j
@RequestMapping("/accountgroup")
@RestController
public class AccountGroupController {
    @Autowired
    private AccountGroupService accountGroupService;

    //查找所有
    @GetMapping
    public ResponseEntity<ResultMsg> findAll(){

        return ResponseEntity.ok(ResultMsg.success(accountGroupService.findAll()));
    }
    //根据ID查找
    @GetMapping("{id}")
    public ResponseEntity<ResultMsg> findById(@PathVariable("id") Integer id){

        return ResponseEntity.ok(ResultMsg.success(accountGroupService.findById(id)));
    }
    //添加
    @PostMapping()
    public ResponseEntity<ResultMsg> addNew(@RequestBody AccountGroup accountGroup){
        accountGroupService.addnew(accountGroup);
        return ResponseEntity.ok(ResultMsg.success());
    }


    //修改
    @PutMapping("{id}")
    public ResponseEntity<ResultMsg> update(@PathVariable("id") Integer id, @RequestBody AccountGroup accountGroup){
        accountGroup.setId(id);
        accountGroupService.update(accountGroup);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //删除
    @DeleteMapping("{id}")
    public ResponseEntity<ResultMsg> delete(@PathVariable("id") Integer id){
        accountGroupService.delete(id);
        return ResponseEntity.ok(ResultMsg.success());
    }

}
