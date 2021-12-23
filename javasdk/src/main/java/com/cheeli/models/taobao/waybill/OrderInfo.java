package com.cheeli.models.taobao.waybill;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class OrderInfo {

    @JSONField(name="order_channels_type")
    private String orderChannelsType;
    @JSONField(name="trade_order_list")
    private List<String> tradeOrderList;
    public void setOrderChannelsType(String orderChannelsType) {
        this.orderChannelsType = orderChannelsType;
    }
    public String getOrderChannelsType() {
        return orderChannelsType;
    }

    public void setTradeOrderList(List<String> tradeOrderList) {
        this.tradeOrderList = tradeOrderList;
    }
    public List<String> getTradeOrderList() {
        return tradeOrderList;
    }

}
