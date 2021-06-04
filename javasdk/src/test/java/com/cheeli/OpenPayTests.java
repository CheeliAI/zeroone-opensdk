package com.cheeli;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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




@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenPayTests.class)
public class OpenPayTests {



    @Test
    public void contextLoads() {
    }



    /**
     *  发起一笔支付交易-- 支付宝订购版
     * @throws Exception
     */
    @Test
    public void TradePreCreate4Subs() throws Exception {

        //************************** 请求参数填写开始 ***************************************/

        //支付金额 (单位分），    修改
        String  amount ="1000";
        // 确保传入的开发者订单号唯一, 订单号生成规则开发者可自定，  修改
        String ext_trade_no =  String.valueOf(System.currentTimeMillis());
        // 扩展参数，付款成功时回调给开发者时会原样返回，根据自己需求填写或为空。
        String extra_common_param ="";
        // 订单标题
        String body ="测试商品";
        //  1：支付宝订购版 2：支付宝代收版  3： 微信代收版  4：支付宝商家签约版  5：微信商家签约版
        String  agentChannel = "1";
        // 回调通知url，开发者自己的服务器Url
        String notify_url = Config.PayNotifyUrl;
        //************************** 请求参数填写结束 ***************************************/
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", Config.TBSellerNick);
        Long timestamp = System.currentTimeMillis() / 1000;
       // Long timestamp = 837137814712L;
        data.put("timestamp", timestamp.toString());
        data.put("ext_trade_no",ext_trade_no );
        data.put("amount",amount );
        data.put("attach",extra_common_param );
        data.put("body" , body );
        data.put("notify_url",notify_url );
        data.put("agent_channel",agentChannel );


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
        String result ="";
        //发起POST请求
        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost( Config.TradePreCreate );

            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result =  EntityUtils.toString(httpResponse.getEntity());
                JSONObject jsonObject  =  JSON.parseObject(result);
                Integer  code = jsonObject.getInteger("code");
                String   message = jsonObject.getString("message");
                JSONObject   dataObject = jsonObject.getJSONObject("data");
                String   qr_url = dataObject.getString("qr_url");
                System.out.println("qr_url:"+ qr_url);

            } else {
                result =  ("doPost Error Response: " + httpResponse.getStatusLine().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println(result);

    }



    /**
     *  发起一笔支付交易 -- 微信代收版
     * @throws Exception
     */

    @Test
    public void TradePreCreate4WeiXinAgent() throws Exception {

        //************************** 请求参数填写开始 ***************************************/

        //支付金额 (单位分），10 元  ， 修改
        String  amount ="1000";
        // 确保传入的开发者订单号唯一, 订单号生成规则开发者可自定，  修改
        String ext_trade_no =String.valueOf(System.currentTimeMillis());
        // 扩展参数，付款成功时回调给开发者时会原样返回，根据自己需求填写或为空。
        String attach = "";
        // 订单标题
        String body ="故宫3日游团票";
        //  1：支付宝订购版  2：支付宝代收版  3： 微信代收版  4：支付宝商家签约版  5：微信商家签约版
        String  agentChannel = "3";
        // 回调通知url，开发者自己的服务器Url
        String notify_url = Config.PayNotifyUrl;

        //************************** 请求参数填写结束 ***************************************/

        String result ="";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.TradePreCreate  );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("ext_trade_no",ext_trade_no );
        data.put("amount",amount );
        data.put("attach",attach );
        data.put("body" , body );
        data.put("notify_url",notify_url );
        data.put("agent_channel",agentChannel );


        //参数签名
        try {
            data.put("sign", Utils.Sign(data, Config.AppSecret  ));
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
                JSONObject jsonObject  =  JSON.parseObject(result);
                Integer  code = jsonObject.getInteger("code");
                String   message = jsonObject.getString("message");
                JSONObject   dataObject = jsonObject.getJSONObject("data");
                String   qr_url = dataObject.getString("qr_url");
                System.out.println("qr_url:"+ qr_url);

            } else {
                result =  ("doPost Error Response: " + httpResponse.getStatusLine().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println(result);

    }




    /**
     *  发起一笔支付交易 -- 微信签约版
     * @throws Exception
     */

    @Test
    public void TradePreCreate4WeiXinSigning() throws Exception {

        //************************** 请求参数填写开始 ***************************************/

        //支付金额 (单位分）
        String  amount ="10000";
        String  mchId  ="1608725221"; //other  微信签约的商户号
//        String  mchId  ="1587507961"; // mb 微信签约的商户号
        // 确保传入的开发者订单号唯一, 订单号生成规则开发者可自定，  修改
        String ext_trade_no =String.valueOf(System.currentTimeMillis());
        // 扩展参数，付款成功时回调给开发者时会原样返回，根据自己需求填写或为空。
        String attach ="biz=sms";
        // 订单标题
        String body ="故宫3日游团票";
        //  1：支付宝订购版 2：支付宝代收版  3： 微信代收版  4：支付宝商家签约版  5：微信商家签约版
        String  agentChannel = "5";
        // 回调通知url，开发者自己的服务器Url
        String notify_url = Config.PayNotifyUrl;

        //************************** 请求参数填写结束 ***************************************/

        String result ="";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.TradePreCreate );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("ext_trade_no",ext_trade_no );
        data.put("mch_id",mchId );
        data.put("amount",amount );
        data.put("attach",attach );
        data.put("body" , body );
        data.put("notify_url",notify_url );
        data.put("agent_channel",agentChannel );


        //参数签名
        try {
            data.put("sign", Utils.Sign(data, Config.AppSecret   ));
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
                JSONObject jsonObject  =  JSON.parseObject(result);
                Integer  code = jsonObject.getInteger("code");
                String   message = jsonObject.getString("message");
                JSONObject   dataObject = jsonObject.getJSONObject("data");
                String   qr_url = dataObject.getString("qr_url");
                System.out.println("qr_url:"+ qr_url);

            } else {
                result =  ("doPost Error Response: " + httpResponse.getStatusLine().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println(result);

    }


    /**
     *  发起一笔支付交易 -- 支付宝签约版
     * @throws Exception
     */

    @Test
    public void TradePreCreate4AlipaySigning() throws Exception {

        //************************** 请求参数填写开始 ***************************************/

        //支付金额 (单位分）
        String  amount ="10000";
        String  mchId  ="2088701568962424"; //签约的支付宝商户号
        // 确保传入的开发者订单号唯一, 订单号生成规则开发者可自定，  修改
        String ext_trade_no =String.valueOf(System.currentTimeMillis());
        // 扩展参数，付款成功时回调给开发者时会原样返回，根据自己需求填写或为空。
        String attach ="biz=sms";
        // 订单标题
        String body ="故宫3日游团票";
        //  1：支付宝订购版 2：支付宝代收版  3： 微信代收版  4：支付宝商家签约版  5：微信商家签约版
        String  agentChannel = "4";
        // 回调通知url，开发者自己的服务器Url
        String notify_url = Config.PayNotifyUrl;

        //************************** 请求参数填写结束 ***************************************/

        String result ="";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.TradePreCreate );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("ext_trade_no",ext_trade_no );
        data.put("mch_id",mchId );
        data.put("amount",amount );
        data.put("attach",attach );
        data.put("body" , body );
        data.put("notify_url",notify_url );
        data.put("agent_channel",agentChannel );


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
                result =  EntityUtils.toString(httpResponse.getEntity());
                JSONObject jsonObject  =  JSON.parseObject(result);
                Integer  code = jsonObject.getInteger("code");
                String   message = jsonObject.getString("message");
                JSONObject   dataObject = jsonObject.getJSONObject("data");
                String   qr_url = dataObject.getString("qr_url");
                System.out.println("qr_url:"+ qr_url);

            } else {
                result =  ("doPost Error Response: " + httpResponse.getStatusLine().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println(result);

    }





    /**
     *  发起一笔支付交易 -- 支付宝代收版
     * @throws Exception
     */

    @Test
    public void TradePreCreate4AlipayAgent() throws Exception {

        //************************** 请求参数填写开始 ***************************************/

        //支付金额 (单位分），  修改
        String  amount ="1000";
        // 确保传入的开发者订单号唯一, 订单号生成规则开发者可自定，  修改
        String ext_trade_no =String.valueOf(System.currentTimeMillis());
        // 扩展参数，付款成功时回调给开发者时会原样返回，根据自己需求填写或为空。
        String attach ="biz=sms";
        // 订单标题
        String body ="故宫3日游团票";
        //  1：支付宝订购版 2：支付宝代收版  3： 微信代收版  4：支付宝商家签约版  5：微信商家签约版
        String  agentChannel = "2";
        // 回调通知url，开发者自己的服务器Url
        String notify_url = Config.PayNotifyUrl;

        //************************** 请求参数填写结束 ***************************************/

        String result ="";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.TradePreCreate );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("ext_trade_no",ext_trade_no );
        data.put("amount",amount );
        data.put("attach",attach );
        data.put("body" , body );
        data.put("notify_url",notify_url );
        data.put("agent_channel",agentChannel );


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
                result =  EntityUtils.toString(httpResponse.getEntity());
                JSONObject jsonObject  =  JSON.parseObject(result);
                Integer  code = jsonObject.getInteger("code");
                String   message = jsonObject.getString("message");
                JSONObject   dataObject = jsonObject.getJSONObject("data");
                String   qr_url = dataObject.getString("qr_url");
                System.out.println("qr_url:"+ qr_url);

            } else {
                result =  ("doPost Error Response: " + httpResponse.getStatusLine().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println(result);

    }





    /**
     *  发起一笔支付交易 -- 微信jsapi代收版
     * @throws Exception
     */

    @Test
    public void TradePreCreate4WeiXinJSAPIAgent() throws Exception {

        //************************** 请求参数填写开始 ***************************************/

        //支付金额 (单位分），  修改
        String  amount ="10";
        // 确保传入的开发者订单号唯一, 订单号生成规则开发者可自定，  修改
        String ext_trade_no =String.valueOf(System.currentTimeMillis());
        // 扩展参数，付款成功时回调给开发者时会原样返回，根据自己需求填写或为空。
        String attach ="biz=sms";
        // 订单标题
        String body ="故宫3日游团票";

        // 回调通知url，开发者自己的服务器Url
        String notify_url = Config.PayNotifyUrl;
        String openid = "oxL-nt_wXbwtIh04tCNZ27T1X57M"; //miller

        //************************** 请求参数填写结束 ***************************************/

        String result ="";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.TradePreCreate4WeiXinJSAPI );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("ext_trade_no",ext_trade_no );
        data.put("amount",amount );
        data.put("attach",attach );
        data.put("body" , body );
        data.put("notify_url",notify_url );
        data.put("openid",openid );


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
                result =  EntityUtils.toString(httpResponse.getEntity());
                JSONObject jsonObject  =  JSON.parseObject(result);
                Integer  code = jsonObject.getInteger("code");
                String   message = jsonObject.getString("message");
                JSONObject   dataObject = jsonObject.getJSONObject("data");
                String   qr_url = dataObject.getString("qr_url");
                System.out.println("qr_url:"+ qr_url);

            } else {
                result =  ("doPost Error Response: " + httpResponse.getStatusLine().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println(result);

    }






}
