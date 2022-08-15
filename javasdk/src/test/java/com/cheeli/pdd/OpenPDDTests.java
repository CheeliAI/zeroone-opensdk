package com.cheeli.pdd;

import com.alibaba.fastjson.JSON;
import com.cheeli.Config;
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

/**
 * 场景：拼多多的接入， 合作伙伴使用，可管理其他授权的店铺。即可以管理多家店铺
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenPDDTests.class )
public class OpenPDDTests {

    @Test
    public void contextLoads() {
    }



    /**
     *  查询物流公司列表
     * @throws Exception
     */
    @Test
    public void getLogisticesCompany() throws Exception {

        String result ="";
        String tb_seller_nick = Config.PddSellerNick ; // 拼多多卖家
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.PddLogisticesCompanyUrl );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

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
     *  订单发货
     * @throws Exception
     */
    @Test
    public void logisticesCompanyOnineSend() throws Exception {

        String result ="";
        String sellerNick = Config.PddSellerNick ; // 拼多多卖家
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.PddLogisticesOnlineSendUrl );
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", sellerNick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());


        // 订单号 ,必填
        data.put("order_sn", "220429-167987137462221");
       //  快递公司编号，必填，  可通过查询快递公司列表获取，注意是Id不是code
        data.put("logistics_id","115"); //121 yunda
        // 快递单号,必填
        data.put("tracking_number","78255494972314");

        // 发货个性内容，非必填。 支持imei（手机串号），deviceSn（设备序列号）内容。
        // 形如：imei=11,22,3333; 以 “imei=” 开头，以英文分号“;”结尾，中间为手机IMEI串号信息，多个串号以英文逗号 “,”拼接释义：该订单包含三个手机IMEI串号，分别为11、22和3333；其他内容的格式同理。
        data.put("feature","");

        // 退货地址的id，不填则取商家默认地址， 非必填
        data.put("refund_address_id","");

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
     *  获取订单列表
     * @throws Exception
     */
    @Test
    public void getOrderList() throws Exception {

        String result ="";
        String tb_seller_nick = Config.PddSellerNick ; // 拼多多卖家
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.PddOrderListUrl );
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        //  必填        发货状态，1：待发货，2：已发货待签收，3：已签收 5：全部
        data.put("order_status", "1");
        // 必填	售后状态 1：无售后或售后关闭，2：售后处理中，3：退款中，4： 退款成功 5：全部
        data.put("refund_status", "1");
        // 必填，成交时间开始时间的时间戳，指格林威治时间 1970 年 01 月 01 日 00 时 00 分 00 秒(北京时间 1970 年 01 月 01 日 08 时 00 分 00 秒)起至现在的总秒数
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);


        long startDate = calendar.getTime().getTime() / 1000;
//        data.put("start_confirm_at", "1610301600");
        data.put("start_confirm_at", String.valueOf(startDate));

       // 必填，成交时间结束时间的时间戳，指格林威治时间 1970 年 01 月 01 日 00 时 00 分 00 秒(北京时间 1970 年 01 月 01 日 08 时 00 分 00 秒)起至现在的总秒数 PS：开始时间结束时间间距不超过 24 小时
        long endDate = new Date().getTime() / 1000 ;
        data.put("end_confirm_at", String.valueOf(endDate));
//        data.put("end_confirm_at", "1610377200" );
       // 必填	返回页码 默认 1，页码从 1 开始 PS：当前采用分页返回，数量和页数会一起传，如果不传，则采用 默认值
        data.put("page", "1");
        //   必填	返回数量，默认 100。最大 100
        data.put("page_size", "100");
        // 非必填	订单类型 0-普通订单 ，1- 定金订单
        data.put("trade_type", "");
        // 非必填	是否启用has_next的分页方式，如果指定true,则返回的结果中不包含总记录数，但是会新增一个是否存在下一页的的字段，通过此种方式获取增量交易，效率在原有的基础上有80%的提升。
        data.put("use_has_next", "false");

        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));

        doHttpRequest(Config.PddOrderListUrl, data);


    }

    /**
     *  获取增量订单列表
     * @throws Exception
     */
    @Test
    public void getPddOrderListIncrement() throws Exception {

        String result ="";
        String sellerNick = Config.PddSellerNick ; // 拼多多卖家
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.PddOrderListUrl );
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", sellerNick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        // 必填 订单类型（是否抽奖订单），0-全部，1-非抽奖订单，2-抽奖订单
        data.put("is_lucky_flag", "0");
        //  必填        发货状态，1：待发货，2：已发货待签收，3：已签收 5：全部
        data.put("order_status", "5");
        // 必填	售后状态 1：无售后或售后关闭，2：售后处理中，3：退款中，4： 退款成功 5：全部
        data.put("refund_status", "5");
        // 必填，成交时间开始时间的时间戳，指格林威治时间 1970 年 01 月 01 日 00 时 00 分 00 秒(北京时间 1970 年 01 月 01 日 08 时 00 分 00 秒)起至现在的总秒数
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - 30);


        long startDate = calendar.getTime().getTime() / 1000;
