package com.targetmol.account.controller;


import com.targetmol.account.service.CompanyService;
import com.targetmol.common.vo.PageResult;
import com.targetmol.domain.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/company")
@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("{comid}")
    public ResponseEntity<Company> findByComid(@PathVariable("comid") String comid){

        return ResponseEntity.ok(companyService.findByComid(comid));
    }

    @GetMapping
    public ResponseEntity<PageResult<Company>> findByAll(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="pageSize",defaultValue = "20") Integer pageSize,
            @RequestParam(value="softBy",required = false) String softBy,
            @RequestParam(value="desc",defaultValue = "false") Boolean desc,
            @RequestParam(value="key",required = false) String key,
            @RequestParam(value="showDel" ,defaultValue="false") Boolean showDel
            ){

        return ResponseEntity.ok(companyService.findByAll(page,pageSize,softBy,desc,key,showDel));
    }

    @PostMapping
    public ResponseEntity<Company> saveCompany(@RequestBody Company company){


        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.save(company));
    }

}
