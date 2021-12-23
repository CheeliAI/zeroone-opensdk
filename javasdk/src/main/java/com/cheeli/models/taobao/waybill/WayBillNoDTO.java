package com.cheeli.models.taobao.waybill;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;


public class WayBillNoDTO {

    @JSONField(name="cp_code")
    private String cpCode;
    @JSONField(name="dms_sorting")
    private boolean dmsSorting;
    @JSONField(name="resource_code")
    private String resourceCode;
    private Sender sender;
    @JSONField(name="store_code")
    private String storeCode;
    @JSONField(name="trade_order_info_dtos")
    private List<TradeOrderInfoDTO> tradeOrderInfoDtos;
    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }
    public String getCpCode() {
        return cpCode;
    }

    public void setDmsSorting(boolean dmsSorting) {
        this.dmsSorting = dmsSorting;
    }
    public boolean getDmsSorting() {
        return dmsSorting;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }
    public String getResourceCode() {
        return resourceCode;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }
    public Sender getSender() {
        return sender;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }
    public String getStoreCode() {
        return storeCode;
    }

    public void setTradeOrderInfoDtos(List<TradeOrderInfoDTO> tradeOrderInfoDtos) {
        this.tradeOrderInfoDtos = tradeOrderInfoDtos;
    }
    public List<TradeOrderInfoDTO> getTradeOrderInfoDtos() {
        return tradeOrderInfoDtos;
    }

}