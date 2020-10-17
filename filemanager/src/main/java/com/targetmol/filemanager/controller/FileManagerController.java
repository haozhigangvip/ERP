package com.targetmol.filemanager.controller;


import com.targetmol.common.vo.ResultMsg;
import com.targetmol.filemanager.service.FileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;


/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
@RequestMapping()
public class FileManagerController{
    @Value("${tsbio.fastdfs.tracker_servers}")
    String tracker_servers;
    @Value("${tsbio.fastdfs.file_port}")
    String file_port;

    @Autowired
    FileManagerService fileManagerService;

    @PostMapping
    public ResponseEntity<ResultMsg> upload(MultipartFile file) {

        return ResponseEntity.ok(ResultMsg.success(fileManagerService.upload(file)));
    }

    @GetMapping()
    public ModelAndView  getfile(@RequestParam("id") String fileid){
        String url="http://"+tracker_servers+":"+file_port+"/"+fileid;
        return  new ModelAndView(new RedirectView(url));
    }
    @DeleteMapping
    public ResponseEntity<ResultMsg>  delfile(@RequestParam("id") String fileid){
        fileManagerService.delflile(fileid);
        return ResponseEntity.ok(ResultMsg.success());
    }

    @PutMapping("{id}")
        public ResponseEntity<ResultMsg> getShortfile(@PathVariable("id") String urlid, @RequestBody() Map<String,String> mp){
        return ResponseEntity.ok(ResultMsg.success(fileManagerService.getShortUrl(urlid,mp.get("key"))));
    }

}
