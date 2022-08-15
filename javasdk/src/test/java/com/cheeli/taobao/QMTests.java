package com.cheeli.taobao;

import com.alibaba.fastjson.JSON;
import com.cheeli.Config;
import com.cheeli.models.LogisticesBatchSendItem;
import com.cheeli.utils.QimenSignUtils;
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

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * 场景：合作伙伴使用，可管理其他授权的店铺。即可以管理多家店铺
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QMTests.class )
public class QMTests {

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
     * @throws Exception
     */
    @Test
    public void  getOpenDevAccount() throws Exception {

        String result ="";
//        String url = "https://open.fw199.com/gateway/partner/account/detail";
        String url = "http://127.0.0.1:8080/gateway/partner/account/detail";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( url );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        //发起POST请求
        try {
            //参数签名
            data.put("sign", Utils.Sign(data,Config.AppSecret));
            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
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

    /**
     * 查询商家的支付宝账户信息
     * @throws Exception
     */
    @Test
    public void getAlipayAccountInfo() throws Exception {

        String result ="";
//        String url = "https://open.fw199.com/gateway/alipay/account/detail";
        String url = "http://web2.vs.fw199.com/gateway/alipay/account/detail";
        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( url );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        //发起POST请求
        try {
            //参数签名
            data.put("sign", Utils.Sign(data,Config.AppSecret));
            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
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




    /**
     * 查询某个淘宝卖家是否授权给自己
     * @throws Exception
     */
    @Test
    public void checkStoreGrant() throws Exception {

        String result ="";
        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.TaoBaoStoreGrantUrl );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick",tb_seller_nick);

        //发起POST请求
        try {
            //参数签名
            data.put("sign", Utils.Sign(data,Config.AppSecret));
            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
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






    /**
     * 支付宝单笔交易查询
     * @throws Exception
     */
    @Test
    public void getAlipayTradeDetail() throws Exception {

        String result ="";
        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
        String alipay_order_no = "20200811200040011100790075219429"; // 支付宝交易订单号
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.AliPayTradeDetailUrl );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        data.put("alipay_order_no", alipay_order_no);
//       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        //发起POST请求
        try {
            //参数签名
            data.put("sign", Utils.Sign(data,Config.AppSecret));
            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
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





    /**
     * 支付宝交易明细查询
     * @throws Exception
     */
    @Test
    public void getAlipayTradeList() throws Exception {

        String result ="";
        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.AliPayTradeListUrl );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());


        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -90);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data.put("start_time", df.format(calendar.getTime()));
        Date end = new Date();
        data.put("end_time",  df.format(end));
        data.put("page_no", "1");
        data.put("page_size","20");

        //参数签名
        try {
            data.put("sign", Utils.Sign(data,Config.AppSecret));
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
                result =  EntityUtils.toString(httpResponse.getEntity());
            } else {
                result =  ("doPost Error Response: " + httpResponse.getStatusLine().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println(result);

    }

    /**
     *  查询物流公司列表
     * @throws Exception
     */
    @Test
    public void getLogisticesCompany() throws Exception {

        String result ="";
        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.LogisticesCompanyUrl );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());


        // 是否查询推荐物流公司.可选值:true,false.如果不提供此参数,将会返回所有支持电话联系的物流公司.
        data.put("is_recommended", "true");
        // 推荐物流公司的下单方式.可选值:offline(电话联系/自己联系),online(在线下单),all(即电话联系又在线下单).
        // 此参数仅仅用于is_recommended 为ture时。就是说对于推荐物流公司才可用.如果不选择此参数将会返回推荐物流中支持电话联系的物流公司.
        data.put("order_mode","all");
        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
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



    /**
     *  在线订单发货处理（支持货到付款）
     * @throws Exception
     */
    @Test
    public void logisticesCompanyOnineSend() throws Exception {


//        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
//
//
//        //业务参数
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("appid",  Config.AppId);
//        data.put("tb_seller_nick", tb_seller_nick);
//        Long timestamp = System.currentTimeMillis() / 1000;
//        data.put("timestamp", timestamp.toString());
//
//        // 淘宝交易ID，必须
//        data.put("tid", "1148987073290565830");
//       //  物流公司代码.如"POST"就代表中国邮政,"ZJS"就代表宅急送 ，通过接口 "查询物流公司列表"   获取。
//        data.put("company_code","SF");
//        // 运单号.具体一个物流公司的真实运单号码。淘宝官方物流会校验，请谨慎传入；
//        data.put("out_sid","SF1191992347148");
//        // 卖家交易备注旗帜，   可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)，说明：如果不想加旗帜，则传空串""
//        data.put("flag","1");
//        // 卖家交易备注。最大长度: 1000个字节  ，说明：如果不想加旗帜，则传空串""
//        data.put("memo","效果不错");
//        // 参数签名
//        data.put("sign", Utils.Sign(data,Config.AppSecret));
//
//        // 调用服务API
//        doHttpRequest(Config.LogisticesOnlineSendUrl,data);


        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        // 淘宝交易ID，必须
        data.put("tid", "1471254948271960249");
        //  物流公司代码.如"POST"就代表中国邮政,"ZJS"就代表宅急送 ，通过接口 "查询物流公司列表"   获取。
        data.put("company_code","YUNDA");
        // 运单号.具体一个物流公司的真实运单号码。淘宝官方物流会校验，请谨慎传入；
        data.put("out_sid","4311540087965");
        // 卖家交易备注旗帜，   可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)，说明：如果不想加旗帜，则传空串""
        data.put("flag","1");
        // 卖家交易备注。最大长度: 1000个字节  ，说明：如果不想加旗帜，则传空串""
        data.put("memo","效果不错，API");
        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));

        // 调用服务API
        doHttpRequest(Config.LogisticesOnlineSendUrl,data);



    }



    /**
     *  自己联系物流（线下物流）发货
     *  用户调用该接口可实现自己联系发货（线下物流），使用该接口发货，交易订单状态会直接变成卖家已发货。不支持货到付款、在线下单类型的订单。
     * @throws Exception
     */
    @Test
    public void logisticesCompanyOffineSend() throws Exception {


        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        // 淘宝交易ID，必须
        data.put("tid", "1471254948271960249");
        //  物流公司代码.如"POST"就代表中国邮政,"ZJS"就代表宅急送 ，通过接口 "查询物流公司列表"   获取。
        data.put("company_code","YUNDA");
        // 运单号.具体一个物流公司的真实运单号码。淘宝官方物流会校验，请谨慎传入；
        data.put("out_sid","4311540087965");
        // 卖家交易备注旗帜，   可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)，说明：如果不想加旗帜，则传空串""
        data.put("flag","1");
        // 卖家交易备注。最大长度: 1000个字节  ，说明：如果不想加旗帜，则传空串""
        data.put("memo","通过API发货");
        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));

        // 调用服务API
        doHttpRequest(Config.LogisticesOfflineSendUrl,data);

    }

