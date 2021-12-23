/**
  * Copyright 2021 jb51.net 
  */
package com.cheeli.models.taobao.waybill;

public class Sender {

    private Address address;
    private String mobile;
    private String name;
    private String phone;
    public void setAddress(Address address) {
         this.address = address;
     }
     public Address getAddress() {
         return address;
     }

    public void setMobile(String mobile) {
         this.mobile = mobile;
     }
     public String getMobile() {
         return mobile;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setPhone(String phone) {
         this.phone = phone;
     }
     public String getPhone() {
         return phone;
     }

}