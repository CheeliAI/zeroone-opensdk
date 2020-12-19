package com.cheeli.tradeserver.model;

import com.alibaba.fastjson.annotation.JSONField;

public class ChangeMemoWithTrade {


    @JSONField(name="trade_info")
    private TradeInfo  tradeInfo;

    @JSONField(name="trade_memo")
    private String   tradeMemo;

    public TradeInfo getTradeInfo() {
        return tradeInfo;
    }

    public void setTradeInfo(TradeInfo tradeInfo) {
        this.tradeInfo = tradeInfo;
    }

    public String getTradeMemo() {
        return tradeMemo;
    }

    public void setTradeMemo(String tradeMemo) {
        this.tradeMemo = tradeMemo;
    }

    

}
