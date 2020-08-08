package com.cheeli;

public class Config {



    //开发者的ID和密钥
    public   static    String AppId  = "aaa";
    public  static   String AppSecret = "bbb"; //  //修改
    public  static String TBSellerNick="百鞋馆";//     淘宝卖家
    public  static String PddSellerNick="pdd92853259xx";//    拼多卖家

    // 付款通知回调Url
    public  static String PayNotifyUrl ="http://192.168.0.202:12345/paynotice";

    //正式环境
    public   static    String     baseUrl = "https://open.fw199.com";
    public   static    String     AliPayTradeListUrl= baseUrl +  "/gateway/alipay/trade/list";
    public   static    String     AliPayTradeDetailUrl=baseUrl +   "/gateway/alipay/trade/detail";
    public   static    String     GetAlipayQRCodeUrl= baseUrl +   "/gateway/partner/pay/startalipay";
    public   static    String     QueryAccountInvokeQtyUrl = baseUrl +  "/gateway/partner/account/invokeqty";
    public   static    String     TradePreCreate = baseUrl +   "/gateway/partner/pay/tradeprecreate";
    public   static    String     LogisticesCompanyUrl = baseUrl +   "/gateway/taobao/logistices/getcompany";
    public   static    String     LogisticesOnlineSendUrl = baseUrl +   "/gateway/taobao/logistices/onlinesend";
    public   static    String     PddLogisticesCompanyUrl = baseUrl +   "/gateway/pdd/logistices/getcompany";
    public   static    String     PddLogisticesOnlineSendUrl = baseUrl +   "/gateway/pdd/logistices/onlinesend";
    public   static    String     PddOrderDetailUrl = baseUrl +   "/gateway/pdd/order/detail";
    public   static    String     PddOrderListUrl = baseUrl +   "/gateway/pdd/order/list";
}
