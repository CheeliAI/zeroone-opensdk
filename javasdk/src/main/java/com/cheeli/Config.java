
package com.cheeli;

public class Config {


    //开发者的ID和密钥
    public   static    String AppId  = "xxx";
    public   static   String AppSecret = "xxx";

    public  static String TBSellerNick=   "xxx" ;
    public  static String DDSellerNick=   "oms12804644";
    public  static String PddSellerNick= "pdd14544212593";
    public  static String SFClientCode = "xxx"; // 顺丰的客户编码
    public  static String SFCheckWord = "xxx"; // 顺丰的校验码

    // 付款通知回调Url ，改为开发者自己网站或系统
    public  static String PayNotifyUrl ="http://fcpush.vs.fw199.com/paynotice";

    //正式环境
    public   static    String     baseUrl = "https://open.fw199.com";
//    public   static    String     baseUrl = "http://127.0.0.1:8080";

    public   static    String     qmUrl = "http://u8d0yp7743.api.taobao.com/router/qmtest";
    public   static    String     AliPayTradeListUrl= baseUrl +  "/gateway/alipay/trade/list";
    public   static    String     AliPayTradeDetailUrl=baseUrl +   "/gateway/alipay/trade/detail";
    public   static    String     GetAlipayQRCodeUrl= baseUrl +   "/gateway/partner/pay/startalipay";
    public   static    String     QueryAccountInvokeQtyUrl = baseUrl +  "/gateway/partner/account/invokeqty";
    public   static    String     TradePreCreate = baseUrl +   "/gateway/partner/pay/tradeprecreate";
    public   static    String     QueryPayTradeUrl = baseUrl +   "/gateway/partner/pay/trade/query";
    public   static    String     PayWXPersonalCreateNativePayUrl = baseUrl +   "/gateway/partner/pay/wx/personal/native";
    public   static    String     F2FQRPayTradeCreateUrl = baseUrl +   "/gateway/partner/pay/f2f/qrpay/trade/create";
    public   static    String     LogisticesCompanyUrl = baseUrl +   "/gateway/taobao/logistices/getcompany";
    public   static    String     LogisticesOnlineSendUrl = baseUrl +   "/gateway/taobao/logistices/onlinesend";
    public   static    String     LogisticesOfflineSendUrl = baseUrl +   "/gateway/taobao/logistices/offlinesend";
    public   static    String     LogisticesV2OfflineSendUrl = baseUrl +   "/gateway/taobao/logistices/v2/offlinebatchsend";

    public   static    String     PddLogisticesCompanyUrl = baseUrl +   "/gateway/pdd/logistices/getcompany";
    public   static    String     PddLogisticesOnlineSendUrl = baseUrl +   "/gateway/pdd/logistices/onlinesend";
    public   static    String     PddOrderDetailUrl = baseUrl +   "/gateway/pdd/order/detail";
    public   static    String     PddGoodsOuterCatMappingGetRequestUrl = baseUrl +   "/gateway/pdd/goods/outer/cat/mapping/get";
    public   static    String     PddGoodsOuterCatMappingBatchGetRequestUrl = baseUrl +   "/gateway/pdd/goods/outer/cat/mapping/batchget";

    public   static    String     PddWayBillSearchByNoUrl = baseUrl +   "/gateway/pdd/waybill/searchbyno";

