package com.targetmol.system.controller;

import com.targetmol.parent.common.vo.ResultMsg;
import com.targetmol.domain.system.PermissionGroup;
import com.targetmol.system.service.PermissionGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/group")
@RestController
public class PemissionGroupController {
    @Autowired
    private PermissionGroupService permissionGroupService;

    //添加权限组
    @PostMapping
    public ResponseEntity<ResultMsg> addPermissionGroup(@RequestBody PermissionGroup permissionGroup){
        permissionGroupService.addPermissionGroup(permissionGroup);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //绑定用户
    @PutMapping("binduser")
    public  ResponseEntity<ResultMsg>bindUsers(@RequestBody Map<String,Object>mp){
        Integer gid=(Integer)mp.get("groupid");
        List<Integer> uids=(List<Integer>)mp.get("uids");
        permissionGroupService.bindUsers(gid,uids);
        return ResponseEntity.ok(ResultMsg.success());
    }


    //根据id修改权限组
    @PutMapping("{id}")
    public ResponseEntity<ResultMsg> savePermissionGroup(@PathVariable Integer id,@RequestBody PermissionGroup permissionGroup){
        permissionGroup.setId(id);
        permissionGroupService.savePermissionGroup(permissionGroup);
        return ResponseEntity.ok(ResultMsg.success());
    }


    //根据ID删除权限组
    @DeleteMapping("{id}")
    public ResponseEntity<ResultMsg> delPermissionGroup(@PathVariable Integer id){
        permissionGroupService.delPermissionGroup(id);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //根据组ID查询绑定的用户
    @GetMapping("{gid}")
    public ResponseEntity<ResultMsg>findUserByGid(@PathVariable Integer gid){
        return ResponseEntity.ok(ResultMsg.success(permissionGroupService.findAllByGid(gid)));
    }

    //查询所有权限组
    @GetMapping
    public ResponseEntity<ResultMsg> findAll(){
        return ResponseEntity.ok(ResultMsg.success(permissionGroupService.findAll()));
    }

    //根据用户ID查询所有子用户
    @GetMapping("subuser")
    public  ResponseEntity<ResultMsg> findAllSubUserByUid(@RequestParam("uid") Integer uid){
        return ResponseEntity.ok(ResultMsg.success(permissionGroupService.findAllSubUidByUid(uid)));
    }

}
