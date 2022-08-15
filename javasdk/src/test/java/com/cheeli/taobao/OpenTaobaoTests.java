package com.cheeli.taobao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cheeli.Config;
import com.cheeli.models.LogisticesBatchSendItem;
import com.cheeli.models.taobao.trade.Order;
import com.cheeli.models.taobao.trade.Trade;
import com.cheeli.models.taobao.trade.TradeResponse;
import com.cheeli.models.taobao.waybill.*;
import com.cheeli.tradeserver.model.ChangeMemoWithTrade;

import com.cheeli.tradeserver.model.TradeMemo;
import com.cheeli.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringEscapeUtils;
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

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 场景：合作伙伴使用，可管理其他授权的店铺。即可以管理多家店铺
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes =OpenTaobaoTests.class )
public class OpenTaobaoTests {

    @Test
    public void contextLoads() {
    }


//    /**
//     * 合作伙伴调用此接口，进行发货，可能发自己家的店铺，也可发下属的店铺
//     *
//     * @throws Exception
//     */
//
//    @Test
//    public void sendGoodsPartner() throws Exception {
//        String appid = "pt661153122";
//        String appkey = "201572ai29km63@p"; //private key  //修改
//        String sellernick = "百鞋馆"; //卖家账号，店铺千牛主号  //修改
//        String tid = "666313280002565830";// 订单号  //修改
//        String buyer_nick = "快客";//修改
//        String alipay_no = "2019071722001136191051601598";    ////修改
//        String token = Utils.MD5(appkey + sellernick + tid);
//        String buyer_message = ""; // 修改
//        String buyer_email = "6234222@qq.com";//买家邮件      //修改
//
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> trade = new HashMap<String, Object>();
//        trade.put("appid", appid);
//        trade.put("tb_seller_nick", sellernick);
//        trade.put("tid", tid);
//        trade.put("token", token);
//        trade.put("buyer_nick", buyer_nick);
//        trade.put("buyer_message", buyer_message);
//        trade.put("buyer_email", buyer_email);
//        trade.put("alipay_no", alipay_no);
//        trade.put("flag", 1); //订单的小旗，-1表示不插旗，卖家交易备注旗帜，可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)
//        trade.put("memo", "这个订单比较特殊，记一下"); //订单备注不为空，插旗才有效。
//        //子订单
//        Map<String, Object> order = new HashMap<String, Object>();
//        order.put("price", 1.40);//修改
//        order.put("num_iid", "556984715717");//修改
//        order.put("payment", "1.40");//修改
//        order.put("title", "思飞网络|国外文件主机 datafile 1G流量");//修改
//        order.put("num", 1);//修改
//        order.put("outer_iid", "Ryushare_1024_30");//修改
//        order.put("outer_sku_id", "Ryushare_1024_30");//修改
//        order.put("content", "亲爱的用户【快客】,请使用订单编号【534175553569487022】 进行使用");//修改
//
//        List<Map<?, ?>> orders = new ArrayList<Map<?, ?>>();
//        orders.add(order);
//        trade.put("orders", orders);
//        String json = mapper.writeValueAsString(trade);
//
//        String sendgoodsUrl = "https://tb.fw199.com/partner/trade/dummysend";
//        String response = Utils.post(sendgoodsUrl, json);
//        System.out.println(response);
//
//
//    }


