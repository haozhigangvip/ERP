package com.targetmol.sales.controller.Account;

import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.sales.Account.AreaGroup;
import com.targetmol.sales.service.Account.AreaGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 区域分组
 */
@Slf4j
    @RequestMapping("/aeragroup")
@RestController
public class AreaGroupController {
    @Autowired
    private AreaGroupService accountGroupService;

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
    public ResponseEntity<ResultMsg> addNew(@RequestBody AreaGroup areaGroup){
        accountGroupService.addnew(areaGroup);
        return ResponseEntity.ok(ResultMsg.success());
    }


    //修改
    @PutMapping("{id}")
    public ResponseEntity<ResultMsg> update(@PathVariable("id") Integer id, @RequestBody AreaGroup areaGroup){
        areaGroup.setId(id);
        accountGroupService.update(areaGroup);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //删除
    @DeleteMapping("{id}")
    public ResponseEntity<ResultMsg> delete(@PathVariable("id") Integer id){
        accountGroupService.delete(id);
        return ResponseEntity.ok(ResultMsg.success());
    }

}
