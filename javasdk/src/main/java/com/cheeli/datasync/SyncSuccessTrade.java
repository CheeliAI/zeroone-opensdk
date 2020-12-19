package com.cheeli.datasync;

import com.alibaba.fastjson.annotation.JSONField;

public class SyncSuccessTrade {

    @JSONField(name = "buyer_nick")
    private String buyerNick;

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public String getSellerNick() {
        return sellerNick;
    }

    public void setSellerNick(String sellerNick) {
        this.sellerNick = sellerNick;
    }

    @JSONField(name = "payment")
    private String payment;

    @JSONField(name = "oid")
    private long oid;

    @JSONField(name = "tid")
    private long tid;

    @JSONField(name = "seller_nick")
    private String sellerNick;





}
