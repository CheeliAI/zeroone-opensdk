package com.cheeli.datasync;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.core.annotation.Order;

import java.util.List;

public class SyncTrade {

    @JSONField(name = "trade_source")
    private String tradeSource;

    public String getTradeSource() {
        return tradeSource;
    }

    public void setTradeSource(String tradeSource) {
        this.tradeSource = tradeSource;
    }

    public String getSellerNick() {
        return sellerNick;
    }

    public void setSellerNick(String sellerNick) {
        this.sellerNick = sellerNick;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getReceiverState() {
        return receiverState;
    }

    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverTown() {
        return receiverTown;
    }

    public void setReceiverTown(String receiverTown) {
        this.receiverTown = receiverTown;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getAlipayNo() {
        return alipayNo;
    }

    public void setAlipayNo(String alipayNo) {
        this.alipayNo = alipayNo;
    }

    @JSONField(name = "seller_nick")
    private String sellerNick;
    @JSONField(name = "tid")
    private long tid;
    @JSONField(name = "buyer_nick")
    private String buyerNick;
    @JSONField(name = "buyer_message")
    private String buyerMessage;
    @JSONField(name = "buyer_email")
    private String buyerEmail;
    @JSONField(name = "receiver_state")
    private String receiverState;
    @JSONField(name = "receiver_city")
    private String receiverCity;
    @JSONField(name = "receiver_town")
    private String receiverTown;
    @JSONField(name = "receiver_address")
    private String receiverAddress;
    @JSONField(name = "receiver_name")
    private String receiverName;
    @JSONField(name = "receiver_mobile")
    private String receiverMobile;
    @JSONField(name = "alipay_no")
    private String alipayNo;

    private List<Order> orders;


    public void setOrders(List<Order> orders){
        this.orders = orders;
    }
    public List<Order> getOrders(){
        return this.orders;
    }


}