    public   static    String     PddOrderV2DetailUrl = baseUrl +   "/gateway/pdd/order/v2/detail";
    public   static    String     PddOrderListUrl = baseUrl +   "/gateway/pdd/order/list";
    public   static    String     PddOrderV2ListUrl = baseUrl +   "/gateway/pdd/order/v2/list";
    public   static    String     TaoBaoOrderListUrl = baseUrl +   "/gateway/taobao/order/list";
    public   static    String     TaoBaoOrderExtListUrl = baseUrl +   "/gateway/taobao/order/ext/list";
    public   static    String     TaoBaoOrderDetailUrl = baseUrl +   "/gateway/taobao/order/detail";
    public   static    String     TaoBaoCaiNiaoCloudPrintStdtemplatesUrl = baseUrl +   "/gateway/taobao/cainiao/cloudprint/getstdtemplates";
    public   static    String     TaoBaoCaiNiaoCloudPrintMytemplatesUrl = baseUrl +   "/gateway/taobao/cainiao/cloudprint/getmytemplates";
    public   static    String     TaoBaoCaiNiaoWayBillSearchUrl = baseUrl +   "/gateway/taobao/cainiao/waybill/search";
    public   static    String     AlibabaItemPublishSchemaGetUrl = baseUrl +   "/gateway/alibaba/item/publish/schema/get";
    public   static    String     AlibabaItemPublishPropsGetUrl = baseUrl +   "/gateway/alibaba/item/publish/props/get";
    public   static    String     AlibabaItemPublishSubmitUrl = baseUrl +   "/gateway/alibaba/item/publish/submit";
    public   static    String     AlibabaItemEditSchemaGetUrl = baseUrl +   "/gateway/alibaba/item/edit/schema/get";
    public   static    String     AlibabaItemEditSubmitUrl = baseUrl +   "/gateway/alibaba/item/edit/submit";
    public   static    String     AlibabaItemEditFastUpdateUrl = baseUrl +   "/gateway/alibaba/item/edit/fast/update";
    public   static    String     AlibabaItemOperateUpshelfUrl = baseUrl +   "/gateway/alibaba/item/operate/upshelf";
    public   static    String     AlibabaItemOperateDownshelfUrl = baseUrl +   "/gateway/alibaba/item/operate/downshelf";
    public   static    String     AlibabaItemOperateDeleteUrl = baseUrl +   "/gateway/alibaba/item/operate/delete";
    public   static    String     TmallProductMatchSchemaGetRequestUrl = baseUrl +   "/gateway/taobao/tmall/product/match/schema/get";
    public   static    String     TmallProductSchemaMatchRequestUrl = baseUrl +   "/gateway/taobao/tmall/product/schema/match";
    public   static    String     TmallProductSchemaGetRequestUrl = baseUrl +   "/gateway/taobao/tmall/product/schema/get";
    public   static    String     TmallProductAddSchemaGetRequestUrl = baseUrl +   "/gateway/taobao/tmall/product/add/schema/get";
    public   static    String     TmallProductSchemaAddRequestUrl = baseUrl +   "/gateway/taobao/tmall/product/schema/add";

    public   static    String     TaoBaoCaiNiaoWayBillCancelUrl = baseUrl +   "/gateway/taobao/cainiao/waybill/cancel";
    public   static    String     TaoBaoCaiNiaoWayBillUpateUrl = baseUrl +   "/gateway/taobao/cainiao/waybill/update";
    public   static    String     TaoBaoCaiNiaoWayBillBatchGetUrl = baseUrl +   "/gateway/taobao/cainiao/waybill/batchget";
    public   static    String     TaoBaoCaiNiaoWayBillV2BatchGetUrl = baseUrl +   "/gateway/taobao/cainiao/waybill/v2/batchget";
    public   static    String     TaoBaoCaiNiaoWayBillV3BatchGetUrl = baseUrl +   "/gateway/taobao/cainiao/waybill/v3/batchget";
    public   static    String     TaoBaoCaiNiaoWayBillGetUrl = baseUrl +   "/gateway/taobao/cainiao/waybill/get";
    public   static    String     TaoBaoCaiNiaoCloudprintCustomeraresGetUrl = baseUrl +   "/gateway/taobao/cainiao/cloudprint/customares/get";
    public   static    String     TaoBaoCaiNiaoCloudISVTplGetUrl = baseUrl +   "/gateway/taobao/cainiao/cloudprint/isvtemplates/get";
    public   static    String     TaoBaoCaiNiaoWayBillGetByWBCodeUrl = baseUrl +   "/gateway/taobao/cainiao/waybill/querybywbcode";
    public   static    String     TradePreCreate4WeiXinJSAPI = baseUrl +   "/gateway/partner/pay/tradeprecreate4wxjsapi";
    public   static    String     TaoBaoLogisticesDummySend = baseUrl +   "/gateway/taobao/logistices/dummysend";
    public   static    String     TaoBaoQianNiuSendMsg = baseUrl +   "/gateway/taobao/qianniu/sendmsg";
    public   static    String     TaoBaoStoreGrantUrl = baseUrl +   "/gateway/partner/store/grant";
    public   static    String     PartnerBillDownUrl = baseUrl +   "/gateway/partner/bill/down";
    public   static    String     TaoBaoUpdateMemoUrl = baseUrl +   "/gateway/taobao/order/updatememo";
    public   static    String     TaoBaoItemOnSaleUrl = baseUrl +   "/gateway/taobao/item/getonsale";
    public   static    String     TaoBaoItemDetailUrl = baseUrl +   "/gateway/taobao/item/detail";
    public   static    String     TaoBaoItemPromotionGetUrl = baseUrl +   "/gateway/taobao/item/promotion/get";
    public   static    String     TaoBaoItemUpdateListingUrl = baseUrl +   "/gateway/taobao/item/updatelisting";
    public   static    String     TaoBaoItemUpdateDeListingUrl = baseUrl +   "/gateway/taobao/item/updatedelisting";
    public   static    String     TaoBaoItemUpdateStockUrl = baseUrl +   "/gateway/taobao/item/updatestock";
    public   static    String     TaoBaoItemImageAnyone= baseUrl +   "/gateway/taobao/item/image/anyone";
    public   static    String     TaoBaoTBKTpWdConvertUrl = baseUrl +   "/gateway/taobao/tbk/tpwdconvert";
    public   static    String     TaoBaoTBKTpWdCreateUrl = baseUrl +   "/gateway/taobao/tbk/tpwdcreate";
    public   static    String     TaoBaoTBKIsTBKOrderUrl = baseUrl +   "/gateway/taobao/tbk/istbkorder";
    public   static    String     LogisticesOfflineBatchSendUrl = baseUrl +   "/gateway/taobao/logistices/offlinebatchsend";
    public   static    String     TaoBaoItemAddUrl = baseUrl +   "/gateway/taobao/item/add";
    public   static    String     TaoBaoItemDeleteUrl = baseUrl +   "/gateway/taobao/item/delete";
    public   static    String     TaoBaoItemCatGet = baseUrl +   "/gateway/taobao/itemcats/get";
    public   static    String     TaoBaoItemPropsGet = baseUrl +   "/gateway/taobao/itemprops/get";
    public   static    String     TaoBaoItemPropsValuesGet = baseUrl +   "/gateway/taobao/itempropvalues/get";

