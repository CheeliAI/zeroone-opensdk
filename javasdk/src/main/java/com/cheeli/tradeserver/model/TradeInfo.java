package com.cheeli.tradeserver.model;

/**
 * Copyright 2020 bejson.com
 */

import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2020-12-05 23:45:17
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class TradeInfo {

    private String alipay_no;
    private String buyer_email;
    private String buyer_message;
    private String buyer_nick;
    private Date created;
    private String discount_fee;
    private List<Order> orders;
    private Date pay_time;
    private String payment;
    private String pic_path;
    private String post_fee;
    private String price;
    private String receiver_address;
    private String receiver_city;
    private String receiver_district;
    private String receiver_mobile;
    private String receiver_name;
    private String receiver_state;
    private String receiver_town;
    private String receiver_zip;
    private int seller_flag;
    private String seller_memo;
    private String seller_nick;
    private String status;
    private long tid;
    private String total_fee;
    private String trade_source;
    public void setAlipay_no(String alipay_no) {
        this.alipay_no = alipay_no;
    }
    public String getAlipay_no() {
        return alipay_no;
    }

    public void setBuyer_email(String buyer_email) {
        this.buyer_email = buyer_email;
    }
    public String getBuyer_email() {
        return buyer_email;
    }

    public void setBuyer_message(String buyer_message) {
        this.buyer_message = buyer_message;
    }
    public String getBuyer_message() {
        return buyer_message;
    }

    public void setBuyer_nick(String buyer_nick) {
        this.buyer_nick = buyer_nick;
    }
    public String getBuyer_nick() {
        return buyer_nick;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    public Date getCreated() {
        return created;
    }

    public void setDiscount_fee(String discount_fee) {
        this.discount_fee = discount_fee;
    }
    public String getDiscount_fee() {
        return discount_fee;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    public List<Order> getOrders() {
        return orders;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }
    public Date getPay_time() {
        return pay_time;
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

    public void setPost_fee(String post_fee) {
        this.post_fee = post_fee;
    }
    public String getPost_fee() {
        return post_fee;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getPrice() {
        return price;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }
    public String getReceiver_address() {
        return receiver_address;
    }

    public void setReceiver_city(String receiver_city) {
        this.receiver_city = receiver_city;
    }
    public String getReceiver_city() {
        return receiver_city;
    }

    public void setReceiver_district(String receiver_district) {
        this.receiver_district = receiver_district;
    }
    public String getReceiver_district() {
        return receiver_district;
    }

    public void setReceiver_mobile(String receiver_mobile) {
        this.receiver_mobile = receiver_mobile;
    }
    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }
    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_state(String receiver_state) {
        this.receiver_state = receiver_state;
    }
    public String getReceiver_state() {
        return receiver_state;
    }

    public void setReceiver_town(String receiver_town) {
        this.receiver_town = receiver_town;
    }
    public String getReceiver_town() {
        return receiver_town;
    }

    public void setReceiver_zip(String receiver_zip) {
        this.receiver_zip = receiver_zip;
    }
    public String getReceiver_zip() {
        return receiver_zip;
    }

    public void setSeller_flag(int seller_flag) {
        this.seller_flag = seller_flag;
    }
    public int getSeller_flag() {
        return seller_flag;
    }

    public void setSeller_memo(String seller_memo) {
        this.seller_memo = seller_memo;
    }
    public String getSeller_memo() {
        return seller_memo;
    }

    public void setSeller_nick(String seller_nick) {
        this.seller_nick = seller_nick;
    }
    public String getSeller_nick() {
        return seller_nick;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }
    public long getTid() {
        return tid;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }
    public String getTotal_fee() {
        return total_fee;
    }

    public void setTrade_source(String trade_source) {
        this.trade_source = trade_source;
    }
    public String getTrade_source() {
        return trade_source;
    }

}