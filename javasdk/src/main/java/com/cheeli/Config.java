package com.cheeli;

public class Config {


    //开发者的ID和密钥,demo
    public   static    String AppId  = "pt2411531762";
    public  static   String AppSecret = "211522ai29km63";
    public  static String TBSellerNick = "xxx";//    订购的淘宝卖家账号
    public  static String PddSellerNick="pdd75712771556";//    拼多卖家

    // 付款通知回调Url ，改为开发者自己网站或系统
    public  static String PayNotifyUrl ="http://dev.baolai.la:12345/paynotice";


    //正式环境
    public   static    String     baseUrl = "https://open.fw199.com";
    public   static    String     AliPayTradeListUrl= baseUrl +  "/gateway/alipay/trade/list";
    public   static    String     AliPayTradeDetailUrl=baseUrl +   "/gateway/alipay/trade/detail";
    public   static    String     GetAlipayQRCodeUrl= baseUrl +   "/gateway/partner/pay/startalipay";
    public   static    String     QueryAccountInvokeQtyUrl = baseUrl +  "/gateway/partner/account/invokeqty";
    public   static    String     TradePreCreate = baseUrl +   "/gateway/partner/pay/tradeprecreate";
    public   static    String     LogisticesCompanyUrl = baseUrl +   "/gateway/taobao/logistices/getcompany";
    public   static    String     LogisticesOnlineSendUrl = baseUrl +   "/gateway/taobao/logistices/onlinesend";
    public   static    String     LogisticesOfflineSendUrl = baseUrl +   "/gateway/taobao/logistices/offlinesend";
    public   static    String     PddLogisticesCompanyUrl = baseUrl +   "/gateway/pdd/logistices/getcompany";
    public   static    String     PddLogisticesOnlineSendUrl = baseUrl +   "/gateway/pdd/logistices/onlinesend";
    public   static    String     PddOrderDetailUrl = baseUrl +   "/gateway/pdd/order/detail";
    public   static    String     PddOrderListUrl = baseUrl +   "/gateway/pdd/order/list";
    public   static    String     TaoBaoOrderListUrl = baseUrl +   "/gateway/taobao/order/list";
    public   static    String     TaoBaoOrderDetailUrl = baseUrl +   "/gateway/taobao/order/detail";
    public   static    String     TaoBaoCaiNiaoCloudPrintStdtemplatesUrl = baseUrl +   "/gateway/taobao/cainiao/cloudprint/getstdtemplates";
    public   static    String     TaoBaoCaiNiaoWayBillSearchUrl = baseUrl +   "/gateway/taobao/cainiao/waybill/search";
    public   static    String     TaoBaoCaiNiaoWayBillBatchGetUrl = baseUrl +   "/gateway/taobao/cainiao/waybill/batchget";
    public   static    String     TradePreCreate4WeiXinJSAPI = baseUrl +   "/gateway/partner/pay/tradeprecreate4wxjsapi";
    public   static    String     TaoBaoLogisticesDummySend = baseUrl +   "/gateway/taobao/logistices/dummysend";
    public   static    String     TaoBaoQianNiuSendMsg = baseUrl +   "/gateway/taobao/qianniu/sendmsg";
    public   static    String     TaoBaoStoreGrantUrl = baseUrl +   "/gateway/partner/store/grant";
    public   static    String     TaoBaoUpdateMemoUrl = baseUrl +   "/gateway/taobao/order/updatememo";

}