    /**
     *  自己联系物流（线下物流）发货 --批量发送模式
     *  用户调用该接口可实现自己联系发货（线下物流），使用该接口发货，交易订单状态会直接变成卖家已发货。不支持货到付款、在线下单类型的订单。
     * @throws Exception
     */
    @Test
    public void logisticesCompanyOffineBatchSend() throws Exception {


        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        List<LogisticesBatchSendItem> list = new ArrayList();
        LogisticesBatchSendItem item =  new LogisticesBatchSendItem();
        // 第1个订单
        // 淘宝交易ID，必须
        item.setTid("1394382349229565830");
        // 运单号.具体一个物流公司的真实运单号码。淘宝官方物流会校验，请谨慎传入；
        item.setOutSid("773075543162332");
        //  物流公司代码.如"POST"就代表中国邮政,"ZJS"就代表宅急送 ，通过接口 "查询物流公司列表"   获取。
        item.setCompanyCode("STO");
        // 卖家交易备注旗帜，   可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)，说明：如果不想加旗帜，则传空串""
        item.setFlag("");
        // 卖家交易备注。最大长度: 1000个字节  ，说明：如果不想加旗帜，则传空串""
        item.setMemo("");
        list.add(item);

        // 第2个订单
        item =  new LogisticesBatchSendItem();
        // 淘宝交易ID，必须
        item.setTid("1394614838090565830");
        // 运单号.具体一个物流公司的真实运单号码。淘宝官方物流会校验，请谨慎传入；
        item.setOutSid("773075543162399");
        //  物流公司代码.如"POST"就代表中国邮政,"ZJS"就代表宅急送 ，通过接口 "查询物流公司列表"   获取。
        item.setCompanyCode("STO");
        // 卖家交易备注旗帜，   可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)，说明：如果不想加旗帜，则传空串""
        item.setFlag("");
        // 卖家交易备注。最大长度: 1000个字节  ，说明：如果不想加旗帜，则传空串""
        item.setMemo("");
        list.add(item);

        String jsonItems = JSON.toJSONString(list);
        System.out.println(jsonItems);
        data.put("items",jsonItems );
        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));

        // 调用服务API
        doHttpRequest(Config.LogisticesOfflineBatchSendUrl,data);

    }


    /**
     *   获取订单列表
     * @throws Exception
     */
    @Test
    public void getTradeList() throws Exception {

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


                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_MONTH, -2);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        data.put("start_created", df.format(calendar.getTime()));
                    data.put("start_created", "2021-06-01 00:00:00");
                    Date end = new Date();
