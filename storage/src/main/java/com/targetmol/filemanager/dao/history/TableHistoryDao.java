package com.targetmol.filemanager.dao.history;


import com.targetmol.filemanager.domain.file.FileSystem;
import com.targetmol.filemanager.domain.history.TableHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Created by Administrator.
 */
public interface TableHistoryDao extends MongoRepository<TableHistory,String> {

}
