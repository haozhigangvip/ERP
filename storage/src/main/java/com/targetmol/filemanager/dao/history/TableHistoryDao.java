package com.targetmol.filemanager.dao.history;


import com.targetmol.filemanager.domain.file.FileSystem;
import com.targetmol.filemanager.domain.history.TableHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator.
 */
@Component
public interface TableHistoryDao extends MongoRepository<TableHistory,String> {

    @Query(value="{'$and':[{'tablename':?0},{'tableid':?1},{'creatime':{'$gte':?2}},{'creatime':{'$lt':?3}}]}")
    List<TableHistory> findAllHistory(String tbname, Integer tid, Date startdate, Date enddate);


}
