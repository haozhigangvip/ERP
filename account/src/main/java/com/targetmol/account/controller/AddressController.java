package com.targetmol.account.controller;

import com.targetmol.account.service.AddressServcie;
import com.targetmol.account.service.ContactService;
import com.targetmol.common.vo.PageResult;
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
//
//    //按ID查询联系人
//    @GetMapping("{autoid}")
//    public ResponseEntity<Contact> findbyContactid(@PathVariable("autoid") Integer autoid){
//        return ResponseEntity.ok(contactService.findByAutoId(autoid));
//    }

    //查询联系人
    @GetMapping()
    public ResponseEntity<List<Address>> findByAll(@RequestParam("contid") String contid){

        return ResponseEntity.ok(addressServcie.findByAll(contid));
    }



    //新增联系人
    @PostMapping
    public ResponseEntity<Address> addAddress(@RequestBody Address address){
        return ResponseEntity.ok(addressServcie.addAddress(address));
    }
    //修改联系人
    @PutMapping()
    public ResponseEntity<Address> updateAddress(@RequestBody Address address){
        return ResponseEntity.ok(addressServcie.updateAddress(address));
    }
    //删除联系人
    @DeleteMapping("{autoid}")
    public ResponseEntity deleteAddress(@PathVariable("autoid") Integer autoid){
        addressServcie.delAddress(autoid);
        return ResponseEntity.ok("删除成功");
    }
}
