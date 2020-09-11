package com.targetmol.account.controller;

import com.targetmol.account.service.AddressServcie;
import com.targetmol.common.vo.PageResult;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.account.Address;
import com.targetmol.domain.account.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/address")
@RestController
public class AddressController {

    @Autowired
    private AddressServcie addressServcie;

    //按ID查询地址
    @GetMapping("{addrid}")
    public ResponseEntity<ResultMsg> findbyContactid(@PathVariable("addrid") Integer addrid){
        return ResponseEntity.ok(ResultMsg.success(addressServcie.findByContId(addrid)));
    }




    //查询联系人
    @GetMapping
    public ResponseEntity<PageResult<Address>> findByAll(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="pagesize",defaultValue = "20") Integer pageSize,
            @RequestParam(value="softby",required = false) String softBy,
            @RequestParam(value="desc",defaultValue = "false") Boolean desc,
            @RequestParam(value="key",required = false) String key,
            @RequestParam(value="contid",required = false) Integer contid,
            @RequestParam(value="default",required = false) Integer def
    ){

        return ResponseEntity.ok(addressServcie.findByAll(page,pageSize,softBy,desc,key,contid,def));
    }


    //新增地址
    @PostMapping
    public ResponseEntity<ResultMsg> addAddress(@RequestBody Address address){

        return ResponseEntity.ok(ResultMsg.success(addressServcie.addAddress(address)));
    }

    //修改地址
    @PutMapping("{addrid}")
    public ResponseEntity<ResultMsg> updateAddress(@PathVariable("addrid") Integer addrid,
                                                   @RequestBody Address address){
        address.setAddrid(addrid);

        return ResponseEntity.ok(ResultMsg.success(addressServcie.updateAddress(address)));
    }

    //删除地址
    @DeleteMapping("{addrid}")
    public ResponseEntity<ResultMsg> deleteAddress(@PathVariable("addrid") Integer addrid){
        addressServcie.unActiveAddress(addrid);
        return ResponseEntity.ok(ResultMsg.success());
    }



}
