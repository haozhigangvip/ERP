package com.targetmol.account.controller;

import com.targetmol.account.service.InvoiceInfoService;
import com.targetmol.common.vo.ResultMsg;
import com.targetmol.domain.sales.InvoiceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/invoiceinfo")
@RestController
public class InvoiceInfoController {
    @Autowired
    private InvoiceInfoService invoiceInfoService;

    //根据联系人ID查询所有开票信息123
    @GetMapping
    public ResponseEntity<ResultMsg> findAllInvoiceInfo(@RequestParam(value="contid") Integer contid){
        return ResponseEntity.ok(ResultMsg.success(invoiceInfoService.findAllByContId(contid)));
    }

    //根据开票信息ID进行查询
    @GetMapping("{invoiceid}")
    public ResponseEntity<ResultMsg> findAllState(@PathVariable("invoiceid") Integer invoiceid){
        //TEST213411222
        return ResponseEntity.ok(ResultMsg.success(invoiceInfoService.findById(invoiceid)));
    }

    //添加开票信息
    @PostMapping
    public ResponseEntity<ResultMsg> addInvoiceInfo(@RequestBody InvoiceInfo invoiceInfo){
        invoiceInfoService.addInvoiceInfo(invoiceInfo);
        return ResponseEntity.ok(ResultMsg.success());

    }

    //根据ID修改开票信息
    @PutMapping("{invoiceid}")
    public ResponseEntity<ResultMsg> updateInvoiceInfo(@PathVariable("invoiceid") Integer invoiceid,
                                                   @RequestBody InvoiceInfo invoiceInfo){
        invoiceInfo.setId(invoiceid);
        invoiceInfoService.updateInvoiceInfo(invoiceInfo);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //根据ID删除开票信息
    @DeleteMapping("{invoiceid}")
    public ResponseEntity<ResultMsg> delInvoiceInfo(@PathVariable("invoiceid") Integer invoiceid){
        //TEST
        invoiceInfoService.delInvoiceInfo(invoiceid);
        return ResponseEntity.ok(ResultMsg.success());

    }
}
