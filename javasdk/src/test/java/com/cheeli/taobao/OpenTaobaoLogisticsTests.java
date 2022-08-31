package com.cheeli.taobao;

import com.alibaba.fastjson.JSON;
import com.cheeli.Config;
import com.cheeli.models.LogisticesBatchSendItem;
import com.cheeli.models.taobao.trade.TradeResponse;
import com.cheeli.models.taobao.waybill.Data;
import com.cheeli.models.taobao.waybill.WayBillResponse;
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
@SpringBootTest(classes = OpenTaobaoLogisticsTests.class )
public class OpenTaobaoLogisticsTests {

    @Test
    public void contextLoads() {
    }





    /**
     * 查询物流公司列表
     *
     * @throws Exception
     */
    @Test
    public void getLogisticesCompany() throws Exception {

        String result = "";
        String tb_seller_nick = Config.TBSellerNick; //要查询支付宝的淘宝商家
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(Config.LogisticesCompanyUrl);

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());


        // 是否查询推荐物流公司.可选值:true,false.如果不提供此参数,将会返回所有支持电话联系的物流公司.
        data.put("is_recommended", "true");
        // 推荐物流公司的下单方式.可选值:offline(电话联系/自己联系),online(在线下单),all(即电话联系又在线下单).
        // 此参数仅仅用于is_recommended 为ture时。就是说对于推荐物流公司才可用.如果不选择此参数将会返回推荐物流中支持电话联系的物流公司.
        data.put("order_mode", "all");
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));

        doHttpRequest(Config.LogisticesCompanyUrl, data);

