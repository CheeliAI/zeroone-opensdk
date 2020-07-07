package com.cheeli;

public class Config {


    //开发者的ID和密钥,demo
    public   static    String AppId  = "pt2411531762";
    public  static   String AppSecret = "211522ai29km63";
    public  static String TBSellerNick = "xxx";//    要查询的淘宝商家，注意此商家一定要授权给开发者。
    // 付款通知回调Url ，改为开发者自己网站或系统
    public  static String PayNotifyUrl ="http://dev.baolai.la:12345/paynotice";


    //正式环境
    public   static    String  baseUrl = "https://open.fw199.com";
    public   static    String   AliPayTradeListUrl= baseUrl +  "/gateway/alipay/trade/list";
    public   static    String    AliPayTradeDetailUrl=baseUrl +   "/gateway/alipay/trade/detail";
    public   static    String    GetAlipayQRCodeUrl= baseUrl +   "/gateway/partner/pay/startalipay";
    public   static    String    QueryAccountInvokeQtyUrl = baseUrl +  "/gateway/partner/account/invokeqty";
    public   static    String    TradePreCreate = baseUrl +   "/gateway/partner/pay/tradeprecreate";



}