    //    public   static    String     TaoBaoTmallItemSchemaAddUrl = baseUrl +   "/gateway/taobao/tmall/item/schema/add";
//    public   static    String     TaoBaoTmallProductSchemaMatchUrl = baseUrl +   "/gateway/taobao/product/schema/match";
//    public   static    String     TaoBaoTmallItemAddSchemaGetUrl = baseUrl +   "/gateway/taobao/tmall/item/add/schema/get";
    public   static    String     TaoBaoTmallItemCatsAuthorizeGetUrl = baseUrl +   "/gateway/taobao/itemcats/authorize/get";
    public   static    String     TaoBaoTopOAIDDecryptUrl = baseUrl +   "/gateway/taobao/top/oaid/decrypt";

    public   static    String     PddCloudPrintStdTplGetUrl = baseUrl +   "/gateway/pdd/cloudprint/stdtemplates/get";
    public   static    String     PddWayBillSearchUrl = baseUrl +   "/gateway/pdd/waybill/search";
    public   static    String     PddWayBillCancelUrl = baseUrl +   "/gateway/pdd/waybill/cancel";
    public   static    String     PddWayBillGetUrl = baseUrl +   "/gateway/pdd/waybill/get";
    public   static    String     PddWayBillUpdateUrl = baseUrl +   "/gateway/pdd/waybill/update";
    public   static    String     PddWayBillV2GetUrl = baseUrl +   "/gateway/pdd/waybill/v2/get";
    public   static    String     TaobaoCaiNiaoReachableBatchjudgeUrl  = baseUrl +   "/gateway/taobao/cainiao/reachable/batchjudge";
    public   static    String     SendSMSUrl  = baseUrl +   "/gateway/sms/send";
    public   static    String     SendSMSV2Url  = baseUrl +   "/gateway/sms/v2/send";
    public   static    String     AddrResolveUrl  = baseUrl +   "/gateway/addr/resolve";
    public   static    String     TaoBaoTradeUpdatePushExpress  = baseUrl +   "/gateway/taobao/order/update/pushexpress";
    public   static    String     TaoBaoItemUploadImage = baseUrl +   "/gateway/taobao/item/upload/image";
    public   static    String     TaoBaoItemDeleteImage = baseUrl +   "/gateway/taobao/item/delete/image";
    public   static    String     TaoBaoItemAddSku = baseUrl +   "/gateway/taobao/item/add/sku";
    public   static    String     TaoBaoPictureCategoryAdd = baseUrl +   "/gateway/taobao/picture/category/add";
    public   static    String     TaoBaoPictureUpload = baseUrl +   "/gateway/taobao/picture/upload";
    public   static    String     TaoBaoItemPropImgUploadUrl = baseUrl +   "/gateway/taobao/item/propimg/upload";
    public   static    String     TaoBaoRefundListUrl = baseUrl +   "/gateway/taobao/refund/list";
    public   static    String     TaoBaoRefundDetailUrl = baseUrl +   "/gateway/taobao/refund/detail";
    public   static    String     TaoBaoRefunReceiveUrl = baseUrl +   "/gateway/taobao/refund/receive";
    public   static    String     TaoBaoRefunMessageAddUrl = baseUrl +   "/gateway/taobao/refund/message/add";
    public   static    String     TaoBaoRefunMessageGetUrl = baseUrl +   "/gateway/taobao/refund/message/get";
    public   static    String     TaoBaoTradeRatesGetUrl = baseUrl +   "/gateway/taobao/traderates/get";
    public   static    String     TaoBaorefundreturnRefillUrl = baseUrl +   "/gateway/taobao/refund/returngoods/refill";
    public   static    String     TaoBaorefundreturnRefuseUrl = baseUrl +   "/gateway/taobao/refund/returngoods/refuse";
    public   static    String     TaoBaorefundreturnAgreeUrl = baseUrl +   "/gateway/taobao/refund/returngoods/agree";
    public   static    String     TaoBaoTradeUpdatePaidPushExpressUrl = baseUrl +   "/gateway/taobao/order/update/paidpushexpress";
    public   static    String     TaoBaoTradeIncrementListUrl = baseUrl +   "/gateway/taobao/order/increment/list";
    public   static    String     TaoBaoLogisticesTraceSearchUrl = baseUrl +   "/gateway/taobao/logistices/tracesearch";
    public   static    String     TaoBaoLogisticesAddrSearchUrl = baseUrl +   "/gateway/taobao/logistices/addr/search";
    public   static    String     TaoBaoLogisticesAddrAddUrl = baseUrl +   "/gateway/taobao/logistices/addr/add";
    public   static    String     TaoBaoLogisticesAddModifyUrl = baseUrl +   "/gateway/taobao/logistices/addr/modify";
    public   static    String     TaoBaoLogisticesAddrRemoveUrl = baseUrl +   "/gateway/taobao/logistices/addr/remove";
    public   static    String     TaoBaoLogisticesDeliveryTplListUrl = baseUrl +   "/gateway/taobao/logistices/delivery/template/get";
    public   static    String     TaoBaoOrderShippingAddressUpdateUrl = baseUrl +   "/gateway/taobao/order/shippingaddress/update";
    public   static    String     TaoBaologisticesConsignResendUrl = baseUrl +   "/gateway/taobao/logistices/consign/resend";
    public   static    String     WlbOrderJzConsignRequestUrl = baseUrl +   "/gateway/taobao/logistices/wlb/order/jz/consign";
    public   static    String     AlibabaEinvoiceApplyGetUrl = baseUrl +   "/gateway/taobao/einvoice/apply/get";
    public   static    String     JstSmsSignnameCreateRequestUrl = baseUrl +   "/gateway/taobao/jst/sms/sign/create";
    public   static    String     JstSmsSignnameQueryRequestUrl = baseUrl +   "/gateway/taobao/jst/sms/sign/query";
    public   static    String     JstSmsSignnameDeleteRequestUrl = baseUrl +   "/gateway/taobao/jst/sms/sign/delete";
    public   static    String     JstSmsTemplateCreateRequestUrl = baseUrl +   "/gateway/taobao/jst/sms/template/create";
    public   static    String     JstSmsTemplateQueryRequestUrl = baseUrl +   "/gateway/taobao/jst/sms/template/query";
    public   static    String     JstSmsTemplateDeleteRequestUrl = baseUrl +   "/gateway/taobao/jst/sms/template/delete";
    public   static    String     JstSmsOAIDSendRequestUrl = baseUrl +   "/gateway/taobao/jst/sms/oaid/send";
    public   static    String     JstSmsTidSendRequestUrl = baseUrl +   "/gateway/taobao/jst/sms/tid/send";