//        data.put("end_created",  df.format(end));
                    data.put("end_created", "2021-06-10 00:00:00");
                    data.put("status", "WAIT_SELLER_SEND_GOODS"); // 待发货订单 WAIT_SELLER_SEND_GOODS
                    data.put("buyer_nick", "");
                    data.put("page_no", "1");
                    data.put("page_size", "30");
                    data.put("use_has_next", "true");
                    //qimeng
                    data.put("method", "erp.trade.get");
                    data.put("app_key", "12573539");
                    data.put("sign", Utils.Sign(data, Config.AppSecret));


//                    data.put("sign", QimenSignUtils.sign(data, Config.AppSecret));
                    // 调用服务API
                    long startTime = System.currentTimeMillis(); //获取开始时间
                    doHttpRequest(Config.QMTaoBaoOrderListUrl, data);
                    long endTime = System.currentTimeMillis(); //获取结束时间
                }catch (Exception ex){
                    ex.printStackTrace();
                }


            } //run

        });




    }


    public static void parallelTesk(int threadNum, Runnable task){

        // 1. 定义闭锁来拦截线程
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate  = new CountDownLatch(threadNum);

        // 2. 创建指定数量的线程
        for (int i = 0; i <threadNum; i++) {
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
        long start =  System.nanoTime();

        startGate.countDown();
        try {
            endGate.await();
        } catch (InterruptedException e) {
        }

        long end = System.nanoTime();
        System.out.println("cost times :" +(end - start));
    }



    /**
     *   获取增量订单列表
     * @throws Exception
     */
    @Test
    public void getIncrementTradeList() throws Exception {

        String tb_seller_nick = Config.TBSellerNick; //要查询的淘宝商家
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        // 订单修改的开始时间和结束时间不要超过24小时
        data.put("start_modified", "2021-05-27 08:35:00");
        data.put("end_modified",  "2021-05-27 23:55:00");
//        data.put("status",  "WAIT_SELLER_SEND_GOODS"); // 待发货订单 WAIT_SELLER_SEND_GOODS
        data.put("page_no", "1");
        data.put("page_size","20");
        data.put("use_has_next","false");
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        long startTime = System.currentTimeMillis(); //获取开始时间
        doHttpRequest(Config.TaoBaoTradeIncrementListUrl ,data);
        long endTime = System.currentTimeMillis(); //获取结束时间


    }




    /**
     *   获取订单详情
     * @throws Exception
     */
    @Test
    public void getTradeDetail() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tid", "2019061080035565830");//bb
//        data.put("tid", "1646882497743565830");
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoOrderDetailUrl ,data);

    }
    /**
     *   批量获取订单详情
     * @throws Exception
     */
    @Test
    public void getTradeDetailBatch() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
//        data.put("tid", "1702708671154565830");//bb
        data.put("tids", "1984565594911565830,1645829892702565830,1024065443098565830");
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoOrderDetailBatchUrl ,data);

    }




    /**
     * 获取所有的菜鸟标准电子面单模板
     * @throws Exception
     */
    @Test
    public void getCloudPrintStdtemplates() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoCaiNiaoCloudPrintStdtemplatesUrl ,data);

    }



    /**
     *   查询面单服务订购及面单使用情况
     * @throws Exception
     */
    @Test
    public void getCaiNiaoWayBillSearch() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("cp_code", "YTO"); //  物流公司code  比如，YUNDA
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoCaiNiaoWayBillSearchUrl ,data);

    }





    /**
     *    电子面单云打印接口 -批量
     * @throws Exception
     */
    @Test
    public void getCaiNiaoWayBillGetBatch() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
        String reqeustData ="{\"cp_code\":\"STO\",\"dms_sorting\":false, \"resource_code\":\"\",\"sender\":{\"address\":{\"city\":\"广州市\",\"detail\":\"石湖云仓\",\"district\":\"白云区\",\"province\":\"广东省\"},\"mobile\":\"17800002366\",\"name\":\"朱迎春\",\"phone\":\"0571232222\"},\"store_code\":\"\",\"trade_order_info_dtos\":[{\"object_id\":\"1\",\"order_info\":{\"order_channels_type\":\"TB\",\"trade_order_list\":[\"90020206078841641229\"]},\"package_info\":{\"id\":\"1\",\"items\":[{\"count\":50,\"name\":\"名片\"}],\"volume\":1,\"weight\":1},\"recipient\":{\"address\":{\"city\":\"杭州市\",\"detail\":\"横村镇桐千路8118号华艺大酒店单身公寓12楼\",\"district\":\"桐庐县\",\"province\":\"浙江省\",\"town\":\"横村镇\"},\"mobile\":\"13357035578\",\"name\":\"洪勇军修改-智能发货\",\"phone\":\"057123222233\"},\"template_url\":\"http:\\/\\/cloudprint.cainiao.com\\/template\\/standard\\/256771\\/9\",\"user_id\":0}]}";
