package com.cheeli.models.pdd;

public class TradeResponse {

    private int code;
    private String message;
    private Data data;
    private String trace_id;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public void setTrace_id(String trace_id) {
        this.trace_id = trace_id;
    }
    public String getTrace_id() {
        return trace_id;
    }



}
