package com.targetmol.filemanager.service.history;


import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.filemanager.dao.history.TableHistoryDao;

import com.targetmol.filemanager.domain.history.TableHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.Date;
import java.util.List;


@Service
public class TableHistoryService {

    @Autowired
    TableHistoryDao tableHistoryDao;


    public void addHistory(TableHistory history) {
        tableHistoryDao.insert(history);
    }

    public List<TableHistory> findHistory(String tbname, Integer tid, Date startdate, Date enddate) {
        if(StringUtil.isEmpty(tbname)||tid==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        List<TableHistory> lst=tableHistoryDao.findAllHistory(tbname,tid,startdate,enddate);
        return lst;
    }
}