//        data.put("start_updated_at", "1658907680");
        data.put("start_updated_at", String.valueOf(startDate));

       // 必填，成交时间结束时间的时间戳，指格林威治时间 1970 年 01 月 01 日 00 时 00 分 00 秒(北京时间 1970 年 01 月 01 日 08 时 00 分 00 秒)起至现在的总秒数 PS：开始时间结束时间间距不超过 30分钟
        long endDate = new Date().getTime() / 1000  ;
        data.put("end_updated_at", String.valueOf(endDate));
//        data.put("end_updated_at", "1658907880");
//        data.put("end_updated_at", "1622086044" );
       // 必填	返回页码 默认 1，页码从 1 开始 PS：当前采用分页返回，数量和页数会一起传，如果不传，则采用 默认值
        data.put("page", "1");
        data.put("page_size", "30");

        // 非必填	订单类型 0-普通订单 ，1- 定金订单
        data.put("trade_type", "");
        // 非必填	是否启用has_next的分页方式，如果指定true,则返回的结果中不包含总记录数，但是会新增一个是否存在下一页的的字段，通过此种方式获取增量交易，效率在原有的基础上有80%的提升。
        data.put("use_has_next", "false");

        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));

        doHttpRequest( Config.PddOrderListIncrementUrl, data);


    }

    /**
     *  获取订单列表 V2版本
     * @throws Exception
     */
    @Test
    public void getOrderListV2() throws Exception {

        String result ="";
        String tb_seller_nick = Config.PddSellerNick ; // 拼多多卖家
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.PddOrderV2ListUrl );
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        //  必填        发货状态，1：待发货，2：已发货待签收，3：已签收 5：全部
        data.put("order_status", "5");
        // 必填	售后状态 1：无售后或售后关闭，2：售后处理中，3：退款中，4： 退款成功 5：全部
        data.put("refund_status", "5");
        // 注意日期格式
        data.put("start_confirm_at", "2022-04-17 00:00:33");
         // 注意日期格式
          data.put("end_confirm_at", "2022-08-17 23:56:33" );
        // 必填	返回页码 默认 1，页码从 1
        data.put("page", "1");
        // 必填	返回数量， 最大 30
        data.put("page_size", "30");

        // 传空为全部， 订单备注标记，1-红色，2-黄色，3-绿色，4-蓝色，5-紫色
        data.put("remark_tag", "");

        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));

        doHttpRequest(Config.PddOrderV2ListUrl, data);


    }

    /**
     *  获取订单详情
     * @throws Exception
     */
    @Test
    public void getOrderDetail() throws Exception {

        String result ="";
        String seller_nick = Config.PddSellerNick ; // 拼多多卖家账号
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.PddOrderDetailUrl );
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        //  必填 ，订单号
        data.put("order_sn", "220731-240103003520680");
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



    @Test
    public void getOrderDetailV2() throws Exception {

        String result ="";
        String seller_nick = Config.PddSellerNick ; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        //  必填 ，订单号
//        data.put("order_sn", "210317-001532562392221");
        data.put("order_sn", "220429-167987137462221");
        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));

        doHttpRequest(Config.PddOrderV2DetailUrl, data);



    }

    /**
     * 通过面单号查询面单信息
     * @throws Exception
     */
    @Test
    public void PddWayBillSearchByNo() throws Exception {

        String result ="";
        String seller_nick = Config.PddSellerNick ; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());


        String requestData ="[" +
                "    {\"object_id\":\"aa2832\",\"waybill_code\":\"9866025654820\", \"wp_code\":\"YZXB\"}," +
                "    {\"object_id\":\"aa2833\",\"waybill_code\":\"9866025654823\", \"wp_code\":\"YZXB\"}" +
                "]";

          requestData ="[" +
                "    {\"object_id\":\"aa2832\",\"waybill_code\":\"SF1359992464402\", \"wp_code\":\"SF\"}," +
                "    {\"object_id\":\"aa2833\",\"waybill_code\":\"SF1370123807304\", \"wp_code\":\"SF\"}" +
                "]";

        data.put("request_data", requestData );
        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        doHttpRequest(Config.PddWayBillSearchByNoUrl, data);

    }


    /**
     *
     * @throws Exception
     */
    @Test
    public void getCloudPrintStdTpl() throws Exception {

        String result ="";
        String seller_nick = Config.PddSellerNick ; // 拼多多卖家账号
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.PddCloudPrintStdTplGetUrl );
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        //   非必填	快递公司code
         data.put("wp_code", "SF");
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
     * 拼多多修改订单备注
     * @throws Exception
     */
    @Test
    public void
    pddOrderNoteUpdate() throws Exception {

        String result ="";
        String seller_nick = Config.PddSellerNick ; // 拼多多卖家账号
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.PddOrderNoteUpdateUrl );
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        // 必填，订单号
//       data.put("order_sn", "220429-401625660982221");//my
       data.put("order_sn", "220417-347581991741092");
       data.put("note", "try2222");

       // 非必填	，备注标记：1-红色，2-黄色，3-绿色，4-蓝色，5-紫色，tag与tag_name关联，都入参或都不入参
       data.put("tag", "2");
       // 非必填，标记名称；长度最大为3个字符，tag与tag_name关联，都入参或都不入参
       data.put("tag_name", "黄色");

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
     * 查询面单服务订购及面单使用情况
     * @throws Exception
     */
    @Test
    public void PddWayBillSearch() throws Exception {

        String result ="";
        String seller_nick = Config.PddSellerNick ; // 拼多多卖家账号
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.PddWayBillSearchUrl );
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        //   非必填	快递公司code
//         data.put("wp_code", "SF");
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
     *  拼多多电子面单取号
     * @throws Exception
     */
    @Test
    public void PddWayBillGet() throws Exception {

        String result = "";
        String seller_nick = Config.PddSellerNick; // 拼多多卖家账号

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        String requestData = "{" +
                "    \"sender\":{" +
                "        \"address\":{" +
                "            \"city\": \"上海市\", " +
                "            \"detail\": \"商城路738\"," +
                "            \"district\": \"浦东新区\"," +
                "            \"province\": \"上海市\"," +
                "            \"town\":\"陆家嘴街道\"" +
                "        }," +
                "        \"mobile\":\"18817834074\"," +
                "        \"name\":\"test\"," +
                "        \"phone\":\"\"" +
                "    }," +
                "    \"trade_order_info_dtos\":[" +
                "        {" +
                "            \"logistics_services\": {\"SVC-COD\": {\"value\": \"200\" }}," +
                "            \"object_id\":\"1000\"," +
                "            \"order_info\":{" +
                "                \"order_channels_type\":\"pdd\"," +
                "                \"trade_order_list\":[" +
                "                    \"210317-001532562392221\"" +
                "                ]" +
                "            }," +
                "            \"package_info\":{" +
                "                \"goods_description\":\"11\"," +
                "                \"id\":\"10\"," +
                "                \"items\":[" +
                "                    {" +
                "                        \"count\":1," +
                "                        \"name\":\"A4打印纸\"" +
                "                    }" +
                "                ]," +
                "                \"packaging_description\":\"A4打印纸的包装说明\"," +
                "                \"total_packages_count\":1," +
                "                \"volume\":22," +
                "                \"weight\":22" +
                "            }," +
                "            \"recipient\":{" +
                "                \"address\":{" +
                "                    \"city\": \"上海市\", " +
                "                    \"detail\": \"~AgAAAACjmWAIZ1vG5AG+pNSXy3yNfQqkPwX83TI8H0IYyf0f3qi8Bdzs2b5+nfKniLc2NB+tLVUhUMWwHkN8dg==~4Uc5fqNUxr5Sntd4fXM7UZAPXpVNVVXr~1~~\"," +
                "                    \"district\": \"浦东新区\"," +
                "                    \"province\": \"上海市\"," +
                "                    \"town\":\"陆家嘴街道\"" +
                "                }," +
            //    "                \"mobile\":\"$WsuJt4Yh3K7a$AgAAAACjmWAGZ1vG5ABLUicfAzPuXzSdjyz1biPoE6E=$1$$\"," +
                "                \"name\":\"~AgAAAACjmWAFZ1vG5ABcHJJ+v1G1LcKH2BRzsYjGe/s=~niqTGhCNifXk~1~~\"," +
                "                \"phone\":\"021-87678765\"" +
                "            }," +
                "            \"template_url\":\"https://file-link.pinduoduo.com/xlobo_std\"," +
                "            \"user_id\":133214" +
                "        }," +

                "        {" +
                "            \"logistics_services\":\"\"," +
                "            \"object_id\":\"1002\"," +
                "            \"order_info\":{" +
                "                \"order_channels_type\":\"pdd\"," +
                "                \"trade_order_list\":[" +
                "                    \"210317-001532562392222\"" +
                "                ]" +
                "            }," +
                "            \"package_info\":{" +
                "                \"goods_description\":\"11\"," +
                "                \"id\":\"10\"," +
                "                \"items\":[" +
                "                    {" +
                "                        \"count\":1," +
                "                        \"name\":\"A4打印纸\"" +
                "                    }" +
                "                ]," +
                "                \"packaging_description\":\"A4打印纸的包装说明\"," +
                "                \"total_packages_count\":1," +
                "                \"volume\":22," +
                "                \"weight\":22" +
                "            }," +
                "            \"recipient\":{" +
                "                \"address\":{" +
                "                    \"city\": \"上海市\", " +
                "                    \"detail\": \"2~AgAAA2CjmWAIZ1vG5AG+pNSXy3yNfQqkPwX83TI8H0IYyf0f3qi8Bdzs2b5+nfKniLc2NB+tLVUhUMWwHkN8dg==~4Uc5fqNUxr5Sntd4fXM7UZAPXpVNVVXr~1~~\"," +
                "                    \"district\": \"浦东新区\"," +
                "                    \"province\": \"上海市\"," +
                "                    \"town\":\"陆家嘴街道\"" +
                "                }," +
             //   "                \"mobile\":\"3$WsuJt4Yh3K7a$AgAAAACjmWAGZ1vG5ABLUicfAzPuXzSdjyz1biPoE6E=$1$$\"," +
                "                \"name\":\"23~AgAAAACjmWAFZ1vG5ABcHJJ+v1G1LcKH2BRzsYjGe/s=~niqTGhCNifXk~1~~\"," +
                "                \"phone\":\"021-87678765\"" +
                "            }," +
                "            \"template_url\":\"https://file-link.pinduoduo.com/xlobo_std\"," +
                "            \"user_id\":133214" +
                "        }" +

                "    ]," +
                "    \"wp_code\":\"YZXB\"" + "}";


//      String requestData ="{\"trade_order_info_dtos\":[{\"logistics_services\":\"\",\"template_url\":\"https://file-link.pinduoduo.com/yunda_one\",\"user_id\":115604514,\"recipient\":{\"address\":{\"province\":\"浙江省\",\"city\":\"杭州市\",\"district\":\"西湖区\",\"detail\":\"江南大道88号\"},\"phone\":\"\",\"mobile\":\"13900000000\",\"name\":\"张三\"},\"object_id\":\"1\",\"package_info\":{\"volume\":2,\"total_packages_count\":1,\"goods_description\":\"\",\"weight\":100,\"id\":\"231\",\"items\":[{\"count\":1,\"name\":\"test-1\"}],\"packaging_description\":\"\"},\"order_info\":{\"order_channels_type\":\"pdd\",\"trade_order_list\":[\"2702047085795\"]}}],\"sender\":{\"address\":{\"province\":\"湖南省\",\"city\":\"长沙市\",\"district\":\"岳麓区\",\"detail\":\"麓谷企业广场F1栋1688\"},\"phone\":\"18367826718\",\"mobile\":\"18367826718\",\"name\":\"周富贵\"},\"wp_code\":\"YUNDA2\"}";


//        requestData = "{\"trade_order_info_dtos\":[{\"logistics_services\":{\"INSURE\":{\"value\":0}},\"object_id\":\"1473145222379868160\",\"order_info\":{\"order_channels_type\":\"pdd\",\"trade_order_list\":[\"8000000000000\"]},\"template_url\":\"https://file-link.pinduoduo.com/sfky_std\",\"user_id\":0,\"recipient\":{\"address\":{\"town\":\"\",\"city\":\"杭州市\",\"province\":\"浙江省\",\"district\":\"西湖区\",\"detail\":\"江南大道88号\"},\"mobile\":\"13900000000\",\"phone\":\"\",\"name\":\"张三\"},\"package_info\":{\"total_packages_count\":1,\"goods_description\":\"小香包-广州顺丰\",\"weight\":1,\"packaging_description\":\"小香包-广州顺丰\",\"volume\":1,\"id\":\"1473145222379868160\",\"items\":[{\"count\":1,\"name\":\"小香包-广州顺丰\"}]}}],\"nick\":\"pdd70724554437\",\"sender\":{\"address\":{\"town\":\"\",\"city\":\"广州市\",\"province\":\"广东省\",\"district\":\"白云区\",\"detail\":\"白云区白云仓\"},\"mobile\":\"18153860107\",\"phone\":\"\",\"name\":\"云梦\"},\"appId\":\"AOfRx7ZAX3oMJkp7\",\"appSecret\":\"jfiLS83Q2JgjN0Ef\",\"wp_code\":\"SFKY\"}";
//        requestData = "{\"trade_order_info_dtos\":[{\"logistics_services\":\"\",\"object_id\":\"1473145222379868160\",\"order_info\":{\"order_channels_type\":\"pdd\",\"trade_order_list\":[\"8000000000000\"]},\"template_url\":\"https://file-link.pinduoduo.com/sfky_std\",\"user_id\":0,\"recipient\":{\"address\":{\"town\":\"\",\"city\":\"杭州市\",\"province\":\"浙江省\",\"district\":\"西湖区\",\"detail\":\"江南大道88号\"},\"mobile\":\"13900000000\",\"phone\":\"\",\"name\":\"张三\"},\"package_info\":{\"total_packages_count\":1,\"goods_description\":\"小香包-广州顺丰\",\"weight\":1,\"packaging_description\":\"小香包-广州顺丰\",\"volume\":1,\"id\":\"1473145222379868160\",\"items\":[{\"count\":1,\"name\":\"小香包-广州顺丰\"}]}}],\"nick\":\"pdd70724554437\",\"sender\":{\"address\":{\"town\":\"\",\"city\":\"广州市\",\"province\":\"广东省\",\"district\":\"白云区\",\"detail\":\"白云发货云仓\"},\"mobile\":\"18153860107\",\"phone\":\"\",\"name\":\"云梦\"},\"appId\":\"AOfRx7ZAX3oMJkp7\",\"appSecret\":\"jfiLS83Q2JgjN0Ef\",\"wp_code\":\"FENGWANG\"}";
        requestData = "{ \n" +
                "    \"sender\":{\n" +
                "        \"address\":{\n" +
                "            \"city\":\"上海市\",\n" +
                "            \"detail\":\"商城路738\",\n" +
                "            \"district\":\"浦东新区\",\n" +
                "            \"province\":\"上海市\",\n" +
                "            \"town\":\"\"\n" +
                "        },\n" +
                "        \"mobile\":\"18817834074\",\n" +
                "        \"name\":\"test\",\n" +
                "        \"phone\":\"\"\n" +
                "    },\n" +
                "    \"trade_order_info_dtos\":[\n" +
                "        {\n" +
                "            \"logistics_services\":\"\",\n" +
                "            \"object_id\":\"1520603511442911234\",\n" +
                "            \"order_info\":{\n" +
                "                \"order_channels_type\":\"pdd\",\n" +
                "                \"trade_order_list\":[\n" +
                "                    \"1\"\n" +
                "                ]\n" +
                "            },\n" +
                "            \"package_info\":{\n" +
                "                \"goods_description\":\"香包-广州韵达\",\n" +
                "                \"id\":\"1520603511442911234\",\n" +
                "                \"items\":[\n" +
                "                    {\n" +
                "                        \"count\":1,\n" +
                "                        \"name\":\"香包-广州韵达\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"packaging_description\":\"香包-广州韵达\",\n" +
                "                \"total_packages_count\":1,\n" +
                "                \"volume\":1,\n" +
                "                \"weight\":1\n" +
                "            },\n" +
                "            \"recipient\":{\n" +
                "                \"address\":{\n" +
                "                    \"city\":\"汕头市\",\n" +
                "                    \"detail\":\"隆都到货打电话不打投诉\",\n" +
                "                    \"district\":\"澄海区\",\n" +
                "                    \"province\":\"广东省\",\n" +
                "                    \"town\":\"\"\n" +
                "                },\n" +
                "                \"mobile\":\"19189670214\",\n" +
                "                \"name\":\"柳言\",\n" +
                "                \"phone\":\"\"\n" +
                "            },\n" +
                "            \"template_url\":\"https://file-link.pinduoduo.com/yunda_std\",\n" +
                "            \"user_id\":0\n" +
                "        }\n" +
                "    ],\n" +
                "    \"wp_code\":\"SF\"\n" +
                "}";

        //   非必填	快递公司code
        data.put("request_data", requestData);
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        doHttpRequest(Config.PddWayBillGetUrl, data);

    }


    @Test
    public void PddWayBillUpdate() throws Exception {
        String seller_nick = Config.PddSellerNick ; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        String requestData = "{" +
                "     \"object_id\":\"1000\"," +
                "     \"sender\":{" +
                "        \"address\":{" +
                "            \"city\": \"上海市\", " +
                "            \"detail\": \"商城路738\"," +
                "            \"district\": \"浦东新区\"," +
                "            \"province\": \"上海市\"," +
                "            \"town\":\"陆家嘴街道\"" +
                "        }," +
                " " +
                "        \"mobile\":\"13813122333\"," +
                "        \"name\":\"陈先生\"," +
                "        \"phone\":\"\"" +
                "    }," +
                "    \"package_info\":{" +
                "                \"items\":[" +
                "                    {" +
                "                        \"count\":1," +
                "                        \"name\":\"A4打印纸\"" +
                "                    }" +
                "                ]," +
                "                \"volume\":22," +
                "                \"weight\":22" +
                "            }," +
                "     \"recipient\":{" +
                "                \"address\":{" +
                "                    \"city\": \"上海市\", " +
                "                    \"detail\": \"~AgAAAACjmWAIZ1vG5AG+pNSXy3yNfQqkPwX83TI8H0IYyf0f3qi8Bdzs2b5+nfKniLc2NB+tLVUhUMWwHkN8dg==~4Uc5fqNUxr5Sntd4fXM7UZAPXpVNVVXr~1~~\"," +
                "                    \"district\": \"浦东新区\"," +
                "                    \"province\": \"上海市\"," +
                "                    \"town\":\"陆家嘴街道\"" +
                "                }," +
                "                \"mobile\":\"$WsuJt4Yh3K7a$AgAAAACjmWAGZ1vG5ABLUicfAzPuXzSdjyz1biPoE6E=$1$$\"," +
                "                \"name\":\"~AgAAAACjmWAFZ1vG5ABcHJJ+v1G1LcKH2BRzsYjGe/s=~niqTGhCNifXk~1~~\"," +
                "                \"phone\":\"\"" +
                "     }," +
                "            \"template_url\":\"https://file-link.pinduoduo.com/xlobo_std\"," +
                "            \"waybill_code\":\"9866025654820\" ," +
                     "    \"wp_code\":\"YZXB\"" +"}";

        data.put("request_data",  requestData);
        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        doHttpRequest(Config.PddWayBillUpdateUrl, data);

    }





    /**
     *  拼多多电子面单取号 V2版，优化了参数，不需要输入收件相关的密文
     * @throws Exception
     */
    @Test
    public void PddWayBillV2Get() throws Exception {

        String seller_nick = Config.PddSellerNick ; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
//        // 批量取号
//        String requestData = "{\n" +
//                "    \"sender\": {\n" +
//                "        \"address\": {\n" +
//                "            \"city\": \"上海市\",\n" +
//                "            \"detail\": \"东方路238\",\n" +
//                "            \"district\": \"浦东新区\",\n" +
//                "            \"province\": \"上海市\",\n" +
//                "            \"town\": \"陆家嘴街道\"\n" +
//                "        },\n" +
//                "        \"mobile\": \"13812197833\",\n" +
//                "        \"name\": \"陈先生\",\n" +
//                "        \"phone\": \"\"\n" +
//                "    },\n" +
//                "    \"trade_order_info_dtos\": [{\n" +
//                "        \"logistics_services\": \"\",\n" +
//                "        \"object_id\": \"1000\",\n" +
//                "        \"order_info\": {\n" +
//                "            \"order_channels_type\": \"pdd\",\n" +
//                "            \"trade_order_list\": [\"210324-595938509442221\"]\n" +
//                "        },\n" +
//                "        \"package_info\": {\n" +
//                "            \"goods_description\": \"11\",\n" +
//                "            \"id\": \"10\",\n" +
//                "            \"items\": [{\n" +
//                "                \"count\": 1,\n" +
//                "                \"name\": \"A4打印纸\"\n" +
//                "            }],\n" +
//                "            \"packaging_description\": \"A4打印纸的包装说明\",\n" +
//                "            \"total_packages_count\": 1,\n" +
//                "            \"volume\": 22,\n" +
//                "            \"weight\": 22\n" +
//                "        }, \n" +
//                "        \"template_url\": \"https://file-link.pinduoduo.com/xlobo_std\",\n" +
//                "        \"user_id\": 133214\n" +
//                "    },\n" +
//                "\n" +
//                "    {\n" +
//                "        \"logistics_services\": \"\",\n" +
//                "        \"object_id\": \"1002\",\n" +
//                "        \"order_info\": {\n" +
//                "            \"order_channels_type\": \"pdd\",\n" +
//                "            \"trade_order_list\": [\"210304-155155172182221\"]\n" +
//                "        },\n" +
//                "        \"package_info\": {\n" +
//                "            \"goods_description\": \"11\",\n" +
//                "            \"id\": \"11\",\n" +
//                "            \"items\": [{\n" +
//                "                \"count\": 1,\n" +
//                "                \"name\": \"A4打印纸\"\n" +
//                "            }],\n" +
//                "            \"packaging_description\": \"A4打印纸的包装说明\",\n" +
//                "            \"total_packages_count\": 1,\n" +
//                "            \"volume\": 22,\n" +
//                "            \"weight\": 22\n" +
//                "        },\n" +
//                "        \n" +
//                "        \"template_url\": \"https://file-link.pinduoduo.com/xlobo_std\",\n" +
//                "        \"user_id\": 133214\n" +
//                "    }\n" +
//                "\n" +
//                "   ],\n" +
//                "    \"wp_code\": \"YZXB\"\n" +
//                "}";
//        String requestData = "{\n" +
//                "\t\t\"sender\": {\n" +
//                "\t\t\t\"address\": {\n" +
//                "\t\t\t\t\"city\": \"嘉兴市\",\n" +
//                "\t\t\t\t\"detail\": \"濮院镇紫金小区111号\",\n" +
//                "\t\t\t\t\"district\": \"桐乡市\",\n" +
//                "\t\t\t\t\"province\": \"浙江省\",\n" +
//                "\t\t\t\t\"town\": \"\",\n" +
//                "\t\t\t\t\"country\": \"\"\n" +
//                "\t\t\t},\n" +
//                "\t\t\t\"mobile\": \"15858933093\",\n" +
//                "\t\t\t\"name\": \"张帆勇\",\n" +
//                "\t\t\t\"phone\": \"\"\n" +
//                "\t\t},\n" +
//                "\t\t\"trade_order_info_dtos\": [{\n" +
//                "\t\t\t\"logistics_services\": \"\",\n" +
//                "\t\t\t\"object_id\": \"090916311645842529\",\n" +
//                "\t\t\t\"order_info\": {\n" +
//                "\t\t\t\t\"order_channels_type\": \"pdd\",\n" +
//                "\t\t\t\t\"trade_order_list\": [\"210909-305156617100066\"]\n" +
//                "\t\t\t},\n" +
//                "\t\t\t\"package_info\": {\n" +
//                "\t\t\t\t\"goods_description\": \"\",\n" +
//                "\t\t\t\t\"id\": \"090916311645842529\",\n" +
//                "\t\t\t\t\"items\": [{\n" +
//                "\t\t\t\t\t\"count\": 1,\n" +
//                "\t\t\t\t\t\"name\": \"111X1 \"\n" +
//                "\t\t\t\t}],\n" +
//                "\t\t\t\t\"packaging_description\": \"\"\n" +
//                "\t\t\t},\n" +
//                "\t\t\t\"template_url\": \"https:\\/\\/file-link.pinduoduo.com\\/yzxb_one\",\n" +
//                "\t\t\t\"user_id\": 0\n" +
//                "\t\t}],\n" +
//                "\t\t\"wp_code\": \"YZXB\"\n" +
//                "\t}";


       // String requestData = "{\"sender\":{\"address\":{\"province\":\"\\u6d59\\u6c5f\\u7701\",\"city\":\"\\u676d\\u5dde\\u5e02\",\"district\":\"\\u8427\\u5c71\\u533a\",\"detail\":\"\\u57a6\\u8f896\\u8def500\\u53f7\",\"town\":\"\"},\"mobile\":\"13500003425\",\"name\":\"\\u9648\\u4fca\\u5b8f\",\"phone\":\"\"},\"trade_order_info_dtos\":[{\"logistics_services\":\"\",\"object_id\":\"\",\"order_info\":{\"order_channels_type\":\"pdd\",\"trade_order_list\":[\"211108-367368682951606\"]},\"package_info\":{\"goods_description\":\"\",\"id\":\"\",\"items\":[{\"count\":1,\"name\":\"A4\\u6253\\u5370\\u7eb8\"}],\"packaging_description\":\"A4\\u6253\\u5370\\u7eb8\\u7684\\u5305\\u88c5\\u8bf4\\u660e\",\"total_packages_count\":1,\"volume\":22,\"weight\":22},\"template_url\":\"https:\\/\\/file-link.pinduoduo.com\\/yunda_std\",\"user_id\":0}],\"wp_code\":\"YUNDA\"}";
        //   非必填	快递公司code

       String requestData ="{\"sender\":{\"address\":{\"city\":\"上海市\",\"detail\":\"商城路738\",\"district\":\"浦东新区\",\"province\":\"上海市\",\"town\":\"\"},\"mobile\":\"18817834074\",\"name\":\"test\",\"phone\":\"18314796663\"},\"trade_order_info_dtos\":[{\"logistics_services\":\"\",\"object_id\":\"1000\",\"order_info\":{\"order_channels_type\":\"pdd\",\"trade_order_list\":[\"220429-167987137462221\"]},\"package_info\":{\"goods_description\":\"11\",\"id\":\"11\",\"items\":[{\"count\":1,\"name\":\"A4打印纸\"}],\"packaging_description\":\"A4打印纸的包装说明\",\"total_packages_count\":1,\"volume\":22,\"weight\":22},\"template_url\":\"https:\\/\\/file-link.pinduoduo.com\\/xlobo_std\",\"user_id\":0}],\"wp_code\":\"SF\"}";
        data.put("request_data",  requestData);
        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        doHttpRequest(Config.PddWayBillV2GetUrl,data);

    }

    @Test
    public void PddOrderBatchGet() throws Exception {

        String result ="";
        String seller_nick = Config.PddSellerNick ; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", seller_nick);
        data.put("tids", "220429-167987137462221,220608-083765502862704");
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        doHttpRequest(Config.PddOrderDetailBatchGetUrl,data);
    }




    /**
     *  拼多多电子面单取号
     * @throws Exception
     */
    @Test
    public void PddWayBillCancel() throws Exception {

        String result ="";
        String seller_nick = Config.PddSellerNick ; // 拼多多卖家账号
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.PddWayBillCancelUrl );
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        //   非必填	 电子面单号
        data.put("waybill_code",  "SF1359992464402");
        //  非必填	 快递公司code
        data.put("wp_code",  "SF");

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
     * 拼多多批量订单解密-不建议使用，会被风控
     * @throws Exception
     */
    @Test
    public void PddOrderDecryptFront() throws Exception {

        String result = "";
        String seller_nick = Config.PddSellerNick; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("request_id", "q282");
        data.put("order_sn", "210928-530301612911731,210928-028146430842787,210928-154227198941895,210928-621821347223483,210928-204949503490595");
//        data.put("order_sn", "210928-028146430842787");
//        data.put("order_sn", "210928-028146430842787");
//        data.put("page_table_id", "22");
//        data.put("decrypt_report_type", "3");
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        doHttpRequest(Config.PddOrderDecryptFrontUrl, data);

    }
    /**
     * 拼多多批量订单解密
     * @throws Exception
     */
    @Test
    public void PddOrderDecryptUrl() throws Exception {

        String result ="";
        String seller_nick = Config.PddSellerNick ; // 拼多多卖家账号
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.PddOrderDecryptUrl );
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
//        String requestData = "[  " +
//                "{ " +
//                "    \"data_tag\": \"210324-137383117442221\", " +
//                "    \"encrypted_data\" : \"~AgAAAACsR/YIKKS3PwCYSjTyfx86NuSnnk2yjjvC8/ncds7W/9mHrVxxtod16Da8~nAcOCnvZBKe6+Os8CPzrXB4Q57wwFRst~0~~\" " +
//                "}, " +
//                "{ " +
//                "    \"data_tag\": \"210324-137383117442221\", " +
//                "    \"encrypted_data\" : \"~AgAAAACsR/YFKKS3PwC/TLT8VlVfZC3hYRBhtE6JdhU=~7rDNKhB7n0mq~0~~\" " +
//                "}, " +
//                "{ " +
//                "    \"data_tag\": \"210324-137383117442221\", " +
//                "    \"encrypted_data\" : \"$j6CIhOBvrLmR$AgAAAACsR/YGKKS3PwCiLZaCRkKjaqqpUiEAJKo9kcM=$0$$\" " +
//                "}   " +
//                "]";

        String requestData = "[ {\"data_tag\":\"220608-658301316622704\",\"encrypted_data\":\"~AgAAAAFsA/UFsQQHYgD/4MbA/Iv1bwzvsPbOfiURzzM=~mPMFaonc~5~~\"}]";

        data.put("request_data",  requestData);
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