    /**
     * 开发者查询自己的账户信息
     *
     * @throws Exception
     */
    @Test
    public void getOpenDevAccount() throws Exception {

        String result = "";
        String url = "https://open.fw199.com/gateway/partner/account/detail";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        //发起POST请求
        try {
            //参数签名
            data.put("sign", Utils.Sign(data, Config.AppSecret));
            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(httpResponse.getEntity());
            } else {
                result = ("doPost Error Response: " + httpResponse.getStatusLine().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println(result);

    }

    /**
     * 查询商家的支付宝账户信息
     *
     * @throws Exception
     */
    @Test
    public void getAlipayAccountInfo() throws Exception {

        String result = "";
//        String url = "https://open.fw199.com/gateway/alipay/account/detail";
        String url = "http://web2.vs.fw199.com/gateway/alipay/account/detail";
        String tb_seller_nick = Config.TBSellerNick; //要查询支付宝的淘宝商家
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        //发起POST请求
        try {
            //参数签名
            data.put("sign", Utils.Sign(data, Config.AppSecret));
            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(httpResponse.getEntity());
            } else {
                result = ("doPost Error Response: " + httpResponse.getStatusLine().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println(result);

    }


    /**
     * 查询某个淘宝卖家是否授权给自己
     *
     * @throws Exception
     */
    @Test
    public void checkStoreGrant() throws Exception {


        String tb_seller_nick = Config.TBSellerNick; //要查询支付宝的淘宝商家

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(Config.TaoBaoStoreGrantUrl);

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        String result = doHttpRequest(Config.TaoBaoStoreGrantUrl, data);
        System.out.println(result);

    }


    /**
     * 支付宝单笔交易查询
     *
     * @throws Exception
     */
    @Test
    public void getAlipayTradeDetail() throws Exception {

        String result = "";
        String tb_seller_nick = Config.TBSellerNick; //要查询支付宝的淘宝商家
        String alipay_order_no = "2022032322001164271404592254"; // 支付宝交易订单号
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(Config.AliPayTradeDetailUrl);

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        data.put("alipay_order_no", alipay_order_no);
//       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        //发起POST请求
        try {
            //参数签名
            data.put("sign", Utils.Sign(data, Config.AppSecret));
            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(httpResponse.getEntity());
            } else {
                result = ("doPost Error Response: " + httpResponse.getStatusLine().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println(result);

    }


    /**
     * 支付宝交易明细查询
     *
     * @throws Exception
     */
    @Test
    public void getAlipayTradeList() throws Exception {

        String result = "";
        String tb_seller_nick = Config.TBSellerNick; //要查询支付宝的淘宝商家
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(Config.AliPayTradeListUrl);

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -6);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data.put("start_time", df.format(calendar.getTime()));
        Date end = new Date();
        data.put("end_time", df.format(end));
        data.put("page_no", "1");
        data.put("page_size", "20");

        //参数签名
        try {
            data.put("sign", Utils.Sign(data, Config.AppSecret));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        //发起POST请求
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(httpResponse.getEntity());
            } else {
                result = ("doPost Error Response: " + httpResponse.getStatusLine().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println(result);

    }







    /**
     * 获取订单列表
     *
     * @throws Exception
     */
    @Test
    public void getTradeList() throws Exception {

        parallelTesk(1, new Runnable() {
            @Override
            public void run() {

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -25);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date end = new Date();
                try {

                    int currentPage = 1;
                    String tb_seller_nick = Config.TBSellerNick; //要查询的淘宝商家
                    //业务参数
                    Map<String, String> data = new HashMap<String, String>();
                    data.put("appid", Config.AppId);
                    data.put("tb_seller_nick", tb_seller_nick);
                    Long timestamp = System.currentTimeMillis() / 1000;
                    data.put("timestamp", timestamp.toString());
//                    data.put("timestamp", "1640224517");
//           data.put("hold_oaid", "0");  // 如果不知道此参数的意义，不要传入hold_oaid！！，

        data.put("start_created", df.format(calendar.getTime()));
//                    data.put("start_created", "2022-04-03 00:00:00");

        data.put("end_created",  df.format(end));
//                    data.put("end_created", "2022-04-03 23:59:59");
//                    data.put("status", "WAIT_SELLER_SEND_GOODS"); // 待发货订单 WAIT_SELLER_SEND_GOODS
//                    data.put("buyer_nick", "");
                    data.put("page_no", String.valueOf(currentPage));
                    data.put("page_size", "30");
                    data.put("use_has_next", "false");
                    data.put("buyer_open_id", "AAHW90gYAAjcqSrFKsIjFOPr");

                    //fixed(一口价) auction(拍卖) guarantee_trade(一口价、拍卖) auto_delivery(自动发货) independent_simple_trade(旺店入门版交易) independent_shop_trade(旺店标准版交易) ec(直冲)
                    // cod(货到付款) fenxiao(分销) game_equipment(游戏装备) shopex_trade(ShopEX交易) netcn_trade(万网交易)
                    // external_trade(统一外部交易)o2o_offlinetrade（O2O交易）step (万人团)nopaid(无付款订单)pre_auth_type(预授权0元购机交易)


//                    data.put("type", "tmall_i18n,guarantee_trade,auto_delivery,ec,cod,step,o2o_offlinetrade");
                    data.put("type", "fixed,auction,guarantee_trade,auto_delivery,independent_simple_trade,independent_shop_trade," +
                            "ec,cod,fenxiao,game_equipment,shopex_trade,netcn_trade,external_trade,o2o_offlinetrade,step,nopaid,pre_auth_type,tmall_i18n,guarantee_trade,auto_delivery,ec,cod,step,o2o_offlinetrade");
                    data.put("sign", Utils.Sign(data, Config.AppSecret));
                    // 调用服务API
                    long startTime = System.currentTimeMillis(); //获取开始时间
                    String response = doHttpRequest(Config.TaoBaoOrderListUrl, data);
                    System.out.println(response);

                    JSONObject root = JSON.parseObject(response);
                    int total_results = root.getJSONObject("data").getIntValue("total_results");

                    currentPage = currentPage + 1;
                    int page = total_results / 30 + 1;
                    while (currentPage  <= page && currentPage < 1 ) {

                          tb_seller_nick = Config.TBSellerNick; //要查询的淘宝商家
                        //业务参数
                        data = new HashMap<String, String>();
                        data.put("appid", Config.AppId);
                        data.put("tb_seller_nick", tb_seller_nick);
                          timestamp = System.currentTimeMillis() / 1000;
                        data.put("timestamp", timestamp.toString());
//                    data.put("timestamp", "1640185838");
//           data.put("hold_oaid", "0");  // 如果不知道此参数的意义，不要传入hold_oaid！！，
                        data.put("start_created", df.format(calendar.getTime()));
                        data.put("end_created",  df.format(end));
                        data.put("status", "WAIT_SELLER_SEND_GOODS"); // 待发货订单 WAIT_SELLER_SEND_GOODS
                        data.put("buyer_nick", "");
                        data.put("page_no", String.valueOf(currentPage));
                        data.put("page_size", "30");
                        data.put("use_has_next", "false");
//                    data.put("type", "guarantee_trade,auto_delivery,ec,cod,step,o2o_offlinetrade");
                        data.put("sign", Utils.Sign(data, Config.AppSecret));
                        // 调用服务API
                          startTime = System.currentTimeMillis(); //获取开始时间
                          response = doHttpRequest(Config.TaoBaoOrderListUrl, data);
                         currentPage = currentPage + 1;

                    }

                    System.out.println("total_results:" + total_results);
                    long endTime = System.currentTimeMillis(); //获取结束时间
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            } //run

        });


    }


    /**
     * 获取订单列表(礼物专用）
     *
     * @throws Exception
     */
    @Test
    public void TaoBaoOrderExtList() throws Exception {

        parallelTesk(1, new Runnable() {
            @Override
            public void run() {

                try {
                    String tb_seller_nick = Config.TBSellerNick; //要查询的淘宝商家
                    //业务参数
                    Map<String, String> data = new HashMap<String, String>();
                    data.put("appid", Config.AppId);
                    data.put("tb_seller_nick", tb_seller_nick);
                    Long timestamp = System.currentTimeMillis() / 1000;
                    data.put("timestamp", timestamp.toString());
                    data.put("start_created", "2021-01-10 16:59:58");
                    data.put("end_created", "2022-07-13 16:59:58");
//                    data.put("status", "WAIT_SELLER_SEND_GOODS");
//                    data.put("buyer_nick", "liweiyi");
//                    data.put("buyer_message_op", "ne");
//                    data.put("buyer_message", "不要搞错");
//                    data.put("seller_memo_op", "like");
//                    data.put("seller_memo", "7号钟");
//                    data.put("seller_flag", "2");
//                    data.put("refunding", "1");
//                     data.put("tid", "2494295280998769316");
//                     data.put("tid", "2615730483879565830,2634413835396034024,2654902836472034024,2655914185182034024");
//                     data.put("tid", "1645829892703565830");
//                     data.put("tid", "2470273740323565830");
                    data.put("page_no", "1");
                    data.put("page_size", "10");
                    data.put("sign", Utils.Sign(data, Config.AppSecret));
                    // 调用服务API
                    long startTime = System.currentTimeMillis(); //获取开始时间
                    String response = doHttpRequest(Config.TaoBaoOrderExtListUrl, data);
                    System.out.println(response);
                    long endTime = System.currentTimeMillis(); //获取结束时间
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } //run

        });


    }


    public static void parallelTesk(int threadNum, Runnable task) {

        String a ="{\n" +
                "    \"name\": \"fc_namespace_shard\",\n" +
                "    \"online\": true,\n" +
                "    \"read_only\": false,\n" +
                "    \"allowed_dbs\": {\n" +
                "        \"autosend\": true\n" +
                "    },\n" +
                "    \"slow_sql_time\": \"1000\",\n" +
                "    \"black_sql\": [\n" +
                "        \"\"\n" +
                "    ],\n" +
                "    \"allowed_ip\": null,\n" +
                "    \"slices\": [\n" +
                "        {\n" +
                "            \"name\": \"slice-0\",\n" +
                "            \"user_name\": \"root\",\n" +
                "            \"password\": \"admin2000\",\n" +
                "            \"master\": \"192.168.0.202:3306\",\n" +
                "            \"slaves\": [],\n" +
                "            \"statistic_slaves\": null,\n" +
                "            \"capacity\": 12,\n" +
                "            \"max_capacity\": 24,\n" +
                "            \"idle_timeout\": 60\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"slice-1\",\n" +
                "            \"user_name\": \"root\",\n" +
                "            \"password\": \"admin2000\",\n" +
                "            \"master\": \"192.168.0.213:3306\",\n" +
                "            \"slaves\": [],\n" +
                "            \"statistic_slaves\": [],\n" +
                "            \"capacity\": 12,\n" +
                "            \"max_capacity\": 24,\n" +
                "            \"idle_timeout\": 60\n" +
                "        }\n" +
                "    ],\n" +
                "    \"shard_rules\": [\n" +
                "        {\n" +
                "            \"db\": \"autosend\",\n" +
                "            \"table\": \"open_partner_fee\",\n" +
                "            \"type\": \"hash\",\n" +
                "            \"key\": \"partner_id\",\n" +
                "            \"locations\": [\n" +
                "                128,\n" +
                "                128\n" +
                "            ],\n" +
                "            \"slices\": [\n" +
                "                \"slice-0\",\n" +
                "                \"slice-1\"\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"db\": \"autosend\",\n" +
                "            \"table\": \"seller_trade\",\n" +
                "            \"type\": \"hash\",\n" +
                "            \"key\": \"seller_nick\",\n" +
                "            \"locations\": [\n" +
                "                512,\n" +
                "                512\n" +
                "            ],\n" +
                "            \"slices\": [\n" +
                "                \"slice-0\",\n" +
                "                \"slice-1\"\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"users\": [\n" +
                "        {\n" +
                "            \"user_name\": \"ksroot\",\n" +
                "            \"password\": \"123456\",\n" +
                "            \"namespace\": \"test_kingshard_hash\",\n" +
                "            \"rw_flag\": 2,\n" +
                "            \"rw_split\": 1,\n" +
                "            \"other_property\": 0\n" +
                "        }\n" +
                "    ],\n" +
                "    \"default_slice\": \"slice-0\",\n" +
                "    \"global_sequences\": null\n" +
                "}";

        // 1. 定义闭锁来拦截线程
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(threadNum);

        // 2. 创建指定数量的线程
        for (int i = 0; i < threadNum; i++) {
            Thread t = new Thread(() -> {
                try {
                    startGate.await();
                    try {
                        task.run();
                    } finally {
                        endGate.countDown();
                    }
                } catch (InterruptedException e) {

                }
            });

            t.start();
        }

        // 3. 线程统一放行，并记录时间！
        long start = System.nanoTime();

        startGate.countDown();
        try {
            endGate.await();
        } catch (InterruptedException e) {
        }

        long end = System.nanoTime();
        System.out.println("cost times :" + (end - start));
    }


    /**
     * 获取增量订单列表
     *
     * @throws Exception
     */
    @Test
    public void getIncrementTradeList() throws Exception {

        String tb_seller_nick = Config.TBSellerNick; //要查询的淘宝商家
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
//        data.put("hold_oaid", "0");  // 如果不知道此参数的意义，不要传入hold_oaid！！，
        // 订单修改的开始时间和结束时间不要超过24小时
        data.put("start_modified", "2022-08-11 00:35:00");
        data.put("end_modified", "2022-08-11 11:38:00");
//        data.put("status",  "WAIT_SELLER_SEND_GOODS"); // 待发货订单 WAIT_SELLER_SEND_GOODS
        data.put("page_no", "1");
        data.put("page_size", "20");
        data.put("use_has_next", "false");
//        data.put("buyer_open_uid", "AAGM90gYAAjcqSrFKsJi7Izh");
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        long startTime = System.currentTimeMillis(); //获取开始时间
        String result = doHttpRequest(Config.TaoBaoTradeIncrementListUrl, data);
        System.out.println(result);
        long endTime = System.currentTimeMillis(); //获取结束时间


    }


    /**
     * 获取订单详情
     *
     * @throws Exception
     */
    @Test
    public void getTradeDetail() throws Exception {

        String tb_seller_nick = Config.TBSellerNick; //要查询支付宝的淘宝商家
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
//        data.put("hold_oaid", "0");  // 如果不知道此参数的意义，不要传入hold_oaid！！，
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
//        data.put("tid", "2573604720966917733");//bb2556897626634036344
        data.put("tid", "1632191775688219192"); // bxg
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        String response = doHttpRequest(Config.TaoBaoOrderDetailUrl, data);
        TradeResponse tradeResponse = JSON.parseObject(response, TradeResponse.class);
        System.out.println(response);


    }


    @Test
    public void getTradeInvoiceAmount() throws Exception {

        String tb_seller_nick = Config.TBSellerNick; //淘宝商家
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        // 多个订单用英文逗号隔开
//        data.put("tids", "1887215151362838239,1887963157835558129");
        data.put("tids", "1636620963121666895");//chen le
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.TaoBaoOrderInvoiceAmountBatchUrl, data);
        System.out.println(resp);
    }


    /**
     * 批量获取订单详情
     *
     * @throws Exception
     */
    @Test
    public void getTradeDetailBatch() throws Exception {

        String tb_seller_nick = Config.TBSellerNick; //要查询支付宝的淘宝商家
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("hold_oaid", "1");  // 如果不知道此参数的意义，不要传入hold_oaid！！，
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
//        data.put("tid", "1702708671154565830");//bb
        data.put("tids", "2806732333002005505");// cl
//        data.put("tids", "2022670911868884661,2020329900642003618");


        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.TaoBaoOrderDetailBatchUrl, data);
        System.out.println(resp);
        TradeResponse tradeResponse = JSON.parseObject(resp, TradeResponse.class);
        if (tradeResponse == null) {
            System.out.println("json解析错误:" + resp);
        }

        if (tradeResponse.getCode() != 0) {
            String msg = String.format("code:%d, msg:%s, ", tradeResponse.getCode(), tradeResponse.getMessage());
            System.out.println(msg);
        } else {

            for (com.cheeli.models.taobao.trade.Data dataItem : tradeResponse.getData()) {
                String itemMsg = String.format("code:%d, msg:%s, tid:%s,payment:%s,buyer_nick:%s ", dataItem.getCode(), dataItem.getMessage(),
                        dataItem.getTid(), dataItem.getTrade().getPayment(), dataItem.getTrade().getBuyerNick());
                System.out.println(itemMsg);

            }

        }
    }


    /**
     * 获取所有的菜鸟标准电子面单模板
     *
     * @throws Exception
     */
    @Test
    public void getCloudPrintStdtemplates() throws Exception {

        String tb_seller_nick = Config.TBSellerNick; //要查询支付宝的淘宝商家
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.TaoBaoCaiNiaoCloudPrintStdtemplatesUrl, data);
        System.out.println(resp);

    }

    /**
     *  获取用户(自定义）使用的菜鸟电子面单模板信息
     * @throws Exception
     */
    @Test
    public void TaoBaoCaiNiaoCloudPrintMytemplates() throws Exception {

        String tb_seller_nick = Config.TBSellerNick; //要查询支付宝的淘宝商家
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        String result = doHttpRequest(Config.TaoBaoCaiNiaoCloudPrintMytemplatesUrl, data);
        System.out.println(result);

    }


    /**
     * 查询面单服务订购及面单使用情况
     *
     * @throws Exception
     */
    @Test
    public void getCaiNiaoWayBillSearch() throws Exception {

        String tb_seller_nick = Config.TBSellerNick; //要查询支付宝的淘宝商家
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("cp_code", "POSTB"); //  物流公司code  比如，YUNDA POSTB
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.TaoBaoCaiNiaoWayBillSearchUrl, data);
        System.out.println(resp);
    }

    /**
     *   取消电子面单
     * @throws Exception
     */
    @Test
    public void getCaiNiaoWayBillCancel() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("cp_code", "POSTB"); //  物流公司code  比如，YUNDA
        data.put("waybill_code", "9885425269561"); // 电子面单号
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoCaiNiaoWayBillCancelUrl ,data);

    }




    /**
     * 更新电子面单
     *
     * @throws Exception
     */
    @Test
    public void getCaiNiaoWayBillUpdate() throws Exception {

        String tb_seller_nick = Config.TBSellerNick;
//        String reqeustData = "{ " +
//                "    \"cp_code\": \"POSTB\"," +
//                "     \"logistics_services\": \"\"," +
//                "     \"object_id\": \"473184714813813\"," +
//                "      \"package_info\": { " +
//                "             \"items\": [{" +
//                "                 \"count\": 1," +
//                "                 \"name\": \"赠品：XXXX\"" +
//                "             }]," +
//                "             \"volume\": 1," +
//                "             \"weight\": 1" +
//                "         }," +
//                "      \"recipient\": {" +
//                "            \"address\": {" +
//                "                \"city\": \"广州市\"," +
//                "                \"detail\": \"凤凰城111-1号\"," +
//                "                \"district\": \"白云区\"," +
//                "                \"province\": \"广东省\"" +
//                "            }," +
//                "            \"mobile\": \"15378197524\"," +
//                "            \"name\": \"朱鹏\"," +
//                "            \"phone\": \"\"," +
//                "            \"caid\" : \"\"," +
//                "            \"oaid\" : \"\"" +
//                "        }," +
//                "     \"sender\": { " +
//                "         \"mobile\": \"18666073308\"," +
//                "         \"name\": \"罗生\"," +
//                "         \"phone\": \"\"" +
//                "     },  " +
//                "     \"template_url\": \"http://cloudprint.cainiao.com/template/standard/304351/5\"," +
//                "     \"waybill_code\": \"9883698891776\"" +
//                "     " +
//                " }";


                String reqeustData = "{ " +
                "    \"cp_code\": \"ZJS\"," +
                "     \"logistics_services\": \"\"," +
                "     \"object_id\": \"473184714813813\"," +
                "      \"package_info\": { " +
                "             \"items\": [{" +
                "                 \"count\": 1," +
                "                 \"name\": \"赠品：XXXX\"" +
                "             }]," +
                "             \"volume\": 1," +
                "             \"weight\": 1" +
                "         }," +
                "      \"recipient\": {" +
                "            \"address\": {" +
                "                \"city\": \"上海市\"," +
                "                \"detail\": \"\"," +
                "                \"district\": \"浦东新区\"," +
                "                \"province\": \"上海\"" +
                "            }," +
                "            \"mobile\": \"\"," +
                "            \"name\": \"\"," +
                "            \"phone\": \"\"," +
                "            \"caid\" : \"\" ," +
               "             \"oaid\" : \"1h12M4LqkhUkt1ibEk6VyZv2gRsECYSQayzH33CwBP1s3CmlCdx1CpVPJwkYcmvfj7u30St\"" +
                "        }," +
                "     \"sender\": { " +
                "         \"mobile\": \"18666073309\"," +
                "         \"name\": \"\"," +
                "         \"phone\": \"\"" +
                "     },  " +
                "     \"template_url\": \"http://cloudprint.cainiao.com/template/standard/304351/5\"," +
                "     \"waybill_code\": \"ZJS000301211805\"" +
                "     " +
                " }";
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("request_data", reqeustData);
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        String resp = doHttpRequest(Config.TaoBaoCaiNiaoWayBillUpateUrl, data);


    }


  @Test
    public void caiNiaoWayBillGetByWBCodeUrl() throws Exception {


        String tb_seller_nick = Config.TBSellerNick; //要查询支付宝的淘宝商家
        String reqeustData = "[" +
                "{" +
                "    \"cp_code\": \"POSTB\"," +
                "     \"waybill_code\": \"9883698891776\" ," +
                "     \"object_id\": \"112222\"" +
                "     " +
                " }," +
                "{" +
                "    \"cp_code\": \"POSTB\"," +
                "     \"waybill_code\": \"9883698891776\" ," +
                "     \"object_id\": \"112223\"" +
                "     " +
                " }," +
                "{" +
                "    \"cp_code\": \"POSTB\"," +
                "     \"waybill_code\": \"9883698891776\" ," +
                "     \"object_id\": \"112224\"" +
                "     " +
                " }," +
                "{" +
                "    \"cp_code\": \"POSTB\"," +
                "     \"waybill_code\": \"9883698891776\" ," +
                "     \"object_id\": \"112225\"" +
                "     " +
                " }" +
                "]";
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("request_data", reqeustData);
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
//        doHttpRequest(Config.TaoBaoCaiNiaoWayBillBatchGetUrl ,data);
        String resp = doHttpRequest(Config.TaoBaoCaiNiaoWayBillGetByWBCodeUrl, data);


    }





    /**
     *    电子面单云打印接口 -批量
     * @throws Exception
     */
    @Test
    public void getCaiNiaoWayBillGetBatch() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
//        String reqeustData ="{\"cp_code\":\"STO\",\"dms_sorting\":false, \"resource_code\":\"\",\"sender\":{\"address\":{\"city\":\"广州市\",\"detail\":\"石湖云仓\",\"district\":\"白云区\",\"province\":\"广东省\"},\"mobile\":\"17800002366\",\"name\":\"朱迎春\",\"phone\":\"0571232222\"},\"store_code\":\"\",\"trade_order_info_dtos\":[{\"object_id\":\"1\",\"order_info\":{\"order_channels_type\":\"TB\",\"trade_order_list\":[\"90020206078841641229\"]},\"package_info\":{\"id\":\"1\",\"items\":[{\"count\":50,\"name\":\"名片\"}],\"volume\":1,\"weight\":1},\"recipient\":{\"address\":{\"city\":\"杭州市\",\"detail\":\"横村镇桐千路8118号华艺大酒店单身公寓12楼\",\"district\":\"桐庐县\",\"province\":\"浙江省\",\"town\":\"横村镇\"},\"mobile\":\"13357035578\",\"name\":\"洪勇军修改-智能发货\",\"phone\":\"057123222233\"},\"template_url\":\"http:\\/\\/cloudprint.cainiao.com\\/template\\/standard\\/256771\\/9\",\"user_id\":0}]}";
//        String reqeustData ="{\n" +
//                "    \"cp_code\": \"STO\",\n" +
//                "    \"dms_sorting\": false,\n" +
//                "    \"resource_code\": \"\",\n" +
//                "    \"sender\": {\n" +
//                "        \"address\": {\n" +
//                "            \"city\": \"广州市\",\n" +
//                "            \"detail\": \"石湖云仓\",\n" +
//                "            \"district\": \"白云区\",\n" +
//                "            \"province\": \"广东省\"\n" +
//                "        },\n" +
//                "        \"mobile\": \"17800002366\",\n" +
//                "        \"name\": \"朱迎春\",\n" +
//                "        \"phone\": \"\"\n" +
//                "    },\n" +
//                "    \"store_code\": \"\",\n" +
//                "    \"trade_order_info_dtos\": [\n" +
//                "        {\n" +
//                "            \"object_id\": \"7329529\",\n" +
//                "            \"order_info\": {\n" +
//                "                \"order_channels_type\": \"TB\",\n" +
//                "                \"trade_order_list\": [\n" +
//                "                    \"7329529\"\n" +
//                "                ]\n" +
//                "            },\n" +
//                "            \"package_info\": {\n" +
//                "                \"id\": \"225\",\n" +
//                "                \"items\": [\n" +
//                "                    {\n" +
//                "                        \"count\": 1,\n" +
//                "                        \"name\": \"赠品：自提件\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"volume\": 1,\n" +
//                "                \"weight\": 1\n" +
//                "            },\n" +
//                "            \"recipient\": {\n" +
//                "                \"address\": {\n" +
//                "                    \"city\": \"吕梁市\",\n" +
//                "                    \"detail\": \"凤山街道北川河东路善水新苑二单元1902户\",\n" +
//                "                    \"district\": \"离石区\",\n" +
//                "                    \"province\": \"山西省\"\n" +
//                "                },\n" +
//                "                \"mobile\": \"15869015241\",\n" +
//                "                \"name\": \"吴家\",\n" +
//                "                \"phone\": \"\"\n" +
//                "            },\n" +
//                "            \"template_url\": \"http:\\/\\/cloudprint.cainiao.com\\/template\\/standard\\/256771\\/9\",\n" +
//                "            \"user_id\": 271\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"object_id\": \"7329530\",\n" +
//                "            \"order_info\": {\n" +
//                "                \"order_channels_type\": \"TB\",\n" +
//                "                \"trade_order_list\": [\n" +
//                "                    \"7329530\"\n" +
//                "                ]\n" +
//                "            },\n" +
//                "            \"package_info\": {\n" +
//                "                \"id\": \"225\",\n" +
//                "                \"items\": [\n" +
//                "                    {\n" +
//                "                        \"count\": 1,\n" +
//                "                        \"name\": \"赠品：自提件\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"volume\": 1,\n" +
//                "                \"weight\": 1\n" +
//                "            },\n" +
//                "            \"recipient\": {\n" +
//                "                \"address\": {\n" +
//                "                    \"city\": \"金华市\",\n" +
//                "                    \"detail\": \"西城街道杨官路118号\",\n" +
//                "                    \"district\": \"永康市\",\n" +
//                "                    \"province\": \"浙江省\"\n" +
//                "                },\n" +
//                "                \"mobile\": \"15869015241\",\n" +
//                "                \"name\": \"吴家\",\n" +
//                "                \"phone\": \"\"\n" +
//                "            },\n" +
//                "            \"template_url\": \"http:\\\\/\\\\/cloudprint.cainiao.com\\\\/template\\\\/standard\\\\/290659\\\\/42\",\n" +
//                "            \"user_id\": 271\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"object_id\": \"7329531\",\n" +
//                "            \"order_info\": {\n" +
//                "                \"order_channels_type\": \"TB\",\n" +
//                "                \"trade_order_list\": [\n" +
//                "                    \"7329531\"\n" +
//                "                ]\n" +
//                "            },\n" +
//                "            \"package_info\": {\n" +
//                "                \"id\": \"225\",\n" +
//                "                \"items\": [\n" +
//                "                    {\n" +
//                "                        \"count\": 1,\n" +
//                "                        \"name\": \"赠品：自提件\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"volume\": 1,\n" +
//                "                \"weight\": 1\n" +
//                "            },\n" +
//                "            \"recipient\": {\n" +
//                "                \"address\": {\n" +
//                "                    \"city\": \"营口市\",\n" +
//                "                    \"detail\": \"石桥街道二零二国道佳美馨居2号楼\",\n" +
//                "                    \"district\": \"大石桥市\",\n" +
//                "                    \"province\": \"辽宁省\"\n" +
//                "                },\n" +
//                "                \"mobile\": \"15869015241\",\n" +
//                "                \"name\": \"吴家\",\n" +
//                "                \"phone\": \"\"\n" +
//                "            },\n" +
//                "            \"template_url\": \"http:\\\\/\\\\/cloudprint.cainiao.com\\\\/template\\\\/standard\\\\/290659\\\\/42\",\n" +
//                "            \"user_id\": 271\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"object_id\": \"7329532\",\n" +
//                "            \"order_info\": {\n" +
//                "                \"order_channels_type\": \"TB\",\n" +
//                "                \"trade_order_list\": [\n" +
//                "                    \"7329532\"\n" +
//                "                ]\n" +
//                "            },\n" +
//                "            \"package_info\": {\n" +
//                "                \"id\": \"225\",\n" +
//                "                \"items\": [\n" +
//                "                    {\n" +
//                "                        \"count\": 1,\n" +
//                "                        \"name\": \"赠品：自提件\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"volume\": 1,\n" +
//                "                \"weight\": 1\n" +
//                "            },\n" +
//                "            \"recipient\": {\n" +
//                "                \"address\": {\n" +
//                "                    \"city\": \"聊城市\",\n" +
//                "                    \"detail\": \"青年路街道嘉和苑小区3号楼5单元602\",\n" +
//                "                    \"district\": \"临清市\",\n" +
//                "                    \"province\": \"山东省\"\n" +
//                "                },\n" +
//                "                \"mobile\": \"15869015241\",\n" +
//                "                \"name\": \"吴家\",\n" +
//                "                \"phone\": \"\"\n" +
//                "            },\n" +
//                "            \"template_url\": \"http:\\\\/\\\\/cloudprint.cainiao.com\\\\/template\\\\/standard\\\\/290659\\\\/42\",\n" +
//                "            \"user_id\": 271\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"object_id\": \"7329533\",\n" +
//                "            \"order_info\": {\n" +
//                "                \"order_channels_type\": \"TB\",\n" +
//                "                \"trade_order_list\": [\n" +
//                "                    \"7329533\"\n" +
//                "                ]\n" +
//                "            },\n" +
//                "            \"package_info\": {\n" +
//                "                \"id\": \"225\",\n" +
//                "                \"items\": [\n" +
//                "                    {\n" +
//                "                        \"count\": 1,\n" +
//                "                        \"name\": \"赠品：自提件\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"volume\": 1,\n" +
//                "                \"weight\": 1\n" +
//                "            },\n" +
//                "            \"recipient\": {\n" +
//                "                \"address\": {\n" +
//                "                    \"city\": \"沧州市\",\n" +
//                "                    \"detail\": \"河城街镇河城街中心校\",\n" +
//                "                    \"district\": \"献县\",\n" +
//                "                    \"province\": \"河北省\"\n" +
//                "                },\n" +
//                "                \"mobile\": \"15869015241\",\n" +
//                "                \"name\": \"吴家\",\n" +
//                "                \"phone\": \"\"\n" +
//                "            },\n" +
//                "            \"template_url\": \"http:\\\\/\\\\/cloudprint.cainiao.com\\\\/template\\\\/standard\\\\/290659\\\\/42\",\n" +
//                "            \"user_id\": 271\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";


//        String reqeustData = "{\n" +
//                "\n" +
//                "    \"cp_code\": \"POSTB\",\n" +
//                "    \"need_encrypt\": true,\n" +
//                "    \"dms_sorting\": false,\n" +
//                "    \"resource_code\": \"\",\n" +
//                "    \"sender\": {\n" +
//                "        \"address\": {\n" +
//                "            \"city\": \"广州市\",\n" +
//                "            \"detail\": \"人和镇秀盛路128号6栋2楼\",\n" +
//                "            \"district\": \"白云区\",\n" +
//                "            \"province\": \"广东省\"\n" +
//                "        },\n" +
//                "        \"mobile\": \"18666073308\",\n" +
//                "        \"name\": \"罗生\",\n" +
//                "        \"phone\": \"\"\n" +
//                "    },\n" +
//                "    \"store_code\": \"\",\n" +
//                "    \"trade_order_info_dtos\": [{\n" +
//                "        \"object_id\": \"59\",\n" +
//                "        \"order_info\": {\n" +
//                "            \"order_channels_type\": \"TB\",\n" +
//                "            \"trade_order_list\": [\"1962345350175995558\"]\n" +
//                "        },\n" +
//                "        \"package_info\": {\n" +
//                "            \"id\": \"20210719_59_11\",\n" +
//                "            \"items\": [{\n" +
//                "                \"count\": 1,\n" +
//                "                \"name\": \"赠品：香包\"\n" +
//                "            }],\n" +
//                "            \"volume\": 1,\n" +
//                "            \"weight\": 1\n" +
//                "        },\n" +
//                "        \"recipient\": {\n" +
//                "            \"address\": {\n" +
//                "                \"city\": \"广州市\",\n" +
//                "                \"detail\": \"凤凰城111-1号\",\n" +
//                "                \"district\": \"白云区\",\n" +
//                "                \"province\": \"广东省\"\n" +
//                "            },\n" +
//                "            \"mobile\": \"15378197524\",\n" +
//                "            \"name\": \"朱鹏\",\n" +
//                "            \"phone\": \"\"\n" +
//                "        },\n" +
//                "        \"template_url\": \"http://cloudprint.cainiao.com/template/standard/304351/5\",\n" +
//                "        \"user_id\": 3\n" +
//                "    }]\n" +
//                "}";



        // 宅急送一联单
//        String reqeustData = "{\n" +
//                "\n" +
//                "    \"cp_code\": \"ZJS\",\n" +
//                "    \"need_encrypt\": true,\n" +
//                "    \"dms_sorting\": false,\n" +
//                "    \"resource_code\": \"\",\n" +
//                "    \"sender\": {\n" +
//                "        \"address\": {\n" +
//                "            \"city\": \"上海市\",\n" +
//                "            \"detail\": \"商城路738号1205\",\n" +
//                "            \"district\": \"浦东新区\",\n" +
//                "            \"province\": \"上海\"\n" +
//                "        },\n" +
//                "        \"mobile\": \"18666073308\",\n" +
//                "        \"name\": \"罗生\",\n" +
//                "        \"phone\": \"\"\n" +
//                "    },\n" +
//                "    \"store_code\": \"\",\n" +
//                "    \"trade_order_info_dtos\": [{\n" +
//                "        \"object_id\": \"59\",\n" +
//                "        \"order_info\": {\n" +
//                "            \"order_channels_type\": \"TB\",\n" +
//                "            \"trade_order_list\": [\"2292723541822820234\"]\n" +
//                "        },\n" +
//                "        \"package_info\": {\n" +
//                "            \"id\": \"20210719_59_11\",\n" +
//                "            \"items\": [{\n" +
//                "                \"count\": 1,\n" +
//                "                \"name\": \"赠品：袜子\"\n" +
//                "            }],\n" +
//                "            \"volume\": 1,\n" +
//                "            \"weight\": 1\n" +
//                "        },\n" +
//                "        \"recipient\": {\n" +
//                "            \"address\": {\n" +
//                "                \"city\": \"三门峡市\",\n" +
//                "                \"detail\": \"城*镇万洋**小**号楼**东户\",\n" +
//                "                \"district\": \"渑池县\",\n" +
//                "                \"province\": \"河南省\"\n" +
//                "            },\n" +
//                "             \"mobile\": \"*******3197\",\n" +
//                "            \"name\": \"杨**\",\n" +
//                "            \"phone\": \"\",\n" +
//                "            \"oaid\": \"16xHhMmDUM4GGo4X8ialTzqkkTx1wzvZ0tdhNp7CvGyj9dlVLiaHXzhZXXZxQsFRNux12mlw\", " +
//                "            \"tid\": \"2292723541822820234\"  " +
//                "        },\n" +
//                "        \"template_url\": \"http://cloudprint.cainiao.com/template/standard/309188//5\",\n" +
//                "        \"user_id\": 3\n" +
//                "    }]\n" +
//                "}";


        // 中通一联单
        String reqeustData = "{\n" +
                "\n" +
                "    \"cp_code\": \"YUNDA\",\n" +
                "    \"need_encrypt\": true,\n" +
                "    \"dms_sorting\": false,\n" +
                "    \"resource_code\": \"\",\n" +
                "    \"sender\": {\n" +
                "        \"address\": {\n" +
                "            \"city\": \"东莞市\",\n" +
                "            \"detail\": \"沙田镇韵达分拨中心\",\n" +
                "            \"district\": \"\",\n" +
                "            \"province\": \"广东省\"\n" +
                "        },\n" +
                "        \"mobile\": \"18234052519\",\n" +
                "        \"name\": \"柔先生\",\n" +
                "        \"phone\": \"\"\n" +
                "    },\n" +
                "    \"store_code\": \"\",\n" +
                "    \"trade_order_info_dtos\": [{\n" +
                "        \"object_id\": \"59\",\n" +
                "        \"order_info\": {\n" +
                "            \"order_channels_type\": \"TB\",\n" +
                "            \"trade_order_list\": [\"1509699326532298798\"]\n" +
                "        },\n" +
                "        \"package_info\": {\n" +
                "            \"id\": \"20210719_59_11\",\n" +
                "            \"items\": [{\n" +
                "                \"count\": 1,\n" +
                "                \"name\": \"赠品：袜子\"\n" +
                "            }],\n" +
                "            \"volume\": 1,\n" +
                "            \"weight\": 1\n" +
                "        },\n" +
                "        \"recipient\": {\n" +
                "            \"address\": {\n" +
                "                \"city\": \"阜阳市\",\n" +
                "                \"detail\": \"安徽省阜阳市太和县城*镇建**路**号菜鸟驿站\",\n" +
                "                \"district\": \"太和县\",\n" +
                "                \"province\": \"安徽省\"\n" +
                "            },\n" +
                "             \"mobile\": \"*******5558\",\n" +
                "            \"name\": \"陈**\",\n" +
                "            \"phone\": \"\",\n" +
                "            \"oaid\": \"1iaXSwXQ56lMZ08dKpUmG43vB1Gq4Z7KbvE4H4IiaqkGIjXwDPtx1glvmn0oALoBw4zUicwo3\", " +
                "            \"tid\": \"1509699326532298798\"  " +
                "        },\n" +
                "        \"template_url\": \"http://cloudprint.cainiao.com/template/standard/288658/38\",\n" +
                "        \"user_id\": 3\n" +
                "    }]\n" +
                "}";



//        String reqeustData ="{\"cp_code\":\"ZTO\",\"dms_sorting\":false,\"three_pl_timing\":false,\"need_encrypt\":true,\"resource_code\":\"\",\"sender\":{\"address\":{\"city\":\"无锡市\",\"detail\":\"华友一路50号\",\"district\":\"新吴区\",\"province\":\"江苏省\"},\"mobile\":\"13169711001\",\"name\":\"基佐专营店\",\"phone\":\"\"},\"store_code\":\"\",\"trade_order_info_dtos\":[{\"object_id\":\"17446826\",\"order_info\":{\"order_channels_type\":\"TB\",\"trade_order_list\":[\"17446826\"]},\"package_info\":{\"id\":\"2081\",\"items\":[{\"count\":1,\"name\":\"赠品：贺卡\"}],\"volume\":1,\"weight\":1},\"recipient\":{\"address\":{\"city\":\"杭州市\",\"detail\":\"文一西路969号\",\"district\":\"余杭区\",\"province\":\"浙江省\"},\"mobile\":\"13912345678\",\"name\":\"张三丰\",\"phone\":\"\",\"oaid\":\"\",\"tid\":\"\"},\"template_url\":\"http:\\/\\/cloudprint.cainiao.com\\/template\\/standard\\/300336\\/38\",\"user_id\":25421}]}";
        //业务参数
//       String reqeustData = "{\"trade_order_info_dtos\":[{\"template_url\":\"http://cloudprint.cainiao.com/template/standard/290659/44\",\"user_id\":0,\"recipient\":{\"address\":{\"province\":\"山东省\",\"city\":\"菏泽市\",\"district\":\"成武县\",\"detail\":\"成*县**镇孙寺**楼村\"},\"phone\":\"*******7205\",\"name\":\"宋**\",\"mobile\":\"*******7205\",\"tid\":\"2122002217029646973\",\"oaid\":\"1ibL9eFmJBPGPj3YayfT2IRsd4spAYiclZcARVsVrUqk04B8iasx1qmlkjn5cibwaSesIRqISN\"},\"out_order_no\":\"21092015344577390098990006110001\",\"isAutoDeliver\":1,\"object_id\":\"23b3d348d5fca9bbf5bf237ed66f151d\",\"package_info\":{\"volume\":1,\"total_packages_count\":1,\"goods_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\",\"weight\":1,\"id\":\"123a\",\"items\":[{\"name\":\"随机礼品\",\"count\":1}],\"packaging_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\"},\"order_info\":{\"order_channels_type\":\"TB\",\"trade_order_list\":[\"2122002217029646973\"]}},{\"template_url\":\"http://cloudprint.cainiao.com/template/standard/290659/44\",\"user_id\":0,\"recipient\":{\"address\":{\"province\":\"浙江省\",\"city\":\"嘉兴市\",\"district\":\"海宁市\",\"detail\":\"硖*街道塘**路**号\"},\"phone\":\"*******0145\",\"name\":\"刘**\",\"mobile\":\"*******0145\",\"tid\":\"2122466258892300655\",\"oaid\":\"1XVyeecJ2HT6jKKOgFrpTsF96Yiaq2IGeYt46jqktmCIviakApQBicwgLibP3DyG3n1x1t5AXP\"},\"out_order_no\":\"21092015344577390098990006110002\",\"isAutoDeliver\":1,\"object_id\":\"89f44e8a47adef934f7c0ed09a9129db\",\"package_info\":{\"volume\":1,\"total_packages_count\":1,\"goods_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\",\"weight\":1,\"id\":\"123a\",\"items\":[{\"name\":\"随机礼品\",\"count\":1}],\"packaging_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\"},\"order_info\":{\"order_channels_type\":\"TB\",\"trade_order_list\":[\"2122466258892300655\"]}},{\"template_url\":\"http://cloudprint.cainiao.com/template/standard/290659/44\",\"user_id\":0,\"recipient\":{\"address\":{\"province\":\"黑龙江省\",\"city\":\"绥化市\",\"district\":\"绥棱县\",\"detail\":\"绥*镇柏林雅苑***-*-*** （不许代收，送货上门）\"},\"phone\":\"*******7168\",\"name\":\"岳**\",\"mobile\":\"*******7168\",\"tid\":\"2123178375702457753\",\"oaid\":\"1KujckE9HicaibbvjBfWLbNA4hLxopsHSL62yYaaTBjvmK4TZ8qkJJ2kW2Bniaex1NtRP1rnQ\"},\"out_order_no\":\"21092015344577390098990006110003\",\"isAutoDeliver\":1,\"object_id\":\"afa67e47eb623815199fa9f7de83983e\",\"package_info\":{\"volume\":1,\"total_packages_count\":1,\"goods_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\",\"weight\":1,\"id\":\"123a\",\"items\":[{\"name\":\"随机礼品\",\"count\":1}],\"packaging_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\"},\"order_info\":{\"order_channels_type\":\"TB\",\"trade_order_list\":[\"2123178375702457753\"]}},{\"template_url\":\"http://cloudprint.cainiao.com/template/standard/290659/44\",\"user_id\":0,\"recipient\":{\"address\":{\"province\":\"广东省\",\"city\":\"深圳市\",\"district\":\"南山区\",\"detail\":\"南*街道**城**园*座****\"},\"phone\":\"*******1781\",\"name\":\"丁**\",\"mobile\":\"*******1781\",\"tid\":\"2122469498842254349\",\"oaid\":\"1byRHTTTsxrrVKvv4lgmF8sCOR9oVyM4jMf57qseySqk0uaz7ZqM13WDSUbw4YMrbcix1bz\"},\"out_order_no\":\"21092015344577390098990006110004\",\"isAutoDeliver\":1,\"object_id\":\"5b854bf58610a40d5dfc159c93e6a1cf\",\"package_info\":{\"volume\":1,\"total_packages_count\":1,\"goods_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\",\"weight\":1,\"id\":\"123a\",\"items\":[{\"name\":\"随机礼品\",\"count\":1}],\"packaging_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\"},\"order_info\":{\"order_channels_type\":\"TB\",\"trade_order_list\":[\"2122469498842254349\"]}},{\"template_url\":\"http://cloudprint.cainiao.com/template/standard/290659/44\",\"user_id\":0,\"recipient\":{\"address\":{\"province\":\"吉林省\",\"city\":\"长春市\",\"district\":\"朝阳区\",\"detail\":\"重*街道**街**号烟草大**楼中银国际证券\"},\"phone\":\"*******3066\",\"name\":\"赵**\",\"mobile\":\"*******3066\",\"tid\":\"2122466942968072722\",\"oaid\":\"1YqibKRsBibU9sjFUspKzr1QA8eCHA5xkpYTC84CaQ1fvfBtqqkV45D55EFx1efKXiatomROJ\"},\"out_order_no\":\"21092015344577390098990006110005\",\"isAutoDeliver\":1,\"object_id\":\"023928de97707182eb67af791ba0c120\",\"package_info\":{\"volume\":1,\"total_packages_count\":1,\"goods_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\",\"weight\":1,\"id\":\"123a\",\"items\":[{\"name\":\"随机礼品\",\"count\":1}],\"packaging_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\"},\"order_info\":{\"order_channels_type\":\"TB\",\"trade_order_list\":[\"2122466942968072722\"]}},{\"template_url\":\"http://cloudprint.cainiao.com/template/standard/290659/44\",\"user_id\":0,\"recipient\":{\"address\":{\"province\":\"上海\",\"city\":\"上海市\",\"district\":\"闵行区\",\"detail\":\"古*街道古龙**街\"},\"phone\":\"*******1128\",\"name\":\"江**\",\"mobile\":\"*******1128\",\"tid\":\"2123185539517176172\",\"oaid\":\"111yM4qk3b82fm3UF2wck8QeZZJvoMgWCFfUoK6Ojm3FQy35Tx3kmMib4ictRD5Mia7x1slBx\"},\"out_order_no\":\"21092015344577390098990006110006\",\"isAutoDeliver\":1,\"object_id\":\"eaeb770eea98e1b5ce2fa43a80bbcaab\",\"package_info\":{\"volume\":1,\"total_packages_count\":1,\"goods_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\",\"weight\":1,\"id\":\"123a\",\"items\":[{\"name\":\"随机礼品\",\"count\":1}],\"packaging_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\"},\"order_info\":{\"order_channels_type\":\"TB\",\"trade_order_list\":[\"2123185539517176172\"]}},{\"template_url\":\"http://cloudprint.cainiao.com/template/standard/290659/44\",\"user_id\":0,\"recipient\":{\"address\":{\"province\":\"广东省\",\"city\":\"佛山市\",\"district\":\"南海区\",\"detail\":\"九*镇下北鱼珠**市场***仓鑫岳木业\"},\"phone\":\"*******9168\",\"name\":\"张**\",\"mobile\":\"*******9168\",\"tid\":\"2120757048359883443\",\"oaid\":\"1YmBXfiandjbw9y45Oy5GjmlNtq0Xz3znrpQq8dqkHiaYIic1N0peibx1S3gglibGJiceJkn46ia0\"},\"out_order_no\":\"21092015344577390098990006110007\",\"isAutoDeliver\":1,\"object_id\":\"8115f571040fda342489b846a05ff18d\",\"package_info\":{\"volume\":1,\"total_packages_count\":1,\"goods_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\",\"weight\":1,\"id\":\"123a\",\"items\":[{\"name\":\"随机礼品\",\"count\":1}],\"packaging_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\"},\"order_info\":{\"order_channels_type\":\"TB\",\"trade_order_list\":[\"2120757048359883443\"]}},{\"template_url\":\"http://cloudprint.cainiao.com/template/standard/290659/44\",\"user_id\":0,\"recipient\":{\"address\":{\"province\":\"辽宁省\",\"city\":\"沈阳市\",\"district\":\"沈河区\",\"detail\":\"朱**街道药**路***号\"},\"phone\":\"*******0426\",\"name\":\"赵**\",\"mobile\":\"*******0426\",\"tid\":\"2122022269084848256\",\"oaid\":\"1Jd2sI4KUbibAlp91SfaomnOqkpfXia0pShSYC58kEnd6Lx1thzq1q6p0XuibA2asUlSo4OqP\"},\"out_order_no\":\"21092015344577390098990006110008\",\"isAutoDeliver\":1,\"object_id\":\"5597a90b90ce0c17ce0c0c4a40cfe44c\",\"package_info\":{\"volume\":1,\"total_packages_count\":1,\"goods_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\",\"weight\":1,\"id\":\"123a\",\"items\":[{\"name\":\"随机礼品\",\"count\":1}],\"packaging_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\"},\"order_info\":{\"order_channels_type\":\"TB\",\"trade_order_list\":[\"2122022269084848256\"]}},{\"template_url\":\"http://cloudprint.cainiao.com/template/standard/290659/44\",\"user_id\":0,\"recipient\":{\"address\":{\"province\":\"四川省\",\"city\":\"广安市\",\"district\":\"邻水县\",\"detail\":\"柑*镇街上清水河**单元***\"},\"phone\":\"*******3541\",\"name\":\"甘**\",\"mobile\":\"*******3541\",\"tid\":\"2120755572745568144\",\"oaid\":\"1T2SfR4Kya1ZLrjuSVlic9C3kjVOdLCZdqkmx1xlYSIuetUjfRbiag12bNhSGf5vp6ozHdGa\"},\"out_order_no\":\"21092015344577390098990006110009\",\"isAutoDeliver\":1,\"object_id\":\"790d56e651a2742dc5866c187e5f8ab1\",\"package_info\":{\"volume\":1,\"total_packages_count\":1,\"goods_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\",\"weight\":1,\"id\":\"123a\",\"items\":[{\"name\":\"随机礼品\",\"count\":1}],\"packaging_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\"},\"order_info\":{\"order_channels_type\":\"TB\",\"trade_order_list\":[\"2120755572745568144\"]}},{\"template_url\":\"http://cloudprint.cainiao.com/template/standard/290659/44\",\"user_id\":0,\"recipient\":{\"address\":{\"province\":\"广东省\",\"city\":\"广州市\",\"district\":\"荔湾区\",\"detail\":\"海*街**路**号**园龙**路店\"},\"phone\":\"*******9363\",\"name\":\"顺**\",\"mobile\":\"*******9363\",\"tid\":\"2120771052415206827\",\"oaid\":\"1OMfuQfbgGm3ejWTiaeOJULCdN4hmqkRxbL7XehqibcYKHsFx1hibDUCk5uRQC1xCYvxpCo1K\"},\"out_order_no\":\"21092015344577390098990006110010\",\"isAutoDeliver\":1,\"object_id\":\"ab895bf30f21391b873f61e0324c6466\",\"package_info\":{\"volume\":1,\"total_packages_count\":1,\"goods_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\",\"weight\":1,\"id\":\"123a\",\"items\":[{\"name\":\"随机礼品\",\"count\":1}],\"packaging_description\":\"亲，感谢您对小店的支持，这是小店随机赠送您的礼品，您的宝贝也已经加急发出，运单号或者物流可以直接进店咨询客服帮您查询哦~\"},\"order_info\":{\"order_channels_type\":\"TB\",\"trade_order_list\":[\"2120771052415206827\"]}}],\"need_encrypt\":true,\"sender\":{\"address\":{\"province\":\"广东省\",\"city\":\"广州市\",\"district\":\"海珠区\",\"detail\":\"宝岗大道3号\"},\"mobile\":\"18028856751\",\"name\":\"阿笛\"},\"cp_code\":\"YTO\"}";


       // sf demo
//        String reqeustData = "{\n" +
//                "    \"cp_code\": \"SF\",\n" +
//                "    \"brand_code\": \"FW\",\n" +
//                "    \"dms_sorting\": false,\n" +
//                "    \"three_pl_timing \": false,\n" +
//                "    \"need_encrypt\": true,\n" +
//                "    \"resource_code \": \"\",\n" +
//                "    \"sender\": {\n" +
//                "        \"mobile\": \"13907896832\",\n" +
//                "        \"name\": \"\\u738b\\u53bb\\u9f99\",\n" +
//                "        \"phone\": \"\",\n" +
//                "        \"address\": {\n" +
//                "            \"city\": \"泉州市\",\n" +
//                "              \"detail\": \"世茂摩天城1期\",\n" +
//                "              \"district\": \"石狮市\",\n" +
//                "              \"province\": \"福建省\"\n" +
//                "        }\n" +
//                "    },\n" +
//                "    \"store_code \": \"\",\n" +
//                "    \"trade_order_info_dtos\": [{\n" +
//                "        \"object_id\": \"500000033\",\n" +
//                "        \"order_info\": {\n" +
//                "            \"order_channels_type\": \"TB\",\n" +
//                "            \"trade_order_list\": [\"2203578759552580083\"]\n" +
//                "        },\n" +
//                "        \"package_info\": {\n" +
//                "            \"id\": \"10\",\n" +
//                "            \"items\": [{\n" +
//                "                \"count\": 1,\n" +
//                "                \"name\": \"A4\\\\u6253\\\\u5370\\\\u7eb8\"\n" +
//                "            }],\n" +
//                "            \"goods_description\": \"文件\",\n" +
//                 "            \"volume\": 1,\n" +
//                "            \"weight\": 1\n" +
//                "        },\n" +
//                "        \"recipient\": {\n" +
//                "            \"address\": {\n" +
//                "                \"city\": \"\\u6b66\\u6c49\\u5e02\",\n" +
//                "                \"detail\": \"\\u6768*\\u8857*\\u9053\\u4f59**\\u8def\\u6021**\\u5c0f\\u533a\",\n" +
//                "                \"district\": \"\\u6b66\\u660c\\u533a\",\n" +
//                "                \"province\": \"\\u6e56\\u5317\\u7701\",\n" +
//                "                \"town\": \"\"\n" +
//                "            },\n" +
//                "            \"mobile\": \"55555559993\",\n" +
//                "            \"name\": \"@**\",\n" +
//                "            \"phone\": \"\",\n" +
//                "            \"oaid\": \"1GzEV8mUSicnbibo1i4bbqk4gg7tngN2HGyED9JibmAvc3tV6q6PDMrhMQOWnfYX1Ibix1c3xZA\",\n" +
//                "            \"tid\": \"2203578759552580082\"\n" +
//                "        },\n" +
//                "        \"template_url\": \"http://cloudprint.cainiao.com/template/standard/490404/8\",\n" +
//                "        \"user_id\": 0\n" +
//                "    }]\n" +
//                "}";

     //   String reqeustData = "{\"cp_code\":\"SF\",\"brand_code\":\"FW\",\"need_encrypt\":true,\"dms_sorting\":false,\"sender\":{\"address\":{\"city\":\"广州市\",\"detail\":\"钟落潭镇白沙市场南街17号\",\"district\":\"白云区\",\"province\":\"广东省\"},\"mobile\":\"13268269353\",\"name\":\"吴先生\"},\"trade_order_info_dtos\":[{\"object_id\":\"6188a9a78ddacdb162e24e9c\",\"order_info\":{\"order_channels_type\":\"TB\",\"trade_order_list\":[\"10867267\"]},\"package_info\":{\"volume\":0,\"weight\":0,\"items\":[{\"count\":1,\"name\":\"香包\"}]},\"recipient\":{\"address\":{\"city\":\"深圳市\",\"detail\":\"富裕路1号\",\"district\":\"龙华区\",\"province\":\"广东省\"},\"mobile\":\"15768474117\",\"name\":\"张三\"},\"template_url\":\"http://cloudprint.cainiao.com/template/standard/490207/7\",\"user_id\":0}]}";

        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
//        data.put("hold_oaid", "1");


           reqeustData = "{\n" +
                 "    \"cp_code\": \"YUNDA\", \n" +
                 "    \"dms_sorting\": false, \n" +
                 "    \"three_pl_timing\": false, \n" +
                 "    \"need_encrypt\": true, \n" +
                 "    \"resource_code\": \"\", \n" +
                 "    \"sender\": {\n" +
                 "        \"address\": {\n" +
                 "            \"province\": \"广东省\", \n" +
                 "            \"city\": \"广州市\", \n" +
                 "            \"district\": \"白云区\", \n" +
                 "            \"detail\": \"新科下村君毅创意园\"\n" +
                 "        }, \n" +
                 "        \"mobile\": \"18028856751\", \n" +
                 "        \"name\": \"陈先生\", \n" +
                 "        \"phone\": \"\"\n" +
                 "    }, \n" +
                 "    \"store_code\": \"\", \n" +
                 "    \"trade_order_info_dtos\": [\n" +
                 "        {\n" +
                 "            \"object_id\": \"22042406234839701246\", \n" +
                 "            \"package_info\": {\n" +
                 "                \"id\": \"1\", \n" +
                 "                \"items\": [\n" +
                 "                    {\n" +
                 "                        \"count\": 1, \n" +
                 "                        \"name\": \"勿选技术测试专用，没有物流信息的！\"\n" +
                 "                    }\n" +
                 "                ], \n" +
                 "                \"volume\": 1, \n" +
                 "                \"weight\": 1\n" +
                 "            }, \n" +
                 "            \"order_info\": {\n" +
                 "                \"order_channels_type\": \"TB\", \n" +
                 "                \"trade_order_list\": [\n" +
                 "                    \"22042406234839701246\"\n" +
                 "                ]\n" +
                 "            }, \n" +
                 "            \"recipient\": {\n" +
                 "                \"address\": {\n" +
                 "                    \"province\": \"广西壮族自治区\", \n" +
                 "                    \"city\": \"柳州市\", \n" +
                 "                    \"district\": \"柳南区\", \n" +
                 "                    \"detail\": \"航岭路航阳北苑985栋\", \n" +
                 "                    \"town\": \"\"\n" +
                 "                }, \n" +
                 "                \"mobile\": \"17000000985\", \n" +
                 "                \"name\": \"兰扯\", \n" +
                 "                \"oaid\": \"\", \n" +
                 "                \"tid\": \"\"\n" +
                 "            }, \n" +
                 "            \"user_id\": 0, \n" +
                 "            \"template_url\": \"http://cloudprint.cainiao.com/template/standard/288658/38\"\n" +
                 "        }\n" +
                 "    ]\n" +
                 "}";
         data.put("request_data",reqeustData );

        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
//        doHttpRequest(Config.TaoBaoCaiNiaoWayBillBatchGetUrl ,data);
        String resp = doHttpRequest(Config.TaoBaoCaiNiaoWayBillV2BatchGetUrl, data);
        System.out.println(resp);

        WayBillResponse wayBillResponse = JSON.parseObject(resp, WayBillResponse.class);

        if (wayBillResponse == null ){
            System.out.println("json解析错误:" +  resp);
        }

        if (wayBillResponse.getCode() != 0 ) {
            String msg = String.format("code:%d, msg:%s, ", wayBillResponse.getCode(), wayBillResponse.getMessage());
            System.out.println(msg);
        } else {

            for (Data dataItem : wayBillResponse.getData()) {
                String itemMsg = String.format("code:%d, msg:%s, object_id:%s,waybill_code:%s,partner_waybill_code:%s ", dataItem.getCode(), dataItem.getMessage(),
                        dataItem.getObjectId(), dataItem.getWaybillCode(), dataItem.getParentWaybillCode());
                System.out.println(itemMsg);

            }

        }

    }

    @Test
    public void TaoBaoCaiNiaoCloudprintCustomeraresGet() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("template_id", "16282123");
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.TaoBaoCaiNiaoCloudprintCustomeraresGetUrl, data);
        System.out.println(resp);
    }

    @Test
    public void TaoBaoCaiNiaoCloudISVTplGet() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
