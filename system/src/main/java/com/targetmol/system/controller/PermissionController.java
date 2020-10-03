package com.targetmol.system.controller;

import com.targetmol.parent.common.vo.ResultMsg;
import com.targetmol.domain.system.Permission;
import com.targetmol.system.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/permission")
@RestController
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    //添加权限
    @PostMapping
    public ResponseEntity<ResultMsg> addPermission(@RequestBody Permission permission) throws Exception {
        permissionService.addPermission(permission);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //修改权限
    @PutMapping("{id}")
    public ResponseEntity<ResultMsg> updatePermission(@PathVariable(value="id") Integer id,
                                                      @RequestBody Permission permission) throws Exception {
        permission.setId(id);
        permissionService.updatePermission(permission);
        return ResponseEntity.ok(ResultMsg.success());
    }
    //删除权限
    @DeleteMapping("{id}")
    public ResponseEntity<ResultMsg> deletePermission(@PathVariable(value="id") Integer id){
         permissionService.deletePermission(id);
        return ResponseEntity.ok(ResultMsg.success());
    }
    //查询所有权限表
    @GetMapping
    public ResponseEntity<List<Permission>> findByAll()
    {
        return ResponseEntity.ok(permissionService.findByAll());
    }

    //按ID查询权限
    @GetMapping("{id}")
    public ResponseEntity<ResultMsg> findById(@PathVariable("id") Integer id) throws Exception{
        permissionService.findById(id);
        return ResponseEntity.ok(ResultMsg.success());
    }




}
