package com.targetmol.filemanager.service.history;


import com.targetmol.filemanager.dao.history.TableHistoryDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class TableHistoryService {

    @Autowired
    TableHistoryDao tableHistoryDao;


}
