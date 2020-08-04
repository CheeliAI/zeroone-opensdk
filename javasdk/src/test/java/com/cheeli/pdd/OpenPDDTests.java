package com.cheeli.pdd;

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
     *  在线订单发货处理（支持货到付款）
     * @throws Exception
     */
    @Test
    public void logisticesCompanyOnineSend() throws Exception {

        String result ="";
        String tb_seller_nick = Config.PddSellerNick ; // 拼多多卖家
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.PddLogisticesOnlineSendUrl );
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());


        // 订单号 ,必填
        data.put("order_sn", "200801-472949884102221");
       //  快递公司编号，必填，  可通过查询快递公司列表获取，注意是Id不是code
        data.put("logistics_id","44");
        // 快递单号,必填
        data.put("tracking_number","SF1191992347148");

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





}
