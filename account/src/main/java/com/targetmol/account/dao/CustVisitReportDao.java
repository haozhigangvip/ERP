package com.targetmol.account.dao;

import com.targetmol.parent.common.mapper.BaseMapper;
import com.targetmol.domain.account.CustVisitReport;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

public interface CustVisitReportDao extends BaseMapper<CustVisitReport> {

    @SelectProvider(type = CustVisitReportDao.custVisitReportDaoProvider.class, method = "findAllByAnyPara")
    List<CustVisitReport> findAll(String softBy, Boolean desc, Integer contid, String startDate, String endDate);

    class custVisitReportDaoProvider {
        public String findAllByAnyPara(String softBy, Boolean desc, Integer contid, String startDate, String endDate) {
            String sqlstr = "select a.*,b.name as contactname,e.companyid,e.companyname,e.name as salename from cust_visit_report as a left join `user` as e on a.saleid=e.uid LEFT join contact as b on a.contid=b.contactid LEFT JOIN (select d.contactid,d.def,c.companyid,c.companyname from company_info as c  LEFT JOIN contact_company  as d on c.companyid=d.companyid) as e on a.contid=e.contactid    where e.def=1";
            if (contid != null) {
                sqlstr += " and contid=#{contid}";
            }
            if(StringUtil.isEmpty(startDate)==false || StringUtil.isEmpty(endDate)==false) {
                    sqlstr+=" and (visitdate BETWEEN '"+(startDate==null?endDate:startDate) +" 00:00:00' and '" +
                            (endDate==null?startDate:endDate)+" 23:59:59')";

            }
            if (StringUtil.isEmpty(softBy) ==false) {
                sqlstr += " order by " + softBy + (desc ? " DESC" : " ASC");
            }

            return sqlstr;
        }

    }
}