package com.targetmol.system.controller;

import com.targetmol.common.controller.BaseController;
import com.targetmol.common.utils.JwtUtils;
import com.targetmol.common.vo.PageResult;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.system.User;
import com.targetmol.domain.system.ext.UserExt;
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
public class UserController extends BaseController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){

        this.userService=userService;
    }

    @Autowired
    private JwtUtils jwtUtils;

    //查找所有用户
//    @PreAuthorize("hasAuthority('user_list_all')")
    @GetMapping()
    public ResponseEntity<PageResult<UserExt>> findByAll(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="pagesize",defaultValue = "30") Integer pageSize,
            @RequestParam(value="softby",required = false) String softBy,
            @RequestParam(value="desc",defaultValue = "false") Boolean desc,
            @RequestParam(value="key",required = false) String key,
            //active属性：0 所有 ，1仅看已激活 ，2仅看未激活
            @RequestParam(value="active" ,defaultValue="1") Integer active,
            @RequestParam(value="showsales" ,defaultValue="false")Boolean showsales
    ){

        return ResponseEntity.ok(userService.findByAll(page,pageSize,softBy,desc,key,active,showsales));
    }


//    @PreAuthorize("hasAuthority('user_list_sales')")
    @GetMapping("/sales")
    public ResponseEntity<ResultMsg> findByAllSales(){
        return ResponseEntity.ok(ResultMsg.success(userService.findAllSales()));
    }


    //添加用户
    @PostMapping
    public  ResponseEntity<ResultMsg>addUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok(ResultMsg.success());
    }



    //修改用户
    @PutMapping("{uid}")
    public ResponseEntity<ResultMsg>updateUser(@PathVariable("uid") Integer uid, @RequestBody User user){
        user.setUid(uid);
        userService.updateUser(user);
        return ResponseEntity.ok(ResultMsg.success());
    }



    //按ID查询用户
    @GetMapping("{uid}")
    public  ResponseEntity<ResultMsg>findById(@PathVariable("uid") Integer uid) throws Exception{
        return ResponseEntity.ok(ResultMsg.success(userService.findById(uid)));
    }
    //根据钉钉ID查询用户
    @PostMapping("findByddID")
    public User findByDdId(@RequestParam("ddId") String  ddid,@RequestParam("code") String code){
        return userService.findByDdId(ddid,code);
    }


    //根据ID删除用户
    @DeleteMapping("{uid}")
    public ResponseEntity<ResultMsg>deleteById(@PathVariable("uid")Integer uid ,@RequestParam(value = "actived",defaultValue ="0") Integer actived){
        userService.updateActive(uid ,actived);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //分配角色
    @PutMapping("/assignRoles")
    public ResponseEntity<ResultMsg>assignRoles(@RequestBody Map<String, Object> map){
        //1.获取被分配的用户ID
        Integer uid=  (Integer)map.get("uid");
        //2.获取角色ID集合
        List<Integer> rolesids=(List<Integer>)map.get("roleids");
        //3.调用Service完成分配角色
        userService.assignRoles(uid,rolesids);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //登录验证
    @GetMapping("/profile")
    public ResponseEntity<ResultMsg>login(@RequestParam("username")String username){
        //根据用户名及密码查询用户

        return ResponseEntity.ok(ResultMsg.success(userService.login(username)));
    }
    @GetMapping("refreshCode")
    public ResponseEntity<ResultMsg> refreshCode(@RequestParam("username") String username) {
        userService.refreshCode(username);
        return ResponseEntity.ok(ResultMsg.success());
    }


}
