package com.targetmol.common.client;

import com.targetmol.common.vo.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@FeignClient(value="SYSTEM-SERVICE",path = "/sys/user")
public interface UserFeignClent {
    @GetMapping("{uid}")
    public  ResponseEntity<ResultMsg>findById(@PathVariable("uid") Integer uid) throws Exception ;

    @GetMapping("/profile")
    public ResponseEntity<ResultMsg>login(@RequestParam("username") String username);
}
