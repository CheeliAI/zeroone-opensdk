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

import java.util.*;

/**
 * 场景：拼多多的接入， 合作伙伴使用，可管理其他授权的店铺。即可以管理多家店铺
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenPDDGoodsTests.class )
public class OpenPDDGoodsTests {

    @Test
    public void contextLoads() {
    }


    /**
     *  商品列表接口
     * @throws Exception
     */
    @Test
    public void PddGoodsListGet() throws Exception {

        String seller_nick = Config.PddSellerNick; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("is_onsale", "1");
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        doHttpRequest(Config.PddGoodsListGetUrl, data);

    }

    /**
     *  商品明细接口
     * @throws Exception
     */
    @Test
    public void PddGoodsDetailGet() throws Exception {

        String seller_nick = Config.PddSellerNick; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("goods_id", "160210883299");
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        doHttpRequest(Config.PddGoodsDetailGetUrl, data);

    }


   /**
     *  商品上架状态设置
     * @throws Exception
     */
    @Test
    public void PddGoodsSaleStatusSet() throws Exception {

        String seller_nick = Config.PddSellerNick; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("goods_id", "160210883299");
        data.put("is_onsale", "0");
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        doHttpRequest(Config.PddGoodsSaleStatusSetUrl, data);

    }

    /**
     *  商品运费模版接口
     * @throws Exception
     */
    @Test
    public void PddGoodsLogisticsTemplateGet() throws Exception {

        String seller_nick = Config.PddSellerNick; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("page", "1");
        data.put("page_size", "20");
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        doHttpRequest(Config.PddGoodsLogisticsTemplateGetUrl, data);

    }




    /**
     *  商品图片上传接口
     * @throws Exception
     */
    @Test
    public void PddGoodsImageUploadRequest() throws Exception {

        String seller_nick = Config.PddSellerNick; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        String base64Image  = Utils.getBase64ImageFromBinary("/Users/miller/Downloads/mm.jpeg");
        base64Image = "data:image/jpeg;base64," + base64Image;
         data.put("image", base64Image );
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        doHttpRequest(Config.PddGoodsImageUploadRequestUrl, data);

    }

    /**
     *  商品属性类目接口
     * @throws Exception
     */
    @Test
    public void PddGoodsSpecGetRequestUrl() throws Exception {

        String seller_nick = Config.PddSellerNick; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("cat_id", "18056" );
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        doHttpRequest(Config.PddGoodsSpecGetRequestUrl, data);

    }

    /**
     *  类目预测接口
     * @throws Exception
     */
    @Test
    public void PddGoodsOuterCatMappingGetRequest() throws Exception {

        String result ="";
        String seller_nick = Config.PddSellerNick ; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("outer_cat_id", "0");
        data.put("outer_cat_name", "");
        data.put("outer_goods_name", "几许时光黄桃芒果酒草莓番石榴酒红树莓西柚女士微醺低度酒375m");
        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        doHttpRequest(Config.PddGoodsOuterCatMappingGetRequestUrl, data);


    }
    /**
     *  商品属性类目接口
     * @throws Exception
     */
    @Test
    public void PddGoodsSpecIdGetRequest() throws Exception {

        String seller_nick = Config.PddSellerNick; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("parent_spec_id", "1216" );
        data.put("spec_name", "超大号" );
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        doHttpRequest(Config.PddGoodsSpecIdGetRequestUrl, data);

    }

    /**
     *  商品地区/国家接口
     * @throws Exception
     */
    @Test
    public void PddGoodsCountryGetRequest() throws Exception {

        String seller_nick = Config.PddSellerNick; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        doHttpRequest(Config.PddGoodsCountryGetRequestUrl, data);

    }

    /**
     *  获取商品提交的商品详情
     * @throws Exception
     */
    @Test
    public void PddGoodsCommitDetailGetRequest() throws Exception {

        String seller_nick = Config.PddSellerNick; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("goods_commit_id", "72948403362");
        data.put("goods_id", "286996055566");
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        doHttpRequest(Config.PddGoodsCommitDetailGetRequestUrl, data);

    }


    /**
     *  拼多多商品新增接口
     * @throws Exception
     */
    @Test
    public void PddGoodsAddRequest() throws Exception {

        String seller_nick = Config.PddSellerNick; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        String cg = "[\"https://img.pddpic.com/open-gw/2021-10-11/ffc9a1e35840c3cf7f7af66f56c004c1.jpeg\"]";
        data.put("carousel_gallery", cg);
        data.put("cat_id", "17438");
        data.put("cost_template_id", "163280639235619");
        // 地区/国家ID，country_id可以通过pdd.goods.country.get获取，仅在goods_type为2、3时（海淘商品）入参生效，其余goods_type传0
        data.put("country_id", "10");
        data.put("goods_desc", "新包装，保证产品的口感和新鲜度。单颗独立小包装，双重营养，1斤家庭分享装，更实惠新疆一级骏枣夹核桃仁");
        data.put("goods_name", "这是通过API发布的商品不要拍！");

        String dg = "[\"https://img.pddpic.com/open-gw/2021-10-11/ffc9a1e35840c3cf7f7af66f56c004c1.jpeg\"]";

        data.put("detail_gallery", dg);
        data.put("goods_type", "1");
        data.put("is_folt", "true");
        data.put("is_pre_sale", "false");
        data.put("is_refundable", "true");
        data.put("market_price", "2000");
        data.put("second_hand", "false");
        data.put("shipment_limit_second", "86400");

        String skuList = "[{\n" +
                "        \"id\": 0,\n" +
                "        \"limit_quantity\": 0,\n" +
                "        \"out_sku_sn\": \"\",\n" +
                "        \"spec_id_list\":  \"[765]\" ,\n" +
                "        \"is_onsale\": 1,\n" +
                "        \"price_in_yuan\": \"10\",\n" +
                "        \"multi_price\": 1100,\n" +
                "        \"price\": 1200,\n" +
                "        \"quantity\": 20,\n" +
                "        \"thumb_url\": \"https://t16img.yangkeduo.com/garner-api/385531de9957fb1f3de0ce48956ee432.jpeg\",\n" +
                "        \"thumb_url_file_id\": 898996070,\n" +
                "        \"weight\": 0,\n" +
                "        \"spec\": [{\n" +
                "            \"parent_id\": 1216,\n" +
                "            \"parent_name\": \"尺寸\",\n" +
                "            \"spec_id\": 765,\n" +
                "            \"spec_name\": \"小号\"\n" +
                "        }],\n" +

                "        \"sku_srv_templates\": \"\"\n" +
                "    }, {\n" +
                "        \"id\": 0,\n" +
                "        \"limit_quantity\": 0,\n" +
                "        \"out_sku_sn\": \"\",\n" +
                "        \"spec_id_list\":  \"[767]\" ,\n" +
                "        \"is_onsale\": 1,\n" +
                "        \"price_in_yuan\": \"11\",\n" +
                "        \"multi_price\": 1200,\n" +
                "        \"price\": 1300,\n" +
                "        \"quantity\": 20,\n" +
                "        \"thumb_url\": \"https://t16img.yangkeduo.com/garner-api/70ec7360c13688309252f88a9ce49965.jpeg\",\n" +
                "        \"thumb_url_file_id\": 898953569,\n" +
                "        \"weight\": 0,\n" +
                "        \"spec\": [{\n" +
                "            \"parent_id\": 1216,\n" +
                "            \"parent_name\": \"尺寸\",\n" +
                "            \"spec_id\": 767,\n" +
                "            \"spec_name\": \"大号\"\n" +
                "        }],\n" +

                "        \"sku_srv_templates\": \"\"\n" +
                "    }]";

        data.put("sku_list", skuList);
        data.put("is_onsale", "1");
        data.put("limit_quantity", "999");
        data.put("multi_price", "1000");
        data.put("price", "900");
        data.put("quantity", "100");
        data.put("thumb_url", "https://img.pddpic.com/open-gw/2021-10-11/ffc9a1e35840c3cf7f7af66f56c004c1.jpeg");
        data.put("weight", "100");

        String skuProperties = "[{\n" +
                "        \"template_pid\": 96688,\n" +
                "        \"template_module_id\": 22326,\n" +
                "        \"ref_pid\": 310,\n" +
                "        \"pid\": 5,\n" +
                "        \"value\": \"\",\n" +
                "        \"value_unit\": \"\"\n" +
                "    }]";

        data.put("sku_properties",skuProperties);
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        doHttpRequest(Config.PddGoodsAddRequestUrl, data);

    }


    /**
     *   拼多多获取当前授权商家可发布的商品类目信息
     * @throws Exception
     */
    @Test
    public void PddGoodsAuthorizationCatsRequest() throws Exception {

        String seller_nick = Config.PddSellerNick; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("parent_cat_id", "0");
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        doHttpRequest(Config.PddGoodsAuthorizationCatsRequestUrl, data);

    }

    /**
     *   商品标准类目接口
     * @throws Exception
     */
    @Test
    public void PddGoodsCatsRequestUrl() throws Exception {

        String seller_nick = Config.PddSellerNick; // 拼多多卖家账号
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("parent_cat_id", "0");
        // 参数签名
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        doHttpRequest(Config.PddGoodsCatsRequestUrl, data);

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
