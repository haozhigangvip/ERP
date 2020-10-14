package com.targetmol.filemanager.service;

import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.filemanager.dao.FileManagerDao;
import com.targetmol.filemanager.domain.FileSystem;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.util.StringUtil;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class FileManagerService {

    @Value("${tsbio.fastdfs.tracker_servers}")
    String tracker_servers;
    @Value("${tsbio.fastdfs.tracker_port}")
    String tracker_port;
    @Value("${tsbio.fastdfs.connect_timeout_in_seconds}")
    int connect_timeout_in_seconds;
    @Value("${tsbio.fastdfs.network_timeout_in_seconds}")
    int network_timeout_in_seconds;
    @Value("${tsbio.fastdfs.charset}")
    String charset;

    @Autowired
    FileManagerDao fileManagerDao;

    //上传文件
    public FileSystem upload( MultipartFile multipartFile){
        if(multipartFile ==null){
           throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        //第一步：将文件上传到fastDFS中，得到一个文件id
        String fileId = fdfs_upload(multipartFile);
        if(StringUtils.isEmpty(fileId)){
            throw new ErpExcetpion(ExceptionEumn.FILE_UPLOAD_FAILD);
        }

        //第二步：将文件id及其它文件信息存储到mongodb中。
        FileSystem fileSystem = new FileSystem();
        fileSystem.setFileId(fileId);
        fileSystem.setFilePath(fileId);
        Date day=new Date();
        fileSystem.setExpireAt(day);
        fileSystem.setFileName(multipartFile.getOriginalFilename());
        fileSystem.setFileType(multipartFile.getContentType());

        fileManagerDao.save(fileSystem);

      return fileSystem;
    }

    //上传文件到fastDFS

    /**
     *
     * @param multipartFile 文件
     * @return 文件id
     */
    private String fdfs_upload(MultipartFile multipartFile){
         //初始化fastDFS的环境
         initFdfsConfig();
         //创建trackerClient
        TrackerClient trackerClient = new TrackerClient();
        try {
            TrackerServer trackerServer = trackerClient.getConnection();
            //得到storage服务器
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建storageClient来上传文件
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,storeStorage);
            //上传文件
            //得到文件字节
            byte[] bytes = multipartFile.getBytes();
            //得到文件的原始名称
            String originalFilename = multipartFile.getOriginalFilename();
            //得到文件扩展名
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String fileId = storageClient1.upload_file1(bytes, ext, null);
            return fileId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //初始化fastDFS环境
    private void initFdfsConfig(){
        //初始化tracker服务地址（多个tracker中间以半角逗号分隔）
        try {
            ClientGlobal.initByTrackers(tracker_servers+":"+tracker_port);
            ClientGlobal.setG_charset(charset);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
        } catch (Exception e) {
            e.printStackTrace();
            //抛出异常
            throw new ErpExcetpion(ExceptionEumn.FILE_INIT_FAILD);
        }
    }



    public void delflile(String fileid) {
        int reslutErr=0;
        if (StringUtil.isEmpty(fileid)) {
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        //初始化fastDFS的环境
        initFdfsConfig();

        try {
            //创建trackerClient
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            //得到storage服务器
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //得到storage客户端
            StorageClient1 storageClient1= new StorageClient1(trackerServer,storeStorage);

            if(storageClient1==null || storageClient1.delete_file1(fileid)!=0){
                throw new ErpExcetpion(ExceptionEumn.FILE_DELETE_FAILD);

            }
           }catch (Exception e){
            e.printStackTrace();
            throw new ErpExcetpion(ExceptionEumn.FILE_DELETE_FAILD);
        }

    }

}