//        System.out.println(result);

    }


    /**
     * 在线订单发货处理（支持货到付款）
     *
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


        String tb_seller_nick = Config.TBSellerNick; //要查询支付宝的淘宝商家

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        // 淘宝交易ID，必须
        data.put("tid", "2780103567466192524");
        //  物流公司代码.如"POST"就代表中国邮政,"ZJS"就代表宅急送 ，通过接口 "查询物流公司列表"   获取。
        data.put("company_code", "YUNDA");
        // 运单号.具体一个物流公司的真实运单号码。淘宝官方物流会校验，请谨慎传入；
        data.put("out_sid", "432690393369508");
        // 卖家交易备注旗帜，   可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)，说明：如果不想加旗帜，则传空串""
        data.put("flag", "");
        // 卖家交易备注。最大长度: 1000个字节  ，说明：如果不想加旗帜，则传空串""
        data.put("memo", "");
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));

        // 调用服务API
        String resp = doHttpRequest(Config.LogisticesOnlineSendUrl, data);
        System.out.println(resp);

    }


    /**
     * 自己联系物流（线下物流）发货
     * 用户调用该接口可实现自己联系发货（线下物流），使用该接口发货，交易订单状态会直接变成卖家已发货。不支持货到付款、在线下单类型的订单。
     *
     * @throws Exception
     */
    @Test
    public void logisticesCompanyOffineSend() throws Exception {


        String tb_seller_nick = Config.TBSellerNick; //要查询支付宝的淘宝商家

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        // 淘宝交易ID，必须
        data.put("tid", "1701006480971565830");
        //  物流公司代码.如"POST"就代表中国邮政,"ZJS"就代表宅急送 ，通过接口 "查询物流公司列表"   获取。
        data.put("company_code", "YTO");
        // 运单号.具体一个物流公司的真实运单号码。淘宝官方物流会校验，请谨慎传入；
        data.put("out_sid", "YT5611492268564");
        // 卖家交易备注旗帜，   可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)，说明：如果不想加旗帜，则传空串""
        data.put("flag", "1");
        // 卖家交易备注。最大长度: 1000个字节  ，说明：如果不想加旗帜，则传空串""
        data.put("memo", "通过API发货222");
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));

        // 调用服务API
        doHttpRequest(Config.LogisticesOfflineSendUrl, data);

    }

    /**
     * 自己联系物流（线下物流）发货 --批量发送模式
     * 用户调用该接口可实现自己联系发货（线下物流），使用该接口发货，交易订单状态会直接变成卖家已发货。不支持货到付款、在线下单类型的订单。
     * 此接口淘宝将于2021-08-30下线
     *
     * @throws Exception
     */
    @Test
    public void logisticesCompanyOffineBatchSend() throws Exception {


        String tb_seller_nick = Config.TBSellerNick; //要查询支付宝的淘宝商家

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        List<LogisticesBatchSendItem> list = new ArrayList();
        LogisticesBatchSendItem item = new LogisticesBatchSendItem();

        // 第1个订单
        // 淘宝交易ID，必须
        item.setTid("2822788155237646335");
        // 运单号.具体一个物流公司的真实运单号码。淘宝官方物流会校验，请谨慎传入；
        item.setOutSid("432727706871416");
        //  物流公司代码.如"POST"就代表中国邮政,"ZJS"就代表宅急送 ，通过接口 "查询物流公司列表"   获取。
        item.setCompanyCode("YUNDA");
        // 卖家交易备注旗帜，   可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)，说明：如果不想加旗帜，则传空串""
        item.setFlag("5");
        // 卖家交易备注。最大长度: 1000个字节  ，说明：如果不想加旗帜，则传空串""
        item.setMemo("好测试");
        list.add(item);



//        // 第1个订单
//        // 淘宝交易ID，必须
//        item.setTid("2536086204130565830");
//        // 运单号.具体一个物流公司的真实运单号码。淘宝官方物流会校验，请谨慎传入；
//        item.setOutSid("773075543162332");
//        //  物流公司代码.如"POST"就代表中国邮政,"ZJS"就代表宅急送 ，通过接口 "查询物流公司列表"   获取。
//        item.setCompanyCode("STO");
//        // 卖家交易备注旗帜，   可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)，说明：如果不想加旗帜，则传空串""
//        item.setFlag("5");
//        // 卖家交易备注。最大长度: 1000个字节  ，说明：如果不想加旗帜，则传空串""
//        item.setMemo("好测试");
//        list.add(item);

//        // 第2个订单
//        item = new LogisticesBatchSendItem();
//        // 淘宝交易ID，必须
//        item.setTid("2537423066732565830");
//        // 运单号.具体一个物流公司的真实运单号码。淘宝官方物流会校验，请谨慎传入；
//        item.setOutSid("773075543162399");
//        //  物流公司代码.如"POST"就代表中国邮政,"ZJS"就代表宅急送 ，通过接口 "查询物流公司列表"   获取。
//        item.setCompanyCode("STO");
//        // 卖家交易备注旗帜，   可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)，说明：如果不想加旗帜，则传空串""
//        item.setFlag("4");
//        // 卖家交易备注。最大长度: 1000个字节  ，说明：如果不想加旗帜，则传空串""
//        item.setMemo("好测试2");
//        list.add(item);

        String jsonItems = JSON.toJSONString(list);
        System.out.println(jsonItems);
        data.put("items", jsonItems);
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));

        // 调用服务API
        String r = doHttpRequest(Config.LogisticesOfflineBatchSendUrl, data);
        System.out.println(r);

    }


    /**
     * 批量发货新接口
     *
     * @throws Exception
     */
    @Test
    public void logisticesCompanyV2OffineBatchSend() throws Exception {

        String tb_seller_nick = Config.TBSellerNick;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
//        String jsonItems ="[" +
//                "    {" +
//                "     \"sender_id\":0," +
//                "     \"feature\":\"\"," +
//                "     \"tid\":\"1915261095690565830\"," +
//                "     \"sub_tid\":\"\"," +
//                "     \"consign_pkgs\": [" +
//                "         {\"out_sid\":\"773075543162399\",\"company_code\":\"STO\" }" +
//                "     ]," +
//                "     \"cancel_id\":0" +
//                "}," +
//                "{" +
//                "    \"sender_id\":0," +
//                "    \"feature\":\"\"," +
//                "    \"tid\":\"1914231385728565830\"," +
//                "    \"sub_tid\":\"\"," +
//                "    \"consign_pkgs\": [" +
//                "        {\"out_sid\":\"773075543162332\",\"company_code\":\"STO\" }" +
//                "    ]," +
//                "    \"cancel_id\":0" +
//                "} " +
//                "]";

//        String jsonItems = "[{\"consign_pkgs\":[{\"out_sid\":\"773108740827636\",\"company_code\":\"STO\"},{\"out_sid\":\"78602319365161\",\"company_code\":\"STO\"}], \"tid\":\"1352825185445857178\",\"sub_tid\":\"1352825185446857178,1352825185447857178\"}]";
        String jsonItems = "[{\n" +
                "        \"sender_id\": 0,  \n" +
                "        \"feature\": \"\",\n" +
                "        \"tid\": \"279078987765406773\",\n" +
                "        \"sub_tid\": \"\",\n" +
                "        \"consign_pkgs\": [{\n" +
                "            \"out_sid\": \"78602319365161\",\n" +
                "            \"company_code\": \"ZTO\"\n" +
                "        }],\n" +
                "        \"cancel_id\": 0\n" +
                "    }]";
        data.put("items", jsonItems);
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.LogisticesV2OfflineSendUrl, data);

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
//        String checkData = "{" +
//                "    \"address_and_service_list\": [{" +
//                "        \"object_id\": \"abc100\"," +
//                "        \"receive_address\": {" +
//                "            \"address_detail\": \"裕田小区12号楼6单元201室\"," +
//                "            \"area_name\": \"呼兰区\"," +
//                "            \"city_name\": \"哈尔滨市\"," +
//                "            \"province_name\": \"黑龙江省\"," +
//                "            \"town_name\": \"利民街道\"" +
//                "        }," +
//                "        \"send_address\": {" +
//                "            \"area_name\": \"黄浦区\"," +
//                "            \"city_name\": \"广州市\"," +
//                "            \"province_name\": \"广东省\"," +
//                "            \"town_name\": \"九佛镇\"" +
//                "        }" +
//                "    }, " +
//                "    {" +
//                "        \"object_id\": \"def200\"," +
//                "        \"oaid\": \"1h12M4LqkhUkt1ibEk6VyZv2gRsECYSQayzH33CwBP1s3CmlCdx1CpVPJwkYcmvfj7u30St\"," +
//                "        \"receive_address\": {" +
//                "            \"address_detail\": \"蟠凤前路82号\"," +
//                "            \"area_name\": \"瓯海区\"," +
//                "            \"city_name\": \"温州市\"," +
//                "            \"province_name\": \"浙江省\"," +
//                "            \"town_name\": \"梧田街道\"" +
//                "        }," +
//                "        \"send_address\": {" +
//                "            \"area_name\": \"黄浦区\"," +
//                "            \"city_name\": \"广州市\"," +
//                "            \"province_name\": \"广东省\"," +
//                "            \"town_name\": \"九佛镇\"" +
//                "        }" +
//                "    }]," +
//                "    \"cp_code\": \"YTO\"," +
//                "    \"send_branch_code\": \"2220111\"" +"}";

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
                "    }" +
                "     ]," +
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
         data.put("tid", "2683463149182072168");
         data.put("is_split", "0");// 表明是否是拆单，默认值0，1表示拆单
