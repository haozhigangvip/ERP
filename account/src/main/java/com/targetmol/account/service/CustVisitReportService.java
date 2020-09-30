package com.targetmol.account.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.targetmol.account.dao.ContactDao;
import com.targetmol.account.dao.CustVisitReportDao;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.vo.PageResult;
import com.targetmol.domain.account.CustVisitReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

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
        if(custVisitReportDao.insert(newReport)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
    }

    //删除拜访报告
    public void deleteCustVisitReport(Integer id) {
        if(id!=null){
            custVisitReportDao.deleteByPrimaryKey(id);
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


    public PageResult findByAll(Integer page, Integer pageSize, String softBy, Boolean desc, String key, Integer contid) {
//        //分页
//        Page pg= PageHelper.startPage(page,pageSize);
//        //过滤
//        Example example=new Example(Company.class);
//        Example.Criteria criteria1=example.createCriteria();
//        Example.Criteria criteria2=example.createCriteria();
//        if(StringUtil.isNotEmpty(key)){
//            criteria1.orLike("companyname","%"+key.trim()+"%")
//                    .orEqualTo("comid",key.toUpperCase().trim());
//            example.and(criteria1);
//        }
//
//        //排序
//        if(StringUtil.isNotEmpty(softBy)) {
//            String orderByClause=softBy+(desc ? " DESC" : " ASC");
//            example.setOrderByClause(orderByClause);
//        }
//        //进行查询
//        List<Company> list=companyDao.selectByExample(example);
//        if(list ==null ||list.size()==0){
//            throw new ErpExcetpion(ExceptionEumn.COMPANYS_ISNOT_FOUND);
//        }
//        //封装到pageHelper
//        PageInfo<Company> pageInfo=new PageInfo<>(pg.getResult());
//        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(), list);
//



        return  null;
    }


    //检查拜访报告是否正确
    private void checkCustVistReport(CustVisitReport custVisitReport){
        //判断必填项是否齐全
        if(custVisitReport==null|| StringUtil.isEmpty(custVisitReport.getContent())||custVisitReport.getSaleid()==null||custVisitReport.getVisitdate()==null){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }
    }

}
