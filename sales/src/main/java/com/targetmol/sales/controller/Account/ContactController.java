package com.targetmol.sales.controller.Account;

import com.targetmol.sales.service.Account.ContactService;
import com.targetmol.common.vo.PageResult;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.sales.Account.Contact;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RequestMapping("/contact")
@RestController
public class ContactController {
    @Autowired
    private ContactService contactService;

    //按ID查询联系人
    @GetMapping("{contid}")
    public ResponseEntity<ResultMsg> findbyContactid(@PathVariable ("contid") Integer contid) throws Exception{
              return ResponseEntity.ok(ResultMsg.success(contactService.findByContId(contid)));
    }




    //查询联系人
    @GetMapping
    public PageResult<Contact> findByAll (
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="pagesize",defaultValue = "30") Integer pageSize,
            @RequestParam(value="softby",required = false) String softBy,
            @RequestParam(value="desc",defaultValue = "false") Boolean desc,
            @RequestParam(value="key",required = false) String key,
            @RequestParam(value="showunactive" ,defaultValue="false")  Boolean showunactive,
            @RequestParam(value="pid",required = false) Integer pid) throws Exception
    {
        PageResult<Contact> conts=contactService.findByAll(page,pageSize,softBy,desc,key,showunactive,pid);
        return conts;
    }

    @GetMapping("findByName")
    public  ResponseEntity<ResultMsg> findByName(@RequestParam("key") String key){
        return ResponseEntity.ok(ResultMsg.success(contactService.findByName(key)));
    }



    //新增联系人
    @PostMapping
    public ResponseEntity<ResultMsg> addContact(@RequestBody Contact contact){
        contactService.add(contact);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //修改联系人
    @PutMapping("{contid}")
    public ResponseEntity<ResultMsg> updateContact(@PathVariable("contid") Integer contid,
                                                   @RequestBody Contact contact){
        contact.setContactid(contid);
        contactService.update(contact);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //删除联系人
    @DeleteMapping("{contid}")
    public ResponseEntity<ResultMsg> deleteContanct(@PathVariable("contid") Integer  contid){

        contactService.setActived(contid,0);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //绑定子联系人
    @PutMapping("{contid}/subcontact")
    public ResponseEntity<ResultMsg> bindSubContact(@PathVariable("contid") Integer pid,
                                                    @RequestParam  ("subcontid") Integer contid,
                                                    @RequestParam(value = "ubind" ,defaultValue = "false") Boolean ubind) throws Exception{
        contactService.bindSubContact(pid,contid,ubind);
        return ResponseEntity.ok(ResultMsg.success()) ;
    }



    //绑定公司
    @PutMapping("{contid}/assignCompany")
    public ResponseEntity<ResultMsg> assignCompany(@PathVariable("contid" )Integer contid,
                                                   @RequestBody Map<String,Object> map) throws Exception{
        contactService.assignCompany(contid,map);
        return ResponseEntity.ok(ResultMsg.success()) ;
    }

    //绑定公司
    @PutMapping("{contid}/unassignCompany")
    public ResponseEntity<ResultMsg> unassignCompany(@PathVariable("contid" )Integer contid,
                                                   @RequestBody Map<String,Object> map){
        contactService.unassignCompany(contid,map);
        return ResponseEntity.ok(ResultMsg.success()) ;
    }

}
