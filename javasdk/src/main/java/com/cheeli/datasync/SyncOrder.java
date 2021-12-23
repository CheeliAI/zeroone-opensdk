package com.cheeli.datasync;

import com.alibaba.fastjson.annotation.JSONField;

public class SyncOrder {

    @JSONField(name = "num_iid")
    private long numIid;

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    @JSONField(name = "oid")
    private long oid;



    private String title;

    private String price;

    private int num;

    private String payment;
    @JSONField(name = "outer_iid")
    private String outerIid;

    public long getNumIid() {
        return numIid;
    }

    public void setNumIid(long numIid) {
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


    @JSONField(name = "divide_order_fee")
    private String divideOrderFee;

    public String getDivideOrderFee() {
        return divideOrderFee;
    }

    public void setDivideOrderFee(String divideOrderFee) {
        this.divideOrderFee = divideOrderFee;
    }

    public String getPartMjzDiscount() {
        return partMjzDiscount;
    }

    public void setPartMjzDiscount(String partMjzDiscount) {
        this.partMjzDiscount = partMjzDiscount;
    }

    @JSONField(name = "part_mjz_discount")
    private String partMjzDiscount;





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
