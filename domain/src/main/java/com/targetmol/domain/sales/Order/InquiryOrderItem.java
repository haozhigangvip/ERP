package com.targetmol.domain.sales.Order;

import com.targetmol.utils.NumberUtils;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 询价单明细
 */
@Table(name="inquiry_order_item")
public class InquiryOrderItem {
    @Id
    @KeySql(useGeneratedKeys= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable =false,name = "id")
    private Integer id;         //id
    private String orderid;     //订单id
    private String cass;        //cass号
    private String tsid;        //货号
    private String name;        //商品名
    private String unit;        //计量单位
    private String spec;        //商品规格
    private Double quantiy;     //数量
    private Double taxrate;     //税率
    private Double price;       //单据
    private Double amount;      //总金额无税
    private String currencyod;  //币种
    private Double discount;    //折扣金额
    private Double discountrate;//扣率
    private Integer gifit;      //是否赠品，0默认，不是，1是
    private String  note;       //备注
    @Transient
    private Double amounttax;   //总金额含税
    @Transient
    private Double tax;         //税额




    public Double getAmounttax() {
        return NumberUtils.round(amount==null?0:amount+(amount==null?0:amount*(taxrate==null?0:taxrate/100)),2);
    }


    public Double getTax() {
        return  NumberUtils.round(amount==null?0:amount*(taxrate==null?null:taxrate/100),2);
    }


    public Integer getGifit() {
        return gifit;
    }

    public void setGifit(Integer gifit) {
        this.gifit = gifit;
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

    public String getCass() {
        return cass;
    }

    public void setCass(String cass) {
        this.cass = cass;
    }

    public String getTsid() {
        return tsid;
    }

    public void setTsid(String tsid) {
        this.tsid = tsid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Double getQuantiy() {
        return quantiy;
    }

    public void setQuantiy(Double quantiy) {
        this.quantiy = quantiy;
    }

    public Double getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(Double taxrate) {
        this.taxrate = taxrate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyod() {
        return currencyod;
    }

    public void setCurrencyod(String currencyod) {
        this.currencyod = currencyod;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getDiscountrate() {
        return discountrate;
    }

    public void setDiscountrate(Double discountrate) {
        this.discountrate = discountrate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
