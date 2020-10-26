package com.targetmol.sales.controller.Account;


import com.targetmol.common.controller.BaseController;
import com.targetmol.sales.service.Account.CompanyService;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.sales.Account.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/company")
@RestController
public class CompanyController extends BaseController {
    @Autowired
    private CompanyService companyService;
    //按id查询单位
    //@PreAuthorize("hasAuthority('company_list_companyid')")
    @GetMapping("{companyid}")
    public ResponseEntity<ResultMsg> findByComid(@PathVariable("companyid") Integer companyid){

        return ResponseEntity.ok(ResultMsg.success(companyService.findById(companyid)));
    }
    //查询所有单位
//    @PreAuthorize("hasAuthority('company_list_all')")
    @GetMapping
    public ResponseEntity<ResultMsg> findByAll(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="pageSize",defaultValue = "20") Integer pageSize,
            @RequestParam(value="softBy",required = false) String softBy,
            @RequestParam(value="desc",defaultValue = "false") Boolean desc,
            @RequestParam(value="key",required = false) String key,
            @RequestParam(value="actived" ,defaultValue="1") Integer actived
            ){
        Object object=companyService.findByAll(page,pageSize,softBy,desc,key,actived);

        return ResponseEntity.ok(ResultMsg.success(object));
    }
    //添加单位
    @PostMapping
    public ResponseEntity<ResultMsg> addCompany(@RequestBody Company company){
        companyService.addCompany(company);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //修改单位
    @PutMapping("{companyid}")
    public ResponseEntity<ResultMsg> updateCompany(@PathVariable("companyid") Integer companyid,
                                                   @RequestBody Company company){
        company.setCompanyid(companyid);
        companyService.updateCompany(company);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //冻结单位
    @DeleteMapping("{companyid}")
    public ResponseEntity<ResultMsg> deleteCompany(@PathVariable("companyid") Integer companyid){
        companyService.setactive(companyid,0);
        return ResponseEntity.ok(ResultMsg.success());
    }

}
