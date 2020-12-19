package com.cheeli.tradeserver.model;


import java.util.Date;


public class Order  {

    private String adjust_fee;
    private String discount_fee;
    private int num;
    private long num_iid;
    private String outer_iid;
    private String outer_sku_id;
    private String payment;
    private String pic_path;
    private String price;
    private String refund_status;
    private String sku_properties_name;
    private String title;
    public void setAdjust_fee(String adjust_fee) {
        this.adjust_fee = adjust_fee;
    }
    public String getAdjust_fee() {
        return adjust_fee;
    }

    public void setDiscount_fee(String discount_fee) {
        this.discount_fee = discount_fee;
    }
    public String getDiscount_fee() {
        return discount_fee;
    }

    public void setNum(int num) {
        this.num = num;
    }
    public int getNum() {
        return num;
    }

    public void setNum_iid(long num_iid) {
        this.num_iid = num_iid;
    }
    public long getNum_iid() {
        return num_iid;
    }

    public void setOuter_iid(String outer_iid) {
        this.outer_iid = outer_iid;
    }
    public String getOuter_iid() {
        return outer_iid;
    }

    public void setOuter_sku_id(String outer_sku_id) {
        this.outer_sku_id = outer_sku_id;
    }
    public String getOuter_sku_id() {
        return outer_sku_id;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
    public String getPayment() {
        return payment;
    }

    public void setPic_path(String pic_path) {
        this.pic_path = pic_path;
    }
    public String getPic_path() {
        return pic_path;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getPrice() {
        return price;
    }

    public void setRefund_status(String refund_status) {
        this.refund_status = refund_status;
    }
    public String getRefund_status() {
        return refund_status;
    }

    public void setSku_properties_name(String sku_properties_name) {
        this.sku_properties_name = sku_properties_name;
    }
    public String getSku_properties_name() {
        return sku_properties_name;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

}