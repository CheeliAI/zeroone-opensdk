package com.cheeli.tradeserver.model;

import com.alibaba.fastjson.annotation.JSONField;

public class OceanusNotice {



    @JSONField(name="action")
    private String action;

    @JSONField(name="message")
    private String message;

    @JSONField(name="code")
    private int  code;

    @JSONField(name="sign")
    private String sign;



    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @JSONField(name="data")
    private String data;


    @JSONField(name="timestamp")
    private String timestamp;


}
