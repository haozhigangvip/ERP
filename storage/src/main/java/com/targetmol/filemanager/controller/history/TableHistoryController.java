package com.targetmol.filemanager.controller.history;


import com.targetmol.common.vo.ResultMsg;
import com.targetmol.filemanager.domain.file.Shortfile;
import com.targetmol.filemanager.domain.history.TableHistory;
import com.targetmol.filemanager.service.file.FileManagerService;
import com.targetmol.filemanager.service.history.TableHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;


/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
@RequestMapping("history")
public class TableHistoryController {

    @Autowired
    TableHistoryService tableHistoryService;

    //添加历史记录
    @PostMapping()
    public ResponseEntity<ResultMsg> addHistory(@RequestBody TableHistory history){
        tableHistoryService.addHistory(history);
        return ResponseEntity.ok(ResultMsg.success());
    }

    //查询历史记录
    @GetMapping ()
    public ResponseEntity<ResultMsg> findHistory(@RequestParam("tablename") String tbname,
                                                 @RequestParam("tableid")Integer tid,
                                                 @RequestParam(value = "startdate",required = false)Date startdate,
                                                 @RequestParam(value = "enddate",required = false)Date enddate) {
       return ResponseEntity.ok(ResultMsg.success( tableHistoryService.findHistory(tbname,tid,startdate,enddate)));
    }


        @DeleteMapping
    public ResponseEntity<ResultMsg>  delfile(@RequestParam("id") String fileid){

        return ResponseEntity.ok(ResultMsg.success());
    }

}
