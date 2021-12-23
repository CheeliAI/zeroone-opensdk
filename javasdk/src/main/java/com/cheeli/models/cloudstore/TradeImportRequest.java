package com.cheeli.models.cloudstore;

import java.util.Date;

public class TradeImportRequest {


    private int cloud_store_id;
    private int op_type;
    private String op_tid;
    private String op_seller_nick;
    private int store_id;
    private int sheet_id;
    private int goods_id;
    private int goods_count;
    private String customer_province;
    private String customer_city;
    private String customer_county;
    private String customer_detail;
    private String customer_detail_ciphertext;
    private String customer_name;
    private String customer_name_ciphertext;
    private String customer_mobile;
    private String customer_mobile_ciphertext;
    private String oaid;
    private String agent_trade_no;
    public void setCloud_store_id(int cloud_store_id) {
        this.cloud_store_id = cloud_store_id;
    }
    public int getCloud_store_id() {
        return cloud_store_id;
    }

    public void setOp_type(int op_type) {
        this.op_type = op_type;
    }
    public int getOp_type() {
        return op_type;
    }

    public void setOp_tid(String op_tid) {
        this.op_tid = op_tid;
    }
    public String getOp_tid() {
        return op_tid;
    }

    public void setOp_seller_nick(String op_seller_nick) {
        this.op_seller_nick = op_seller_nick;
    }
    public String getOp_seller_nick() {
        return op_seller_nick;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }
    public int getStore_id() {
        return store_id;
    }

    public void setSheet_id(int sheet_id) {
        this.sheet_id = sheet_id;
    }
    public int getSheet_id() {
        return sheet_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }
    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_count(int goods_count) {
        this.goods_count = goods_count;
    }
    public int getGoods_count() {
        return goods_count;
    }

    public void setCustomer_province(String customer_province) {
        this.customer_province = customer_province;
    }
    public String getCustomer_province() {
        return customer_province;
    }

    public void setCustomer_city(String customer_city) {
        this.customer_city = customer_city;
    }
    public String getCustomer_city() {
        return customer_city;
    }

    public void setCustomer_county(String customer_county) {
        this.customer_county = customer_county;
    }
    public String getCustomer_county() {
        return customer_county;
    }

    public void setCustomer_detail(String customer_detail) {
        this.customer_detail = customer_detail;
    }
    public String getCustomer_detail() {
        return customer_detail;
    }

    public void setCustomer_detail_ciphertext(String customer_detail_ciphertext) {
        this.customer_detail_ciphertext = customer_detail_ciphertext;
    }
    public String getCustomer_detail_ciphertext() {
        return customer_detail_ciphertext;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name_ciphertext(String customer_name_ciphertext) {
        this.customer_name_ciphertext = customer_name_ciphertext;
    }
    public String getCustomer_name_ciphertext() {
        return customer_name_ciphertext;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }
    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile_ciphertext(String customer_mobile_ciphertext) {
        this.customer_mobile_ciphertext = customer_mobile_ciphertext;
    }
    public String getCustomer_mobile_ciphertext() {
        return customer_mobile_ciphertext;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }
    public String getOaid() {
        return oaid;
    }

    public void setAgent_trade_no(String agent_trade_no) {
        this.agent_trade_no = agent_trade_no;
    }
    public String getAgent_trade_no() {
        return agent_trade_no;
    }


}
