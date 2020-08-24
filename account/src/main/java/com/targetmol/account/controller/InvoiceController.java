//package com.targetmol.account.controller;
//
//import com.targetmol.account.service.InvoiceService;
//import com.targetmol.common.vo.PageResult;
//import com.targetmol.domain.Contact;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RequestMapping("/invoice")
//@RestController
//public class InvoiceController {
//    @Autowired
//    private InvoiceService invoiceService;
//
//    //按ID查询联系人
//    @GetMapping("{autoid}")
//    public ResponseEntity<Contact> findbyContactid(@PathVariable("autoid") Integer autoid){
//        return ResponseEntity.ok(invoiceService.findByAutoId(autoid));
//    }
//
//    //查询联系人
//    @GetMapping
//    public ResponseEntity<PageResult<Contact>> findByAll(
//            @RequestParam(value="page",defaultValue = "1") Integer page,
//            @RequestParam(value="pagesize",defaultValue = "20") Integer pageSize,
//            @RequestParam(value="softby",required = false) String softBy,
//            @RequestParam(value="desc",defaultValue = "false") Boolean desc,
//            @RequestParam(value="key",required = false) String key,
//            @RequestParam(value="showdel" ,defaultValue="false") Boolean showDel
//    ){
//
//        return ResponseEntity.ok(invoiceService.findByAll(page,pageSize,softBy,desc,key,showDel));
//    }
//
//
//    //新增联系人
//    @PostMapping
//    public ResponseEntity<Contact> addContact(@RequestBody Contact contact){
//        return ResponseEntity.ok(invoiceService.add(contact));
//    }
//    //修改联系人
//    @PutMapping()
//    public ResponseEntity<Contact> updateContact(@RequestBody Contact contact){
//        return ResponseEntity.ok(invoiceService.update(contact));
//    }
//    //删除联系人
//    @DeleteMapping("{autoid}")
//    public ResponseEntity deleteContanct(@PathVariable("autoid") Integer autoid){
//        invoiceService.setdelbj(autoid,1);
//        return ResponseEntity.ok("删除成功");
//    }
//
//
//}
