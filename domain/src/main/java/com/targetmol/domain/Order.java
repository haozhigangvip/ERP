package com.targetmol.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Data
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
}