//        data.put("template_id", "11657428");
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.TaoBaoCaiNiaoCloudISVTplGetUrl, data);
        System.out.println(resp);
    }
    /**
     *    发送千牛消息
     *    前提 ：千牛助手和千牛在电脑客户端正常工作。
     * @throws Exception
     */
    @Test
    public void qianiuSendMsg() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
        //业务参数

        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        data.put("buyer_nick", "supercode");
        data.put("msg_content", "这是通过api发送的千牛消息哈");
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoQianNiuSendMsg ,data);

    }

    /**
     *    修改淘宝订单备注
     * @throws Exception
     */
    @Test
    public void updateMemo() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
        //业务参数

        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        String ts = timestamp.toString();
        System.out.println("ts:" + ts);
        data.put("timestamp", ts);
//        data.put("timestamp", "1622786574");
        // 订单号
        data.put("tid", "2774220555354034024");// 1319051751534419683
        // 卖家交易备注旗帜，可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)，默认值为0
        data.put("flag", "1");
        // 卖家交易备注。最大长度: 1000个字节
        data.put("memo", "通过蜂巢API223");
        // 是否对memo的值置空若为true，则不管传入的memo字段的值是否为空，都将会对已有的memo值清空，慎用；
        // 若用false，则会根据memo是否为空来修改memo的值：若memo为空则忽略对已有memo字段的修改，若memo非空，则使用新传入的memo覆盖已有的memo的值
        data.put("reset", "false");

        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.TaoBaoUpdateMemoUrl, data);
        System.out.println(resp);

    }

    /**
     *     获取在售商品
     * @throws Exception
     */
    @Test
    public void getItemOnSale() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
        //业务参数

        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        data.put("fields", "approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru, " +
                "list_time,price,has_discount,has_invoice,has_warranty,has_showcase,modified,delist_time,postage_id,seller_cids,outer_id,sold_quantity");

        data.put("q", "");
        data.put("cid", "");
        data.put("seller_cids", "");
        data.put("page_no", "1");
        data.put("has_discount", "true");
        data.put("has_showcase", "false");
        data.put("order_by", "");
        data.put("is_taobao", "true");
        data.put("is_ex", "false");
        data.put("page_size", "10");
        data.put("start_modified", "");
        data.put("end_modified", "");
        data.put("is_cspu", "false");
        data.put("is_combine", "false");
        data.put("auction_type", "b"); // 商品类型：a-拍卖,b-一口价

        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoItemOnSaleUrl ,data);

    }


    /**
     *  获取单个商品详细信息
     * @throws Exception
     */
    @Test
    public void getItemDetail() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
        //业务参数

        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        String defaultFields  =   "num_iid,title,cid,seller_cids,props, nick,price,approve_status,sku,num,list_time,delist_time,stuff_status,price,post_fee,express_fee,ems_fee,auction_point,skus,item_img,prop_img," +
                "barcode,created,features,sold_quantity" ;
        data.put("fields", defaultFields);
        data.put("num_iid", "660054337778");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoItemDetailUrl ,data);

    }

    /**
     * 获取商品的促销价
     * @throws Exception
     */
    @Test
    public void getTaoBaoItemPromotionPrice() throws Exception {

        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
//        data.put("tb_seller_nick", "百鞋馆");
//        data.put("numiids", "670526518538,672883521546");
//        data.put("numiids", "670526518538,6739290352942");
//        data.put("numiids", "673654583700");//bxg sku
//        data.put("numiids", "657693831673");//bxg no sku
        data.put("numiids", "669345683727");
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        String r = doHttpRequest(Config.TaoBaoItemPromotionGetUrl, data);
        System.out.println(r);

    }

    /**
     *  一口价商品上架
     * @throws Exception
     */
    @Test
    public void  itemUpdateListing() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
        //业务参数

        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        data.put("num", "22");
        data.put("num_iid", "544876335798");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoItemUpdateListingUrl ,data);

    }

    /**
     *   商品下架
     * @throws Exception
     */
    @Test
    public void  itemUpdateDeListing() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        // 商品Id
        data.put("num_iid", "544876335798");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoItemUpdateDeListingUrl ,data);

    }


    /**
     *   更新宝贝库存/sku
     * @throws Exception
     */
    @Test
    public void  itemUpdateStock() throws Exception {


        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        //  商品Id ， 必填
        data.put("num_iid", "544876335798");

        //  要操作的SKU的数字ID，可选。如果不填默认修改宝贝的库存，如果填上则修改该SKU的库存
        data.put("sku_id", "");

        // SKU的商家编码，可选参数。如果不填则默认修改宝贝的库存，如果填了则按照商家编码搜索出对应的SKU并修改库存。当sku_id和本字段都填写时以sku_id为准搜索对应SKU
        data.put("outer_id", "white35");

        // 库存修改值，必选。当全量更新库存时，quantity必须为大于等于0的正整数；当增量更新库存时，quantity为整数，
        // 可小于等于0。若增量更新时传入的库存为负数，则负数与实际库存之和不能小于0。比如当前实际库存为1，传入增量更新quantity=-1，库存改为0
        data.put("quantity", "100");

        // 库存更新方式，可选。1为全量更新，2为增量更新。如果不填，默认为全量更新
        data.put("type", "1");


        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoItemUpdateStockUrl ,data);

    }





    /**
     *   淘口令解析
     * @throws Exception
     */
    @Test
    public void  tbkTPWdConvert() throws Exception {

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        // 需要解析的淘口令   必填
//        data.put("password_content", "0\uD83D\uDC48\uD83D\uDCB0N5xQcrX37jK£dἌ开\uD83D\uDC49淘bào\uD83D\uDC48或點几url链 https://m.tb.cn/h.4TZZUob?sm=4dbe69 至浏lằn器【i9-10900K/10900KF I7 10700K I5 10600K 10400F 盒装CPU处理器】");
        data.put("password_content", "27￥O9UC25ky8Ph￥ https://m.tb.cn/h.foQVkq1  蓝色小西装外套女春秋薄款宽松大码休闲西服欧货2022年新款欧洲站");
//        data.put("password_content", "9 hi:/信0pR4X8LGc3b信  法式小众气质高贵优雅职场轻熟风女装2021夏装新款V领短袖连衣裙");

        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.TaoBaoTBKTpWdConvertUrl, data);
        System.out.println(resp);

    }

    /**
     * 判断是否是淘宝客的订单
     * @throws Exception
     */
     @Test
    public void  tbkIsTBKOrder() throws Exception {

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tids", "2481995377001565830");
//        data.put("tids", "2349936253299620692,2350994511755627774");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoTBKIsTBKOrderUrl ,data);

    }


    @Test
    public void  updateTradePushExpress() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
