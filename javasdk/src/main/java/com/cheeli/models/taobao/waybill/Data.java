/**
  * Copyright 2021 jb51.net 
  */
package com.cheeli.models.taobao.waybill;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Data {

    @JsonProperty("object_id")
    private String objectId;
    @JsonProperty("print_data")
    private PrintData printData;
    @JsonProperty("waybill_code")
    private String waybillCode;
    @JsonProperty("parent_waybill_code")
    private String parentWaybillCode;
    private int code;
    private String message;
    public void setObjectId(String objectId) {
         this.objectId = objectId;
     }
     public String getObjectId() {
         return objectId;
     }

    public void setPrintData(PrintData printData) {
         this.printData = printData;
     }
     public PrintData getPrintData() {
         return printData;
     }

    public void setWaybillCode(String waybillCode) {
         this.waybillCode = waybillCode;
     }
     public String getWaybillCode() {
         return waybillCode;
     }

    public void setParentWaybillCode(String parentWaybillCode) {
         this.parentWaybillCode = parentWaybillCode;
     }
     public String getParentWaybillCode() {
         return parentWaybillCode;
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