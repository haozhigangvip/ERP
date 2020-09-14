package com.targetmol.system.controller;

import com.targetmol.common.vo.PageResult;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.system.Permission;
//import com.targetmol.system.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequestMapping("/permission")
@RestController
public class PermissionController {
    @Autowired
//    private PermissionService permissionService;

    //添加权限
    @PostMapping
    public ResponseEntity<ResultMsg> addPermission(@RequestBody Map<String,Object> map) throws Exception {
//        permissionService.addPermission(map);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //修改权限
    @PutMapping("{id}")
    public ResponseEntity<ResultMsg> updatePermission(@PathVariable(value="id") Integer id,
                                                      @RequestBody Map<String,Object> map) throws Exception {
        map.put("id",id);
//        permissionService.updatePermission(map);
        return ResponseEntity.ok(ResultMsg.success());
    }
    //删除权限
    @DeleteMapping("{id}")
    public ResponseEntity<ResultMsg> deletePermission(@PathVariable(value="id") Integer id){
//        permissionService.deletePermission(id);
        return ResponseEntity.ok(ResultMsg.success());
    }
    //查询所有权限表
    @GetMapping
    public ResponseEntity<PageResult<Permission>> findByAll(@RequestParam(value = "type",required = false,defaultValue = "0") Integer type,
                                                            @RequestParam(value="pid" ,required = false) Integer pid)
    {
//        permissionService.findByAll(type,pid);
        return ResponseEntity.ok(new PageResult<>());
    }

    //按ID查询权限
    @GetMapping("{id}")
    public ResponseEntity<ResultMsg> findById(@PathVariable("id") Integer id) throws Exception{
//        permissionService.findById(id);
        return ResponseEntity.ok(ResultMsg.success());
    }


}