//        String express = "{\"ordersync_after_seller_change_memo\":false,\"ordersync_memo_filter_express\":\"卖家备注 包含 '礼物'\",\"ordersync_seller_flag\":\"1,2\"}";
        String express = "{\"ordersync_after_seller_change_memo\":true,\"ordersync_memo_filter_express\":\"\",\"ordersync_seller_flag\":\"1\"}";
        data.put("request_data", express);
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoTradeUpdatePushExpress ,data);

    }

    @Test
    public void  GetMakeFlagExpressUrl() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoOrderGetMakeFlagExpressUrl ,data);

    }

    @Test
    public void  updatePaidTradePushExpress() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        String express = "{\"is_enable\":true,\"filter_express\":\"买家留言 包含 'vip'\"}";
        data.put("request_data", express);
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoTradeUpdatePaidPushExpressUrl ,data);

    }
    /**
     *  此接口是买家应用调用，卖家身份调用此接口不可用。
     *  请忽略此接口
     * @throws Exception
     */
    @Test
    public void  getRefundList() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        // 退款状态，默认查询所有退款状态的数据，除了默认值外每次只能查询一种状态。
                    //WAIT_SELLER_AGREE(买家已经申请退款，等待卖家同意)
                    //WAIT_BUYER_RETURN_GOODS(卖家已经同意退款，等待买家退货)
                    //WAIT_SELLER_CONFIRM_GOODS(买家已经退货，等待卖家确认收货)
                    //SELLER_REFUSE_BUYER(卖家拒绝退款)
                    //CLOSED(退款关闭)
                    //SUCCESS(退款成功)
         data.put("status", "WAIT_SELLER_AGREE");
         // 页码。传入值为 1 代表第一页，传入值为 2 代表第二页，依此类推。默认返回的数据是从第一页开始
         data.put("page_no", "1");
         // 每页条数。取值范围:大于零的整数; 默认值:40;最大值:100
         data.put("page_size", "40");
         data.put("type", "fixed");

        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoRefundListUrl ,data);

    }




    @Test
    public void  getRefundDetail() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        // 退款单号
