package com.targetmol.system.controller;

import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.Department;
import com.targetmol.system.service.PermissionService;
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
    private PermissionService permissionService;

    //添加权限
    @PostMapping
    public ResponseEntity<ResultMsg> addPermission(@RequestBody Map<String,Object> map) throws Exception {
        permissionService.addPermission(map);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //修改权限
    @PutMapping("{id}")
    public ResponseEntity<ResultMsg> updatePermission(@PathVariable(value="id") Integer id,
                                                      @RequestBody Map<String,Object> map) throws Exception {
        map.put("id",id);
        permissionService.updatePermission(map);
        return ResponseEntity.ok(ResultMsg.success());
    }

}
