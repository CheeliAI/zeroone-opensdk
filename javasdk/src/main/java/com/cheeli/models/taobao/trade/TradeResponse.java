
package com.cheeli.models.taobao.trade;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class  TradeResponse {

    private int code;
    private String message;
    private List<Data> data;
    @JsonProperty("trace_id")
    private String traceId;
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

    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }

    public void setTraceId(String traceId) {
         this.traceId = traceId;
     }
     public String getTraceId() {
         return traceId;
     }

}