//        data.put("refund_id", "132032916631563058");bxg
        data.put("refund_id", "105031778542219291");

        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.TaoBaoRefundDetailUrl, data);
        System.out.println(resp);

    }

    /**
     * OAID解密
     * @throws Exception
     */
    @Test
    public void  topOAIDDecrypt() throws Exception {
        long start = System.nanoTime();

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
//        String requestData = "[{\"tid\": \"2003819439915565830\", \"oaid\": \"1h12M4LqkhUkt1ibEk6VyZv2gRsECYSQayzH33CwBP1s3CmkVyx1m4SItNJ6ZhgUibyzr9RA\" ,\"scene\":\"1007\"}," +
//                "{\"tid\": \"1967781171792565830\", \"oaid\": \"xxxx2h12M4LqkhUkt1ibEk6VyZv2gRsECYSQayzH33CwBP1s3CmlhJx18fN3CCL8zKx9v5icW2vib\" ,\"scene\":\"1007\"}" +
//                "]";

        String requestData = "[{\"tid\": \"2559554679472261300\", \"oaid\": \"1d8HAhgNryB64tMg3RSZ5eqk8ojJhiaJtKqwAgcuNsQ6zwx1nGqQReRGWzxG4sGcSnSsMfL\" ,\"scene\":\"1007\"}]";
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        // 退款单号
        data.put("request_data", requestData);
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.TaoBaoTopOAIDDecryptUrl, data);
        System.out.println(resp);
        long end = System.nanoTime();
        long elapsedTime = end - start;
