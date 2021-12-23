
package com.cheeli.models.taobao.waybill;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrintData {

    private Data data;
    private String signature;
    @JsonProperty("templateURL")
    private String templateurl;
    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

    public void setSignature(String signature) {
         this.signature = signature;
     }
     public String getSignature() {
         return signature;
     }

    public void setTemplateurl(String templateurl) {
         this.templateurl = templateurl;
     }
     public String getTemplateurl() {
         return templateurl;
     }

}