package com.targetmol.filemanager.service.history;


import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.filemanager.dao.history.TableHistoryDao;

import com.targetmol.filemanager.domain.history.TableHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class TableHistoryService {

    @Autowired
    TableHistoryDao tableHistoryDao;


    public void addHistory(TableHistory history) {
        tableHistoryDao.insert(history);
    }

    public List<TableHistory> findHistory(String tbname, Integer tid, String startdate, String enddate) {
        if(StringUtil.isEmpty(tbname)||tid==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        //字符转换日期
        Date sdate=null,edate=null;
        SimpleDateFormat  sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            //获取开始日期
            sdate=startdate!=null?sdf.parse(startdate):new Date(0);
            //结束日期+1天
            edate=enddate!=null?new Date(sdf.parse(enddate).getTime()+24*3600*1000):new Date();
        }catch (Exception e){
            return null;
        }

        //起始日期和结束日期设置默认值
        sdate=sdate==null?new Date(0):sdate;
        edate=edate==null?new Date():edate;

        //查询
        List<TableHistory> lst=tableHistoryDao.findAllHistory(tbname,tid,sdate,edate);
        return lst;
    }
}
