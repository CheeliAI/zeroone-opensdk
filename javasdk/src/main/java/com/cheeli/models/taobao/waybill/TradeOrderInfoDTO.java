package com.cheeli.models.taobao.waybill;

import com.alibaba.fastjson.annotation.JSONField;

public class TradeOrderInfoDTO {

    @JSONField(name="object_id")
    private String objectId;
    @JSONField(name="order_info")
    private OrderInfo orderInfo;
    @JSONField(name="package_info")
    private PackageInfo packageInfo;
    private Recipient recipient;
    @JSONField(name="template_url")
    private String templateUrl;
    @JSONField(name="user_id")
    private int userId;
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public String getObjectId() {
        return objectId;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }
    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }
    public Recipient getRecipient() {
        return recipient;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }
    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

}
