package com.targetmol.domain.sales.Order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.targetmol.utils.NumberUtils;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Currency;
import java.util.Date;
import java.util.List;

/**
 * 询价单
 */

@Table(name="inquiry_order")
@JsonIgnoreProperties(value = { "handler" })
public class InquiryOrder {
    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "id")
    private Integer id;         //id
    private String orderid;     //订单id
    private Integer contactid;  //所属联系人id
    private String companyname; //公司名称
    private String companyid;   //所属公司ID
    private Integer billaddrid;  //票据寄送地址id
    private Integer shipaddrid;//收货地址id
    private Integer salesmanid; //所属销售id
    private Integer shareid;    //共享人id
    private Integer ordermanid; //商务专员id
    private Integer prepareid;  //制单人id
    private String ordersource; //订单来源
    private Date deliverydate;  //期望发货日期
    private Date estimatedate;  //预计到货日期
    private Date creatime;      //创建时间
    private String  orderstate; //订单状态
    private Double  amount;     //订单总金额
    private Double itemtotal;    //订单明细无税金额
    private Double itemtotaltax; //订单明细汉水金额
    private Double deliveryfee;  //订单运费
    private Double adjustment;   //订单调整金额
    private String currencyod;    //币种
    private String deliverymethod;//快递方式
    private String courier;     //快递公司
    private String note;        //订单备注



    @Transient
    private List<InquiryOrderItem> inquiryOrderItemList;


    public Double getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Double adjustment) {
        this.adjustment = adjustment;
    }

    public Double getItemtotal() {
        return itemtotal;
    }

    public void setItemtotal(Double itemtotal) {
        this.itemtotal = itemtotal;
    }

    public Double getItemtotaltax() {
        return itemtotaltax;
    }

    public void setItemtotaltax(Double itemtotaltax) {
        this.itemtotaltax = itemtotaltax;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Integer getContactid() {
        return contactid;
    }

    public void setContactid(Integer contactid) {
        this.contactid = contactid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public Integer getBilladdrid() {
        return billaddrid;
    }

    public void setBilladdrid(Integer billaddrid) {
        this.billaddrid = billaddrid;
    }

    public Integer getShipaddrid() {
        return shipaddrid;
    }

    public void setShipaddrid(Integer shipaddrid) {
        this.shipaddrid = shipaddrid;
    }

    public Integer getSalesmanid() {
        return salesmanid;
    }

    public void setSalesmanid(Integer salesmanid) {
        this.salesmanid = salesmanid;
    }

    public Integer getShareid() {
        return shareid;
    }

    public void setShareid(Integer shareid) {
        this.shareid = shareid;
    }

    public Integer getOrdermanid() {
        return ordermanid;
    }

    public void setOrdermanid(Integer ordermanid) {
        this.ordermanid = ordermanid;
    }

    public Integer getPrepareid() {
        return prepareid;
    }

    public void setPrepareid(Integer prepareid) {
        this.prepareid = prepareid;
    }

    public String getOrdersource() {
        return ordersource;
    }

    public void setOrdersource(String ordersource) {
        this.ordersource = ordersource;
    }

    public Date getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(Date deliverydate) {
        this.deliverydate = deliverydate;
    }

    public Date getEstimatedate() {
        return estimatedate;
    }

    public void setEstimatedate(Date estimatedate) {
        this.estimatedate = estimatedate;
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }

    public String getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(String orderstate) {
        this.orderstate = orderstate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public Double getDeliveryfee() {
        return deliveryfee;
    }

    public void setDeliveryfee(Double deliveryfee) {
        this.deliveryfee = deliveryfee;
    }

    public String getCurrencyod() {
        return currencyod;
    }

    public void setCurrencyod(String currencyod) {
        this.currencyod = currencyod;
    }

    public String getDeliverymethod() {
        return deliverymethod;
    }

    public void setDeliverymethod(String deliverymethod) {
        this.deliverymethod = deliverymethod;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<InquiryOrderItem> getInquiryOrderItemList() {
        return inquiryOrderItemList;
    }

    public void setInquiryOrderItemList(List<InquiryOrderItem> inquiryOrderItemList) {
        this.inquiryOrderItemList = inquiryOrderItemList;
    }
}
