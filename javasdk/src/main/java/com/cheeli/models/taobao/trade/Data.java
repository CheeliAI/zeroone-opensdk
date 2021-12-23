
package com.cheeli.models.taobao.trade;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Data {

    @JsonProperty("request_id")
    private String requestId;
    private Trade trade;
    private String tid;
    private int code;
    private String message;
    public void setRequestId(String requestId) {
         this.requestId = requestId;
     }
     public String getRequestId() {
         return requestId;
     }

    public void setTrade(Trade trade) {
         this.trade = trade;
     }
     public Trade getTrade() {
         return trade;
     }

    public void setTid(String tid) {
         this.tid = tid;
     }
     public String getTid() {
         return tid;
     }

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

}