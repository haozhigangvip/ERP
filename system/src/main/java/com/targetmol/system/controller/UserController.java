package com.targetmol.system.controller;

import com.targetmol.common.vo.PageResult;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.system.User;
import com.targetmol.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

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
    public ResponseEntity<ResultMsg>updateUser(@PathVariable("uid") Integer uid,@RequestBody User user){
        user.setUid(uid);
        return ResponseEntity.ok(ResultMsg.success(userService.updateUser(user)));
    }

    //修改用户激活状态及密码
    @PutMapping("{uid}")
    public ResponseEntity<ResultMsg>active(@PathVariable("uid") Integer uid,
                                           @RequestParam (value="active" ,required = false) Integer active,
                                           @RequestParam(value="password",required = false) String password){
        if(active!=null){
            userService.updateActive(uid,active);
        }
        if(password!=null){
            userService.updatePassword(uid,password);
        }
        return ResponseEntity.ok(ResultMsg.success());
    }

    //按ID查询用户
    @GetMapping("{uid}")
    public  ResponseEntity<ResultMsg>findById(@PathVariable("uid") Integer uid) {
        return ResponseEntity.ok(ResultMsg.success(userService.findById(uid)));
    }



}