//         data.put("sub_tid", "");//拆单子订单列表，当is_split=1时，需要传人；对应的数据是：子订单号的列表。
         data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoLogisticesTraceSearchUrl ,data);

    }




    /**
     * 查询卖家地址库
     * @throws Exception
     */
    @Test
    public void  LogisticsAddressSearchRequest() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        data.put("rdef", "");
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoLogisticesAddrSearchUrl ,data);

    }



    /**
     *  卖家地址库新增
     * @throws Exception
     */
    @Test
    public void  LogisticsAddressAddRequest() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        data.put("contact_name", "张三");
        data.put("province", "上海");
        data.put("city", "上海市");
        data.put("country", "浦东新区");
        data.put("addr", "世纪大道300号");
        data.put("zip_code", "200120");
        data.put("mobile_phone", "13877789900");

        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoLogisticesAddrAddUrl ,data);

    }

    /**
     *  卖家地址库修改
     * @throws Exception
     */
    @Test
    public void  LogisticsAddressModifyRequest() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        data.put("contact_id", "18267569");
        data.put("contact_name", "陈小红");
        data.put("province", "上海");
        data.put("city", "上海市");
        data.put("country", "浦东新区");
        data.put("addr", "世纪大道300号");
        data.put("zip_code", "200120");
        data.put("mobile_phone", "13877789900");

        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoLogisticesAddModifyUrl ,data);

    }

    /**
     *  卖家地址库删除
     * @throws Exception
     */
    @Test
    public void  LogisticsAddressRemoveRequest() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        data.put("contact_id", "18267569");
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoLogisticesAddrRemoveUrl ,data);

    }


    /**
     *  获致运费模板
     * @throws Exception
     */
    @Test
    public void  TaoBaoLogisticesDeliveryTplList() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        data.put("fields", "template_id,template_name,created,modified,supports,assumer,valuation,query_express,query_ems,query_cod,query_post");
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoLogisticesDeliveryTplListUrl ,data);

    }


   /**
     *   修改物流公司和运单号
     * @throws Exception
     */
    @Test
    public void  TaoBaologisticesConsignResend() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        data.put("tid", "2473718905282565830");
        data.put("company_code", "YTO");
        data.put("out_sid", "YT5611492268566");
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaologisticesConsignResendUrl ,data);

    }

   /**
     *   家装类订单使用该接口发货
     * @throws Exception
     */
    @Test
    public void  WlbOrderJzConsignRequest() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        data.put("tid", "2532549276407343839");
        String lgTPDtoJson = "{\"name\":\"圆通速递\", \"code\" : \"YTO\"}";
        data.put("lg_tp_dto", lgTPDtoJson);
        String jz_top_argsJson  = "{\"mail_no\":\"YT6383161721380\"}";
        data.put("jz_top_args", jz_top_argsJson);
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.WlbOrderJzConsignRequestUrl ,data);
        System.out.println(resp);

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

        System.out.println(result);
        return result;
    }


}