//        long convert = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
        long convert = TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);


        System.out.println("cost times :" + convert /1000f + "秒" );
    }

//    @Test
//    public void  taoBaoRefundAgree() throws Exception {
//
//        String tb_seller_nick = Config.TBSellerNick ;
//        //业务参数
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("appid",  Config.AppId);
//        Long timestamp = System.currentTimeMillis() / 1000;
//        data.put("timestamp", timestamp.toString());
//        data.put("tb_seller_nick", tb_seller_nick);
//        // 短信验证码，如果退款金额达到一定的数量，后端会返回调用失败，并同时往卖家的手机发送一条短信验证码。接下来用收到的短信验证码再次发起API调用即可完成退款操作。
//        data.put("code", "840002");
//        // 退款信息，格式：refund_id|amount|version|phase，其中refund_id为退款编号，amount为退款金额（以分为单位），
//        // version为退款最后更新时间（时间戳格式），phase为退款阶段（可选值为：onsale, aftersale，天猫退款必值，淘宝退款不需要传），多个退款以半角逗号分隔。
//        data.put("refund_infos", "110045054952563058|4800|1618830739396");
//         // 签名
//        data.put("sign", Utils.Sign(data,Config.AppSecret));
//        // 调用服务API
//        doHttpRequest(Config.TaoBaoRefundAgreeUrl ,data);
//
//    }

  @Test
    public void  taoBaoRefundAgree() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        // 短信验证码，如果退款金额达到一定的数量，后端会返回调用失败，并同时往卖家的手机发送一条短信验证码。接下来用收到的短信验证码再次发起API调用即可完成退款操作。
