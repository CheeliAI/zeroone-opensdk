/**
  * Copyright 2021 jb51.net 
  */
package com.cheeli.models.taobao.waybill;


public class Address {

    private String city;
    private String detail;
    private String district;
    private String province;
    private String town;
    public void setCity(String city) {
        this.city = city;
    }
    public String getCity() {
        return city;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getDetail() {
        return detail;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
    public String getDistrict() {
        return district;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    public String getProvince() {
        return province;
    }

    public void setTown(String town) {
        this.town = town;
    }
    public String getTown() {
        return town;
    }
}