//        String reqeustData ="{\n" +
//                "    \"cp_code\": \"YTO\",\n" +
//                "    \"dms_sorting\": false,\n" +
//                "    \"resource_code\": \"\",\n" +
//                "    \"sender\": {\n" +
//                "        \"address\": {\n" +
//                "            \"city\": \"泉州市\",\n" +
//                "            \"detail\": \"灵秀镇子江路15号\",\n" +
//                "            \"district\": \"鲤城区\",\n" +
//                "            \"province\": \"福建省\"\n" +
//                "        },\n" +
//                "        \"mobile\": \"13960280440\",\n" +
//                "        \"name\": \"吴远金\",\n" +
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
//                "            \"template_url\": \"http:\\\\/\\\\/cloudprint.cainiao.com\\\\/template\\\\/standard\\\\/290659\\\\/42\",\n" +
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


        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        data.put("request_data",reqeustData );

        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
//        doHttpRequest(Config.TaoBaoCaiNiaoWayBillBatchGetUrl ,data);
        doHttpRequest(Config.TaoBaoCaiNiaoWayBillV2BatchGetUrl ,data);

    }



    /**
     *    淘宝无物流发货
     * @throws Exception
     */
    @Test
    public void logisticesDummySend() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        data.put("tid", "1647731306609662735");
        data.put("memo", "这一个使用API添加的备注，直接发了");
        data.put("flag", "2");
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoLogisticesDummySend ,data);

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
//        data.put("timestamp", ts);
        data.put("timestamp", "1622786574");
        // 订单号
        data.put("tid", "1702708671154565830");// 1319051751534419683
        // 卖家交易备注旗帜，可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)，默认值为0
        data.put("flag", "3");
        // 卖家交易备注。最大长度: 1000个字节
        data.put("memo", "库房发货");
        // 是否对memo的值置空若为true，则不管传入的memo字段的值是否为空，都将会对已有的memo值清空，慎用；
        // 若用false，则会根据memo是否为空来修改memo的值：若memo为空则忽略对已有memo字段的修改，若memo非空，则使用新传入的memo覆盖已有的memo的值
        data.put("reset", "false");

        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoUpdateMemoUrl ,data);

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

        String defaultFields  =   "num_iid,title,cid,seller_cids,props, nick,price,approve_status,sku,num,list_time,delist_time,stuff_status,price,post_fee,express_fee,ems_fee,auction_point,skus,item_imgs,prop_imgs," +
                "barcode,created,features" ;
        data.put("fields", defaultFields);
        data.put("num_iid", "644439734579");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoItemDetailUrl ,data);

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
     *  快递目的地到达查询
     * @throws Exception
     */

    @Test
    public void  taobaoCaiNiaoReachableBatchjudge() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        data.put("tb_seller_nick", tb_seller_nick);
        String checkData = "{" +
                "    \"address_and_service_list\": [{" +
                "        \"object_id\": \"abc100\"," +
                "        \"receive_address\": {" +
                "            \"address_detail\": \"裕田小区12号楼6单元201室\"," +
                "            \"area_name\": \"呼兰区\"," +
                "            \"city_name\": \"哈尔滨市\"," +
                "            \"province_name\": \"黑龙江省\"," +
                "            \"town_name\": \"利民街道\"" +
                "        }," +
                "        \"send_address\": {" +
                "            \"area_name\": \"黄浦区\"," +
                "            \"city_name\": \"广州市\"," +
                "            \"province_name\": \"广东省\"," +
                "            \"town_name\": \"九佛镇\"" +
                "        }" +
                "    }, " +
                "    {" +
                "        \"object_id\": \"def200\"," +
                "        \"receive_address\": {" +
                "            \"address_detail\": \"蟠凤后岸82号\"," +
                "            \"area_name\": \"瓯海区\"," +
                "            \"city_name\": \"温州市\"," +
                "            \"province_name\": \"浙江省\"," +
                "            \"town_name\": \"梧田街道\"" +
                "        }," +
                "        \"send_address\": {" +
                "            \"area_name\": \"黄浦区\"," +
                "            \"city_name\": \"广州市\"," +
                "            \"province_name\": \"广东省\"," +
                "            \"town_name\": \"九佛镇\"" +
                "        }" +
                "    }]," +
                "    \"cp_code\": \"YTO\"," +
                "    \"send_branch_code\": \"2220111\"" +"}";

        // 1:快递 2:快运
       data.put("address_type", "1");
       data.put("data", checkData);
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
       doHttpRequest(Config.TaobaoCaiNiaoReachableBatchjudgeUrl ,data);

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
        data.put("password_content", "0\uD83D\uDC48\uD83D\uDCB0N5xQcrX37jK£dἌ开\uD83D\uDC49淘bào\uD83D\uDC48或點几url链 https://m.tb.cn/h.4TZZUob?sm=4dbe69 至浏lằn器【i9-10900K/10900KF I7 10700K I5 10600K 10400F 盒装CPU处理器】");

        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoTBKTpWdConvertUrl ,data);

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
        String express = "{\"ordersync_after_seller_change_memo\":true,\"ordersync_memo_filter_express\":\"卖家备注 包含 '礼物'\",\"ordersync_seller_flag\":\"1,2\"}";
        data.put("request_data", express);
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoTradeUpdatePushExpress ,data);

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
//         data.put("status", "WAIT_SELLER_AGREE");
         // 页码。传入值为 1 代表第一页，传入值为 2 代表第二页，依此类推。默认返回的数据是从第一页开始
         data.put("page_no", "1");
         // 每页条数。取值范围:大于零的整数; 默认值:40;最大值:100
         data.put("page_size", "40");
         data.put("type", "guarantee_trade,auto_delivery,fixed,auto_delivery");

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
        data.put("refund_id", "110045054952563058");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoRefundDetailUrl ,data);

    }

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
        data.put("code", "840002");
        // 退款信息，格式：refund_id|amount|version|phase，其中refund_id为退款编号，amount为退款金额（以分为单位），
        // version为退款最后更新时间（时间戳格式），phase为退款阶段（可选值为：onsale, aftersale，天猫退款必值，淘宝退款不需要传），多个退款以半角逗号分隔。
        data.put("refund_infos", "110045054952563058|4800|1618830739396");
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
        // 退款状态，默认查询所有退款状态的数据
