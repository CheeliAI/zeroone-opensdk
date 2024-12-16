package com.cheeli.doudian;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenDouDianTests.class )
public class OpenDouDianTests {

    @Test
    public void contextLoads() {
    }


    @Test
    public void  GetOrderList() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        long start = System.currentTimeMillis() / 1000 - 3600 * 24 * 7 ;
        long end = System.currentTimeMillis() / 1000;
        data.put("create_time_start", String.valueOf(start));
        data.put("create_time_end", String.valueOf(end));
//        data.put("combine_status", "[{\"main_status\":\"1,2,3\",\"order_status\":\"1,2,3\"}]");
//        data.put("combine_status", "[{\"order_status\":\"3,5\"}]");
//        data.put("combine_status", "[{\"order_status\":\"3\"}]");
        data.put("is_searchable", true);
        data.put("page", "0");
        data.put("size", "10");
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDTradeListUrl, data);
        System.out.println(resp);
    }

    @Test
    public void  GetOrderListExt() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);

        data.put("create_time_start","2022-07-10 00:00:00" );
        data.put("create_time_end", "2022-08-05 00:00:00");
        data.put("order_by", "create_time");
        data.put("order_asc", false);
        data.put("order_status", 2);
        data.put("seller_flag",4);
        data.put("page", "1");
        data.put("page_size", 10);
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDTradeListExtUrl, data);
        System.out.println(resp);
    }


    @Test
    public void  GetOrderDetail() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("shop_order_id", "4962566906438436758"); //zjb
//        data.put("shop_order_id", "4962887998198291943"); // test
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDTradeDetailUrl, data);
        System.out.println(resp);
    }
    @Test
    public void  GetLogisticsCompanyList() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDLogisticsCompanyListUrl, data);
        System.out.println(resp);
    }

    @Test
    public void  LogisticstemplateList() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDLogisticstemplateListUrl, data);
        System.out.println(resp);

    }

    // 查询商家和物流商的订购关系以及物流单号使用情况
    @Test
    public void   LogisticsShopnetSiteList() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("logistics_code", "");// 获取全部，传空串
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDLogisticsShopnetSiteListUrl, data);
        System.out.println(resp);

    }

    @Test
    public void    LogisticsTraceNoTroute() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("logistics_code", "YT");
        data.put("track_no", "YT6592561110199");//
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDLogisticsTraceNoTrouteUrl, data);
        System.out.println(resp);

    }

//    String reqData2 = "[{\n" +
//            "\t\"order_id\":  \"4948795729456854757\",\n" +
//            "\t\"company_code\": \"zhongtong\",\n" +
//            "\t\"logistics_code\" : \"75531717195834\"\n" +
//            "},\n" +
//            "{\n" +
//            "\t\"order_id\":  \"4948795729456854758\",\n" +
//            "\t\"company_code\": \"zhongtong\",\n" +
//            "\t\"logistics_code\" : \"75531717195835\"\n" +
//            "}]";
    @Test
    public void    OrderLogisticsAdd() throws Exception {
        String reqData = "[{" +
                "\"order_id\": \"4948795729456854758\"," +
                "\"company_code\": \"zhongtong\"," +
                "\"logistics_code\" : \"75531717195834\"" +
                "}]";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("request_data",reqData);
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDOrderLogisticsAddUrl, data);
        System.out.println(resp);

    }

    @Test
    public void    DDAddList() throws Exception {

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("page_size",10);
        data.put("page_no",1);
        data.put("order_by","desc");
        data.put("order_field","update_time");
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDAddListUrl, data);
        System.out.println(resp);

    }

    // 对订单进行插旗
    @Test
    public void    addOrderRmark() throws Exception {

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("order_id","4987737158544862534");
        data.put("remark","这是通过蜂巢写入");
        data.put("is_add_star","true");
        data.put("star","4");
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDOrderAddOrderRemarkUrl, data);
        System.out.println(resp);

    }

    // 取消电子面单
    @Test
    public void  cancelOrder() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("logistics_code","zhongtong");
        data.put("track_no","755449563871502");
