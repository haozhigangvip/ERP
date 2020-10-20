package com.targetmol.account.controller;

import com.targetmol.account.service.AreaService;
import com.targetmol.common.vo.ResultMsg;
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

    //查询国家
    @GetMapping
    public ResponseEntity<ResultMsg> findAllCountry(@RequestParam(value="key",required = false) String key){

        return ResponseEntity.ok(ResultMsg.success(areaService.findAllCountry(key)));
    }

    //查询地区
    @GetMapping("{cid}")
    public ResponseEntity<ResultMsg> findAllState(@PathVariable("cid") Integer cid, @RequestParam(value="key",required = false) String key){

        return ResponseEntity.ok(ResultMsg.success(areaService.findAllState(cid,key)));
    }

    //查询城市
    @GetMapping("{cid}/{sid}")
    public ResponseEntity<ResultMsg> findAllCity(@PathVariable("sid") Integer sid, @RequestParam(value="key",required = false) String key){

        return ResponseEntity.ok(ResultMsg.success(areaService.findAllCity(sid,key)));
    }


}