    public   static    String     PddOrderDecryptUrl = baseUrl +   "/gateway/pdd/order/decrypt";
    public   static    String     PddOrderDecryptFrontUrl = baseUrl +   "/gateway/pdd/order/decrypt/front";
    public   static    String     PddGoodsListGetUrl = baseUrl +   "/gateway/pdd/goods/list/get";
    public   static    String     PddGoodsDetailGetUrl = baseUrl +   "/gateway/pdd/goods/detail/get";
    public   static    String     PddGoodsSaleStatusSetUrl = baseUrl +   "/gateway/pdd/goods/sale/status/set";
    public   static    String     PddGoodsLogisticsTemplateGetUrl = baseUrl +   "/gateway/pdd/goods/logistics/template/get";
    public   static    String     PddGoodsImageUploadRequestUrl = baseUrl +   "/gateway/pdd/goods/image/upload";
    public   static    String     PddGoodsSpecGetRequestUrl = baseUrl +   "/gateway/pdd/goods/spec/get";
    public   static    String     PddGoodsSpecIdGetRequestUrl = baseUrl +   "/gateway/pdd/goods/spec/id/get";
    public   static    String     PddGoodsCountryGetRequestUrl = baseUrl +   "/gateway/pdd/goods/country/get";
    public   static    String     PddGoodsCommitDetailGetRequestUrl = baseUrl +   "/gateway/pdd/goods/commit/detail/get";
    public   static    String    PddGoodsAuthorizationCatsRequestUrl = baseUrl +   "/gateway/pdd/goods/auth/cats/get";
    public   static    String    PddGoodsCatsRequestUrl = baseUrl +   "/gateway/pdd/goods/cats/get";
    public   static    String     PddGoodsAddRequestUrl = baseUrl +   "/gateway/pdd/goods/add";
    public   static    String     TaoBaoRefundAgreeUrl = baseUrl +   "/gateway/taobao/refund/agree";
    public   static    String     TaoBaoRefundRefuseUrl = baseUrl +   "/gateway/taobao/refund/refuse";
    public   static    String     PddOrderNoteUpdateUrl = baseUrl +   "/gateway/pdd/order/noteupdate";
    public   static    String     PartnerSellerSubscribeUrl = baseUrl +   "/gateway/partner/seller/subscribe";
    public   static    String     TaoBaoOrderDetailBatchUrl = baseUrl +   "/gateway/taobao/order/detail/batch";
    public   static    String     PddOrderListIncrementUrl = baseUrl +   "/gateway/pdd/order/list/increment";
    public   static    String     PddOrderDetailBatchGetUrl = baseUrl +   "/gateway/pdd/order/detail/batchget";
    public   static    String     TaoBaoOrderInvoiceAmountBatchUrl = baseUrl +   "/gateway/taobao/order/invoice/amount/get";
    public   static    String     TaoBaoOrderGetMakeFlagExpressUrl = baseUrl +   "/gateway/taobao/order/get/makeflagexpress";
    public   static    String     TaoBaoInventoryListUrl = baseUrl +   "/gateway/taobao/inventory/get";
    public   static    String     TaoBaoItemSellerListUrl = baseUrl +   "/gateway/taobao/item/seller/list";
    public   static    String     TaoBaoBuyerGetopenuidUrl = baseUrl +   "/gateway/taobao/buyer/getopenuid";
    public   static    String     FCPartnerRemoveGrantUrl = baseUrl +   "/gateway/partner/seller/remove/grant";
    public   static    String     FCPartnerStoreAuthUrl = baseUrl +   "/gateway/partner/store/auth";

