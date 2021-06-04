package com.cheeli;

public class Config {


    //开发者的ID和密钥，ok, 百鞋馆
    public   static    String AppId  = "xxx";
    public  static   String AppSecret = "xxx"; //  //修改
    public  static String TBSellerNick="xxx";//     淘宝卖家
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
    public   static    String     PddOrderV2DetailUrl = baseUrl +   "/gateway/pdd/order/v2/detail";
    public   static    String     PddOrderListUrl = baseUrl +   "/gateway/pdd/order/list";
    public   static    String     PddOrderV2ListUrl = baseUrl +   "/gateway/pdd/order/v2/list";
    public   static    String     TaoBaoOrderListUrl = baseUrl +   "/gateway/taobao/order/list";
    public   static    String     TaoBaoOrderDetailUrl = baseUrl +   "/gateway/taobao/order/detail";
    public   static    String     TaoBaoCaiNiaoCloudPrintStdtemplatesUrl = baseUrl +   "/gateway/taobao/cainiao/cloudprint/getstdtemplates";
    public   static    String     TaoBaoCaiNiaoWayBillSearchUrl = baseUrl +   "/gateway/taobao/cainiao/waybill/search";
    public   static    String     TaoBaoCaiNiaoWayBillCancelUrl = baseUrl +   "/gateway/taobao/cainiao/waybill/cancel";
    public   static    String     TaoBaoCaiNiaoWayBillBatchGetUrl = baseUrl +   "/gateway/taobao/cainiao/waybill/batchget";
    public   static    String     TaoBaoCaiNiaoWayBillV2BatchGetUrl = baseUrl +   "/gateway/taobao/cainiao/waybill/v2/batchget";
    public   static    String     TaoBaoCaiNiaoWayBillGetUrl = baseUrl +   "/gateway/taobao/cainiao/waybill/get";
    public   static    String     TradePreCreate4WeiXinJSAPI = baseUrl +   "/gateway/partner/pay/tradeprecreate4wxjsapi";
    public   static    String     TaoBaoLogisticesDummySend = baseUrl +   "/gateway/taobao/logistices/dummysend";
    public   static    String     TaoBaoQianNiuSendMsg = baseUrl +   "/gateway/taobao/qianniu/sendmsg";
    public   static    String     TaoBaoStoreGrantUrl = baseUrl +   "/gateway/partner/store/grant";
    public   static    String     TaoBaoUpdateMemoUrl = baseUrl +   "/gateway/taobao/order/updatememo";
    public   static    String     TaoBaoItemOnSaleUrl = baseUrl +   "/gateway/taobao/item/getonsale";
    public   static    String     TaoBaoItemDetailUrl = baseUrl +   "/gateway/taobao/item/detail";
    public   static    String     TaoBaoItemUpdateListingUrl = baseUrl +   "/gateway/taobao/item/updatelisting";
    public   static    String     TaoBaoItemUpdateDeListingUrl = baseUrl +   "/gateway/taobao/item/updatedelisting";
    public   static    String     TaoBaoItemUpdateStockUrl = baseUrl +   "/gateway/taobao/item/updatestock";
    public   static    String     TaoBaoTBKTpWdConvertUrl = baseUrl +   "/gateway/taobao/tbk/tpwdconvert";
    public   static    String     TaoBaoTBKTpWdCreateUrl = baseUrl +   "/gateway/taobao/tbk/tpwdcreate";
    public   static    String     LogisticesOfflineBatchSendUrl = baseUrl +   "/gateway/taobao/logistices/offlinebatchsend";
    public   static    String     TaoBaoItemAddUrl = baseUrl +   "/gateway/taobao/item/add";
    public   static    String     TaoBaoItemDeleteUrl = baseUrl +   "/gateway/taobao/item/delete";
    public   static    String     TaoBaoItemCatGet = baseUrl +   "/gateway/taobao/itemcats/get";
    public   static    String     TaoBaoItemPropsGet = baseUrl +   "/gateway/taobao/itemprops/get";
    public   static    String     TaoBaoItemPropsValuesGet = baseUrl +   "/gateway/taobao/itempropvalues/get";
    public   static    String     TaoBaoProductMatchSchemaGetUrl = baseUrl +   "/gateway/taobao/product/match/schema/get";
    public   static    String     TaoBaoTmallItemSchemaAddUrl = baseUrl +   "/gateway/taobao/tmall/item/schema/add";
    public   static    String     TaoBaoTmallProductSchemaMatchUrl = baseUrl +   "/gateway/taobao/product/schema/match";
    public   static    String     TaoBaoTmallItemAddSchemaGetUrl = baseUrl +   "/gateway/taobao/tmall/item/add/schema/get";
    public   static    String     TaoBaoTmallItemCatsAuthorizeGetUrl = baseUrl +   "/gateway/taobao/itemcats/authorize/get";

