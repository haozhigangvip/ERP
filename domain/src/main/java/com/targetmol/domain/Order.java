package com.targetmol.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name="[order]")
@JsonIgnoreProperties(value = {"handler"})

public class Order implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer autoid;
    private String orderid;
    private String  invoinfoid;
    private String invoicetype;
    private  String saddressid;
    private String scontactid;
    private String  sphone;
    private String semail;
    private String baddressid;
    private String bcontactid;
    private String bphone;
    private String bemail;
    private String salesman;
    private String orderman;
    private String companyid;
    private Date orderdate;
    private String note;
    private Date creatime;
    private String paypolicy;
    private String orderstatus;
    private String deliverymethod;
    private Double deliveryfee;
    private Double taxrate;
    private Date invoicedate;
    private Date shipdate;
    private Double discount;
    private Date paymentdate;
    private Double listprice;
    private String zoho_no;
    private String zoho_id;

    private Company company;


    public Integer getAutoid() {
        return autoid;
    }

    public void setAutoid(Integer autoid) {
        this.autoid = autoid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getInvoinfoid() {
        return invoinfoid;
    }

    public void setInvoinfoid(String invoinfoid) {
        this.invoinfoid = invoinfoid;
    }

    public String getInvoicetype() {
        return invoicetype;
    }

    public void setInvoicetype(String invoicetype) {
        this.invoicetype = invoicetype;
    }

    public String getSaddressid() {
        return saddressid;
    }

    public void setSaddressid(String saddressid) {
        this.saddressid = saddressid;
    }

    public String getScontactid() {
        return scontactid;
    }

    public void setScontactid(String scontactid) {
        this.scontactid = scontactid;
    }

    public String getSphone() {
        return sphone;
    }

    public void setSphone(String sphone) {
        this.sphone = sphone;
    }

    public String getSemail() {
        return semail;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    public String getBaddressid() {
        return baddressid;
    }

    public void setBaddressid(String baddressid) {
        this.baddressid = baddressid;
    }

    public String getBcontactid() {
        return bcontactid;
    }

    public void setBcontactid(String bcontactid) {
        this.bcontactid = bcontactid;
    }

    public String getBphone() {
        return bphone;
    }

    public void setBphone(String bphone) {
        this.bphone = bphone;
    }

    public String getBemail() {
        return bemail;
    }

    public void setBemail(String bemail) {
        this.bemail = bemail;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getOrderman() {
        return orderman;
    }

    public void setOrderman(String orderman) {
        this.orderman = orderman;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }

    public String getPaypolicy() {
        return paypolicy;
    }

    public void setPaypolicy(String paypolicy) {
        this.paypolicy = paypolicy;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getDeliverymethod() {
        return deliverymethod;
    }

    public void setDeliverymethod(String deliverymethod) {
        this.deliverymethod = deliverymethod;
    }

    public Double getDeliveryfee() {
        return deliveryfee;
    }

    public void setDeliveryfee(Double deliveryfee) {
        this.deliveryfee = deliveryfee;
    }

    public Double getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(Double taxrate) {
        this.taxrate = taxrate;
    }

    public Date getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(Date invoicedate) {
        this.invoicedate = invoicedate;
    }

    public Date getShipdate() {
        return shipdate;
    }

    public void setShipdate(Date shipdate) {
        this.shipdate = shipdate;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Date getPaymentdate() {
        return paymentdate;
    }

    public void setPaymentdate(Date paymentdate) {
        this.paymentdate = paymentdate;
    }

    public Double getListprice() {
        return listprice;
    }

    public void setListprice(Double listprice) {
        this.listprice = listprice;
    }

    public String getZoho_no() {
        return zoho_no;
    }

    public void setZoho_no(String zoho_no) {
        this.zoho_no = zoho_no;
    }

    public String getZoho_id() {
        return zoho_id;
    }

    public void setZoho_id(String zoho_id) {
        this.zoho_id = zoho_id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
