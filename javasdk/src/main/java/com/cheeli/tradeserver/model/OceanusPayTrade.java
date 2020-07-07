package com.cheeli.tradeserver.model;

import com.alibaba.fastjson.annotation.JSONField;

public class OceanusPayTrade {


    public String getAlipayTradeNo() {
        return alipayTradeNo;
    }

    public void setAlipayTradeNo(String alipayTradeNo) {
        this.alipayTradeNo = alipayTradeNo;
    }

    @JSONField(name="alipay_order_no")
     private String    alipayTradeNo  ;


    @JSONField(name="create_time")
    private String     createTime     ;
    @JSONField(name="in_out_type")
    private String     inOutType  ;



    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getInOutType() {
        return inOutType;
    }

    public void setInOutType(String inOutType) {
        this.inOutType = inOutType;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getOppositeLogonId() {
        return oppositeLogonId;
    }

    public void setOppositeLogonId(String oppositeLogonId) {
        this.oppositeLogonId = oppositeLogonId;
    }

    public String getOppositeUserID() {
        return oppositeUserID;
    }

    public void setOppositeUserID(String oppositeUserID) {
        this.oppositeUserID = oppositeUserID;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getMerchantExtTradeNo() {
        return merchantExtTradeNo;
    }

    public void setMerchantExtTradeNo(String merchantExtTradeNo) {
        this.merchantExtTradeNo = merchantExtTradeNo;
    }

    @JSONField(name="modified_time")
    private String      modifiedTime  ;
    @JSONField(name="opposite_user_id")
    private String      oppositeLogonId  ;
    @JSONField(name="alipay_order_no")
    private String      oppositeUserID  ;
    @JSONField(name="order_from")
    private String      orderFrom  ;
    @JSONField(name="order_status")
    private String      orderStatus  ;
    @JSONField(name="order_title")
    private String     orderTitle  ;
    @JSONField(name="order_type")
    private String     orderType  ;
    @JSONField(name="owner_name")
    private String     ownerName  ;
    @JSONField(name="owner_user_id")
    private String     ownerUserId  ;
    @JSONField(name="service_charge")
    private String     serviceCharge  ;
    @JSONField(name="total_amount")
    private String     totalAmount  ;
    @JSONField(name="merchant_ext_trade_no")
    private String     merchantExtTradeNo  ;



}
