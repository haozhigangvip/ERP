package com.targetmol.system.controller;

import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.utils.JwtUtils;
import com.targetmol.common.vo.PageResult;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.system.User;
import com.targetmol.system.service.RoleService;
import com.targetmol.system.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    @Autowired
    private JwtUtils jwtUtils;

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
    //根据ID删除用户
    @DeleteMapping("{uid}")
    public ResponseEntity<ResultMsg>deleteById(@PathVariable("uid")Integer uid ,@RequestParam(value = "actived",defaultValue ="0") Integer actived){
        userService.updateActive(uid ,actived);
        return ResponseEntity.ok(ResultMsg.success());
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

    //登录验证
    @PostMapping("/login")
    public ResponseEntity<ResultMsg>login(@RequestBody Map<String ,Object> loginMap){
        //根据用户名及密码查询用户
        User user=userService.login(loginMap.get("username").toString(),loginMap.get("password").toString());
        //生成JWT信息，返回token
        Map<String,Object> map=new HashMap<>();

        map.put("departmentId",user.getDepartmentid());
        map.put("email",user.getEmail());
        String uid=user.getUid().toString();
        String token=jwtUtils.createJwt(uid,user.getUsername(),map);
        return ResponseEntity.ok(ResultMsg.success(token));
    }

    //获取用户信息鉴权
    @PostMapping("/profile")
    public ResponseEntity<ResultMsg>Profile(HttpServletRequest request) throws Exception{
        String authorization=request.getHeader("Authorization").replace("Bearer ","");

        if(StringUtil.isEmpty(authorization)){
            throw new ErpExcetpion(ExceptionEumn.PERMISSION_GRANT_FAILED);
        }
        String token=authorization;


        Claims claims=jwtUtils.parseJwt(token);
        if(claims==null){
            throw new ErpExcetpion(ExceptionEumn.PERMISSION_GRANT_FAILED);
        }
        String uid=claims.getId();
        System.out.println(uid);
        User user=null;
        if(uid!=null){
             user =userService.findById(Integer.parseInt(uid));
        }
        return  ResponseEntity.ok(ResultMsg.success(user));
    }

}
