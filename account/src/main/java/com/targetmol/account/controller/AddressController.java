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
    @GetMapping("{autoid}")
    public ResponseEntity<ResultMsg> findbyContactid(@PathVariable("autoid") Integer autoid){
        return ResponseEntity.ok(ResultMsg.success(addressServcie.findbyAutoid(autoid)));
    }


    //查询联系人
    @GetMapping
    public ResponseEntity<PageResult<Address>> findByAll(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="pagesize",defaultValue = "20") Integer pageSize,
            @RequestParam(value="softby",required = false) String softBy,
            @RequestParam(value="desc",defaultValue = "false") Boolean desc,
            @RequestParam(value="key",required = false) String key
    ){

        return ResponseEntity.ok(addressServcie.findByAll(page,pageSize,softBy,desc,key));
    }


    //新增地址
    @PostMapping
    public ResponseEntity<ResultMsg> addAddress(@RequestBody Address address){
        addressServcie.addAddress(address);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //修改地址
    @PutMapping("{autoid}")
    public ResponseEntity<ResultMsg> updateAddress(@PathVariable("autoid") Integer autoid,
                                                   @RequestBody Address address){
        address.setAutoid(autoid);
        addressServcie.updateAddress(address);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //删除地址
    @DeleteMapping("{autoid}")
    public ResponseEntity<ResultMsg> deleteAddress(@PathVariable("autoid") Integer autoid){
        addressServcie.delAddress(autoid);
        return ResponseEntity.ok(ResultMsg.success());
    }

}
