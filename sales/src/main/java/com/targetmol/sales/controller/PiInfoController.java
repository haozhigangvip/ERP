package com.targetmol.sales.controller;

import com.targetmol.sales.service.PiInfoService;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.sales.PiInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * 客户角色信息
 */
@Slf4j
@RequestMapping("/piinfo")
@RestController
public class PiInfoController {
    @Autowired
    private PiInfoService piInfoService;


    //查找所有
    @GetMapping
    public ResponseEntity<ResultMsg> findAll(){

        return ResponseEntity.ok(ResultMsg.success(piInfoService.findAll()));
    }
    //根据ID查找
    @GetMapping("{id}")
    public ResponseEntity<ResultMsg> findById(@PathParam("id") Integer id){

        return ResponseEntity.ok(ResultMsg.success(piInfoService.findById(id)));
    }
    //添加
    @PostMapping
    public ResponseEntity<ResultMsg> addNew(@RequestBody PiInfo piInfo){
        piInfoService.addnew(piInfo);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //修改
    @PutMapping("{id}")
    public ResponseEntity<ResultMsg> update(@PathParam("id") Integer id, @RequestBody PiInfo piInfo){
        piInfo.setId(id);
        piInfoService.update(piInfo);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //删除
    @DeleteMapping("{id}")
    public ResponseEntity<ResultMsg> delete(@PathParam("id") Integer id){
        piInfoService.delete(id);
        return ResponseEntity.ok(ResultMsg.success());
    }


}
