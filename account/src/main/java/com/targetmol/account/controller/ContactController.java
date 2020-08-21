package com.targetmol.account.controller;

import com.targetmol.account.service.ContactService;
import com.targetmol.domain.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin2.gluegen.runtime.CPU;

@Slf4j
@RequestMapping("/contact")
@RestController
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping("{contID}")
    public ResponseEntity<Contact> findbyContactid(@PathVariable ("contID") String contid){
              return ResponseEntity.ok(contactService.findByContId(contid));
    }
}