//        data.put("status", "WAIT_SELLER_AGREE");
        data.put("start_modified", "2022-01-01 00:00:00");
        data.put("end_modified", "2022-08-11 00:00:00");
        data.put("page_no", "1");
        data.put("page_size", "30");
        data.put("use_has_next", "true");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoRefunReceiveUrl ,data);

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
     * 物流流转信息查询
     * @throws Exception
     */
    @Test
    public void  TaoBaoLogisticesTraceSearchUrl() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        // 淘宝交易号，请勿传非淘宝交易号
         data.put("tid", "1276643677752460576");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoLogisticesTraceSearchUrl ,data);

    }





//    /**
//     *   淘口令创建 -- 如果需要请联系客服开通
//     * @throws Exception
//     */
//    @Test
//    public void  tbkTPWdCreate() throws Exception {
//
//        //业务参数
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("appid",  Config.AppId);
//        Long timestamp = System.currentTimeMillis() / 1000;
//        data.put("timestamp", timestamp.toString());
//        // 需要解析的淘口令   必填
//        data.put("user_id", "se");
//        data.put("text", "Apple超级计算机");
//        data.put("url", "https://uland.taobao.com/");
//        data.put("logo", "https://img.alicdn.com/imgextra/i2/875470122/O1CN01hSDif41CluxmQkfBC_!!0-item_pic.jpg_430x430q90.jpg");
//        // 签名
//        data.put("sign", Utils.Sign(data,Config.AppSecret));
//        // 调用服务API
//        doHttpRequest(Config.TaoBaoTBKTpWdCreateUrl ,data);
//
//    }





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
