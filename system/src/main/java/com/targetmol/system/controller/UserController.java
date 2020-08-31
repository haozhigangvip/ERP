package com.targetmol.system.controller;

import com.targetmol.common.vo.PageResult;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.system.User;
import com.targetmol.system.service.RoleService;
import com.targetmol.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    private UserController(UserService userService){
        this.userService=userService;
    }

    //查找所有用户
    @GetMapping()
    public ResponseEntity<PageResult<User>> findByAll(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="pagesize",defaultValue = "30") Integer pageSize,
            @RequestParam(value="softby",required = false) String softBy,
            @RequestParam(value="desc",defaultValue = "false") Boolean desc,
            @RequestParam(value="key",required = false) String key,
            //active属性：0 所有 ，1仅看已激活 ，2仅看未激活
            @RequestParam(value="active" ,defaultValue="0") Integer active,
            @RequestParam(value="showsales" ,defaultValue="false")Boolean showsales
    ){
        return ResponseEntity.ok(userService.findByAll(page,pageSize,softBy,desc,key,active,showsales));
    }



    //添加用户
    @PostMapping
    public  ResponseEntity<ResultMsg>addUser(@RequestBody User user) {
        return ResponseEntity.ok(ResultMsg.success(userService.addUser(user)));
    }



    //修改用户
    @PutMapping("{uid}")
    public ResponseEntity<ResultMsg>updateUser(@PathVariable("uid") Integer uid, @RequestBody User user){
        user.setUid(uid);
        return ResponseEntity.ok(ResultMsg.success(userService.updateUser(user)));
    }



    //按ID查询用户
    @GetMapping("{uid}")
    public  ResponseEntity<ResultMsg>findById(@PathVariable("uid") Integer uid) {
        return ResponseEntity.ok(ResultMsg.success(userService.findById(uid)));
    }

    //分配角色
    @PutMapping("assignRoles")
    public ResponseEntity<ResultMsg>assignRoles(@RequestBody Map<String, Object> map){
        //1.获取被分配的用户ID
        Integer uid=  (Integer)map.get("uid");
        //2.获取角色ID集合
        List<Integer> rolesids=(List<Integer>)map.get("roleIds");
        //3.调用Service完成分配角色
        userService.assignRoles(uid,rolesids);
        return ResponseEntity.ok(ResultMsg.success());
    }


}
