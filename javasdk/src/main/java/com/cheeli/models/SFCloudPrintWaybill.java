package com.cheeli.models;

import com.alibaba.fastjson.annotation.JSONField;

public class SFCloudPrintWaybill {

    private String areaNo;
    private String content;
    private String fileName;
    private String fileType;
    private String pageNo;
    private String seqNo;
    private String templateCode;
    private String waybillNo;

    public String getCustomData() {
        return customData;
    }

    public void setCustomData(String customData) {
        this.customData = customData;
    }

    @JSONField(name = "custom_data")
    private String customData;

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }
    public String getAreaNo() {
        return areaNo;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public String getFileType() {
        return fileType;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }
    public String getPageNo() {
        return pageNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }
    public String getSeqNo() {
        return seqNo;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }
    public String getTemplateCode() {
        return templateCode;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }
    public String getWaybillNo() {
        return waybillNo;
    }



}


