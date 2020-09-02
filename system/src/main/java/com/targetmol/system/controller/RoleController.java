package com.targetmol.system.controller;

import com.targetmol.common.vo.PageResult;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.system.Role;
import com.targetmol.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        roleService.addRole(role);
        return ResponseEntity.ok(ResultMsg.success());
    }



    //修改角色
    @PutMapping("{rid}")
    public ResponseEntity<ResultMsg>updateUser(@PathVariable("rid") Integer rid, @RequestBody Role role){
        role.setRid(rid);
        roleService.updateRole(role);
        return ResponseEntity.ok(ResultMsg.success());
    }



    //按ID查询角色
    @GetMapping("{rid}")
    public  ResponseEntity<ResultMsg>findById(@PathVariable("rid") Integer rid) throws Exception{
        return ResponseEntity.ok(ResultMsg.success(roleService.findById(rid)));
    }

    //根据ID删除角色
    @DeleteMapping("{rid}")
    public ResponseEntity<ResultMsg>deleteById(@PathVariable("rid") Integer rid){
        roleService.deleteById(rid);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //分配角色
    @PutMapping("assignPermission")
    public ResponseEntity<ResultMsg>assignPermission(@RequestBody Map<String, Object> map){
        //1.获取被分配的角色ID
        Integer rid=  (Integer)map.get("rid");
        //2.获取权限ID集合
        List<Integer> permsids=(List<Integer>)map.get("permissionIds");
        //3.调用Service完成分配权限
        roleService.assignRoles(rid,permsids);
        return ResponseEntity.ok(ResultMsg.success());
    }


}
