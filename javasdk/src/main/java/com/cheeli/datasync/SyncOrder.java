package com.cheeli.datasync;

import com.alibaba.fastjson.annotation.JSONField;

public class SyncOrder {

    @JSONField(name = "num_iid")
    private int numIid;

    private String title;

    private String price;

    private int num;

    private String payment;
    @JSONField(name = "outer_iid")
    private String outerIid;

    public int getNumIid() {
        return numIid;
    }

    public void setNumIid(int numIid) {
        this.numIid = numIid;
    }

    public String getOuterIid() {
        return outerIid;
    }

    public void setOuterIid(String outerIid) {
        this.outerIid = outerIid;
    }

    public String getOuterSkuId() {
        return outerSkuId;
    }

    public void setOuterSkuId(String outerSkuId) {
        this.outerSkuId = outerSkuId;
    }

    @JSONField(name = "outer_sku_id")
    private String outerSkuId;


    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public String getPrice(){
        return this.price;
    }
    public void setNum(int num){
        this.num = num;
    }
    public int getNum(){
        return this.num;
    }
    public void setPayment(String payment){
        this.payment = payment;
    }
    public String getPayment(){
        return this.payment;
    }




}
