package com.cheeli.models.taobao.trade;

import com.alibaba.fastjson.annotation.JSONField;

public class Order {

    private int cid;
    private int num;
    private String oid;
    private String price;

    @JSONField(name = "title")
    private String title;


    @JSONField(name = "sku_id")
    private String skuId;
    private String status;
    @JSONField(name = "num_iid")
    private long numIid;
    @JSONField(name = "oid_str")
    private String oidStr;
    private String payment;
    @JSONField(name = "pic_path")
    private String picPath;
    @JSONField(name = "outer_iid")
    private String outerIid;
    @JSONField(name = "total_fee")
    private String totalFee;
    @JSONField(name = "adjust_fee")
    private String adjustFee;
    @JSONField(name = "buyer_rate")
    private boolean buyerRate;
    @JSONField(name = "is_daixiao")
    private boolean isDaixiao;
    @JSONField(name = "order_from")
    private String orderFrom;
    @JSONField(name = "is_oversold")
    private boolean isOversold;
    @JSONField(name = "seller_rate")
    private boolean sellerRate;
    @JSONField(name = "seller_type")
    private String sellerType;
    @JSONField(name = "discount_fee")
    private String discountFee;
    @JSONField(name = "nr_outer_iid")
    private String nrOuterIid;
    @JSONField(name = "outer_sku_id")
    private String outerSkuId;
    @JSONField(name = "snapshot_url")
    private String snapshotUrl;
    @JSONField(name = "refund_status")
    private String refundStatus;
    @JSONField(name = "divide_order_fee")
    private String divideOrderFee;
    @JSONField(name = "sku_properties_name")
    private String skuPropertiesName;

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getCid() {
        return cid;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOid() {
        return oid;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public long getNumIid() {
        return numIid;
    }

    public void setNumIid(long numIid) {
        this.numIid = numIid;
    }

    public void setOidStr(String oidStr) {
        this.oidStr = oidStr;
    }

    public String getOidStr() {
        return oidStr;
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

    public void setOuterIid(String outerIid) {
        this.outerIid = outerIid;
    }

    public String getOuterIid() {
        return outerIid;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setAdjustFee(String adjustFee) {
        this.adjustFee = adjustFee;
    }

    public String getAdjustFee() {
        return adjustFee;
    }

    public void setBuyerRate(boolean buyerRate) {
        this.buyerRate = buyerRate;
    }

    public boolean getBuyerRate() {
        return buyerRate;
    }

    public void setIsDaixiao(boolean isDaixiao) {
        this.isDaixiao = isDaixiao;
    }

    public boolean getIsDaixiao() {
        return isDaixiao;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setIsOversold(boolean isOversold) {
        this.isOversold = isOversold;
    }

    public boolean getIsOversold() {
        return isOversold;
    }

    public void setSellerRate(boolean sellerRate) {
        this.sellerRate = sellerRate;
    }

    public boolean getSellerRate() {
        return sellerRate;
    }

    public void setSellerType(String sellerType) {
        this.sellerType = sellerType;
    }

    public String getSellerType() {
        return sellerType;
    }

    public void setDiscountFee(String discountFee) {
        this.discountFee = discountFee;
    }

    public String getDiscountFee() {
        return discountFee;
    }

    public void setNrOuterIid(String nrOuterIid) {
        this.nrOuterIid = nrOuterIid;
    }

    public String getNrOuterIid() {
        return nrOuterIid;
    }

    public void setOuterSkuId(String outerSkuId) {
        this.outerSkuId = outerSkuId;
    }

    public String getOuterSkuId() {
        return outerSkuId;
    }

    public void setSnapshotUrl(String snapshotUrl) {
        this.snapshotUrl = snapshotUrl;
    }

    public String getSnapshotUrl() {
        return snapshotUrl;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setDivideOrderFee(String divideOrderFee) {
        this.divideOrderFee = divideOrderFee;
    }

    public String getDivideOrderFee() {
        return divideOrderFee;
    }

    public void setSkuPropertiesName(String skuPropertiesName) {
        this.skuPropertiesName = skuPropertiesName;
    }

    public String getSkuPropertiesName() {
        return skuPropertiesName;
    }

}
