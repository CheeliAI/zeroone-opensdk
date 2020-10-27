此项目旨在帮助开发者或是中小微企业快速实现以下目标
1. 获取淘宝商家的订单交易、商品、支付等相关数据。
2. 接入支付宝、微信支付，而且费率低于官方标准费率， 由官方结算，周期T+1。 
 

## 如何接入及接口说明
 
1. 淘宝接入及接口说明： http://help.fw199.com/docs/h7b/taobao
2. 拼多多接入及接口说明： http://help.fw199.com/docs/h7b/pdd-preface

## 接入示例代码
1. Java接入示例代码：https://github.com/CheeliAI/zeroone-opensdk/tree/master/javasdk
2. PHP接入示例代码： https://github.com/CheeliAI/zeroone-opensdk/tree/master/phpsdk

## 支付相关接口

1. 生成支付二维码（付款码）代码： src/test/java/com/cheeli/pay/OpenPayTests.java
2. 支付成功回调： src/main/java/com/cheeli/autosend/tradeserver/TradeController.java
3. 文档：http://help.fw199.com/docs/h7b/quickpay-preface
