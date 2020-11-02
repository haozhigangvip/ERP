package com.targetmol.filemanager.service.history;


import com.targetmol.filemanager.dao.history.TableHistoryDao;

import com.targetmol.filemanager.domain.history.TableHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class TableHistoryService {

    @Autowired
    TableHistoryDao tableHistoryDao;


    public void addHistory(TableHistory history) {
        tableHistoryDao.insert(history);
    }
}
