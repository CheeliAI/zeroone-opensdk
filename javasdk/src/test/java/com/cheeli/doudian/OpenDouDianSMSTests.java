package com.cheeli.doudian;

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
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenDouDianSMSTests.class )
public class OpenDouDianSMSTests {

    @Test
    public void contextLoads() {
    }


    @Test
    public void  smsTemplateApplyRequest() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("template_type","CN_MKT");
        data.put("template_name","售后服务");
        data.put("template_content","您购买的商品已重新发出，加'群荐的铺子'公众号，实时售后维护~给您造成不便敬请谅解。回T退订");
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSmsTemplateApplyRequestUrl, data);
        System.out.println(resp);
    }

    // 删除已经审核通过的模板,通过Search接口获取的
    @Test
    public void  DDSmsTemplateDeleteRequest() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("template_id","ST_72dd4e2c");
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSmsTemplateDeleteRequestUrl, data);
        System.out.println(resp);
    }

//    撤销短信模板申请单
    @Test
    public void  DDSmsTemplateRevokeRequest() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("sms_template_apply_id","137991");
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSmsTemplateRevokeRequestUrl, data);
        System.out.println(resp);
    }

    // 查询短信模板申请单
    @Test
    public void  DDSmsTemplateApplyListRequest() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("page", 0);
        data.put("size", 20);
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSmsTemplateApplyListRequestUrl, data);
        System.out.println(resp);
    }

    @Test
    public void  DDSmsTemplateSearchRequest() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("page", 0);
        data.put("size", 20);
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSmsTemplateSearchRequestUrl, data);
        System.out.println(resp);
    }

    @Test
    public void  DDSmsPublicTemplateRequest() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("page", 0);
        data.put("size", 100);
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSmsPublicTemplateRequestURL, data);
        System.out.println(resp);
    }

    @Test
    public void  DDSmsSignApplyRequest() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
//        data.put("sms_sign", "群荐的铺子");
        data.put("sms_sign", "抖店开放平台测试专用旗舰店");
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSmsSignApplyRequestUrl, data);
        System.out.println(resp);
    }

    @Test
    public void  SmsSignSearchRequest() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("page", 0);
        data.put("size", 20);
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.SmsSignSearchRequestUrl, data);
        System.out.println(resp);
    }

    @Test
    public void  DDSmsSignDeleteRequest() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("sms_sign", "抖店开放平台测试专用旗舰店");
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSmsSignDeleteRequestUrl, data);
        System.out.println(resp);
    }

    @Test
    public void  DDSmsSignApplyListRequest() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("page", 0);
        data.put("size", 20);
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSmsSignApplyListRequestUrl, data);
        System.out.println(resp);
    }

    @Test
    public void  DDSmsSendRequest() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
//        String reqData ="{\n" +
//                "    \"sign\": \"群荐的铺子\",\n" +
//                "    \"sms_message_list\": [\n" +
//                "        {\n" +
//                "            \"biz_type\": 1,\n" +
//                "            \"template_param\": \"{\\\"name\\\":\\\"顺丰\\\",\\\"number\\\":\\\"sf3813741334\\\"}\",\n" +
//                "            \"order_id\": \"5001789329480019707\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"biz_type\": 0,\n" +
//                "            \"template_param\": \"{\\\"name\\\":\\\"圆通\\\",\\\"number\\\":\\\"yt3813741888\\\"}\", \n" +
//                "            \"post_tel\": \"$DcwmMAKzUZzUj258P0LNEu8bad1eFd7JmRhBXBwT57s=$PzDIsfCO+TTBInVLz+HpMIBCMLhVelkDGhDwGLF3rSagVB8UTp0EC46bGp5PzYFMPneQ4Rn3p9YcaPxkRyivTMJQMaj7v5WvJ689hh/WCv6h+Q==*CgkIARCtHCABKAESPgo874EEcU1IKaIbmqHwtm/yV5qA3KDq84ibA3LfR7ptQc6Cs5x6h1Pwodhx1Nn0Gbsez1EXWjsKEwRQNBHLGgA=$1$$\"\n" +
//                "        }\n" +
//                "    ],\n" +
//                "    \"template_id\": \"ST_72dd4e2c\",\n" +
//                "    \"tag\": \"bk202201\",\n" +
//                "    \"user_ext_code\": \"abcd\"\n" +
//                "}";

        String reqData ="{\n" +
                "    \"sign\": \"群荐的铺子\",\n" +
                "    \"sms_message_list\": [\n" +
                "        {\n" +
                "            \"biz_type\": 1,\n" +
                "            \"template_param\": \"{\\\"name\\\":\\\"顺丰\\\",\\\"number\\\":\\\"sf3813741334\\\"}\",\n" +
                "            \"order_id\": \"5001789329480019707\"\n" +
                "        } \n" +
                "    ],\n" +
                "    \"template_id\": \"ST_72e7423f\",\n" +
                "    \"tag\": \"bk202201\",\n" +
                "    \"user_ext_code\": \"abcde\"\n" +
                "}";

        data.put("request_data", reqData);
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSmsSendRequestUrl, data);
        System.out.println(resp);
    }


    @Test
    public void  DDSmsSendResultRequest() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);

        long start = System.currentTimeMillis() / 1000 - 3600 * 24 * 7 ;
        long end = System.currentTimeMillis() / 1000;
        data.put("from_time", String.valueOf(start));
        data.put("to_time", String.valueOf(end));
//        data.put("message_id", "3466e7f9-8bad-4fd7-8534-adbd8f891466");

        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSmsSendResultRequestUrl, data);
        System.out.println(resp);
    }


    public static String Sign(Map<String, Object> params,String appSecret) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        StringBuilder query = new StringBuilder();
        query.append(appSecret);
        for (String key : keys) {
            String value = params.get(key).toString();
            query.append(key).append(value);
        }
        query.append(appSecret);
        byte[] md5byte = Utils.encryptMD5(query.toString());
        return  Utils.byte2hex(md5byte);
    }

    private  String doHttpRequest(String url ,  Map<String, Object> data ){
        String result ="";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( url);
        System.out.println("请求Url:" +url);
        System.out.println("请求数据:" + JSON.toJSONString(data));
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
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