    // 丰桥
    public   static    String     SFExpressCreateOrderUrl = baseUrl +   "/gateway/sfexpress/order/create";
    public   static    String     SFExpressSearchOrderUrl = baseUrl +   "/gateway/sfexpress/order/search";
    public   static    String     SFExpressUpdateOrderUrl = baseUrl +   "/gateway/sfexpress/order/update";
    public   static    String     SFExpressFilterOrderUrl = baseUrl +   "/gateway/sfexpress/order/filter";
    public   static    String     SFExpressSearchRoutersUrl = baseUrl +   "/gateway/sfexpress/routes/search";
    public   static    String     SFExpressGetSubMailNosUrl = baseUrl +   "/gateway/sfexpress/order/getsubmailno";
    public   static    String     SFExpressQueryOrderWaybillsUrl = baseUrl +   "/gateway/sfexpress/order/querywaybill";
    public   static    String     SFExpressCloudPrintWayBillUrl = baseUrl +   "/gateway/sfexpress/waybill/cloudprint";

    public   static    String     QMTaoBaoOrderListUrl = qmUrl ;

    // 蜂巢打单
    public   static    String     FCPrinterImportTradeUrl = baseUrl + "/gateway/wbprinter/trade/import" ;
    public   static    String     FCGoodsListUrl = baseUrl + "/gateway/cs/agent/goods/list" ;
    public   static    String     FCCSTradeCreateUrl = baseUrl + "/gateway/cs/agent/trade/create" ;
    public   static    String     FCCSHostTradeListUrl = baseUrl + "/gateway/cs/host/trade/list" ;
    public   static    String     FCCSAgentTradeListUrl = baseUrl + "/gateway/cs/agent/trade/list" ;
    public   static    String     FCCSAHostLogisticsSendUrl = baseUrl + "/gateway/cs/host/logistics/send" ;
    public   static    String     FCCSAHostCancelTradeUrl = baseUrl + "/gateway/cs/host/trade/cancel" ;
    public   static    String     FCCSAHostGoodsUpdateUrl = baseUrl + "/gateway/cs/host/goods/update" ;
    public   static    String     DDTradeListUrl = baseUrl + "/gateway/dd/trade/list" ;
    public   static    String     DDTradeListExtUrl = baseUrl + "/gateway/dd/trade/listext" ;
    public   static    String     DDTradeDetailUrl = baseUrl + "/gateway/dd/trade/detail" ;
    public   static    String     DDLogisticsCompanyListUrl = baseUrl + "/gateway/dd/logistics/company/list" ;
    public   static    String     DDLogisticstemplateListUrl = baseUrl + "/gateway/dd/logistics/template/list" ;
    public   static    String     DDLogisticsWaybillGetUrl = baseUrl + "/gateway/dd/logistics/waybill/get" ;
    public   static    String     DDLogisticsShopnetSiteListUrl = baseUrl + "/gateway/dd/logistics/shopnetsite/list" ;
    public   static    String     DDLogisticsTraceNoTrouteUrl = baseUrl + "/gateway/dd/logistics/trackno/route" ;
    public   static    String     DDOrderLogisticsAddUrl = baseUrl + "/gateway/dd/order/logistics/add" ;
    public   static    String     DDAddListUrl = baseUrl + "/gateway/dd/address/list" ;
    public   static    String     DDOrderDecryptUrl = baseUrl + "/gateway/dd/order/decrypt" ;
    public   static    String     DDLogisticsNewcreateorderUrl = baseUrl + "/gateway/dd/logistics/newcreateorder" ;
    public   static    String     DDLogisticsGetWayBillUrl = baseUrl + "/gateway/dd/logistics/getwaybill" ;
    public   static    String     DDLogisticsGetOutRangeUrl = baseUrl + "/gateway/dd/logistics/getoutrange" ;
    public   static    String     DDLogisticsCancelOrderUrl = baseUrl + "/gateway/dd/order/logistics/cancel" ;
    public   static    String     DDOrderAddOrderRemarkUrl = baseUrl + "/gateway/dd/order/addorderremark" ;
    public   static    String     DDSmsTemplateApplyRequestUrl = baseUrl + "/gateway/dd/sms/template/apply" ;
    public   static    String     DDSmsTemplateDeleteRequestUrl = baseUrl + "/gateway/dd/sms/template/delete" ;
    public   static    String     DDSmsTemplateRevokeRequestUrl = baseUrl + "/gateway/dd/sms/template/revoke" ;
    public   static    String     DDSmsTemplateApplyListRequestUrl = baseUrl + "/gateway/dd/sms/template/apply/list" ;
    public   static    String     DDSmsTemplateSearchRequestUrl = baseUrl + "/gateway/dd/sms/template/search" ;
    public   static    String     DDSmsPublicTemplateRequestURL = baseUrl + "/gateway/dd/sms/public/template" ;
    public   static    String     DDSmsSignApplyRequestUrl = baseUrl + "/gateway/dd/sms/sign/apply" ;
    public   static    String     SmsSignSearchRequestUrl = baseUrl + "/gateway/dd/sms/sign/search" ;
    public   static    String     DDSmsSignDeleteRequestUrl = baseUrl + "/gateway/dd/sms/sign/delete" ;
    public   static    String     DDSmsSignApplyListRequestUrl = baseUrl + "/gateway/dd/sms/sign/apply/list" ;
    public   static    String     DDSmsSendRequestUrl = baseUrl + "/gateway/dd/sms/send" ;
    public   static    String     DDSmsSendResultRequestUrl = baseUrl + "/gateway/dd/sms/send/result" ;
    public   static    String     DDMetrialCreateFoRequestUrl = baseUrl + "/gateway/dd/material/createfolder" ;
    public   static    String     DDMetrialUploadImageSyncRequestUrl = baseUrl + "/gateway/dd/material/uploadimagesync" ;
    public   static    String     DDBatchUploadImageSyncRequestUrl = baseUrl + "/gateway/dd/material/batchuploadimagesync" ;
    public   static    String     DDBatchUploadVideoAsyncRequestUrl = baseUrl + "/gateway/dd/material/batchuploadvideoasync" ;
    public   static    String     DDQueryMaterialDetailRequestUrl = baseUrl + "/gateway/dd/material/querymaterialdetail" ;
    public   static    String     DDSearchMaterialRequestUrl = baseUrl + "/gateway/dd/material/searchmaterial" ;
    public   static    String     DDGetShopCategoryRequestUrl = baseUrl + "/gateway/dd/shop/getshopcategory" ;
    public   static    String     DDShopBrandListRequestUrl = baseUrl + "/gateway/dd/shop/brandlist" ;
    public   static    String     DDFreightTemplateListRequestUrl = baseUrl + "/gateway/dd/freighttemplate/list" ;
    public   static    String     DDGetCatePropertyV2RequestUrl = baseUrl + "/gateway/dd/product/getcatepropertyv2" ;
    public   static    String     DDGetProductUpdateRuleRequestUrl = baseUrl + "/gateway/dd/product/getproductupdaterule" ;
    public   static    String     DDQualificationConfigRequestUrl = baseUrl + "/gateway/dd/product/qualificationconfig" ;
    public   static    String     DDGetRecommendNameRequestUrl = baseUrl + "/gateway/dd/product/getrecommendname" ;
    public   static    String     DDAddv2RequestUrl = baseUrl + "/gateway/dd/product/addv2" ;
    public   static    String     DDProductDetailRequestUrl = baseUrl + "/gateway/dd/product/detail" ;
    public   static    String     DDSetOfflineRequestUrl = baseUrl + "/gateway/dd/product/setoffline" ;
    public   static    String     DDEditV2RequestUrl = baseUrl + "/gateway/dd/product/editv2" ;
    public   static    String     DDProductDetail = baseUrl + "/gateway/dd/product/detail";
    public   static    String     DDProductListv2 = baseUrl + "/gateway/dd/product/listv2";
    public   static    String     DDSkuDetail = baseUrl + "/gateway/dd/sku/detail";
    public   static    String     DDSkuStocknum = baseUrl + "/gateway/dd/sku/stocknum";

