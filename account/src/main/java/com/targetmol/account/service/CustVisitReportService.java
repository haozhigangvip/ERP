package com.targetmol.account.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.targetmol.account.dao.ContactDao;
import com.targetmol.account.dao.CustVisitReportDao;
import com.targetmol.parent.common.emums.ExceptionEumn;
import com.targetmol.parent.common.exception.ErpExcetpion;
import com.targetmol.parent.common.vo.PageResult;
import com.targetmol.domain.account.CustVisitReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class CustVisitReportService {
    @Autowired
    private CustVisitReportDao custVisitReportDao;
    @Autowired
    private ContactDao contactDao;

    //添加拜访报告
    public void addCustVisitReport(CustVisitReport custVisitReport) {
        //检查参数
        checkCustVistReport(custVisitReport);
        //保存
        if(custVisitReportDao.insert(custVisitReport)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }

    }

    //修改拜访报告
    public void editCustVisitReport(CustVisitReport custVisitReport) {
        //检查参数
        checkCustVistReport(custVisitReport);
        //查询该报告是否存在
        CustVisitReport newReport=findCustVisitReportById(custVisitReport.getId());
        if(newReport==null){
            throw  new ErpExcetpion(ExceptionEumn.CUST_VISIT_REPORT_ID_IS_NOT_FOUND);
        }
        newReport.setContent(custVisitReport.getContent());
        newReport.setContid(custVisitReport.getContid());
        newReport.setVisitdate(custVisitReport.getVisitdate());
        //保存
        if(custVisitReportDao.updateByPrimaryKeySelective(newReport)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
    }

    //删除拜访报告
    public void deleteCustVisitReport(Integer id) {
        if(id!=null){
            if(custVisitReportDao.deleteByPrimaryKey(id)<1){
                throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
            }
        }else{
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
    }



    //根据拜访报告ID查询拜访报告
    public CustVisitReport findCustVisitReportById(Integer id) {
        if(id!=null){
            CustVisitReport custVisitReport= custVisitReportDao.selectByPrimaryKey(id);
            if(custVisitReport!=null){
                //根据contid查询联系人名字
                Map<String,String> mp=contactDao.getConNameCompanyName(custVisitReport.getContid());
                if(mp!=null){
                    custVisitReport.setCompanyName(mp.get("companyname"));
                    custVisitReport.setContactName(mp.get("contactname"));
                }
            }
            return custVisitReport;
        }
        return null;
    }


    public PageResult findByAll(Integer page, Integer pageSize, String softBy, Boolean desc, Integer contid,String startDate,String endDate) {
        //分页
        Page pg= PageHelper.startPage(page,pageSize);

        //进行查询
        List<CustVisitReport> list=custVisitReportDao.findAll(softBy,desc,contid,startDate,endDate);

        //封装到pageHelper
        PageInfo<CustVisitReport> pageInfo=new PageInfo<>(pg.getResult());
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(), list);
    }


    //检查拜访报告是否正确
    private void checkCustVistReport(CustVisitReport custVisitReport){
        //判断必填项是否齐全
        if(custVisitReport==null|| StringUtil.isEmpty(custVisitReport.getContent())||custVisitReport.getSaleid()==null||custVisitReport.getVisitdate()==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
        if(contactDao.selectByPrimaryKey(custVisitReport.getContid())==null){
            throw new ErpExcetpion(ExceptionEumn.CONTACT_ISNOT_FOUND);
        }
    }

}
