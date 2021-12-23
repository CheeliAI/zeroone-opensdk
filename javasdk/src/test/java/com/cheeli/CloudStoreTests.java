package com.cheeli;

import com.alibaba.fastjson.JSON;
import com.cheeli.models.cloudstore.TradeImportRequest;
import com.cheeli.models.taobao.trade.Order;
import com.cheeli.models.taobao.trade.Trade;
import com.cheeli.models.taobao.trade.TradeResponse;
import com.cheeli.models.taobao.waybill.*;
import com.cheeli.utils.TradeUtil;
import com.cheeli.utils.UUID;
import com.cheeli.utils.Utils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 场景：合作伙伴使用，可管理其他授权的店铺。即可以管理多家店铺
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudStoreTests.class )
public class CloudStoreTests {

    @Test
    public void contextLoads() {
    }


    @Test
    public void FCGoodsList() throws Exception {

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        doHttpRequest(Config.FCGoodsListUrl,data);
    }


    /**
     * 生成CS订单
     * @throws Exception
     */
    @Test
    public void CSCreateTrade() throws Exception {

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        String trades = "[{ \n" +
                "        \"cloud_store_id\": 66,\n" +
                "        \"op_type\": 1,\n" +
                "        \"op_tid\": \"1701118800802565830\",\n" +
                "        \"op_seller_nick\": \"百鞋馆\",\n" +
                "        \"store_id\": 32,\n" +
                "        \"sheet_id\": 37, \n" +
                "        \"goods_id\": 4,  \n" +
                "        \"goods_count\":1,  \n" +
                "        \"customer_province\": \"上海\",\n" +
                "        \"customer_city\": \"上海市\",\n" +
                "        \"customer_county\": \"浦东新区\",\n" +
                "        \"customer_detail\": \"陆**街道**路***号****室\",\n" +
                "        \"customer_detail_ciphertext\": \"\",\n" +
                "        \"customer_name\": \"李**\",\n" +
                "        \"customer_name_ciphertext\": \"\",\n" +
                "        \"customer_mobile\": \"*******7123\",\n" +
                "        \"customer_mobile_ciphertext\": \"\",\n" +
                "        \"oaid\": \"1h12M4LqkhUkt1ibEk6VyZv2gRsECYSQayzH33CwBP1s3CmlWYx1bREJiasRWEC4TxDdXI6c\"  ,\n" +
                "        \"agent_trade_no\" : \"3882341242150\"\n" +
                "}\n" +
                " ,{ \n" +
                "    \"cloud_store_id\": 66,\n" +
                "    \"op_type\": 1,\n" +
                "    \"op_tid\": \"2335491902399565831\",\n" +
                "    \"op_seller_nick\": \"百鞋馆\",\n" +
                "    \"store_id\": 32,\n" +
                "    \"sheet_id\": 37, \n" +
                "    \"goods_id\": 4,  \n" +
                "    \"goods_count\":1,  \n" +
                "    \"customer_province\": \"上海\",\n" +
                "    \"customer_city\": \"上海市\",\n" +
                "    \"customer_county\": \"闵行区\",\n" +
                "    \"customer_detail\": \"七*镇**路***号*楼***室\",\n" +
                "    \"customer_detail_ciphertext\": \"\",\n" +
                "    \"customer_name\": \"张**\",\n" +
                "    \"customer_name_ciphertext\": \"\",\n" +
                "    \"customer_mobile\": \"*******7558\",\n" +
                "    \"customer_mobile_ciphertext\": \"\",\n" +
                "    \"oaid\": \"1EJibkMshuK1fdr5Ko4x1ibGqkbA48yhdicBVO6rzHKN7RZdJ1Tx1LBPkicXHdgjIewicvbPDgo\" ,\n" +
                "    \"agent_trade_no\" : \"3882341242151\"\n" +
                "\n" +
                "}\n" +
                "]";


        // 导入tb订单
//        String tid = "2335491902399565830";
//        Trade trade = TradeUtil.getTBTrade(tid);
//        TradeImportRequest tiq = new TradeImportRequest();
//        tiq.setCloud_store_id(66); // 根据商品获取
//         tiq.setOp_type(1);
//        tiq.setOp_tid(tid);
//        tiq.setOp_seller_nick(trade.getSellerNick());
//        tiq.setStore_id(32);
//        tiq.setSheet_id(37);
//        tiq.setGoods_id(4);
//        tiq.setGoods_count(1);
//        tiq.setCustomer_province(trade.getReceiverState());
//        tiq.setCustomer_city(trade.getReceiverCity());
//        tiq.setCustomer_county(trade.getReceiverDistrict());
//        tiq.setCustomer_detail(trade.getReceiverAddress());
//        tiq.setCustomer_name(trade.getReceiverName());
//        tiq.setCustomer_mobile(trade.getReceiverMobile());
//        tiq.setOaid(trade.getOaid());
//        tiq.setAgent_trade_no(UUID.fastUUID().toString());
//        List<TradeImportRequest> list = new ArrayList<>();
//        list.add(tiq);
//        trades = JSON.toJSONString(list);


        // 导入pdd订单
//        String tid = "210922-139114607542221";
//        com.cheeli.models.pdd.TradeResponse  TradeResponse = TradeUtil.getPddTrade(tid);
//
//        TradeImportRequest tiq = new TradeImportRequest();
//        tiq.setCloud_store_id(66); // 根据商品获取
//        tiq.setOp_type(2); // pdd
//        tiq.setOp_tid(tid);
//        tiq.setOp_seller_nick("pdd75712771556");
//        tiq.setStore_id(32);
//        tiq.setSheet_id(37);
//        tiq.setGoods_id(4);
//        tiq.setGoods_count(2);
//        tiq.setCustomer_province(TradeResponse.getData().getProvince());
//        tiq.setCustomer_city(TradeResponse.getData().getCity());
//        tiq.setCustomer_county(TradeResponse.getData().getTown());
//        tiq.setCustomer_detail(TradeResponse.getData().getAddress_mask());
//        tiq.setCustomer_detail_ciphertext(TradeResponse.getData().getAddress()); // 密文串
//        tiq.setCustomer_name(TradeResponse.getData().getReceiver_name_mask());
//        tiq.setCustomer_name_ciphertext(TradeResponse.getData().getReceiver_name()); // 密文串
//        tiq.setCustomer_mobile(TradeResponse.getData().getReceiver_phone_mask());
//        tiq.setCustomer_mobile_ciphertext(TradeResponse.getData().getReceiver_phone());// 密文串
//
//        tiq.setAgent_trade_no(UUID.fastUUID().toString());
//        List<TradeImportRequest> list = new ArrayList<>();
//        list.add(tiq);
//        trades = JSON.toJSONString(list);


        data.put("trades", trades);

        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        doHttpRequest(Config.FCCSTradeCreateUrl,data);
    }