    public   static    String     PddCloudPrintStdTplGetUrl = baseUrl +   "/gateway/pdd/cloudprint/stdtemplates/get";
    public   static    String     PddWayBillSearchUrl = baseUrl +   "/gateway/pdd/waybill/search";
    public   static    String     PddWayBillCancelUrl = baseUrl +   "/gateway/pdd/waybill/cancel";
    public   static    String     PddWayBillGetUrl = baseUrl +   "/gateway/pdd/waybill/get";
    public   static    String     PddWayBillV2GetUrl = baseUrl +   "/gateway/pdd/waybill/v2/get";
    public   static    String     TaobaoCaiNiaoReachableBatchjudgeUrl  = baseUrl +   "/gateway/taobao/cainiao/reachable/batchjudge";
    public   static    String     SendSMSUrl  = baseUrl +   "/gateway/sms/send";
    public   static    String     SendSMSV2Url  = baseUrl +   "/gateway/sms/v2/send";
    public   static    String     TaoBaoTradeUpdatePushExpress  = baseUrl +   "/gateway/taobao/order/update/pushexpress";
    public   static    String     TaoBaoItemUploadImage = baseUrl +   "/gateway/taobao/item/upload/image";
    public   static    String     TaoBaoItemAddSku = baseUrl +   "/gateway/taobao/item/add/sku";
    public   static    String     TaoBaoPictureCategoryAdd = baseUrl +   "/gateway/taobao/picture/category/add";
    public   static    String     TaoBaoPictureUpload = baseUrl +   "/gateway/taobao/picture/upload";
    public   static    String     TaoBaoItemPropImgUploadUrl = baseUrl +   "/gateway/taobao/item/propimg/upload";
    public   static    String     TaoBaoRefundListUrl = baseUrl +   "/gateway/taobao/refund/list";
    public   static    String     TaoBaoRefundDetailUrl = baseUrl +   "/gateway/taobao/refund/detail";
    public   static    String     TaoBaoRefunReceiveUrl = baseUrl +   "/gateway/taobao/refund/receive";
    public   static    String     TaoBaoTradeUpdatePaidPushExpressUrl = baseUrl +   "/gateway/taobao/order/update/paidpushexpress";
    public   static    String     TaoBaoTradeIncrementListUrl = baseUrl +   "/gateway/taobao/order/increment/list";
    public   static    String     TaoBaoLogisticesTraceSearchUrl = baseUrl +   "/gateway/taobao/logistices/tracesearch";
    public   static    String     PddOrderDecryptUrl = baseUrl +   "/gateway/pdd/order/decrypt";
    public   static    String     TaoBaoRefundAgreeUrl = baseUrl +   "/gateway/taobao/refund/agree";
    public   static    String     TaoBaoRefundRefuseUrl = baseUrl +   "/gateway/taobao/refund/refuse";
    public   static    String     PddOrderNoteUpdateUrl = baseUrl +   "/gateway/pdd/order/noteupdate";
    public   static    String     PartnerSellerSubscribeUrl = baseUrl +   "/gateway/partner/seller/subscribe";
    public   static    String     TaoBaoOrderDetailBatchUrl = baseUrl +   "/gateway/taobao/order/detail/batch";
    public   static    String     PddOrderListIncrementUrl = baseUrl +   "/gateway/pdd/order/list/increment";




}