//        data.put("code", "840002");
        // 退款信息，格式：refund_id|amount|version|phase，其中refund_id为退款编号，amount为退款金额（以分为单位），
        // version为退款最后更新时间（时间戳格式），phase为退款阶段（可选值为：onsale, aftersale，天猫退款必值，淘宝退款不需要传），多个退款以半角逗号分隔。
        data.put("refund_infos", "88824002297127635|4900|1631266660665|onsale");
         // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoRefundAgreeUrl ,data);

    }






    @Test
    public void  getRefundReceive() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        data.put("buyer_open_uid", "AAHW90gYAAjcqSrFKsIjFOPr");
        // 退款状态，默认查询所有退款状态的数据
//        data.put("status", "WAIT_SELLER_AGREE");
        data.put("start_modified", "2022-06-01 00:00:00");
        data.put("end_modified", "2022-08-11 23:00:00");
        data.put("page_no", "1");
        data.put("page_size", "30");
        data.put("use_has_next", "false");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        String result = doHttpRequest(Config.TaoBaoRefunReceiveUrl, data);
        System.out.println(result);

    }

    /**
     * 卖家拒绝退款
     * @throws Exception
     */
    @Test
    public void  taoBaoRefundRefuse() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        data.put("refund_id", "110056431137563058");
        data.put("refuse_message", "这个是绝好的东西，一定要尝试");

        // 拒绝退款时的退款凭证，一般是卖家拒绝退款时使用的发货凭证，最大长度130000字节，支持的图片格式：GIF, JPG, PNG。 天猫退款为必填项。
      //  String base64ImageLogo = Utils.getBase64ImageFromBinary("/Users/miller/MyDocument/aaa.jpg");
       // data.put("refuse_proof", base64ImageLogo);
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoRefundRefuseUrl ,data);

    }



    /**
     * 仓库中的商品列表
     * @throws Exception
     */
    @Test
    public void  inventoryList() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        data.put("banner", "sold_out");
        data.put("fields", "approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru, list_time,price,has_discount,has_invoice,has_warranty,has_showcase, modified,delist_time,postage_id,seller_cids,outer_id");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoInventoryListUrl ,data);

    }


    /**
     * 批量获取商品信息
     * @throws Exception
     */
    @Test
    public void  itemSellerList() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", Config.TBSellerNick);
        data.put("numiids", "634782672585");
