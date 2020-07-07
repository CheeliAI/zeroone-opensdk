package com.cheeli.tradeserver.model;

public class PushOrderEntity
{
  private long num_iid;

  private String title;

  private String price;
  private long num;
  private String payment;
  private String outer_iid;
  private String outer_sku_id;

  public long getNum_iid()
  {
    return this.num_iid;
  }

  public void setNum_iid(long num_iid) {
    this.num_iid = num_iid;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPrice() {
    return this.price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public long getNum() {
    return this.num;
  }

  public void setNum(long num) {
    this.num = num;
  }

  public String getPayment() {
    return this.payment;
  }

  public void setPayment(String payment) {
    this.payment = payment;
  }

  public String getOuter_iid() {
    return this.outer_iid;
  }

  public void setOuter_iid(String outer_iid) {
    this.outer_iid = outer_iid;
  }

  public String getOuter_sku_id() {
    return this.outer_sku_id;
  }

  public void setOuter_sku_id(String outer_sku_id) {
    this.outer_sku_id = outer_sku_id;
  }
}





