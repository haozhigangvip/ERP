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

//    @GetMapping("a")
//    public ResponseEntity<PageResult<Contact>> findByAll1(
//            @RequestParam(value="page",defaultValue = "1") Integer page,
//            @RequestParam(value="pagesize",defaultValue = "20") Integer pageSize,
//            @RequestParam(value="softby",required = false) String softBy,
//            @RequestParam(value="desc",defaultValue = "false") Boolean desc,
//            @RequestParam(value="key",required = false) String key,
//            @RequestParam(value="showdel" ,defaultValue="false") Boolean showDel
//    ){
//
//        return ResponseEntity.ok(contactService.getByAll(page,pageSize,softBy,desc,key,showDel));
//    }

//    //新增联系人
//    @PostMapping
//    public ResponseEntity<Contact> addContact(@RequestBody Contact contact){
//        return ResponseEntity.ok(contactService.add(contact));
//    }
//    //修改联系人
//    @PutMapping()
//    public ResponseEntity<Contact> updateContact(@RequestBody Contact contact){
//        return ResponseEntity.ok(contactService.update(contact));
//    }
//    //删除联系人
//    @DeleteMapping("{autoid}")
//    public ResponseEntity deleteContanct(@PathVariable("autoid") Integer autoid){
//        contactService.setdelbj(autoid,1);
//        return ResponseEntity.ok("删除成功");
//    }
}
