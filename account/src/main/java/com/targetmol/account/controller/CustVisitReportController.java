package com.targetmol.account.controller;

import com.targetmol.account.service.CustVisitReportService;
import com.targetmol.common.vo.PageResult;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.account.CustVisitReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/visitreport")
@RestController
public class CustVisitReportController {
    @Autowired
    private CustVisitReportService custVisitReportService;

    //添加拜访报告
    @PostMapping
    public ResponseEntity<ResultMsg> addCustVisitReport(@RequestBody CustVisitReport custVisitReport){
        custVisitReportService.addCustVisitReport(custVisitReport);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //修改拜访报告
    @PutMapping("{id}")
    public ResponseEntity<ResultMsg> editCustVisitReport(@PathVariable Integer id, @RequestBody CustVisitReport custVisitReport){
        custVisitReport.setId(id);
        custVisitReportService.editCustVisitReport(custVisitReport);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //删除拜访报告
    @DeleteMapping("{id}")
    public ResponseEntity<ResultMsg> deleteCustVisitReport(@PathVariable Integer id){
        custVisitReportService.deleteCustVisitReport(id);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //按ID修改拜访报告
    @GetMapping("{id}")
    public ResponseEntity<ResultMsg> findCustVisitReportById(@PathVariable Integer id){
        return ResponseEntity.ok(ResultMsg.success(custVisitReportService.findCustVisitReportById(id)));
    }

    //查询所有拜访报告
    @GetMapping
    public PageResult<CustVisitReport> findByAll (
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="pagesize",defaultValue = "30") Integer pageSize,
            @RequestParam(value="softby",required = false) String softBy,
            @RequestParam(value="desc",defaultValue = "false") Boolean desc,
            @RequestParam(value="contid" ,required=false) Integer contid,
            @RequestParam(value="startdate",required = false) String startDate,
            @RequestParam(value="enddate",required = false)String endDate
    ) throws Exception{
        PageResult conts=custVisitReportService.findByAll(page,pageSize,softBy,desc,contid,startDate,endDate);
        return conts;
    }


}
