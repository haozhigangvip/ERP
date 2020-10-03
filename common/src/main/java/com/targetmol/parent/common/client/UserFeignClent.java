package com.targetmol.parent.common.client;

import com.targetmol.parent.common.vo.ResultMsg;
import com.targetmol.domain.system.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value="SYSTEM-SERVICE",path = "/sys/user")
public interface UserFeignClent {
    @GetMapping("{uid}")
    public  ResponseEntity<ResultMsg>findById(@PathVariable("uid") Integer uid) throws Exception ;

    @GetMapping("/profile")
    public ResponseEntity<ResultMsg>login(@RequestParam("username") String username);

    @PostMapping("findByddID")
    public User findByDdId(@RequestParam("ddId") String  ddid,@RequestParam("code") String code);
    @GetMapping("refreshCode")
    public ResponseEntity<ResultMsg> refreshCode(@RequestParam("username") String username) ;
}
