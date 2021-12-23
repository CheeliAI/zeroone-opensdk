
package com.cheeli.models.taobao.trade;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Trade {

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    @JSONField(name = "receiver_phone")
    private String receiverPhone;

    @JsonProperty("adjust_fee")
    private String adjustFee;
    @JsonProperty("alipay_no")
    private String alipayNo;
    @JsonProperty("buyer_area")
    private String buyerArea;
    @JsonProperty("buyer_nick")
    private String buyerNick;
    @JsonProperty("buyer_obtain_point_fee")
    private int buyerObtainPointFee;
    @JsonProperty("buyer_open_uid")
    private String buyerOpenUid;
    @JsonProperty("buyer_rate")
    private boolean buyerRate;
    @JsonProperty("buyer_message")
    private String buyerMessage;
    @JsonProperty("cod_fee")
    private String codFee;
    @JsonProperty("cod_status")
    private String codStatus;
    private String created;
    @JsonProperty("discount_fee")
    private String discountFee;
    @JsonProperty("paid_coupon_fee")
    private String paidCouponFee;
    @JsonProperty("order_tax_fee")
    private String orderTaxFee;
    @JsonProperty("credit_card_fee")
    private String creditCardFee;
    @JsonProperty("coupon_fee")
    private int couponFee;
    @JsonProperty("is_brand_sale")
    private boolean isBrandSale;
    @JsonProperty("is_force_wlb")
    private boolean isForceWlb;
    @JsonProperty("is_lgtype")
    private boolean isLgtype;
    private String modified;
    @JsonProperty("new_presell")
    private boolean newPresell;
    private int num;
    @JsonProperty("num_iid")
    private long numIid;

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Orders getOrders() {
        return orders;
    }

    @JsonProperty("orders")
    private Orders orders;


    @JsonProperty("pay_time")
    private String payTime;
    private String payment;
    @JsonProperty("pic_path")
    private String picPath;
    @JsonProperty("point_fee")
    private int pointFee;
    @JsonProperty("post_fee")
    private String postFee;
    private String price;
    @JsonProperty("real_point_fee")
    private int realPointFee;
    @JsonProperty("received_payment")
    private String receivedPayment;
    @JsonProperty("receiver_address")
    private String receiverAddress;
    @JsonProperty("receiver_city")
    private String receiverCity;
    @JsonProperty("receiver_district")
    private String receiverDistrict;
    @JsonProperty("receiver_mobile")
    private String receiverMobile;
    @JsonProperty("receiver_name")
    private String receiverName;
    @JsonProperty("receiver_state")
    private String receiverState;
    @JsonProperty("receiver_zip")
    private String receiverZip;
    @JsonProperty("seller_flag")
    private int sellerFlag;
    @JsonProperty("seller_memo")
    private String sellerMemo;
    @JsonProperty("seller_nick")
    private String sellerNick;
    @JsonProperty("seller_rate")
    private boolean sellerRate;
    @JsonProperty("shipping_type")
    private String shippingType;
    private String status;
    private String tid;
    @JsonProperty("tid_str")
    private String tidStr;
    private String title;
    @JsonProperty("total_fee")
    private String totalFee;
    private String type;
    @JsonProperty("you_xiang")
    private boolean youXiang;
    @JsonProperty("trade_from")
    private String tradeFrom;
    @JsonProperty("app_name")
    private String appName;

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    @JsonProperty("oaid")
    private String oaid;


    public void setAdjustFee(String adjustFee) {
         this.adjustFee = adjustFee;
     }
     public String getAdjustFee() {
         return adjustFee;
     }

    public void setAlipayNo(String alipayNo) {
         this.alipayNo = alipayNo;
     }
     public String getAlipayNo() {
         return alipayNo;
     }

    public void setBuyerArea(String buyerArea) {
         this.buyerArea = buyerArea;
     }
     public String getBuyerArea() {
         return buyerArea;
     }

    public void setBuyerNick(String buyerNick) {
         this.buyerNick = buyerNick;
     }
     public String getBuyerNick() {
         return buyerNick;
     }

    public void setBuyerObtainPointFee(int buyerObtainPointFee) {
         this.buyerObtainPointFee = buyerObtainPointFee;
     }
     public int getBuyerObtainPointFee() {
         return buyerObtainPointFee;
     }

    public void setBuyerOpenUid(String buyerOpenUid) {
         this.buyerOpenUid = buyerOpenUid;
     }
     public String getBuyerOpenUid() {
         return buyerOpenUid;
     }

    public void setBuyerRate(boolean buyerRate) {
         this.buyerRate = buyerRate;
     }
     public boolean getBuyerRate() {
         return buyerRate;
     }

    public void setBuyerMessage(String buyerMessage) {
         this.buyerMessage = buyerMessage;
     }
     public String getBuyerMessage() {
         return buyerMessage;
     }

    public void setCodFee(String codFee) {
         this.codFee = codFee;
     }
     public String getCodFee() {
         return codFee;
     }

    public void setCodStatus(String codStatus) {
         this.codStatus = codStatus;
     }
     public String getCodStatus() {
         return codStatus;
     }

    public void setCreated(String created) {
         this.created = created;
     }
     public String getCreated() {
         return created;
     }

    public void setDiscountFee(String discountFee) {
         this.discountFee = discountFee;
     }
     public String getDiscountFee() {
         return discountFee;
     }

    public void setPaidCouponFee(String paidCouponFee) {
         this.paidCouponFee = paidCouponFee;
     }
     public String getPaidCouponFee() {
         return paidCouponFee;
     }

    public void setOrderTaxFee(String orderTaxFee) {
         this.orderTaxFee = orderTaxFee;
     }
     public String getOrderTaxFee() {
         return orderTaxFee;
     }

    public void setCreditCardFee(String creditCardFee) {
         this.creditCardFee = creditCardFee;
     }
     public String getCreditCardFee() {
         return creditCardFee;
     }

    public void setCouponFee(int couponFee) {
         this.couponFee = couponFee;
     }
     public int getCouponFee() {
         return couponFee;
     }

    public void setIsBrandSale(boolean isBrandSale) {
         this.isBrandSale = isBrandSale;
     }
     public boolean getIsBrandSale() {
         return isBrandSale;
     }

    public void setIsForceWlb(boolean isForceWlb) {
         this.isForceWlb = isForceWlb;
     }
     public boolean getIsForceWlb() {
         return isForceWlb;
     }

    public void setIsLgtype(boolean isLgtype) {
         this.isLgtype = isLgtype;
     }
     public boolean getIsLgtype() {
         return isLgtype;
     }

    public void setModified(String modified) {
         this.modified = modified;
     }
     public String getModified() {
         return modified;
     }

    public void setNewPresell(boolean newPresell) {
         this.newPresell = newPresell;
     }
     public boolean getNewPresell() {
         return newPresell;
     }

    public void setNum(int num) {
         this.num = num;
     }
     public int getNum() {
         return num;
     }

    public void setNumIid(long numIid) {
         this.numIid = numIid;
     }
     public long getNumIid() {
         return numIid;
     }



    public void setPayTime(String payTime) {
         this.payTime = payTime;
     }
     public String getPayTime() {
         return payTime;
     }

    public void setPayment(String payment) {
         this.payment = payment;
     }
     public String getPayment() {
         return payment;
     }

    public void setPicPath(String picPath) {
         this.picPath = picPath;
     }
     public String getPicPath() {
         return picPath;
     }

    public void setPointFee(int pointFee) {
         this.pointFee = pointFee;
     }
     public int getPointFee() {
         return pointFee;
     }

    public void setPostFee(String postFee) {
         this.postFee = postFee;
     }
     public String getPostFee() {
         return postFee;
     }

    public void setPrice(String price) {
         this.price = price;
     }
     public String getPrice() {
         return price;
     }

    public void setRealPointFee(int realPointFee) {
         this.realPointFee = realPointFee;
     }
     public int getRealPointFee() {
         return realPointFee;
     }

    public void setReceivedPayment(String receivedPayment) {
         this.receivedPayment = receivedPayment;
     }
     public String getReceivedPayment() {
         return receivedPayment;
     }

    public void setReceiverAddress(String receiverAddress) {
         this.receiverAddress = receiverAddress;
     }
     public String getReceiverAddress() {
         return receiverAddress;
     }

    public void setReceiverCity(String receiverCity) {
         this.receiverCity = receiverCity;
     }
     public String getReceiverCity() {
         return receiverCity;
     }

    public void setReceiverDistrict(String receiverDistrict) {
         this.receiverDistrict = receiverDistrict;
     }
     public String getReceiverDistrict() {
         return receiverDistrict;
     }

    public void setReceiverMobile(String receiverMobile) {
         this.receiverMobile = receiverMobile;
     }
     public String getReceiverMobile() {
         return receiverMobile;
     }

    public void setReceiverName(String receiverName) {
         this.receiverName = receiverName;
     }
     public String getReceiverName() {
         return receiverName;
     }

    public void setReceiverState(String receiverState) {
         this.receiverState = receiverState;
     }
     public String getReceiverState() {
         return receiverState;
     }

    public void setReceiverZip(String receiverZip) {
         this.receiverZip = receiverZip;
     }
     public String getReceiverZip() {
         return receiverZip;
     }

    public void setSellerFlag(int sellerFlag) {
         this.sellerFlag = sellerFlag;
     }
     public int getSellerFlag() {
         return sellerFlag;
     }

    public void setSellerMemo(String sellerMemo) {
         this.sellerMemo = sellerMemo;
     }
     public String getSellerMemo() {
         return sellerMemo;
     }

    public void setSellerNick(String sellerNick) {
         this.sellerNick = sellerNick;
     }
     public String getSellerNick() {
         return sellerNick;
     }

    public void setSellerRate(boolean sellerRate) {
         this.sellerRate = sellerRate;
     }
     public boolean getSellerRate() {
         return sellerRate;
     }

    public void setShippingType(String shippingType) {
         this.shippingType = shippingType;
     }
     public String getShippingType() {
         return shippingType;
     }

    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

    public void setTid(String tid) {
         this.tid = tid;
     }
     public String getTid() {
         return tid;
     }

    public void setTidStr(String tidStr) {
         this.tidStr = tidStr;
     }
     public String getTidStr() {
         return tidStr;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setTotalFee(String totalFee) {
         this.totalFee = totalFee;
     }
     public String getTotalFee() {
         return totalFee;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setYouXiang(boolean youXiang) {
         this.youXiang = youXiang;
     }
     public boolean getYouXiang() {
         return youXiang;
     }

    public void setTradeFrom(String tradeFrom) {
         this.tradeFrom = tradeFrom;
     }
     public String getTradeFrom() {
         return tradeFrom;
     }

    public void setAppName(String appName) {
         this.appName = appName;
     }
     public String getAppName() {
         return appName;
     }

}