    public   static    String     DDBrandList = baseUrl + "/gateway/dd/brand/list";
    public   static    String     DDMaterialBatchuploadImagesync = baseUrl + "/gateway/dd/material/batchuploadimagesync";
    public   static    String     DDMaterialBatchuploadVideoasync = baseUrl + "/gateway/dd/material/batchuploadvideoasync";
    public   static    String     DDMaterialCreatefolder = baseUrl + "/gateway/dd/material/createfolder";
    public   static    String     DDMaterialEditfolder = baseUrl + "/gateway/dd/material/editfolder";
    public   static    String     DDMaterialUploadimageSync = baseUrl + "/gateway/dd/material/uploadimagesync";
    public   static    String     DDMaterialQueryMaterialDetail = baseUrl + "/gateway/dd/material/querymaterialdetail";
    public   static    String     DDMaterialSearchMaterial = baseUrl + "/gateway/dd/material/searchmaterial";
    public   static    String     DDMaterialUploadvideoAsync = baseUrl + "/gateway/dd/material/uploadvideoasync";
    public   static    String     DDMaterialUpload = baseUrl + "/gateway/dd/material/materialupload";

    public   static    String     DDProductAddv2 = baseUrl + "/gateway/dd/product/addv2";
    public   static    String     DDProductAuditlist = baseUrl + "/gateway/dd/product/auditlist";
    public   static    String     DDProductCategoryDimlist = baseUrl + "/gateway/dd/product/categorydimlist";
    public   static    String     DDProductCreatecomponentTemplatev2 = baseUrl + "/gateway/dd/product/createcomponenttemplatev2";
    public   static    String     DDProductDatchdelComponenttemplate = baseUrl + "/gateway/dd/product/datchdelcomponenttemplate";
    public   static    String     DDProductDel = baseUrl + "/gateway/dd/product/del";
    public   static    String     DDProductEditbuyerLimit = baseUrl + "/gateway/dd/product/editbuyerlimit";
    public   static    String     DDProductEditcomponentTemplate = baseUrl + "/gateway/dd/product/editcomponenttemplate";
    public   static    String     DDProductEditv2 = baseUrl + "/gateway/dd/product/editv2";
    public   static    String     DDProductGetcascadeValue = baseUrl + "/gateway/dd/product/getcascadevalue";
    public   static    String     DDProductGetcategoryPropertyvalue = baseUrl + "/gateway/dd/product/getcategorypropertyvalue";
    public   static    String     DDProductGetcateProperty = baseUrl + "/gateway/dd/product/getcateproperty";
    public   static    String     DDProductGetcatePropertyv2 = baseUrl + "/gateway/dd/product/getcatepropertyv2";
    public   static    String     DDProductLaunchproduct = baseUrl + "/gateway/dd/product/launchproduct";
    public   static    String     DDProductSetoffline = baseUrl + "/gateway/dd/product/setoffline";
    public   static    String     DDProductSetonline = baseUrl + "/gateway/dd/product/setonline";
    public   static    String     DDShopBrandlist = baseUrl + "/gateway/dd/shop/brandlist";
    public   static    String     DDShopGetshopCategory = baseUrl + "/gateway/dd/shop/getshopcategory";
    public   static    String     DDSkuAddall = baseUrl + "/gateway/dd/sku/addall";
    public   static    String     DDSkuList = baseUrl + "/gateway/dd/sku/list";
    public   static    String     DDSkuEditcode = baseUrl + "/gateway/dd/sku/editcode";
    public   static    String     DDSkuEditprice = baseUrl + "/gateway/dd/sku/editprice";
    public   static    String     DDSkuSyncstock = baseUrl + "/gateway/dd/sku/syncstock";
    public   static    String     DDSkuSyncstockBatch = baseUrl + "/gateway/dd/sku/syncstockbatch";
    public   static    String     DDSpuGetauditInfo = baseUrl + "/gateway/dd/spu/getauditinfo";
    public   static    String     DDSpuGetkeyPropertybyCid = baseUrl + "/gateway/dd/spu/getkeypropertybycid";
    public   static    String     DDSpuGetspuInfobySpuid = baseUrl + "/gateway/dd/spu/getspuinfobyspuid";
    public   static    String     DDSpuGetspuRule = baseUrl + "/gateway/dd/spu/getspurule";
    public   static    String     DDSpuGetspuTpl = baseUrl + "/gateway/dd/spu/getsputpl";

