package com.cheeli.taobao;

import com.alibaba.fastjson.JSON;
import com.cheeli.Config;
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
     * @throws Exception
     */
    @Test
    public void  getOpenDevAccount() throws Exception {

        String result ="";
        String url = "https://open.fw199.com/gateway/partner/account/detail";
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
        calendar.add(Calendar.DAY_OF_MONTH, -7);
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
        data.put("order_mode","offline");
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


        String tb_seller_nick = Config.TBSellerNick ; //要查询支付宝的淘宝商家


        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        // 淘宝交易ID，必须
        data.put("tid", "1148987073290565830");
       //  物流公司代码.如"POST"就代表中国邮政,"ZJS"就代表宅急送 ，通过接口 "查询物流公司列表"   获取。
        data.put("company_code","SF");
        // 运单号.具体一个物流公司的真实运单号码。淘宝官方物流会校验，请谨慎传入；
        data.put("out_sid","SF1191992347148");
        // 卖家交易备注旗帜，   可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)，说明：如果不想加旗帜，则传空串""
        data.put("flag","1");
        // 卖家交易备注。最大长度: 1000个字节  ，说明：如果不想加旗帜，则传空串""
        data.put("memo","效果不错");
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
        data.put("tid", "1366575805196235184");
        //  物流公司代码.如"POST"就代表中国邮政,"ZJS"就代表宅急送 ，通过接口 "查询物流公司列表"   获取。
        data.put("company_code","STO");
        // 运单号.具体一个物流公司的真实运单号码。淘宝官方物流会校验，请谨慎传入；
        data.put("out_sid","773067452686046");
        // 卖家交易备注旗帜，   可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)，说明：如果不想加旗帜，则传空串""
        data.put("flag","1");
        // 卖家交易备注。最大长度: 1000个字节  ，说明：如果不想加旗帜，则传空串""
        data.put("memo","效果不错");
        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));

        // 调用服务API
        doHttpRequest(Config.LogisticesOfflineSendUrl,data);

    }


    /**
     *   获取订单列表
     * @throws Exception
     */
    @Test
    public void getTradeList() throws Exception {


        String tb_seller_nick = Config.TBSellerNick; //要查询的淘宝商家
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());


        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data.put("start_created", df.format(calendar.getTime()));
        Date end = new Date();
        data.put("end_created",  df.format(end));
        data.put("status",  ""); // 待发货订单 WAIT_SELLER_SEND_GOODS
        data.put("buyer_nick",  "");
        data.put("page_no", "1");
        data.put("page_size","20");
        data.put("use_has_next","true");
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        long startTime = System.currentTimeMillis(); //获取开始时间

        doHttpRequest(Config.TaoBaoOrderListUrl ,data);

        long endTime = System.currentTimeMillis(); //获取结束时间

        System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); //输出程序运行时间





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
        data.put("tid", "1366575805196235184");
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoOrderDetailUrl ,data);

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
        data.put("cp_code", ""); //  物流公司code  比如，YUNDA
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
//        String reqeustData ="{\"cp_code\":\"ZTO\",\"dms_sorting\":false,\"resource_code\":\"\",\"sender\":{\"address\":{\"city\":\"长沙市\",\"detail\":\"望城大道61号丰树物流园三期（于野彩卡）\",\"district\":\"望城区\",\"province\":\"湖南省\"},\"mobile\":\"1326443654\",\"name\":\"XX印务-as\",\"phone\":\"0571232222\"},\"store_code\":\"\",\"trade_order_info_dtos\":[{\"object_id\":\"1\",\"order_info\":{\"order_channels_type\":\"TB\",\"trade_order_list\":[\"90020206078841641229\"]},\"package_info\":{\"id\":\"1\",\"items\":[{\"count\":50,\"name\":\"名片\"}],\"volume\":1,\"weight\":1},\"recipient\":{\"address\":{\"city\":\"杭州市\",\"detail\":\"横村镇桐千路8118号华艺大酒店单身公寓12楼\",\"district\":\"桐庐县\",\"province\":\"浙江省\",\"town\":\"横村镇\"},\"mobile\":\"13357035578\",\"name\":\"洪勇军修改-智能发货\",\"phone\":\"057123222233\"},\"template_url\":\"http:\\/\\/cloudprint.cainiao.com\\/template\\/standard\\/256771\\/9\",\"user_id\":0}]}";
        String reqeustData ="{\n" +
                "    \"cp_code\": \"YTO\",\n" +
                "    \"dms_sorting\": false,\n" +
                "    \"resource_code\": \"\",\n" +
                "    \"sender\": {\n" +
                "        \"address\": {\n" +
                "            \"city\": \"泉州市\",\n" +
                "            \"detail\": \"灵秀镇子江路15号\",\n" +
                "            \"district\": \"鲤城区\",\n" +
                "            \"province\": \"福建省\"\n" +
                "        },\n" +
                "        \"mobile\": \"13960280440\",\n" +
                "        \"name\": \"吴远金\",\n" +
                "        \"phone\": \"\"\n" +
                "    },\n" +
                "    \"store_code\": \"\",\n" +
                "    \"trade_order_info_dtos\": [\n" +
                "        {\n" +
                "            \"object_id\": \"7329529\",\n" +
                "            \"order_info\": {\n" +
                "                \"order_channels_type\": \"TB\",\n" +
                "                \"trade_order_list\": [\n" +
                "                    \"7329529\"\n" +
                "                ]\n" +
                "            },\n" +
                "            \"package_info\": {\n" +
                "                \"id\": \"225\",\n" +
                "                \"items\": [\n" +
                "                    {\n" +
                "                        \"count\": 1,\n" +
                "                        \"name\": \"赠品：自提件\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"volume\": 1,\n" +
                "                \"weight\": 1\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"address\": {\n" +
                "                    \"city\": \"吕梁市\",\n" +
                "                    \"detail\": \"凤山街道北川河东路善水新苑二单元1902户\",\n" +
                "                    \"district\": \"离石区\",\n" +
                "                    \"province\": \"山西省\"\n" +
                "                },\n" +
                "                \"mobile\": \"15869015241\",\n" +
                "                \"name\": \"吴家\",\n" +
                "                \"phone\": \"\"\n" +
                "            },\n" +
                "            \"template_url\": \"http:\\\\/\\\\/cloudprint.cainiao.com\\\\/template\\\\/standard\\\\/290659\\\\/42\",\n" +
                "            \"user_id\": 271\n" +
                "        },\n" +
                "        {\n" +
                "            \"object_id\": \"7329530\",\n" +
                "            \"order_info\": {\n" +
                "                \"order_channels_type\": \"TB\",\n" +
                "                \"trade_order_list\": [\n" +
                "                    \"7329530\"\n" +
                "                ]\n" +
                "            },\n" +
                "            \"package_info\": {\n" +
                "                \"id\": \"225\",\n" +
                "                \"items\": [\n" +
                "                    {\n" +
                "                        \"count\": 1,\n" +
                "                        \"name\": \"赠品：自提件\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"volume\": 1,\n" +
                "                \"weight\": 1\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"address\": {\n" +
                "                    \"city\": \"金华市\",\n" +
                "                    \"detail\": \"西城街道杨官路118号\",\n" +
                "                    \"district\": \"永康市\",\n" +
                "                    \"province\": \"浙江省\"\n" +
                "                },\n" +
                "                \"mobile\": \"15869015241\",\n" +
                "                \"name\": \"吴家\",\n" +
                "                \"phone\": \"\"\n" +
                "            },\n" +
                "            \"template_url\": \"http:\\\\/\\\\/cloudprint.cainiao.com\\\\/template\\\\/standard\\\\/290659\\\\/42\",\n" +
                "            \"user_id\": 271\n" +
                "        },\n" +
                "        {\n" +
                "            \"object_id\": \"7329531\",\n" +
                "            \"order_info\": {\n" +
                "                \"order_channels_type\": \"TB\",\n" +
                "                \"trade_order_list\": [\n" +
                "                    \"7329531\"\n" +
                "                ]\n" +
                "            },\n" +
                "            \"package_info\": {\n" +
                "                \"id\": \"225\",\n" +
                "                \"items\": [\n" +
                "                    {\n" +
                "                        \"count\": 1,\n" +
                "                        \"name\": \"赠品：自提件\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"volume\": 1,\n" +
                "                \"weight\": 1\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"address\": {\n" +
                "                    \"city\": \"营口市\",\n" +
                "                    \"detail\": \"石桥街道二零二国道佳美馨居2号楼\",\n" +
                "                    \"district\": \"大石桥市\",\n" +
                "                    \"province\": \"辽宁省\"\n" +
                "                },\n" +
                "                \"mobile\": \"15869015241\",\n" +
                "                \"name\": \"吴家\",\n" +
                "                \"phone\": \"\"\n" +
                "            },\n" +
                "            \"template_url\": \"http:\\\\/\\\\/cloudprint.cainiao.com\\\\/template\\\\/standard\\\\/290659\\\\/42\",\n" +
                "            \"user_id\": 271\n" +
                "        },\n" +
                "        {\n" +
                "            \"object_id\": \"7329532\",\n" +
                "            \"order_info\": {\n" +
                "                \"order_channels_type\": \"TB\",\n" +
                "                \"trade_order_list\": [\n" +
                "                    \"7329532\"\n" +
                "                ]\n" +
                "            },\n" +
                "            \"package_info\": {\n" +
                "                \"id\": \"225\",\n" +
                "                \"items\": [\n" +
                "                    {\n" +
                "                        \"count\": 1,\n" +
                "                        \"name\": \"赠品：自提件\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"volume\": 1,\n" +
                "                \"weight\": 1\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"address\": {\n" +
                "                    \"city\": \"聊城市\",\n" +
                "                    \"detail\": \"青年路街道嘉和苑小区3号楼5单元602\",\n" +
                "                    \"district\": \"临清市\",\n" +
                "                    \"province\": \"山东省\"\n" +
                "                },\n" +
                "                \"mobile\": \"15869015241\",\n" +
                "                \"name\": \"吴家\",\n" +
                "                \"phone\": \"\"\n" +
                "            },\n" +
                "            \"template_url\": \"http:\\\\/\\\\/cloudprint.cainiao.com\\\\/template\\\\/standard\\\\/290659\\\\/42\",\n" +
                "            \"user_id\": 271\n" +
                "        },\n" +
                "        {\n" +
                "            \"object_id\": \"7329533\",\n" +
                "            \"order_info\": {\n" +
                "                \"order_channels_type\": \"TB\",\n" +
                "                \"trade_order_list\": [\n" +
                "                    \"7329533\"\n" +
                "                ]\n" +
                "            },\n" +
                "            \"package_info\": {\n" +
                "                \"id\": \"225\",\n" +
                "                \"items\": [\n" +
                "                    {\n" +
                "                        \"count\": 1,\n" +
                "                        \"name\": \"赠品：自提件\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"volume\": 1,\n" +
                "                \"weight\": 1\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"address\": {\n" +
                "                    \"city\": \"沧州市\",\n" +
                "                    \"detail\": \"河城街镇河城街中心校\",\n" +
                "                    \"district\": \"献县\",\n" +
                "                    \"province\": \"河北省\"\n" +
                "                },\n" +
                "                \"mobile\": \"15869015241\",\n" +
                "                \"name\": \"吴家\",\n" +
                "                \"phone\": \"\"\n" +
                "            },\n" +
                "            \"template_url\": \"http:\\\\/\\\\/cloudprint.cainiao.com\\\\/template\\\\/standard\\\\/290659\\\\/42\",\n" +
                "            \"user_id\": 271\n" +
                "        }\n" +
                "    ]\n" +
                "}";


        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        data.put("request_data",reqeustData );

        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoCaiNiaoWayBillBatchGetUrl ,data);

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

        data.put("tid", "1264569985994565830");
        data.put("memo", "这一个使用API添加的备注");
        data.put("flag", "1");

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
        data.put("timestamp", timestamp.toString());
        // 订单号
        data.put("tid", "1394872671557565830");
        // 卖家交易备注旗帜，可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)，默认值为0
        data.put("flag", "2");
        // 卖家交易备注。最大长度: 1000个字节
        data.put("memo", "这是通过api写的备注");
        // 是否对memo的值置空若为true，则不管传入的memo字段的值是否为空，都将会对已有的memo值清空，慎用；
        // 若用false，则会根据memo是否为空来修改memo的值：若memo为空则忽略对已有memo字段的修改，若memo非空，则使用新传入的memo覆盖已有的memo的值
        data.put("reset", "false");

        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoUpdateMemoUrl ,data);

    }



    private  void doHttpRequest(String url ,  Map<String, String> data ){
        String result ="";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( url);
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
