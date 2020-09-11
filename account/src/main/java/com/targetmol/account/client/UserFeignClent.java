package com.targetmol.account.client;

import com.targetmol.common.vo.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(value="SYSTEM-SERVICE")
public interface UserFeignClent {
    @GetMapping("/sys/user/{uid}")
    public  ResponseEntity<ResultMsg>findById(@PathVariable("uid") Integer uid) throws Exception ;
}