    /**
     *   云仓者获取订单
     * @throws Exception
     */
    @Test
    public void  FCCSHostTradeList() throws Exception {
//
//         String AppId  = "f8mCb1tEn1i6CDsB";
//         String AppSecret = "Wob3MMyk9it5g4dd";

        //pd
        String AppId  = "6bADeFWuYvoVl442";
        String AppSecret = "wkZtQnq3NKoEGMHU";

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("page_no", "1");
        data.put("page_size", "30");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, -7);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data.put("start_created", df.format(calendar.getTime()));
        Date end = new Date();
        data.put("end_created", df.format(end));
        data.put("status", "0");

        // 签名
        data.put("sign", Utils.Sign(data,AppSecret));
        // 调用服务API
        doHttpRequest(Config.FCCSHostTradeListUrl ,data);

    }


    /**
     *   代理商获取订单
     * @throws Exception
     */
    @Test
    public void  FCCSAgentTradeList() throws Exception {

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("page_no", "1");
        data.put("page_size", "30");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, -7);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data.put("start_created", df.format(calendar.getTime()));
        Date end = new Date();
        data.put("end_created", df.format(end));
//        data.put("status", "2");
//        data.put("op_type", "1");
//        data.put("op_seller_nick", "百鞋馆");


        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.FCCSAgentTradeListUrl ,data);

    }


    /**
     * 简化通过订单号直接取号
     * @throws Exception
     */
    @Test
    public void getCaiNiaoWayBillByTid() throws Exception {

        // 要取号的订单号
        String tid = "2335491902399565830" ;

        String tb_seller_nick = Config.TBSellerNick; //要查询支付宝的淘宝商家
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tids",tid);// bxg

        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = Utils.doHttpRequest(Config.TaoBaoOrderDetailBatchUrl, data);
        TradeResponse tradeResponse = JSON.parseObject(resp, TradeResponse.class);
        if (tradeResponse == null) {
            System.out.println("json解析错误:" + resp);
        }

        if (tradeResponse.getCode() != 0) {
            String msg = String.format("code:%d, msg:%s, ", tradeResponse.getCode(), tradeResponse.getMessage());
            System.out.println(msg);
        } else {

            for (com.cheeli.models.taobao.trade.Data dataItem : tradeResponse.getData()) {
                Trade trade = dataItem.getTrade();

                // 准备取号的请求参数
                String wbJsonRequest = builWaybillDTO(trade);
                data = new HashMap<String, String>();
                data.put("appid",  Config.AppId);
                data.put("tb_seller_nick", tb_seller_nick);
                timestamp = System.currentTimeMillis() / 1000;
                data.put("timestamp", timestamp.toString());
                data.put("request_data",wbJsonRequest );
                data.put("sign", Utils.Sign(data,Config.AppSecret));
                resp = Utils.doHttpRequest(Config.TaoBaoCaiNiaoWayBillV2BatchGetUrl, data);
                System.out.println(resp);

                WayBillResponse wayBillResponse = JSON.parseObject(resp, WayBillResponse.class);

                if (wayBillResponse == null ){
                    System.out.println("json解析错误:" +  resp);
                }

                if (wayBillResponse.getCode() != 0 ) {
                    String msg = String.format("code:%d, msg:%s, ", wayBillResponse.getCode(), wayBillResponse.getMessage());
                    System.out.println(msg);
                } else {

                    for (Data wbDataItem : wayBillResponse.getData()) {
                        String itemMsg = String.format("code:%d, msg:%s, object_id:%s,waybill_code:%s,partner_waybill_code:%s ", wbDataItem.getCode(), wbDataItem.getMessage(),
                                wbDataItem.getObjectId(), wbDataItem.getWaybillCode(), wbDataItem.getParentWaybillCode());
                        System.out.println(itemMsg);

                    }

                }


            }

        }


    }




    private String  builWaybillDTO(Trade trade ) {
        WayBillNoDTO wayBillNoDTO = new WayBillNoDTO();
        // sellerTradeProxy.getCompanyCode()  可能会空。 如果是选定模板，点打印快递单。
        // 正确的处理：如果是已经取过号（含在蜂巢打单中取号或是导入面单），则可用 sellerTradeProxy.getCompanyCode()。 如果是要换快递公司取号，则要以界面中选定的模板，此处要优化
//        wayBillNoDTO.setCpCode(sellerTradeProxy.getCompanyCode());
        wayBillNoDTO.setDmsSorting(false);
        wayBillNoDTO.setResourceCode("");// 可填， 配送资源code， 仓库WMS系统对接落地配业务，其它场景请不要使用

        //sender
        Sender sender = new Sender();
        sender.setMobile("18666073308");// 手机与固话不能同时为空,待处理
        sender.setName("罗生");// 发货人姓名不能为空
        sender.setPhone("");// 电子面单云打印失败,商家请求参数错误,固话和手机只能包含+、-、空格、单引号、数字、英文逗号、中文逗号

        Address address = new Address();
        address.setProvince("上海");
        address.setCity("上海市");
        address.setDistrict("浦东新区");
        address.setDetail("商城路738号1205");//必填
        sender.setAddress(address);
        wayBillNoDTO.setSender(sender);

        //region tradeOrderInfoDTO
        TradeOrderInfoDTO tradeOrderInfoDTO = new TradeOrderInfoDTO();
        String uuid = java.util.UUID.randomUUID().toString();
        tradeOrderInfoDTO.setObjectId(uuid);
        // 新增以当前选定模板， 宅急送一联单
        wayBillNoDTO.setCpCode("ZJS");
        tradeOrderInfoDTO.setTemplateUrl("http://cloudprint.cainiao.com/template/standard/309188/5");
        tradeOrderInfoDTO.setUserId(0);// 必填， 请传固定传0，蜂巢开放平台会转处理。

        //orderInfo
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderChannelsType("TB");//必填，订单渠道编码，见 http://help.fw199.com/docs/h7b/tb-order-channel
        List<String> li = new ArrayList<>();
        li.add(trade.getTidStr());
        orderInfo.setTradeOrderList(li);

        //packageInfo
        PackageInfo packageInfo = new PackageInfo();
        packageInfo.setId(null);// 可填
        List<Order> orderList = trade.getOrders().getOrder();
        List<PackageItem> items = new ArrayList<>();
        for (Order item : orderList) {
            PackageItem packageItem = new PackageItem();
            packageItem.setCount(item.getNum());
            packageItem.setName(item.getTitle());
            items.add(packageItem);
        }
        packageInfo.setItems(items);
        packageInfo.setVolume(0);//可填
        packageInfo.setWeight(0);//可填

        //recipient
        Recipient recipient = new Recipient();
        recipient.setMobile(trade.getReceiverMobile());//电子面单云打印失败,商家请求参数错误,固话和手机只能包含+、-、空格、单引号、数字、英文逗号、中文逗号
        recipient.setName(trade.getReceiverName());
        recipient.setPhone(trade.getReceiverPhone());

        recipient.setOaid(trade.getOaid());
        recipient.setTid(trade.getTidStr());
        Address receiverAddress = new Address();
        receiverAddress.setCity(trade.getReceiverCity());
        receiverAddress.setDetail(trade.getReceiverAddress());
        receiverAddress.setDistrict(trade.getReceiverDistrict());
        receiverAddress.setProvince(trade.getReceiverState());
        receiverAddress.setTown(trade.getReceiverDistrict());
        recipient.setAddress(receiverAddress);


        tradeOrderInfoDTO.setOrderInfo(orderInfo);
        tradeOrderInfoDTO.setPackageInfo(packageInfo);
        tradeOrderInfoDTO.setRecipient(recipient);

        List<TradeOrderInfoDTO> tradeOrderInfoDTOs = new ArrayList<>();
        tradeOrderInfoDTOs.add(tradeOrderInfoDTO);
        wayBillNoDTO.setTradeOrderInfoDtos(tradeOrderInfoDTOs);
        //endregion

        String result = JSON.toJSONString(wayBillNoDTO);
        return result;
    }




        @Test
        public void  FCCSAHostLogisticsSend() throws Exception {

            //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",   Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        // 淘宝发货
//        String items = "[{ \n" +
//                "             \"tid\": \"2335491902399565830\", \n" +
//                "             \"out_sid\": \"ZJS000343162606\",\n" +
//                "             \"company_code\": \"ZJS\",\n" +
//                "             \"op_type\": 1,\n" +
//                "             \"op_seller_nick\" : \"百鞋馆\" ,\n" +
//                "             \"redelivery_type\" : 1\n" +
//                "}]";
//        data.put("items", items);


         // 拼多多发货
            String items = "[{ \n" +
                    "             \"tid\": \"210922-139114607542221\", \n" +
                    "             \"out_sid\": \"SF1423999716453\",\n" +
                    "             \"company_code\": \"44\",\n" +
                    "             \"op_type\": 2,\n" +
                    "             \"op_seller_nick\" : \"pdd75712771556\" ,\n" +
                    "             \"redelivery_type\" : 1\n" +
                    "}]";
            data.put("items", items);


        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.FCCSAHostLogisticsSendUrl ,data);

    }

        @Test
        public void  FCCSAHostCancelTrade() throws Exception {


            //业务参数
            Map<String, String> data = new HashMap<String, String>();
            data.put("appid", Config.AppId);
            Long timestamp = System.currentTimeMillis() / 1000;
            data.put("timestamp", timestamp.toString());


            String items = "[{ \n" +
                    "             \"fc_trade_no\": \"478275883241570304\", \n" +
                    "             \"cancel_reason\": \"用户的店铺关闭了\" " +
                    "}]";
            data.put("items", items);


            // 签名
            data.put("sign", Utils.Sign(data, Config.AppSecret));
            // 调用服务API
            doHttpRequest(Config.FCCSAHostCancelTradeUrl, data);

        }


        @Test
        public void  FCCSAHostGoodsUpdate() throws Exception {

            //业务参数
            Map<String, String> data = new HashMap<String, String>();
            data.put("appid", Config.AppId);
            Long timestamp = System.currentTimeMillis() / 1000;
            data.put("timestamp", timestamp.toString());

            // 签名
            data.put("sign", Utils.Sign(data, Config.AppSecret));
            // 调用服务API
            doHttpRequest(Config.FCCSAHostGoodsUpdateUrl, data);

        }


    private  void doHttpRequest(String url ,  Map<String, String> data ){
            String result ="";
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost( url);
            System.out.println("请求Url:" +url);
            System.out.println("请求数据:" + JSON.toJSONString(data));
            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        //发起POST请求
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result =  EntityUtils.toString(httpResponse.getEntity());
            } else {
                result =  ("doPost Error Response: " + httpResponse.getStatusLine().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println(result);
    }


}
