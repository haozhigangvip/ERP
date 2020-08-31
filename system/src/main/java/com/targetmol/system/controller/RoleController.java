package com.targetmol.system.controller;

import com.targetmol.common.vo.PageResult;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.system.Role;
import com.targetmol.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/role")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    //查找所有角色
    @GetMapping()
    public ResponseEntity<PageResult<Role>> findByAll(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="pagesize",defaultValue = "30") Integer pageSize,
            @RequestParam(value="softby",required = false) String softBy,
            @RequestParam(value="desc",defaultValue = "false") Boolean desc,
            @RequestParam(value="key",required = false) String key
    ){
        return ResponseEntity.ok(roleService.findByAll(page,pageSize,softBy,desc,key));
    }



    //添加角色
    @PostMapping
    public  ResponseEntity<ResultMsg>addUser(@RequestBody Role role) {
        return ResponseEntity.ok(ResultMsg.success(roleService.addRole(role)));
    }



    //修改角色
    @PutMapping("{rid}")
    public ResponseEntity<ResultMsg>updateUser(@PathVariable("rid") Integer rid, @RequestBody Role role){
        role.setRid(rid);
        return ResponseEntity.ok(ResultMsg.success(roleService.updateRole(role)));
    }



    //按ID查询角色
    @GetMapping("{rid}")
    public  ResponseEntity<ResultMsg>findById(@PathVariable("rid") Integer rid) {
        return ResponseEntity.ok(ResultMsg.success(roleService.findById(rid)));
    }

    //根据ID删除角色
    @DeleteMapping("{rid}")
    public ResponseEntity<ResultMsg>deleteById(@PathVariable("rid") Integer rid){
        roleService.deleteById(rid);
        return ResponseEntity.ok(ResultMsg.success());
    }


}
