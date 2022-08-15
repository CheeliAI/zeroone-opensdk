package com.cheeli;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.Checksum;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SMSTests.class)
public class SMSTests {



    @Test
    public void contextLoads() {
    }




    @Test
    public void sendSMS() throws Exception {
//        String requestData = "{" +
//                "                \"custom_sms_id\" :\"XS28381382\", " +
//                "                \"extended_code\" :\"\", " +
//                "                \"mobiles\" : [\"13816127333\"], " +
//                "                \"templete_id\" :  20, " +
//                "                \"sign_id\" :1, " +
//                "                \"template_param\" :{\"code\":\"100205\"}" +
//                "         }";


        String requestDataAd = "{" +
                "                \"custom_sms_id\" :\"XS28381382\", " +
                "                \"extended_code\" :\"\", " +
                "                \"mobiles\" : [\"13816127333\"], " +
                "                \"templete_id\" :  32, " +
                "                \"sign_id\" :1, " +
                "                \"template_param\" :{\"url\":\"http://www.baidu.com/ad\"}" +
                "         }";

        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
       // Long timestamp = 837137814712L;
        data.put("timestamp", timestamp.toString());
        data.put("request_data",requestDataAd );
       // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        doHttpRequest(Config.SendSMSUrl, data);


    }

    @Test
    public void sendSMSV2() throws Exception {

        // 通知
        String   requestData = "{" +
                "                \"custom_sms_id\" :\"\", " +
                "                \"extended_code\" :\"\", " +
                "                \"mobiles\" : [\"13816122323\"], " +
                "                \"templete_code\" :  \"SMS_6533902793\", " +
                "                \"sign_name\" :\"四方掌柜\", " +
                "                \"template_param\" :{\"code\":\"2212\"}" +
                "         }";

        // 营销
        String  requestDataAD = "{" +
                "                \"custom_sms_id\" :\"\", " +
                "                \"extended_code\" :\"\", " +
                "                \"mobiles\" : [\"13816127333\"], " +
                "                \"templete_code\" :  \"SMS_6318499315\", " +
                "                \"sign_name\" :\"四方掌柜\", " +
                "                \"template_param\" :{\"url\":\"https://www.163.com/ad\"}" +
                "         }";

        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("request_data",requestDataAD);
        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        doHttpRequest(Config.SendSMSV2Url, data);

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



    @Test
    public void crcTest(){

        String sellerNick = "百鞋馆china100";
        long hashKey = getCRC32Checksum(sellerNick.getBytes());
        System.out.println(hashKey);

    }
    public static long getCRC32Checksum(byte[] bytes) {
        Checksum crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        return crc32.getValue();
    }

}
