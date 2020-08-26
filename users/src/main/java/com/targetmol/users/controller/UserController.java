package com.targetmol.users.controller;

import com.targetmol.common.vo.PageResult;
import com.targetmol.domain.Contact;
import com.targetmol.domain.User;
import com.targetmol.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping()
    public ResponseEntity<PageResult<User>> findByAll(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="pagesize",defaultValue = "20") Integer pageSize,
            @RequestParam(value="softby",required = false) String softBy,
            @RequestParam(value="desc",defaultValue = "false") Boolean desc,
            @RequestParam(value="key",required = false) String key,
            @RequestParam(value="showdel" ,defaultValue="false") Boolean showDel
    ){
        return ResponseEntity.ok(userService.findByAll(page,pageSize,softBy,desc,key,showDel));
    }
}