    public   static    String     DDBrandGetSug = baseUrl + "/gateway/dd/brand/get/sug";
    public   static    String     DDProductGetProductUpdateRule = baseUrl + "/gateway/dd/product/get/product/updaterule";
    public   static    String     DDProductQualificationConfig = baseUrl + "/gateway/dd/product/qualification/config";
    public   static    String     DDFreightTemplateList = baseUrl + "/gateway/dd/freighttemplate/list";
    public   static    String     DDReightTemplateCreate = baseUrl + "/gateway/dd/freighttemplate/create";
    public   static    String     DDProductGetRecommendName = baseUrl + "/gateway/dd/product/get/recommend/name";
    public   static    String     DDProductGetComponentTemplate = baseUrl + "/gateway/dd/product/get/component/template";
    public   static    String     DDProductGetRecommendCategory = baseUrl + "/gateway/dd/product/get/recommend/category";
    public   static    String     DDMaterialBatchUploadImageSync = baseUrl + "/gateway/dd/material/batch/upload/image/sync";
    public   static    String     DDMaterialCreateFolder = baseUrl + "/gateway/dd/material/create/folder";

    public   static    String     AlipayPayPushTradeUrl = baseUrl + "/gateway/partner/pay/quick/alipay/repush" ;


    public static String JDOrderListUrl = baseUrl +   "/gateway/jd/order/ensearch";
    public static String JDExtOrderListUrl = baseUrl +   "/gateway/jd/order/extsearch";
    public static String JDGetOrderUrl = baseUrl +   "/gateway/jd/order/enget";
    public static String JDQueryRemarkByOrderId = baseUrl +   "/gateway/jd/order/remark/querybyorderid";
    public static String JDModifyVenderRemark = baseUrl +   "/gateway/jd/order/modifyvenderremark";
    public static String JDshipment = baseUrl +   "/gateway/jd/order/shipment";
    public static String JDfindSkuById = baseUrl +   "/gateway/jd/sku/findskubyid";
    public static String JDfindWareById = baseUrl +   "/gateway/jd/ware/findwarebyid";
    public static String JDgetVenderCarrier = baseUrl +   "/gateway/jd/alpha/getvendercarrier";
    public static String JDgetProviderSign = baseUrl +   "/gateway/jd/alpha/getprovidersign";
    public static String JDgetProvider = baseUrl +   "/gateway/jd/alpha/getprovider";
    public static String JDwayBillReceive = baseUrl +   "/gateway/jd/alpha/waybill/receive";
    public static String JDwayBillPulldata = baseUrl +   "/gateway/jd/printing/pulldata";
    public static String JDconfirmOrCancel = baseUrl +   "/gateway/jd/alpha/waybill/confirmorcancel";

    public static String JDgetTemplateList = baseUrl +   "/gateway/jd/printing/gettemplatelist";
    public static String JDsensitivePullData = baseUrl +   "/gateway/jd/printing/sensitivepulldata";
    public static String JDgetDeliveryCompany = baseUrl +   "/gateway/jd/delivery/company";
    public static String JDWaybillUnbind = baseUrl +   "/gateway/jd/alpha/waybill/unbind";
    public static String JDWayBillBigshotQuery = baseUrl +   "/gateway/jd/alpha/bigshotquery";
    public static String JDWaybillConfirmOrCancel = baseUrl +   "/gateway/jd/alpha/waybill/confirmorcancel";
    public static String JDGetMobileLIst = baseUrl +   "/gateway/jd/order/getmobilelist";
    public static String JDDecryptOrder= baseUrl +   "/gateway/jd/order/decrypt";
    public static String JDWaybillVendorStockQueryUrl = baseUrl +   "/gateway/jd/alpha/waybill/vendor/stock/query";
}



