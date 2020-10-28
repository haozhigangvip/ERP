package com.targetmol.system.controller;

import com.targetmol.common.vo.ResultMsg;
import com.targetmol.system.service.AreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/area")
@RestController
public class AreaController {
    @Autowired
    private AreaService areaService;

    //查询国家地区及城市
    @GetMapping
    public ResponseEntity<ResultMsg> findAllCountry(@RequestParam(value = "code",required = false) String code,
                                                    @RequestParam(value="key",required = false) String key){

        return ResponseEntity.ok(ResultMsg.success(areaService.findAllArea(code,key)));
    }



}
