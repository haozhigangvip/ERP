package com.targetmol.account.controller;


import com.targetmol.account.service.CompanyService;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.account.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/company")
@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    //按autoid查询company
    @GetMapping("{autoid}")
    public ResponseEntity<ResultMsg> findByComid(@PathVariable("autoid") Integer autoid){

        return ResponseEntity.ok(ResultMsg.success(companyService.findById(autoid)));
    }
    //查询所有company
    @GetMapping
    public ResponseEntity<ResultMsg> findByAll(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="pageSize",defaultValue = "20") Integer pageSize,
            @RequestParam(value="softBy",required = false) String softBy,
            @RequestParam(value="desc",defaultValue = "false") Boolean desc,
            @RequestParam(value="key",required = false) String key,
            @RequestParam(value="showDel" ,defaultValue="false") Boolean showDel
            ){

        return ResponseEntity.ok(ResultMsg.success(companyService.findByAll(page,pageSize,softBy,desc,key,showDel)));
    }
    //添加company
    @PostMapping
    public ResponseEntity<ResultMsg> addCompany(@RequestBody Company company){
        return ResponseEntity.ok(ResultMsg.success(companyService.addCompany(company)));
    }

    //修改company
    @PutMapping("{autoid}")
    public ResponseEntity<ResultMsg> updateCompany(@PathVariable("autoid") Integer autoid,
                                                   @RequestBody Company company){
        company.setAutoid(autoid);
        return ResponseEntity.ok(ResultMsg.success(companyService.updateCompany(company)));
    }

    //删除company
    @DeleteMapping("{autoid}")
    public ResponseEntity<ResultMsg> deleteCompany(@PathVariable("autoid") Integer autoid){
        companyService.setdelbj(autoid,1);
        return ResponseEntity.ok(ResultMsg.success());
    }

}