//      data.put("user_id","1");
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDLogisticsCancelOrderUrl, data);
        System.out.println(resp);

    }


   @Test
    public void   OrderDecrypt() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        String infos = "[" +
                  "    {\"auth_id\":\"4962566906438436758\",\"cipher_text\": \"$nL6jTULVZKFA0t0LGqdhJA3Ce9Q7eWgvwci6nQtCrDs=$YCPkPsqD/HH6GB+q3KmaDsOOUIbdHqhklSgJILaFSAz/5Abk7t4Ls6BXX4UpfTZMj0zXjI7y7K4wgkXWjtbfII+zwIgYE5S2Cq+EC8fNzZ7Y*CgwIARCtHBiYJSABKAESPgo8VB8erixHYJaDE7lRllMSrVZKMFHfj5XUrooVYtBYL2ioq9n0It9+zlO26iqEbzxiM9bHNbFXzYVOQr6LGgA=$1$$\"}" +
                  "    ]";
        data.put("cipher_infos",infos);

        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDOrderDecryptUrl, data);
        System.out.println(resp);

    }


    // 合二为1 获取单号和面单信息
    @Test
    public void  DDLogisticsWaybillGet() throws Exception {
        String reqData = "{\"sender_info\": {" +
                "        \"address\": {" +
                "            \"country_code\": \"CHN\"," +
                "            \"province_name\": \"上海市\"," +
                "            \"city_name\": \"上海市\"," +
                "            \"district_name\": \"青浦区\"," +
                "            \"detail_address\": \"测试\"" +
                "        }," +
                "        \"contact\": {" +
                "            \"name\": \"hanson\"," +
                "            \"mobile\": \"18817834074\"" +
                "        }" +
                "    }," +
                "    \"logistics_code\": \"zhongtong\"," +
                "    \"order_infos\": [" +
                "        {" +
                "            \"order_id\": \"4948863645786431206\"," +
                "            \"pack_id\": \"pkg_01\"," +
                "            \"receiver_info\": {" +
                "                \"address\": {" +
                "                    \"country_code\": \"CHN\"," +
                "                    \"province_name\": \"浙江省\"," +
                "                    \"city_name\": \"杭州市\"," +
                "                    \"district_name\": \"西湖区\"," +
                "                    \"detail_address\": \"#ZbZE6fdvKu0n/gB488d4Uxj7bzQi#n3zBNQgaGXiMjQb7CIwUDrQ/oEQGP43PmvV9Oi4lTooQagOruEjHzoy8JiUfZ/+3kOQABhWWXbhtTCKXDaE5GrNfbVhmef3N2d1u4eIDRZ6cT4fAsxUY4eCRTD+3pg==*CgwIARCtHBiYJSABKAESPgo8azkO9VDljulg7gl8Z8ykmOOfh93YeeMY+p+VbuytBkgfmS61mTnWtH4omldn44DJ4b7CZPzdDybE/QFBGgA=#1##\"" +
                "                }," +
                "                \"contact\": {" +
                "                    \"name\": \"#JsKP#mjMUmDSsFnoIZQyxnI8dKvJm7af8F+6YU+ZQRYZRGpukzSczT5arppGdExz9pleid3zCHGr8FvBmFsMmU5mmZ03F8k/dCNEy*CgwIARCtHBiYJSABKAESPgo8EGql2MQlYZNFj1iHVlSK41VeEYk0sH3UrzR5/8nYgPlbhkrbI2fbWQmlYrkuSJig7K3SHQi1jI6oXkp5GgA=#1##\"," +
                "                    \"mobile\": \"$dzFmfVaRHLlEEdPMqNWcR5WZ01oatEm4hTtC3iO9+sM=$0uwlr1TmDiJNYFPgdS1vCTOdq2ZxXk1ed8aSS8AZLpa8itQk/L9noLXPPnyWt6VOF3ZyyC+N00OGk09TzF+4/Mtn4VeJ0OGAPqZtGTlJGJxx*CgwIARCtHBiYJSABKAESPgo88ghgupGfSv8mUFdaXWzO3J54gbTj1192xyPJJUHcxjR0SqFUEdfoeUVsLKK6EaekOkvU5OTFCWgREZehGgA=$1$$\"" +
                "                }" +
                "            }," +
                "            \"items\": [" +
                "                {" +
                "                    \"item_name\": \"【测试商品勿拍】白色水墨印花衬衣\"," +
                "                    \"item_specs\": \"颜色:默认 尺码:120cm\"," +
                "                    \"item_count\": 1" +
                "                } " +
                "            ]" +
                "        }" +
                "    ]," +
                "    \"user_id\": -1," +
                "    \"order_channel\": \"1\"" +
                "}";


        String reqDataMW = "{\"sender_info\": {" +
                "        \"address\": {" +
                "            \"country_code\": \"CHN\"," +
                "            \"province_name\": \"上海市\"," +
                "            \"city_name\": \"上海市\"," +
                "            \"district_name\": \"青浦区\"," +
                "            \"detail_address\": \"测试\"" +
                "        }," +
                "        \"contact\": {" +
                "            \"name\": \"hanson\"," +
                "            \"mobile\": \"18817834074\"" +
                "        }" +
                "    }," +
                "    \"logistics_code\": \"zhongtong\"," +
                "    \"order_infos\": [" +
                "        {" +
                "            \"order_id\": \"4948863645786431206\"," +
                "            \"pack_id\": \"pkg_01\"," +
                "            \"receiver_info\": {" +
                "                \"address\": {" +
                "                    \"country_code\": \"CHN\"," +
                "                    \"province_name\": \"浙江省\"," +
                "                    \"city_name\": \"杭州市\"," +
                "                    \"district_name\": \"西湖区\"," +
                "                    \"detail_address\": \"文三路28弄18号201\"" +
                "                }," +
                "                \"contact\": {" +
                "                    \"name\": \"张三\"," +
                "                    \"mobile\": \"13816667876\"" +
                "                }" +
                "            }," +
                "            \"items\": [" +
                "                {" +
                "                    \"item_name\": \"【测试商品勿拍】白色水墨印花衬衣\"," +
                "                    \"item_specs\": \"颜色:默认 尺码:120cm\"," +
                "                    \"item_count\": 1" +
                "                } " +
                "            ]" +
                "        }" +
                "    ]," +
                "    \"user_id\": -1," +
                "    \"order_channel\": \"1\"" +
                "}";


        String reqData2 = "{" +
                "    \"sender_info\":{" +
                "        \"address\":{" +
                "            \"country_code\":\"CHN\"," +
                "            \"province_name\":\"上海市\"," +
                "            \"city_name\":\"上海市\"," +
                "            \"district_name\":\"青浦区\"," +
                "            \"detail_address\":\"测试\"" +
                "        }," +
                "        \"contact\":{" +
                "            \"name\":\"hanson\"," +
                "            \"mobile\":\"18817834074\"" +
                "        }" +
                "    }," +
                "    \"logistics_code\":\"zhongtong\"," +
                "    \"order_infos\":[" +
                "        {" +
                "            \"order_id\":\"49488636457864312061\"," +
                "            \"pack_id\":\"pkg_01\"," +
                "            \"receiver_info\":{" +
                "                \"address\":{" +
                "                    \"country_code\":\"CHN\"," +
                "                    \"province_name\":\"浙江省\"," +
                "                    \"city_name\":\"杭州市\"," +
                "                    \"district_name\":\"西湖区\"," +
                "                    \"detail_address\":\"#ZbZE6fdvKu0n/gB488d4Uxj7bzQi#n3zBNQgaGXiMjQb7CIwUDrQ/oEQGP43PmvV9Oi4lTooQagOruEjHzoy8JiUfZ/+3kOQABhWWXbhtTCKXDaE5GrNfbVhmef3N2d1u4eIDRZ6cT4fAsxUY4eCRTD+3pg==*CgwIARCtHBiYJSABKAESPgo8azkO9VDljulg7gl8Z8ykmOOfh93YeeMY+p+VbuytBkgfmS61mTnWtH4omldn44DJ4b7CZPzdDybE/QFBGgA=#1##\"" +
                "                }," +
                "                \"contact\":{" +
                "                    \"name\":\"#JsKP#mjMUmDSsFnoIZQyxnI8dKvJm7af8F+6YU+ZQRYZRGpukzSczT5arppGdExz9pleid3zCHGr8FvBmFsMmU5mmZ03F8k/dCNEy*CgwIARCtHBiYJSABKAESPgo8EGql2MQlYZNFj1iHVlSK41VeEYk0sH3UrzR5/8nYgPlbhkrbI2fbWQmlYrkuSJig7K3SHQi1jI6oXkp5GgA=#1##\"," +
                "                    \"mobile\":\"$dzFmfVaRHLlEEdPMqNWcR5WZ01oatEm4hTtC3iO9+sM=$0uwlr1TmDiJNYFPgdS1vCTOdq2ZxXk1ed8aSS8AZLpa8itQk/L9noLXPPnyWt6VOF3ZyyC+N00OGk09TzF+4/Mtn4VeJ0OGAPqZtGTlJGJxx*CgwIARCtHBiYJSABKAESPgo88ghgupGfSv8mUFdaXWzO3J54gbTj1192xyPJJUHcxjR0SqFUEdfoeUVsLKK6EaekOkvU5OTFCWgREZehGgA=$1$$\"" +
                "                }" +
                "            }," +
                "            \"items\":[" +
                "                {" +
                "                    \"item_name\":\"【测试商品勿拍】白色水墨印花衬衣\"," +
                "                    \"item_specs\":\"颜色:默认 尺码:120cm\"," +
                "                    \"item_count\":1" +
                "                }" +

                "            ]" +
                "        }, " +
                "        {" +
                "            \"order_id\":\"4948795729456854758\", " +
                "            \"receiver_info\":{" +
                "                \"address\":{" +
                "                    \"country_code\":\"CHN\"," +
                "                    \"province_name\":\"浙江省\"," +
                "                    \"city_name\":\"杭州市\"," +
                "                    \"district_name\":\"西湖区\"," +
                "                    \"detail_address\":\"#88d4Uxj7bzQiCZ/tB348#JYQbCE0Z3qS4enifncvRfvy/2//ByIgja0ez2ziH5ejCsBpPzEZ1sSuUDukoVKuQHCIok56Ew2jW/BDm4cri1uAWizYOx5y2JIB2ulq0mxMpj5OtkzMqtg==*CgwIARCtHBiYJSABKAESPgo8ak9uAm8xewS+3cxuj53pXMk1QHFjZPL4lRR6ReHcY/n46MfiA2HAos7aQ2k7eAoqlY9IDo9SgUDxcugRGgA=#1##\"" +
                "                }," +
                "                \"contact\":{" +
                "                    \"name\":\"#JsKP#MU+XJbCwo6OvX9i6/9FGQfXBEikV2PhUf/Y+SvMTXJxVvDfGsu6sZkB/66vPHkQ7eot/UFyVXEiJnhq+rES6wlm5fcvybYJY*CgwIARCtHBiYJSABKAESPgo8iXhpCxN3TRpdw1c354bgKOqDbL9yt0J9qBUSIY/bLcW41gWAURRt6Nxp11BTrA4key6atmBb/BMdActIGgA=#1##\"," +
                "                    \"mobile\":\"$dzFmfVaRHLlEEdPMqNWcR5WZ01oatEm4hTtC3iO9+sM=$Ws0Fjzxv1wXfWsPGHS1XMv6riNju8b3AJtk/fiTAHaOiVUf9h+YSlBuVjF18SAAIA5ymX6H/V6q/HPc0vIUvuTViP4TM40i3RaWOMd7se5At*CgwIARCtHBiYJSABKAESPgo8f8TVWjyoVQNlT7MEj7tPiYwTpuHZWzKSuMWMsi0UV3oIMsp2U3rUASks98PnPsr0i3IwvzrOTMPYbiBnGgA=$1$$\"" +
                "                }" +
                "            }," +
                "            \"items\":[" +
                "                {" +
                "                    \"item_name\":\"【【测试商品勿拍】dy测试上衣-各种编码3\", " +
                "                    \"item_count\":1" +
                "                }" +
                "               " +
                "            ]" +
                "        }" +
                "    ]," +
                "    \"user_id\":-1," +
                "    \"order_channel\":\"1\"" +
                "}";

//        reqDataMW = "{\"sender_info\":{\"address\":{\"country_code\":\"CHN\",\"district_name\":\"中山市\",\"city_name\":\"中山市\",\"detail_address\":\"民安中路191号2楼\",\"province_name\":\"广东省\"},\"contact\":{\"name\":\"林生\",\"mobile\":\"15220909710\"}},\"order_infos\":[{\"order_id\":\"548059574956693\",\"items\":[{\"item_count\":1,\"item_name\":\"茶包-1\"}],\"receiver_info\":{\"address\":{\"country_code\":\"CHN\",\"district_name\":\"小榄镇\",\"city_name\":\"中山市\",\"detail_address\":\"民安中路65号\",\"province_name\":\"广东省\"},\"contact\":{\"name\":\"黄先生\",\"mobile\":\"15398888567\"}}}],\"logistics_code\":\"jtexpress\",\"order_channel\":\"54\"}";

//        reqDataMW = "{\"sender_info\":{\"address\":{\"country_code\":\"CHN\",\"district_name\":\"萧山区\",\"city_name\":\"杭州市\",\"detail_address\":\"弋雪云仓\",\"province_name\":\"浙江省\"},\"contact\":{\"name\":\"过眼云烟\",\"mobile\":\"18525873083\"}},\"order_infos\":[{\"order_id\":\"2654530829489781\",\"items\":[{\"item_count\":1,\"item_name\":\"随机小礼品-1\"}],\"receiver_info\":{\"address\":{\"country_code\":\"CHN\",\"district_name\":\"义乌市\",\"city_name\":\"金华市\",\"detail_address\":\"福田街道紫金一区35栋3单元店面\",\"province_name\":\"浙江省\"},\"contact\":{\"name\":\"水杯\",\"mobile\":\"15058679391\"}}}],\"logistics_code\":\"zhongtong\",\"order_channel\":\"54\"}";

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("request_data", reqDataMW);
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDLogisticsWaybillGetUrl, data);
        System.out.println(resp);

    }

    // 步骤（1）单独获取快递单号
    @Test
    public void  DDLogisticsNewcreateorder() throws Exception {

        String reqData2 = "{" +
                "    \"sender_info\":{" +
                "        \"address\":{" +
                "            \"country_code\":\"CHN\"," +
                "            \"province_name\":\"上海市\"," +
                "            \"city_name\":\"上海市\"," +
                "            \"district_name\":\"青浦区\"," +
                "            \"detail_address\":\"测试\"" +
                "        }," +
                "        \"contact\":{" +
                "            \"name\":\"hanson\"," +
                "            \"mobile\":\"18817834074\"" +
                "        }" +
                "    }," +
                "    \"logistics_code\":\"zhongtong\"," +
                "    \"order_infos\":[" +
                "        {" +
                "            \"order_id\":\"49488636457864312061\"," +
                "            \"pack_id\":\"pkg_01\"," +
                "            \"receiver_info\":{" +
                "                \"address\":{" +
                "                    \"country_code\":\"CHN\"," +
                "                    \"province_name\":\"浙江省\"," +
                "                    \"city_name\":\"杭州市\"," +
                "                    \"district_name\":\"西湖区\"," +
                "                    \"detail_address\":\"#ZbZE6fdvKu0n/gB488d4Uxj7bzQi#n3zBNQgaGXiMjQb7CIwUDrQ/oEQGP43PmvV9Oi4lTooQagOruEjHzoy8JiUfZ/+3kOQABhWWXbhtTCKXDaE5GrNfbVhmef3N2d1u4eIDRZ6cT4fAsxUY4eCRTD+3pg==*CgwIARCtHBiYJSABKAESPgo8azkO9VDljulg7gl8Z8ykmOOfh93YeeMY+p+VbuytBkgfmS61mTnWtH4omldn44DJ4b7CZPzdDybE/QFBGgA=#1##\"" +
                "                }," +
                "                \"contact\":{" +
                "                    \"name\":\"#JsKP#mjMUmDSsFnoIZQyxnI8dKvJm7af8F+6YU+ZQRYZRGpukzSczT5arppGdExz9pleid3zCHGr8FvBmFsMmU5mmZ03F8k/dCNEy*CgwIARCtHBiYJSABKAESPgo8EGql2MQlYZNFj1iHVlSK41VeEYk0sH3UrzR5/8nYgPlbhkrbI2fbWQmlYrkuSJig7K3SHQi1jI6oXkp5GgA=#1##\"," +
                "                    \"mobile\":\"$dzFmfVaRHLlEEdPMqNWcR5WZ01oatEm4hTtC3iO9+sM=$0uwlr1TmDiJNYFPgdS1vCTOdq2ZxXk1ed8aSS8AZLpa8itQk/L9noLXPPnyWt6VOF3ZyyC+N00OGk09TzF+4/Mtn4VeJ0OGAPqZtGTlJGJxx*CgwIARCtHBiYJSABKAESPgo88ghgupGfSv8mUFdaXWzO3J54gbTj1192xyPJJUHcxjR0SqFUEdfoeUVsLKK6EaekOkvU5OTFCWgREZehGgA=$1$$\"" +
                "                }" +
                "            }," +
                "            \"items\":[" +
                "                {" +
                "                    \"item_name\":\"【测试商品勿拍】白色水墨印花衬衣\"," +
                "                    \"item_specs\":\"颜色:默认 尺码:120cm\"," +
                "                    \"item_count\":1" +
                "                }" +

                "            ]" +
                "        }, " +
                "        {" +
                "            \"order_id\":\"4948795729456854758\", " +
                "            \"receiver_info\":{" +
                "                \"address\":{" +
                "                    \"country_code\":\"CHN\"," +
                "                    \"province_name\":\"浙江省\"," +
                "                    \"city_name\":\"杭州市\"," +
                "                    \"district_name\":\"西湖区\"," +
                "                    \"detail_address\":\"#88d4Uxj7bzQiCZ/tB348#JYQbCE0Z3qS4enifncvRfvy/2//ByIgja0ez2ziH5ejCsBpPzEZ1sSuUDukoVKuQHCIok56Ew2jW/BDm4cri1uAWizYOx5y2JIB2ulq0mxMpj5OtkzMqtg==*CgwIARCtHBiYJSABKAESPgo8ak9uAm8xewS+3cxuj53pXMk1QHFjZPL4lRR6ReHcY/n46MfiA2HAos7aQ2k7eAoqlY9IDo9SgUDxcugRGgA=#1##\"" +
                "                }," +
                "                \"contact\":{" +
                "                    \"name\":\"#JsKP#MU+XJbCwo6OvX9i6/9FGQfXBEikV2PhUf/Y+SvMTXJxVvDfGsu6sZkB/66vPHkQ7eot/UFyVXEiJnhq+rES6wlm5fcvybYJY*CgwIARCtHBiYJSABKAESPgo8iXhpCxN3TRpdw1c354bgKOqDbL9yt0J9qBUSIY/bLcW41gWAURRt6Nxp11BTrA4key6atmBb/BMdActIGgA=#1##\"," +
                "                    \"mobile\":\"$dzFmfVaRHLlEEdPMqNWcR5WZ01oatEm4hTtC3iO9+sM=$Ws0Fjzxv1wXfWsPGHS1XMv6riNju8b3AJtk/fiTAHaOiVUf9h+YSlBuVjF18SAAIA5ymX6H/V6q/HPc0vIUvuTViP4TM40i3RaWOMd7se5At*CgwIARCtHBiYJSABKAESPgo8f8TVWjyoVQNlT7MEj7tPiYwTpuHZWzKSuMWMsi0UV3oIMsp2U3rUASks98PnPsr0i3IwvzrOTMPYbiBnGgA=$1$$\"" +
                "                }" +
                "            }," +
                "            \"items\":[" +
                "                {" +
                "                    \"item_name\":\"【【测试商品勿拍】dy测试上衣-各种编码3\", " +
                "                    \"item_count\":1" +
                "                }" +
                "               " +
                "            ]" +
                "        }" +
                "    ]," +
                "    \"user_id\":-1," +
                "    \"order_channel\":\"1\"" +
                "}";

//          reqData2 = "{\"sender_info\":{\"address\":{\"country_code\":\"CHN\",\"district_name\":\"中山市\",\"city_name\":\"中山市\",\"detail_address\":\"民安中路191号2楼\",\"province_name\":\"广东省\"},\"contact\":{\"name\":\"林生\",\"mobile\":\"15220909710\"}},\"order_infos\":[{\"order_id\":\"548059574956693\",\"items\":[{\"item_count\":1,\"item_name\":\"茶包-1\"}],\"receiver_info\":{\"address\":{\"country_code\":\"CHN\",\"district_name\":\"小榄镇\",\"city_name\":\"中山市\",\"detail_address\":\"民安中路65号\",\"province_name\":\"广东省\"},\"contact\":{\"name\":\"黄先生\",\"mobile\":\"15398888567\"}}}],\"logistics_code\":\"jtexpress\",\"order_channel\":\"54\"}";


//        reqData2= "{\"sender_info\":{\"address\":{\"country_code\":\"CHN\",\"district_name\":\"萧山区\",\"city_name\":\"杭州市\",\"detail_address\":\"弋雪云仓\",\"province_name\":\"浙江省\"},\"contact\":{\"name\":\"过眼云烟\",\"mobile\":\"18525873083\"}},\"order_infos\":[{\"order_id\":\"2051901854858927\",\"items\":[{\"item_count\":1,\"item_name\":\"随机小礼品【站点签收】-1\"}],\"receiver_info\":{\"address\":{\"country_code\":\"CHN\",\"district_name\":\"柯桥区\",\"city_name\":\"绍兴市\",\"detail_address\":\"华舍街道华舍街道兴华小区南区\",\"province_name\":\"浙江省\"}," +
//                "\"contact\":{\"name\":\"寿先生\",\"mobile\":\"15705850542\"}}}],\"logistics_code\":\"zhongtong\",\"order_channel\":\"54\"}";

//        reqData2 = "{\"sender_info\":{\"address\":{\"country_code\":\"CHN\",\"district_name\":\"萧山区\",\"city_name\":\"杭州市\",\"detail_address\":\"弋雪云仓\",\"province_name\":\"浙江省\"},\"contact\":{\"name\":\"过眼云烟\",\"mobile\":\"18525873083\"}},\"order_infos\":[{\"order_id\":\"2068402470199704\",\"items\":[{\"item_count\":1,\"item_name\":\"随机小礼品-1\"}],\"receiver_info\":{\"address\":{\"country_code\":\"CHN\",\"district_name\":\"渝北区\",\"city_name\":\"重庆市\",\"detail_address\":\"回兴街道金燕佳园附4号门市-卤菜\",\"province_name\":\"重庆市\"},\"contact\":{\"name\":\"曾沙沙\",\"mobile\":\"13290008859\"}}}],\"logistics_code\":\"zhongtong\",\"order_channel\":\"54\"}";

         Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("request_data", reqData2);
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDLogisticsNewcreateorderUrl, data);
        System.out.println(resp);


    }

    // 单独（步骤2）-获取电子面单信息
    @Test
    public void  DDLogisticsGetWayBill() throws Exception {

       String   reqDataMW ="[{\"logistics_code\":\"zhongtong\" , \"track_no\": \"75531717195834\" }, \n" +
                "     {\"logistics_code\":\"zhongtong\" , \"track_no\": \"75531717195835\" }\n" +
                 "    ]";

         reqDataMW ="[{\"logistics_code\":\"zhongtong\" , \"track_no\": \"75531717195835\" }]";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("request_data", reqDataMW);
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDLogisticsGetWayBillUrl, data);
        System.out.println(resp);

    }


    // 查询地址快递是否可以送达
    @Test
    public void  DDLogisticsGetOutRange() throws Exception {

        String requestData = "{" +
                "    \"logistics_code\": \"zhongtong\"," +
                "    \"addr_list\": [" +
                "        {" +
                "            \"object_id\": \"id001\"," +
                "            \"sender_addr\": {" +
                "                \"country_code\": \"CHN\"," +
                "                \"city_name\": \"杭州市\"," +
                "                \"detail_address\": \"龙湖西溪天街蓝海引擎8楼\"," +
                "                \"district_name\": \"西湖区\"," +
                "                \"province_name\": \"浙江省\"," +
                "                \"street_name\": \"\"" +
                "            }," +
                "            \"receiver_addr\": {" +
                "                \"country_code\": \"CHN\"," +
                "                \"city_name\": \"金华市\"," +
                "                \"detail_address\": \"福田街道紫金一区35栋3单元店面\"," +
                "                \"district_name\": \"义乌市\"," +
                "                \"province_name\": \"浙江省\"," +
                "                \"street_name\": \"\"" +
                "            }" +
                "        }," +
                "        {" +
                "            \"object_id\": \"id002\"," +
                "            \"sender_addr\": {" +
                "                \"country_code\": \"CHN\"," +
                "                \"city_name\": \"杭州市\"," +
                "                \"detail_address\": \"龙湖西溪天街蓝海引擎8楼\"," +
                "                \"district_name\": \"西湖区\"," +
                "                \"province_name\": \"浙江省\"," +
                "                \"street_name\": \"\"" +
                "            }," +
                "            \"receiver_addr\": {" +
                "                \"country_code\": \"CHN\"," +
                "                \"city_name\": \"象山县\"," +
                "                \"detail_address\": \"天安路999号\"," +
                "                \"district_name\": \"宁波市\"," +
                "                \"province_name\": \"浙江省\"," +
                "                \"street_name\": \"\"" +
                "            }" +
                "        }" +
                "" +
                "    ]," +
                "    \"type\": 0," +
                "    \"service_list\": []," +
                "    \"product_type\": \"\"," +
                "    \"delivery_req\": {}" +
                "}";

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("request_data", requestData);
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDLogisticsGetOutRangeUrl, data);
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
