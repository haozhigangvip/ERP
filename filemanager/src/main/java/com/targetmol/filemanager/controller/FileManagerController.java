package com.targetmol.filemanager.controller;


import com.targetmol.common.vo.ResultMsg;
import com.targetmol.filemanager.domain.Shortfile;
import com.targetmol.filemanager.service.FileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
@RequestMapping
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

//    @GetMapping
//    public ModelAndView  getfile(@RequestParam("id") String fileid){
//        String url="http://"+tracker_servers+":"+file_port+"/"+fileid;
//        ModelAndView vw=new ModelAndView();
//        vw.setViewName("forward:"+url);
//        return  vw;
//    }
    //查看图片
    @GetMapping(value = "img",produces ={ MediaType.IMAGE_JPEG_VALUE,MediaType.APPLICATION_PDF_VALUE})
    public byte[]  showimg(@RequestParam("id") String  fileid) {
       byte[] b= fileManagerService.downloadfile(fileid);
        return b;
    }
    //下载文件
    @GetMapping("down")
    public void  download(@RequestParam("id") String  fileid, HttpServletResponse response) {
        byte[] b= fileManagerService.downloadfile(fileid);
        downfile(fileid,b,response);
    }

    @DeleteMapping
    public ResponseEntity<ResultMsg>  delfile(@RequestParam("id") String fileid){
        fileManagerService.delflile(fileid);
        return ResponseEntity.ok(ResultMsg.success());
    }

    @PutMapping("{id}")
        public void downloadByShortUrl(@PathVariable("id") String urlid, @RequestBody() Map<String,String> mp,HttpServletResponse response){

        Shortfile shortfile =fileManagerService.getShortUrl(urlid,mp.get("key"));
        downfile(shortfile.getFileid(),shortfile.getB(),response);

    }

    private void downfile(String fileid,byte[] b,HttpServletResponse response){
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileid.substring(fileid.lastIndexOf("/")+1) );

        ServletOutputStream out=null;
        try{
            out= response.getOutputStream();
            out.write(b);
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                out.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

}