//        data.put("fields", "num_iid,title,nick,approve_status,num,sku,detail_url,type,cid,seller_cids,props,desc,pic_url,num,price,item_img,prop_img,videos,wap_detail_url,wap_desc,item_rectangle_img,cuntao_item_specific,"  +
//               "desc_modules,wireless_desc,mpic_video");

        data.put("fields", "title,nick,pic_url,price");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.TaoBaoItemSellerListUrl, data);
        System.out.println("返回结果");
        System.out.println(resp);

    }

    /**
     *  更改交易的收货地址
     * @throws Exception
     */
    @Test
    public void  TaoBaoOrderShippingAddressUpdate() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", Config.TBSellerNick);
        data.put("tid", "2145482892651565830");
        data.put("receiver_name", "习大牛");
        data.put("receiver_mobile", "13817778899");

        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoOrderShippingAddressUpdateUrl ,data);

    }

    /**
     *   创建退款留言/凭证
     * @throws Exception
     */
    @Test
    public void  TaoBaoRefunMessageAdd() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", Config.TBSellerNick);
        data.put("refund_id", "132050558727563058");
        data.put("content", "亲，不好意思，已经在路上了,不要退了哈");
        //  图片（凭证）。类型: JPG,GIF,PNG;最大为: 500K。
        String base64ImageLogo = Utils.getBase64ImageFromBinary("/Users/miller/MyDocument/aaa.jpg");
        data.put("image", base64ImageLogo);
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoRefunMessageAddUrl ,data);

    }


/**
     *  查询退款留言/凭证列表
     * @throws Exception
     */
    @Test
    public void  TaoBaoRefunMessageGet() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", Config.TBSellerNick);
        data.put("refund_id", "105031778542219291");
        data.put("page_no", "1");
        data.put("page_size", "20");

        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        String result = doHttpRequest(Config.TaoBaoRefunMessageGetUrl, data);
        System.out.println(result);

    }

/**
     *  查询退款留言/凭证列表
     * @throws Exception
     */
    @Test
    public void  TaoBaoTradeRatesGet() throws Exception {

//        {"rate_type":"get","role":"seller","tb_seller_nick":"三龙贸易欢迎您","appid":"SidVkAP2toV4sec8","sign":"a1e3cd5ca094947a7827129dc0cf3639","timestamp":"1652088939"}


        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", Config.TBSellerNick);
        data.put("rate_type", "get");
        data.put("role", "buyer");
        data.put("page_no", "1");
        data.put("page_size", "100");
        data.put("use_has_next", "true");
//        data.put("start_date", "2021-11-16 00:00:00");
//        data.put("end_date", "2021-11-16 10:00:00");


        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        String rsp = doHttpRequest(Config.TaoBaoTradeRatesGetUrl, data);
        System.out.println(rsp);

    }


/**
     *  卖家回填物流信息
     * @throws Exception
     */
    @Test
    public void  TaoBaorefundreturnRefill() throws Exception {

//        //业务参数
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("appid",  Config.AppId);
//        Long timestamp = System.currentTimeMillis() / 1000;
//        data.put("timestamp", timestamp.toString());
//        data.put("tb_seller_nick", Config.TBSellerNick);
//        data.put("refund_id", "132020568245563058");
//        data.put("refund_phase", "aftersale");
//        data.put("logistics_waybill_no", "EG893082275CS");
//        data.put("logistics_company_code", "EMS");
//        // 签名
//        data.put("sign", Utils.Sign(data,Config.AppSecret));
//        // 调用服务API
//        doHttpRequest(Config.TaoBaorefundreturnRefillUrl ,data);





        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", Config.TBSellerNick);
        data.put("refund_id", "133296699332616758");
        data.put("refund_phase", "onsale");
        data.put("logistics_waybill_no", "3123456789");
        data.put("logistics_company_code", "OTHER");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaorefundreturnRefillUrl ,data);



    }



    /** 卖家拒绝退货
     *
     * @throws Exception
     */
    @Test
    public void  TaoBaorefundreturnRefuse() throws Exception {

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", Config.TBSellerNick);
        data.put("refund_id", "132020568245563058");
        data.put("refund_phase", "aftersale");
        data.put("refund_version", "322223");
        String base64ImageLogo = Utils.getBase64ImageFromBinary("/Users/miller/MyDocument/aaa.jpg");
        data.put("refuse_proof", base64ImageLogo);

        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaorefundreturnRefuseUrl ,data);

    }


    /** 卖家同意退货
     *
     * @throws Exception
     */
//    @Test
//    public void  TaoBaorefundreturnAgree() throws Exception {
//
//        //业务参数
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("appid",  Config.AppId);
//        Long timestamp = System.currentTimeMillis() / 1000;
//        data.put("timestamp", timestamp.toString());
//        data.put("tb_seller_nick", Config.TBSellerNick);
//        data.put("refund_id", "132032916631563058");
//        data.put("mobile", "13877228899");
//        data.put("tel", "05718848388");
//        data.put("name", "张三");
//        data.put("address", "浙江省杭州市西湖区32路11号12小区");
//        data.put("post", "310000");
//        data.put("seller_address_id", "18701489");
//
//        // 签名
//        data.put("sign", Utils.Sign(data,Config.AppSecret));
//        // 调用服务API
//        doHttpRequest(Config.TaoBaorefundreturnAgreeUrl ,data);
//
//    }

    @Test
    public void  TaoBaorefundreturnAgree() throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", Config.TBSellerNick);
        data.put("refund_id", "133216488750616758");
        data.put("mobile", "");
        data.put("tel", "02161515750");
        data.put("name", "诚衣(上海)商贸有限公司");
        data.put("address", "民强路1236号(B栋东侧)");
        data.put("post", "201612");
        data.put("remark", "系统同意退货");
        data.put("refund_phase", "onsale");
        data.put("refund_version", "1634549278137");
        //data.put("seller_address_id", "18701489"); // 通过查询卖家地址库接口获取
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaorefundreturnAgreeUrl ,data);

    }




    private  String doHttpRequest(String url ,  Map<String, String> data ){
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

        return result;
    }


}
