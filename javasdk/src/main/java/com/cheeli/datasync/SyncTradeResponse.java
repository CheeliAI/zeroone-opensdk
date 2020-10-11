package com.cheeli.datasync;

public class SyncTradeResponse {

    private String uuid;

    private int code;

    private String msg;

    private String topic;

    private String data;

    public void setUuid(String uuid){
        this.uuid = uuid;
    }
    public String getUuid(){
        return this.uuid;
    }
    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setTopic(String topic){
        this.topic = topic;
    }
    public String getTopic(){
        return this.topic;
    }
    public void setData(String data){
        this.data = data;
    }
    public String getData(){
        return this.data;
    }


}
