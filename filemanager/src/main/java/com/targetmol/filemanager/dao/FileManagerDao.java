package com.targetmol.filemanager.dao;


import com.targetmol.filemanager.domain.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Created by Administrator.
 */
public interface FileManagerDao extends MongoRepository<FileSystem,String> {
    @Query(value="{'urlId':?0}")
     FileSystem findByUrlid(String urlid);
}
