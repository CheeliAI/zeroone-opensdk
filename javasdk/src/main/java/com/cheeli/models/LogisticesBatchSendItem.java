package com.cheeli.models;

import com.alibaba.fastjson.annotation.JSONField;

public class LogisticesBatchSendItem {

    @JSONField(name="tid")
    private String  tid ;

    @JSONField(name="company_code")
    private String companyCode ;
    @JSONField(name="out_sid")
    private String outSid   ;

    @JSONField(name="flag")
    private String flag;

    @JSONField(name="memo")
    private String memo;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getOutSid() {
        return outSid;
    }

    public void setOutSid(String outSid) {
        this.outSid = outSid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }




}
