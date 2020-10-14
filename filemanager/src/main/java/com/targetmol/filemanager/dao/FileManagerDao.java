package com.targetmol.filemanager.dao;

import com.targetmol.filemanager.domain.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Administrator.
 */
public interface FileManagerDao extends MongoRepository<FileSystem,String> {
}
