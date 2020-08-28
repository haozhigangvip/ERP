package com.targetmol.account.controller;

import com.targetmol.account.service.AddressServcie;
import com.targetmol.account.service.ContactService;
import com.targetmol.common.vo.PageResult;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.Address;
import com.targetmol.domain.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/address")
@RestController
public class AddressController {

    @Autowired
    private AddressServcie addressServcie;

    //按ID查询地址
    @GetMapping("{autoid}")
    public ResponseEntity<ResultMsg> findbyContactid(@PathVariable("autoid") Integer autoid){
        return ResponseEntity.ok(ResultMsg.success(addressServcie.findbyAutoid(autoid)));
    }

    //按联系人ID查询所有地址
    @GetMapping()
    public ResponseEntity<ResultMsg> findByAll(@RequestParam("contid") String contid){

        return ResponseEntity.ok(ResultMsg.success(addressServcie.findByAll(contid)));
    }



    //新增地址
    @PostMapping
    public ResponseEntity<ResultMsg> addAddress(@RequestBody Address address){
        return ResponseEntity.ok(ResultMsg.success(addressServcie.addAddress(address)));
    }

    //修改地址
    @PutMapping()
    public ResponseEntity<ResultMsg> updateAddress(@RequestBody Address address){
        return ResponseEntity.ok(ResultMsg.success(addressServcie.updateAddress(address)));
    }

    //删除地址
    @DeleteMapping("{autoid}")
    public ResponseEntity<ResultMsg> deleteAddress(@PathVariable("autoid") Integer autoid){
        addressServcie.delAddress(autoid);
        return ResponseEntity.ok(ResultMsg.success());
    }

}
