package com.cheeli.tradeserver.model;

import java.util.List;

public class PushTradeEntity
{
  public long tb_seller_id;
  public String tid;
  public String token;
  public String buyer_nick;
  public String buyer_message;
  public String buyer_email;

  public String alipay_no;
  public String buyer_area;
  public String buyer_ip;
  public String buyer_alipay_no;


  public String receiver_mobile;
  public String receiver_name;
  public String receiver_country;
  public String receiver_state;
  public String receiver_city;
  public String receiver_district;
  public String receiver_town;
  public String receiver_zip;


  public List<PushOrderEntity> orders;
}
