package com.targetmol.account.controller;

import com.targetmol.account.service.AreaService;
import com.targetmol.domain.Area;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/area")
@RestController
public class AreaController {
    @Autowired
    private AreaService areaService;

    //查询国家
    @GetMapping
    public ResponseEntity<List<Area>> findAllCountry(@RequestParam(value="key",required = false) String key){

        return ResponseEntity.ok(areaService.findAllCountry(key));
    }

    //查询地区
    @GetMapping("{cid}")
    public ResponseEntity<List<Area>> findAllState(@PathVariable("cid") Integer cid, @RequestParam(value="key",required = false) String key){

        return ResponseEntity.ok(areaService.findAllState(cid,key));
    }
    //查询城市
    @GetMapping("{cid}/{sid}")
    public ResponseEntity<List<Area>> findAllCity(@PathVariable("sid") Integer sid, @RequestParam(value="key",required = false) String key){

        return ResponseEntity.ok(areaService.findAllCity(sid,key));
    }
}
