package com.cheeli.sfexpress;

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

import java.util.*;

/**
 * 场景：拼多多的接入， 合作伙伴使用，可管理其他授权的店铺。即可以管理多家店铺
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenSFTests.class )
public class OpenSFTests {

    @Test
    public void contextLoads() {
    }



    /**
     *  下单
     * @throws Exception
     */
    @Test
    public void createOrder() throws Exception {

        String result ="";
        String jsonData  = "{" +
                "\"cargoDetails\": [{" +
                "\"amount\": 308.0," +
                "\"count\": 1.0," +
                "\"name\": \"君宝牌地毯\"," +
                "\"unit\": \"个\"," +
                "\"volume\": 0.0," +
                "\"weight\": 0.1" +
                "}]," +
                "\"contactInfoList\": [{" +
                "\"address\": \"十堰市丹江口市公园路155号\"," +
                "\"city\": \"十堰市\"," +
                "\"company\": \"清雅轩保健品专营店\"," +
                "\"contact\": \"张三丰\"," +
                "\"contactType\": 1," +
                "\"county\": \"武当山风景区\"," +
                "\"mobile\": \"17006805888\"," +
                "\"province\": \"湖北省\"" +
                "}, {" +
                "\"address\": \"湖北省襄阳市襄城区环城东路122号\"," +
                "\"city\": \"襄阳市\"," +
                "\"contact\": \"郭襄阳\"," +
                "\"county\": \"襄城区\"," +
                "\"contactType\": 2," +
                "\"mobile\": \"18963828829\"," +
                "\"province\": \"湖北省\"" +
                "}]," +
                "\"customsInfo\": {}," +
                "\"expressTypeId\": 1," +
                "\"extraInfoList\": []," +
                "\"isOneselfPickup\": 0," +
                "\"language\": \"zh-CN\"," +
//                "\"monthlyCard\": \"75512367xxx\"," +  // 月结卡号
                "\"orderId\": \"QIAO-20210619-201\"," +
                "\"parcelQty\": 1," +
                "\"payMethod\": 1," +
                "\"totalWeight\": 6" +
                "}";


        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.SFExpressCreateOrderUrl );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("request_data", jsonData);
        data.put("client_code",  Config.SFClientCode);
        data.put("check_word",  Config.SFCheckWord);
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
     *  查询订单
     * @throws Exception
     */
    @Test
    public void searchOrder() throws Exception {

        String result ="";
        String jsonData  = "{" +
                "\"searchType\": \"1\"," +
                "\"orderId\": \"QIAO-20200618-200\"," +
                "\"language\": \"zh-cn\"" +
                "}";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.SFExpressSearchOrderUrl );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("request_data", jsonData);
        data.put("client_code",  Config.SFClientCode);
        data.put("check_word",  Config.SFCheckWord);
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
     * 订单确认/取消接口
     * @throws Exception
     */
    @Test
    public void updateOrder() throws Exception {

        String result ="";

        // dealType：2表示取消
        String jsonData  = "{" +
                "    \"dealType\": 2," +
                "    \"orderId\": \"QIAO-20210619-201\"" +
                "}";


        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.SFExpressUpdateOrderUrl );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("request_data", jsonData);
        data.put("client_code",  Config.SFClientCode);
        data.put("check_word",  Config.SFCheckWord);
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
     * 订单筛选接口
     * @throws Exception
     */
    @Test
    public void filterOrder() throws Exception {

        String result ="";

        // dealType：2表示取消
        String jsonData  = "[{" +
                "\"contactInfos\": [{" +
                "\"address\": \"广东深圳市南山区粤海街道创智天地大厦B栋27楼南区\"," +
                "\"city\": \"深圳市\"," +
                "\"contactType\": 1," +
                "\"country\": \"中国\"," +
                "\"county\": \"\"," +
                "\"province\": \"广东省\"," +
                "\"tel\": \"4001118851\"" +
                "}, {" +
                "\"address\": \"南朗镇翠亨村101\"," +
                "\"city\": \"中山市\"," +
                "\"contactType\": 2," +
                "\"country\": \"中国\"," +
                "\"county\": \"\"," +
                "\"province\": \"广东省\"," +
                "\"tel\": \"15888888111\"" +
                "}]," +
                "\"filterType\": 1 " +
                "}]";


        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.SFExpressFilterOrderUrl );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("request_data", jsonData);
        data.put("client_code",  Config.SFClientCode);
        data.put("check_word",  Config.SFCheckWord);
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
     * 路由查询
     * @throws Exception
     */
    @Test
    public void searchRouters() throws Exception {

        String result ="";

        // dealType：2表示取消
        String jsonData  = "{" +
                "\"language\": \"0\"," +
                "\"trackingType\": \"2\"," +
                "\"trackingNumber\": [\"QIAO-20200605-003\"]," +
                "\"methodType\": \"1\"" +
                "}";


        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.SFExpressSearchRoutersUrl );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("request_data", jsonData);
        data.put("client_code",  Config.SFClientCode);
        data.put("check_word",  Config.SFCheckWord);
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
     * 子单号申请接口
     * @throws Exception
     */
    @Test
    public void getSubMailNo() throws Exception {

        String result ="";
        // dealType：2表示取消
        String jsonData  = "{" +
                "    \"orderId\": \"QIAO-20200618-201\"," +
                "    \"parcelQty\": 3" +
                "}";


        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.SFExpressGetSubMailNosUrl );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("request_data", jsonData);
        data.put("client_code",  Config.SFClientCode);
        data.put("check_word",  Config.SFCheckWord);
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
     * 清单运费查询接口
     * @throws Exception
     */
    @Test
    public void queryOrderWayBill() throws Exception {

        String result ="";
        // dealType：2表示取消
        String jsonData  = "{\"trackingNum\":\"QIAO-20200605-200\",\"trackingType\":\"1\"}";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.SFExpressQueryOrderWaybillsUrl );
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("request_data", jsonData);
        data.put("client_code",  Config.SFClientCode);
        data.put("check_word",  Config.SFCheckWord);
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
     * 云打印面单打印
     * @throws Exception
     */
    @Test
    public void cloudPrintWayBill() throws Exception {

        String result ="";
        // dealType：2表示取消
     String jsonData  = "{\"templateCode\": \"fm_76130_standard_ZLXXKWh\",  \"version\":\"1.0\",\"fileType\":\"pdf\",\"documents\": [{\"masterWaybillNo\": \"SF1303262056675\" , \"isPrintLogo\":true,\"remark\":\"下午3点后再送1\"}]}";


          jsonData  = "{\"templateCode\": \"fm_76130_standard_ZLXXKWh\",  \"version\":\"1.0\",\"fileType\":\"pdf\",\"documents\": [{\"masterWaybillNo\": \"SF1160302680579\" , \"isPrintLogo\":true,\"remark\":\"下午3点后再送1\"}]}";
          jsonData  = "{\"templateCode\": \"fm_76130_standard_ZLXXKWh\",  \"version\":\"2.0\",\"fileType\":\"pdf\",\"documents\": [{\"masterWaybillNo\": \"SF1642395340510\" , \"isPrintLogo\":true,\"remark\":\"下午1点后再送1\"}]}";

          jsonData  = "{\"templateCode\": \"fm_76130_standard_ZLXXKWh\",  \"version\":\"2.0\",\"fileType\":\"pdf\",\"documents\": [{\"masterWaybillNo\": \"SF1160302680579\" , \"isPrintLogo\":true,\"remark\":\"下午3点后再送1\"}]}";
      // 子母件时的json
//        String jsonData  = "{" +
//                "    \"templateCode\": \"fm_76130_standard_ZLXXKWh\"," +
//                "    \"version\": \"2.0\"," +
//                "    \"fileType\": \"pdf\"," +
//                "    \"documents\": [{" +
//                "        \"masterWaybillNo\": \"SF7444432055982\"," +
//                "        \"seq\": \"1\"," +
//                "        \"sum\": \"2\"" +
//                "    }, {" +
//                "        \"masterWaybillNo\": \"SF7444432055982\"," +
//                "        \"branchWaybillNo\": \"SF7444510471516\"," +
//                "        \"seq\": \"2\"," +
//                "        \"sum\": \"2\"" +
//                "    } ]" +
//                "}";


        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.SFExpressCloudPrintWayBillUrl );

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("custom_data", "112233ks");// 自定义数据，推送时会原样返回
        data.put("request_data", jsonData);
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
