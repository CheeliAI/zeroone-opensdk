package com.cheeli.taobao;

import com.alibaba.fastjson.JSON;
import com.cheeli.Config;
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
import java.util.concurrent.TimeUnit;

/**
 * 场景： 淘宝电子发票相关接口
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenEInvoiceTests.class )
public class OpenEInvoiceTests {

    @Test
    public void contextLoads() {
    }


    /**
     * 获取开票信息
     * @throws Exception
     */

    @Test
    public void  AlibabaEinvoiceApplyGet() throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", Config.TBSellerNick);
        data.put("apply_id", "01wopSpcmXX5BlCqk-7EdrOOEsMzW5LYO4B_HZSANhC0Y");
        data.put("platform_tid", "2061887863088580545");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.AlibabaEinvoiceApplyGetUrl ,data);

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
