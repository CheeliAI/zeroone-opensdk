package com.cheeli.doudian;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cheeli.Config;
import com.cheeli.utils.Utils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kin
 * @version 1.0
 * @date 2023/6/22 9:31 上午
 */
public class OpenDouDianProductReleaseTests {

    //创建资料文件夹
    @Test
    public void  DDMaterialCreateFolder() throws Exception {
        String   reqData ="{\"name\":\"测试\" , \"parent_folder_id\": \"0\", \"type\": 0 }";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("request_data", reqData);
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDMetrialCreateFoRequestUrl, data);
        System.out.println(resp);

    }

    //上传图片
    @Test
    public void  DDMaterialUploadImageSync() throws Exception {
        String   reqData ="{\"folder_id\":\"0\" , \"url\": \"https://profile-avatar.csdnimg.cn/default.jpg\", \"material_name\":\"测试\",\"need_distinct\":\"true\"}";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("request_data", reqData);
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDMetrialUploadImageSyncRequestUrl, data);
        System.out.println(resp);

    }





    //批量上传图片
    @Test
    public void  DDBatchUploadImageSync() throws Exception {
        String   reqData ="{\"materials\":[{\"request_id\":\"\",\"folder_id\":\"0\",\"material_type\":\"photo\",\"name\":\"test\",\"url\":\"http://xxx.xxx\"}],\"need_distinct\":false}";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("request_data", reqData);
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDBatchUploadImageSyncRequestUrl, data);
        System.out.println(resp);

    }

    //批量上传视频到素材中心 {"materials":[{"request_id":"","folder_id":"","material_type":"","name":"","url":""}]}
    @Test
    public void  DDBatchUploadVideoAsync() throws Exception {
        String   reqData ="";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("request_data", reqData);

        data.put("materials","[\n" +
            "    {\n" +
            "      \"request_id\": \"123456789\",\n" +
            "      \"folder_id\": \"73691281073984637961425\",\n" +
            "      \"name\": \"testwin.mp4\",\n" +
                       "      \"url\": \"https://huadong-jyd.oss-cn-shanghai.aliyuncs.com/winner.mp4?Expires=1733922435&OSSAccessKeyId=TMP.3KgYh9AoTCPGaEih8vdZMViDpb3iyzcpEQHe3SQMCdATJGxCiQGLcXAYyRvafLjeXdmwW1EL37H6sRXacatZA79fJamYHi&Signature=yxEP914t5e%2BduvhdRK6E%2BFBHfgI%3D\",\n" +
//            "      \"file_uri\": \"/Users/miller/working/demodata/winner.mp4\",\n" +
            "      \"material_type\": \"video\"\n" +
            "    }\n" +
            "  ]" );

        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDBatchUploadVideoAsyncRequestUrl, data);
        System.out.println(resp);

    }


    //查看素材
    @Test
    public void  DDQueryMaterialDetail() throws Exception {
        String   reqData ="{\"material_id\":\"72474249983862049830798\"}";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("request_data", reqData);
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDQueryMaterialDetailRequestUrl, data);
        System.out.println(resp);

    }


    //搜索素材  {"material_id":"","material_name":"测试","material_type":null,
    // "audit_status":[3],"create_time_start":"","create_time_end":"",
    // "folder_id":"","page_num":0,"page_size":0,"order_type":0,"operate_status":[1]}
    //,"create_time_start":"","create_time_end":""
    @Test
    public void  DDSearchMaterial() throws Exception {
        String   reqData ="{\"material_id\":\"72474249983862049830798\",\"material_name\":\"测试\",\"material_type\":null,\n" +
                "     \"audit_status\":[3]," +
                "     \"folder_id\":\"0\",\"page_num\":1,\"page_size\":20,\"order_type\":0,\"operate_status\":[1]}";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("request_data", reqData);
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSearchMaterialRequestUrl, data);
        System.out.println(resp);

    }

    //获取叶子类目  {"cid":0}
    @Test
    public void  DDGetShopCategory() throws Exception {
//        String   reqData ="";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
//        data.put("request_data", reqData);
        //20080  21024
        data.put("cid",0);// 21001项链   33223 无线传输设备
        data.put("sign", Sign(data, Config.AppSecret));

        // 调用服务API
        String resp = doHttpRequest(Config.DDGetShopCategoryRequestUrl, data);
        System.out.println(resp);

    }

    //获取品牌 {"category_id":123,"query":"wu"}
    @Test
    public void  DDShopBrandList() throws Exception {
        String   reqData ="{\"category_id\":20212,\"query\":\"a\"}";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("request_data", reqData);
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDShopBrandListRequestUrl, data);
        System.out.println(resp);

    }


    //获取运费模板列表  {"name":"模版1","page":"0","size":"10"}  页数（默认为0，第一页从0开始） ,每页模板数（默认为10）
    @Test
    public void  DDFreightTemplateList() throws Exception {
        String   reqData ="{\"name\":\"\",\"page\":\"1\",\"size\":\"10\"}";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("request_data", reqData);
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDFreightTemplateListRequestUrl, data);
        System.out.println(resp);

    }

    //获取类目下面的所有属性  {"category_leaf_id":1342353245}
    @Test
    public void  DDGetCatePropertyV2() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("category_leaf_id", 20220);
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDGetCatePropertyV2RequestUrl, data);

        JSONObject jsonObject = JSON.parseObject(resp);
        JSONArray data1 = jsonObject.getJSONObject("data").getJSONArray("data");
        JSONArray ja = (JSONArray) data1.clone();
        ja.clear();
        for (int i = 0; i < data1.size(); i++) {
            JSONObject o = (JSONObject) data1.get(i);
            if(o.getIntValue("required")==1){
                ja.add(o);
//                System.out.println(JSON.toJSONString(o));
            }
        }
        System.out.println("###Result:"+JSON.toJSONString(ja));

    }

    //承诺发货时间  {"category_id":20219,"senses":[1001]}
    @Test
    public void  DDGetProductUpdateRule() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("category_id", 20219);
        data.put("senses", "[1001]");
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDGetProductUpdateRuleRequestUrl, data);
        System.out.println(resp);

    }

    //资质  {"category_id":20215}
    @Test
    public void  DDQualificationConfig() throws Exception {
        String   reqData ="{\"category_id\":20215}";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("request_data", reqData);
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDQualificationConfigRequestUrl, data);
        System.out.println(resp);

    }

    //first_cid,first_cid_name,second_cid,second_cid_name,third_cid,third_cid_name,  select_property
    //商品类目属性，参考"select_property":{"1687":[{"value":0,"name":"填入品牌名"}],"3320":[{"value":18972,"name":"99新"}]}
    @Test
    public void  DDGetRecommendName() throws Exception {
        String   reqData ="";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("request_data", reqData);
        data.put("scene",new String[]{"product_name_prefix"});
        data.put("category_leaf_id",20215);
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDGetRecommendNameRequestUrl, data);
        System.out.println(resp);

    }

    //发布商品  {"outer_product_id":"232334","product_type":0,"category_leaf_id":20000,"product_format":"货号|8888^上市年份季节|2018年秋季","name":"xxx补水液","recommend_remark":"这个商品很好啊","pic":"img_url1|img_url2|img_url3","description":"img_url1|img_url2|img_url3","pay_type":1,"delivery_method":7,"cdf_category":"1","reduce_type":1,"assoc_ids":"1|2|3","freight_id":123,"weight":1000,"weight_unit":1,"delivery_delay_day":7,"presell_type":0,"presell_delay":7,"presell_end_time":"2020-02-21 18:54:27","supply_7day_return":0,"mobile":"40012345","commit":true,"brand_id":123,"remark":"备注","out_product_id":123,"quality_list":null,"spec_name":"颜色-尺码","specs":"颜色|红色,黑色^尺码|S,M","spec_prices":"","spec_pic":"img_url,img_url,img_url","maximum_per_order":200,"limit_per_buyer":1,"minimum_per_order":2,"product_format_new":"","spu_id":14567,"appoint_delivery_day":2,"third_url":"http://img.alicdn.com/xxxx","extra":"略","src":"略","standard_brand_id":111,"need_check_out":true,"poi_resource":{"valid_days":0,"valid_start":0,"valid_end":0,"service_num":"","notification":"","code_type":0,"count":0,"couponSecondExchange":0,"total_can_use_count":0,"link":"","condition":"","coupon_return_methods":null},"car_vin_code":"BA111111111111111","presell_config_level":3,"need_recharge_mode":true,"account_template_id":"122112","presell_delivery_type":1,"white_back_ground_pic_url":"http://aaaa","long_pic_url":"http://aaaa","after_sale_service":null,"sell_channel":null,"start_sale_type":0}
    //影响字段：pic，description，spec_pic，white_back_ground_pic_url，long_pic_url，pic_url ，
    // 这些字段需传入素材中心URL地址，不支持直接使用外部图片url。

    // 叶子类目id通过shop/getShopCategory获取，入参cid ，一级类目cid=0 ，获取到二级类目后再通过这个作为入参获取后面的类目，依次循环，直到is_leaf=true即为叶子类目ID
    //product_format_new 通过 getCatePropertyV2接口获取 格式如下：[\"241\":[{\"value\":38566,\"name\":\"薄款\",\"diy_type\":1}]], required=1为必选，required=0为可选
    @Test
    public void  DDAddV2() throws Exception {

        String reqData="{\n" +
                "  \"outer_product_id\": \"0\",\n" +
                "  \"product_type\": 0,\n" +
                "\"weight\":1000,\"weight_unit\":1,"+
                "  \"presell_type\":0,"+
                "  \"delivery_delay_day\":2,"+
                "  \"category_leaf_id\": 20212,\n" +
                "  \"standard_brand_id\": 1290669022,\n" +
                "  \"name\": \"男装\",\n" +
                "  \"pic\": \"https://p3-aio.ecombdimg.com/obj/ecom-shop-material/ALSUvYM_m_3c2ebaeeabfc77571bf33a26a69a03eb_sx_22605_www250-250\",\n" +
                "  \"description\": \"https://p3-aio.ecombdimg.com/obj/ecom-shop-material/ALSUvYM_m_3c2ebaeeabfc77571bf33a26a69a03eb_sx_22605_www250-250\",\n" +
                "  \"reduce_type\": 1,\n" +
                "  \"freight_id\": 29298187,\n" +
                "  \"mobile\": \"40012345\",\n" +
                "  \"commit\": true,\n" +
                "\"product_format_new\":\"{\\\"241\\\":[{\\\"value\\\":38566,\\\"name\\\":\\\"薄款\\\",\\\"diy_type\\\":1}],\\\"1209\\\":[{\\\"value\\\":27385,\\\"name\\\":\\\"青春流行\\\",\\\"diy_type\\\":1}],\\\"2199\\\":[{\\\"value\\\":1991,\\\"name\\\":\\\"通用\\\",\\\"diy_type\\\":1}],\\\"1896\\\":[{\\\"value\\\":16122,\\\"name\\\":\\\"超短款\\\",\\\"diy_type\\\":1}],\\\"1343\\\":[{\\\"value\\\":38906,\\\"name\\\":\\\"春季\\\",\\\"diy_type\\\":1}],\\\"3059\\\":[{\\\"value\\\":56134,\\\"name\\\":\\\"宽松型\\\",\\\"diy_type\\\":1}],\\\"1714\\\":[{\\\"value\\\":955,\\\"name\\\":\\\"上班\\\",\\\"diy_type\\\":1}],\\\"785\\\":[{\\\"value\\\":26473,\\\"name\\\":\\\"牛皮\\\",\\\"diy_type\\\":0}],\\\"1467\\\":[{\\\"value\\\":168366,\\\"name\\\":\\\"39%-49%(含)\\\",\\\"diy_type\\\":0}],\\\"1825\\\":[{\\\"value\\\":33150,\\\"name\\\":\\\"拼接\\\",\\\"diy_type\\\":1}]}\","+
                "\"after_sale_service\": \"{\\\"damaged_order_return\\\":\\\"1\\\",\\\"supply_7day_return\\\":\\\"1\\\",\\\"supply_allergy_return\\\":\\\"0\\\",\\\"supply_day_return_code\\\":\\\"1\\\",\\\"supply_day_return_copywriting\\\":\\\"7天无理由退货\\\",\\\"supply_day_return_days\\\":\\\"7\\\",\\\"supply_day_return_selector\\\":\\\"7-1\\\",\\\"supply_red_ass_return\\\":\\\"0\\\",\\\"support_authentic_guaranteeV2\\\":\\\"1\\\"}\","+
                "\"spec_prices\":\"[{\\\"out_sku_id\\\":11,\\\"price\\\":990,\\\"spec_detail_name1\\\":\\\"红色\\\",\\\"spec_detail_name2\\\":\\\"S\\\",\\\"stock_num\\\":184},{\\\"out_sku_id\\\":22,\\\"price\\\":990,\\\"spec_detail_name1\\\":\\\"红色\\\",\\\"spec_detail_name2\\\":\\\"L\\\",\\\"stock_num\\\":1}]\"," +
                "\"specs\":\"颜色|红色^尺码|S,L\""+
                "}";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("request_data", reqData);
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDAddv2RequestUrl, data);
        System.out.println(resp);

    }

    @Test
    public void  DDProductDetail() throws Exception {
//        String   reqData ="{\"product_id\":\"3668963015870513459\"}";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
//        data.put("request_data", reqData);
        data.put("product_id", "3668963015870513459");
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductDetailRequestUrl, data);
        System.out.println(resp);

    }

    @Test
    public void  DDProductSetOffline() throws Exception {
        String   reqData ="{\"product_id\":3624800399778820330,\"outer_product_id\":\"\",\"store_id\":0}";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("request_data", reqData);
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSetOfflineRequestUrl, data);
        System.out.println(resp);

    }


    @Test
    public void  DDProductEditV2() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid", Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("product_id", "3724250815662260681");
        data.put("name", "测试商品请勿购买女装-v2");
        data.put("commit", "true");
        data.put("sign", Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDEditV2RequestUrl, data);
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



    @Test
    public void  GetBrandList() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        //        long start = System.currentTimeMillis() / 1000 - 3600 * 24 * 7 ;
        //        long end = System.currentTimeMillis() / 1000;
        //        data.put("create_time_start", String.valueOf(start));
        //        data.put("create_time_end", String.valueOf(end));
        data.put("category_id", "20219");
        data.put("query","");
        data.put("brand_ids","[]");
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDBrandList, data);
        System.out.println(resp);
    }



    //创建文件夹
    @Test
    public void  DDMaterialCreatefolder() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("name", "测试0514");
        data.put("parent_folder_id", "0");
        data.put("type", 0);
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDMaterialCreatefolder, data);
        System.out.println(resp);
    }

    // 编辑/移动文件夹  73691281073984637961425
    @Test
    public void  DDMaterialEditfolder() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        //        long start = System.currentTimeMillis() / 1000 - 3600 * 24 * 7 ;
        //        long end = System.currentTimeMillis() / 1000;
        //        data.put("create_time_start", String.valueOf(start));
        //        data.put("create_time_end", String.valueOf(end));
        data.put("name", "测试0514New2");
        data.put("folder_id", "74466317772871109241425");
        //        data.put("to_folder_id", "");
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDMaterialEditfolder, data);
        System.out.println(resp);
    }

    //TODO 未找到接口
    @Test
    public void  DDShopBrandlist() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        //        long start = System.currentTimeMillis() / 1000 - 3600 * 24 * 7 ;
        //        long end = System.currentTimeMillis() / 1000;
        //        data.put("create_time_start", String.valueOf(start));
        //        data.put("create_time_end", String.valueOf(end));
        data.put("name", "测试0514New");
        data.put("folder_id", "73691281073984637961425");
        //        data.put("to_folder_id", "");
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDShopBrandlist, data);
        System.out.println(resp);
    }

    //TODO 未找到接口
    @Test
    public void  DDSkuAddall() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        //        long start = System.currentTimeMillis() / 1000 - 3600 * 24 * 7 ;
        //        long end = System.currentTimeMillis() / 1000;
        //        data.put("create_time_start", String.valueOf(start));
        //        data.put("create_time_end", String.valueOf(end));
        data.put("name", "测试0514New");
        data.put("folder_id", "73691281073984637961425");
        //        data.put("to_folder_id", "");
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSkuAddall, data);
        System.out.println(resp);
    }

    //TODO
    @Test
    public void  DDSkuList() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        //        long start = System.currentTimeMillis() / 1000 - 3600 * 24 * 7 ;
        //        long end = System.currentTimeMillis() / 1000;
        //        data.put("create_time_start", String.valueOf(start));
        //        data.put("create_time_end", String.valueOf(end));
        data.put("product_id", 3668963015870513459L);
        //        data.put("out_product_id", 0);
        //        data.put("to_folder_id", "");
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSkuList, data);
        System.out.println(resp);
    }


    //TODO
    @Test
    public void  DDSkuEditcode() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        //        long start = System.currentTimeMillis() / 1000 - 3600 * 24 * 7 ;
        //        long end = System.currentTimeMillis() / 1000;
        //        data.put("create_time_start", String.valueOf(start));
        //        data.put("create_time_end", String.valueOf(end));
        data.put("code", "sk100xxx");
        data.put("sku_id", 3436768406271746L);
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSkuEditcode, data);
        System.out.println(resp);
    }

    //TODO
    @Test
    public void  DDSkuEditprice() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("price", 222223);
//        data.put("code", "sk100");
                data.put("sku_id", 3436768406271746L);
        //        data.put("out_sku_id", 0);
        //        data.put("product_id", 0);
        //        data.put("out_product_id", 0);
        //        data.put("store_id", 0);
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSkuEditprice, data);
        System.out.println(resp);
    }

    @Test
    public void  DDSkuSyncstock() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("code", "sk100xxx");
//                data.put("sku_id", 173787598);
        //        data.put("out_sku_id", 44354354);
        //        data.put("product_id", 3539925204033339668L);
        //        data.put("out_product_id", 435243654356L);
        //        data.put("out_warehouse_id", "dy123");
        //        data.put("supplier_id", "1");
        //        data.put("incremental", false);
        //        data.put("idempotent_id", "202206021500001001");
        data.put("stock_num", 5000);
        data.put("step_stock_num", 200);
        //        data.put("store_id", 12345);
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSkuSyncstock, data);
        System.out.println(resp);
    }


    @Test
    public void  DDSkuSyncstockBatch() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("product_id",3668963015870513459L );
        //        data.put("idempotent_id", "2");
        data.put("incremental", false);
        //        data.put("outer_product_id", "123");
        //        data.put("store_id", 123);
        data.put("sku_sync_list","[\n" +
            "    {\n" +
            //                "      \"outer_sku_id\": \"232331\",\n" +
            "      \"sku_id\": \"3398076948315650\",\n" +
            "      \"sku_type\": \"0\",\n" +
            "      \"stock_num\": \"1000\",\n" +
            "      \"step_stock_num\": \"500\",\n" +
            "      \"stock_map\": [\n" +
            "        {\n" +
            "          \"out_warehouse_id\": \"123\",\n" +
            "          \"stock_num\": \"10\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"supplier_id\": \"1\"\n" +
            "    }\n" +
            "  ]");//sku_sync_list是一个对象
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSkuSyncstockBatch, data);
        System.out.println(resp);
    }


    @Test
    public void  DDSpuGetauditInfo() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("spu_id","7166887849936109828" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSpuGetauditInfo, data);
        System.out.println(resp);
    }


    //未找到，使用getSpu接口
    @Test
    public void  DDSpuGet() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("category_leaf_id","22863" );
//        data.put("spu_id","7120712486076088620" );
        data.put("key_properties","[\n" +
            "    {\n" +
            "        \"property_id\": \"4299\",\n" +
            "            \"property_values\": [\n" +
            "        {\n" +
            "            \"value_id\": \"49341\",\n" +
            "                \"value_name\": \"苹果/Apple\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSpuGetkeyPropertybyCid, data);
        System.out.println(resp);
    }

    @Test
    public void  DDSpuGetspuRule() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("category_id",23362 );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSpuGetspuRule, data);
        System.out.println(resp);
    }

    @Test
    public void  DDSpuGetspuTpl() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("category_id",23362 );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSpuGetspuTpl, data);
        System.out.println(resp);
    }



    //product



    @Test
    public void  DDProductAuditlist() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("publish_status","1" );
        data.put("page","0" );
        data.put("size","20" );
//        data.put("product_id","3668963015870513459" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductAuditlist, data);
        System.out.println(resp);
    }

    @Test
    public void  DDProductCategoryDimlist() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("publish_status","0" );
        data.put("page","0" );
        data.put("size","20" );
        data.put("product_id","11" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductCategoryDimlist, data);
        System.out.println(resp);
    }

    //{"code":0,"message":"ok","data":{"template_id":7369486293141373223}}
    @Test
    public void  DDProductCreateComponentTemplateV2() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;

        String front_data ="{\n" + "  \"title\": \"测试模板3\",\n" + "  \"desc\": \"测试\",\n" + "  \"tempName\": \"测试模板3副本1副本1\",\n" + "  \"configTable\": [\n" + "    {\n" + "      \"size\": \"XS\",\n" + "      \"specMap\": { \"身高（cm）\": \"170\", \"体重（斤）\": \"50\", \"胸围（cm）\": \"53\" }\n" + "    },\n" + "    {\n" + "      \"size\": \"M\",\n" + "      \"specMap\": { \"身高（cm）\": \"160\", \"体重（斤）\": \"40\", \"胸围（cm）\": \"60\" }\n" + "    }\n" + "  ],\n" + "  \"selectedSpecs\": [\"身高（cm）\", \"体重（斤）\", \"胸围（cm）\"],\n" + "  \"specOptions\": [\"身高（cm）\", \"体重（斤）\", \"胸围（cm）\"],\n" + "  \"selectedSize\": [\"XS\", \"M\"]\n" + "}";
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("template_type","size_info" );
        data.put("template_sub_type","clothing" );
        data.put("template_name","模板名称1" );
        data.put("component_front_data",front_data);
        data.put("shareable","true" );
        data.put("category_id","214691" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductCreatecomponentTemplatev2, data);
        System.out.println(resp);
    }



    @Test
    public void  DDProductEditcomponentTemplate() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("template_id","7369486293141373223" );
        data.put("template_name","服装上衣模板2" );
        data.put("component_front_data","{\\\"title\\\":\\\"服装上衣模板\\\",\\\"desc\\\":\\\"dy服装上衣模板\\\",\\\"tempName\\\":\\\"dy服装上衣模板\\\",\\\"configTable\\\":[{\\\"size\\\":\\\"XS\\\",\\\"specMap\\\":{\\\"身高（cm）\\\":\\\"180\\\",\\\"体重（斤）\\\":\\\"70\\\",\\\"胸围（cm）\\\":\\\"80\\\"}},{\\\"size\\\":\\\"M\\\",\\\"specMap\\\":{\\\"身高（cm）\\\":\\\"185\\\",\\\"体重（斤）\\\":\\\"80\\\",\\\"胸围（cm）\\\":\\\"85\\\"}}],\\\"selectedSpecs\\\":[\\\"身高（cm）\\\",\\\"体重（斤）\\\",\\\"胸围（cm）\\\"],\\\"specOptions\\\":[\\\"身高（cm）\\\",\\\"体重（斤）\\\",\\\"胸围（cm）\\\"],\\\"selectedSize\\\":[\\\"xl\\\",\\\"xxl\\\"]}" );
        data.put("shareable","true" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductEditcomponentTemplate, data);
        System.out.println(resp);
    }

    @Test
    public void  DDProductDatchdelComponenttemplate() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("template_id","7369486293141373223" );
        data.put("template_name","服装上衣模板" );
        data.put("component_front_data","{\\\"title\\\":\\\"服装上衣模板\\\",\\\"desc\\\":\\\"dy服装上衣模板\\\",\\\"tempName\\\":\\\"dy服装上衣模板\\\",\\\"configTable\\\":[{\\\"size\\\":\\\"XS\\\",\\\"specMap\\\":{\\\"身高（cm）\\\":\\\"180\\\",\\\"体重（斤）\\\":\\\"70\\\",\\\"胸围（cm）\\\":\\\"80\\\"}},{\\\"size\\\":\\\"M\\\",\\\"specMap\\\":{\\\"身高（cm）\\\":\\\"185\\\",\\\"体重（斤）\\\":\\\"80\\\",\\\"胸围（cm）\\\":\\\"85\\\"}}],\\\"selectedSpecs\\\":[\\\"身高（cm）\\\",\\\"体重（斤）\\\",\\\"胸围（cm）\\\"],\\\"specOptions\\\":[\\\"身高（cm）\\\",\\\"体重（斤）\\\",\\\"胸围（cm）\\\"],\\\"selectedSize\\\":[\\\"xl\\\",\\\"xxl\\\"]}" );
        data.put("shareable","true" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductDatchdelComponenttemplate, data);
        System.out.println(resp);
    }

    @Test
    public void  DDProductEditbuyerLimit() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("product_id","41231241241" );
        data.put("maximum_per_order","200" );
        data.put("limit_per_buyer","1" );
        data.put("minimum_per_order","2" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductEditbuyerLimit, data);
        System.out.println(resp);
    }

    @Test
    public void  DDProductDel() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("product_id","3723365663813795925" );
        //        data.put("out_product_id","4156451" );
        data.put("delete_forever","false" );
        //        data.put("store_id","1111420330" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductDel, data);
        System.out.println(resp);
    }


    // 获取级联属性
    @Test
    public void  DDProductGetcascadeValue() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("category_id","20220" );
        data.put("property_id","1381" );//241 厚度
        data.put("cascade_info","[\n" +
            "    {\n" +
            "        \"value_id\": \"23964\",\n" +
            "            \"value_name\": \"是\",\n" +
            "            \"cascade_id\": \"290\"\n" +
            "    }\n" +
            "  ]" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductGetcascadeValue, data);
        System.out.println(resp);
    }



//    查询属性项的属性值
    @Test
    public void  DDProductGetcategoryPropertyvalue() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("category_id","20212" );
        data.put("property_id","241" ); // 241 厚度
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductGetcategoryPropertyvalue, data);
        System.out.println(resp);
    }



    @Test
    public void  DDProductGetcateProperty() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("category_id","31860" );
        data.put("property_id","123" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductGetcateProperty, data);
        System.out.println(resp);
    }


    @Test
    public void  DDProductGetcatePropertyv2() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("category_leaf_id","1342353245" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductGetcatePropertyv2, data);
        System.out.println(resp);
    }

    @Test
    public void  DDProductLaunchproduct() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("product_id","3723483088060285329" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductLaunchproduct, data);
        System.out.println(resp);
    }

    @Test
    public void  DDProductSetoffline() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("product_id","3723483088060285329" );
        //        data.put("outer_product_id","2333424" );
        //        data.put("store_id","12345" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductSetoffline, data);
        System.out.println(resp);
    }

    @Test
    public void  DDProductSetonline() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("product_id","3684948274437030273" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductSetonline, data);
        System.out.println(resp);
    }

    @Test
    public void  DDSkuStocknum() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        //        data.put("sku_id","12345" );
        data.put("code","sk100" );
        //        data.put("out_warehouse_id","AN871" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSkuStocknum, data);
        System.out.println(resp);
    }

    @Test
    public void  DDSkuDetail() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        //        data.put("sku_id","1234" );
        data.put("code","sk100" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDSkuDetail, data);
        System.out.println(resp);
    }


    @Test
    public void  DDProductListv2() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        long start = System.currentTimeMillis() / 1000 - 3600 * 24 * 30 ;
        long end = System.currentTimeMillis() / 1000;
        data.put("create_time_start", String.valueOf(start));
        data.put("create_time_end", String.valueOf(end));
        data.put("status", "0");
        data.put("check_status", "3");
        data.put("product_type", "0");
        data.put("start_time", start);
        data.put("end_time", end);
        data.put("update_start_time", start);
        data.put("update_end_time", end);
        data.put("page", "1");
        data.put("size", "10");
        //        data.put("store_id", "123456");
        //        data.put("name", "标题");
        data.put("use_cursor", "false");
        //        data.put("cursor_id", "WzE2ODI1Nzc4MjksMTc2NDMxMDczMDU3MDg0M10=");
        data.put("can_combine_product", "true");
        data.put("lookup_option", "{\n" +
            "        \"need_name_affix\": \"true\",\n" +
            "                \"need_title_limit\": \"true\"\n" +
            "    }");
        data.put("product_id", "[]");
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductListv2, data);
        System.out.println(resp);
    }

    //  发布商品 20005
    @Test
    public void  DDProductAddv2() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);


        data.put("product_type", 0); // 0-普通，3-虚拟
        data.put("category_leaf_id", 20219);
        data.put("name", "测试商品请勿购买女装2024款");
        data.put("pic", "https://p3-aio.ecombdimg.com/obj/ecom-shop-material/v1_ALSUvYM_70630259408755264040798_16991a88a61d1a1f4ab8bc526fcb7762_sx_388998_www800-800|https://p3-aio.ecombdimg.com/obj/ecom-shop-material/ALSUvYM_m_1ca7af9ff5adb5687879c18484d72777_sx_190683_www800-800|https://p3-aio.ecombdimg.com/obj/ecom-shop-material/v1_ALSUvYM_70526582776577067660798_51d5ff356da0f3664980f84a9029bedb_sx_24925_www800-800");
        data.put("description", "https://p3-aio.ecombdimg.com/obj/ecom-shop-material/v1_ALSUvYM_70526582776577067660798_51d5ff356da0f3664980f84a9029bedb_sx_24925_www800-800");
        data.put("pay_type", 1); // 1在线支付
        data.put("reduce_type", 1); // 减库存类型：1-拍下减库存 2-付款减库存
        data.put("freight_id", 0); // 运费模板 通过/freightTemplate/list接口获取
        data.put("delivery_delay_day", 2); // 承诺发货时间，具体规则用/product/getProductUpdateRule获取
        data.put("presell_type", 0); // 发货模式，0-现货发货，1-预售发货，2-阶梯发货，默认0
        data.put("after_sale_service", "{\"supply_day_return_selector\":\"7-1\"}");
        data.put("mobile", "400123456"); // 抖店后台不展示，后续会考虑下线
        data.put("commit", "true"); // false仅保存草稿，true保存+提审
        data.put("product_format_new", "{\"241\":[{\"diy_type\":1,\"name\":\"常规款\",\"value\":17314}],\"631\":[{\"diy_type\":1,\"name\":\"东西南北风\",\"value\":0}],\"785\":[{\"measure_info\":{\"values\":[{\"module_id\":582,\"value\":\"亚麻\",\"unit_id\":0},{\"module_id\":583,\"unit_name\":\"%\",\"unit_id\":15,\"value\":\"100\"}],\"template_id\":252,\"value_name\":\"亚麻100%\"},\"diy_type\":1,\"name\":\"亚麻100%\",\"value\":0}],\"813\":[{\"diy_type\":1,\"name\":\"X型\",\"value\":8017}],\"981\":[{\"diy_type\":1,\"name\":\"蛋糕裙\",\"value\":22253}],\"1467\":[{\"diy_type\":0,\"name\":\"59%-69%(含)\",\"value\":168368}],\"1551\":[{\"diy_type\":1,\"name\":\"超短裙\",\"value\":22064}],\"1687\":[{\"diy_type\":0,\"name\":\"\",\"value\":0}],\"1878\":[{\"diy_type\":1,\"name\":\"高腰\",\"value\":16521}],\"2592\":[{\"diy_type\":1,\"name\":\"甜美风\",\"value\":192481}]}");
        data.put("standard_brand_id", 596120136); // 品牌
         data.put("presell_config_level", 2);
        data.put("size_info_template_id", 7369486293141373223L); // 尺码模板id https://op.jinritemai.com/docs/guide-docs/242/2094
        data.put("spec_info", "{\"spec_values\":[{\"property_name\":\"颜色分类\",\"property_id\":2752,\"values\":[{\"value_name\":\"白色\",\"value_id\":35497}]},{\"property_name\":\"尺码大小\",\"property_id\":4704,\"values\":[{\"value_name\":\"M\",\"value_id\":0}]}]}}");
        data.put("spec_prices_v2", "[{\"sell_properties\":[{\"value_name\":\"白色\",\"property_name\":\"颜色分类\"},{\"value_name\":\"M\",\"property_name\":\"尺码大小\"}],\"stock_num\":11,\"price\":1,\"code\":\"sk100\"}]");


        //
        //
        //        data.put("outer_product_id", "232334");
        //        data.put("product_type", "0");
        //        data.put("category_leaf_id", "20005");
        //        data.put("name", "测试产品发布");
        //        data.put("recommend_remark", "这个商品很好啊");
        //        //图片链接
        //        data.put("pic", "https://p3-aio.ecombdimg.com/obj/ecom-shop-material/v1_ALSUvYM_70630259408755264040798_16991a88a61d1a1f4ab8bc526fcb7762_sx_388998_www800-800|https://p3-aio.ecombdimg.com/obj/ecom-shop-material/ALSUvYM_m_1ca7af9ff5adb5687879c18484d72777_sx_190683_www800-800|https://p3-aio.ecombdimg.com/obj/ecom-shop-material/v1_ALSUvYM_70526582776577067660798_51d5ff356da0f3664980f84a9029bedb_sx_24925_www800-800");
        //        data.put("description", "https://p3-aio.ecombdimg.com/obj/ecom-shop-material/v1_ALSUvYM_70526582776577067660798_51d5ff356da0f3664980f84a9029bedb_sx_24925_www800-800");
        //        data.put("pay_type", "1");
        //        data.put("delivery_method", "7");
        //        data.put("cdf_category", "1");
        //        data.put("reduce_type", "1");
        //        data.put("assoc_ids", "1|2|3");
        //        data.put("freight_id", "123");
        //        data.put("weight", "1000");
        //        data.put("weight_unit", "1");
        //        data.put("delivery_delay_day", "7");
        //        data.put("presell_type", "0");
        //        data.put("presell_delay", "7");
        //        data.put("presell_end_time", "2020-02-21 18:54:27");
        ////        data.put("supply_7day_return", "该字段将在2023年4月30日下线，请开发使用最新的after_sale_service字段传值；");
        //        data.put("mobile", "40012345");
        //        data.put("commit", "true");
        //        data.put("remark", "备注");
        //        data.put("out_product_id", "123");
        //        List<Map<String, Object>> quality_list = new ArrayList<>();
        //        Map<String, Object> quality_item = new HashMap<>();
        //        quality_item.put("quality_key", "3457***9470978");
        //        quality_item.put("quality_name", "进货凭证");
        //        List<Map<String, Object>> quality_attachments = new ArrayList<>();
        //        Map<String, Object> quality_attachment_item = new HashMap<>();
        //        quality_attachment_item.put("media_type", "1");
        //        quality_attachment_item.put("url", "\"http://www.byted***.com/YYYY\"");
        //        quality_attachments.add(quality_attachment_item);
        //        quality_item.put("quality_attachments", quality_attachments);
        //        quality_item.put("quality_id", "1234");
        //        quality_item.put("quality_content_name", "版权页资质");
        //        quality_list.add(quality_item);
        //        data.put("quality_list", JSON.toJSONString(quality_list));
        //        data.put("spec_name", "颜色-尺码");
        //        data.put("specs", "颜色|红色,黑色^尺码|S,M");
        //        data.put("spec_prices", "[{\"spec_detail_name1\":\"红色\",\"spec_detail_name2\":\"S\",\"spec_detail_name3\":\"\",\"stock_num\":11,\"price\":100,\"code\":\"\",\"step_stock_num\":0,\"supplier_id\":\"\",\"outer_sku_id\":\"\",\"delivery_infos\":[{\"info_type\":\"weight\",\"info_value\":\"100\",\"info_unit\":\"mg\"}]},{\"spec_detail_name1\":\"红色\",\"spec_detail_name2\":\"M\",\"spec_detail_name3\":\"\",\"stock_num\":22,\"price\":100,\"code\":\"\",\"step_stock_num\":0,\"supplier_id\":\"\",\"outer_sku_id\":\"\",\"delivery_infos\":[{\"info_type\":\"weight\",\"info_value\":\"100\",\"info_unit\":\"mg\"}]},{\"spec_detail_name1\":\"黑色\",\"spec_detail_name2\":\"S\",\"spec_detail_name3\":\"\",\"stock_num\":44,\"price\":100,\"code\":\"\",\"step_stock_num\":0,\"supplier_id\":\"\",\"outer_sku_id\":\"\",\"delivery_infos\":[{\"info_type\":\"weight\",\"info_value\":\"100\",\"info_unit\":\"mg\"}]},{\"spec_detail_name1\":\"黑色\",\"spec_detail_name2\":\"M\",\"spec_detail_name3\":\"\",\"stock_num\":55,\"price\":100,\"code\":\"\",\"step_stock_num\":0,\"supplier_id\":\"\",\"outer_sku_id\":\"\",\"delivery_infos\":[{\"info_type\":\"weight\",\"info_value\":\"100\",\"info_unit\":\"mg\"}]}]");
        //        data.put("spec_pic", "img_url,img_url,img_url");
        //        data.put("maximum_per_order", "200");
        //        data.put("limit_per_buyer", "1");
        //        data.put("minimum_per_order", "2");
        //        data.put("product_format_new", "{\"405\":[{\"value\":27664,\"name\":\"复习资料\",\"diy_type\":0}],\"449\":[{\"value\":0,\"name\":\"佚名\",\"diy_type\":0}],\"501\":[{\"value\":7310,\"name\":\"否\",\"diy_type\":0}],\"855\":[{\"value\":61683,\"name\":\"北京出版社\",\"diy_type\":0}],\"1088\":[{\"value\":407,\"name\":\"小学五年级\",\"diy_type\":0}],\"1319\":[{\"value\":0,\"name\":\"1\",\"diy_type\":0}],\"1601\":[{\"value\":13911,\"name\":\"通用版\",\"diy_type\":0}],\"1618\":[{\"value\":0,\"name\":\"9787218122861\",\"diy_type\":0}],\"1831\":[{\"value\":0,\"name\":\"小学英语看图说话写话二年级\",\"diy_type\":0}],\"2000\":[{\"value\":34762,\"name\":\"无\",\"diy_type\":0}],\"2229\":[{\"value\":0,\"name\":\"1\",\"diy_type\":0}],\"2763\":[{\"value\":25193,\"name\":\"英语\",\"diy_type\":0}],\"3271\":[{\"value\":0,\"name\":\"1\",\"diy_type\":0}],\"3296\":[{\"value\":0,\"name\":\"16.80元\",\"diy_type\":0}]}");
        //        data.put("spu_id", "14567");
        //        data.put("appoint_delivery_day", "2");
        //        data.put("third_url", "http://img.alicdn.com/xxxx");
        //        data.put("extra", "略");
        //        data.put("src", "略");
        //        data.put("standard_brand_id", "111");
        //        data.put("need_check_out", "true");
        //        Map<String, Object> poi_resource = new HashMap<>();
        //        poi_resource.put("valid_days", "5");
        //        poi_resource.put("valid_start", "1636616483");
        //        poi_resource.put("valid_end", "1636616483");
        //        poi_resource.put("service_num", "13111111111");
        //        poi_resource.put("notification", "领取须知");
        //        poi_resource.put("code_type", "1");
        //        poi_resource.put("count", "1");
        //        poi_resource.put("couponSecondExchange", "0");
        //        poi_resource.put("total_can_use_count", "1");
        //        poi_resource.put("link", "https://xxx.xxx.xxx");
        //        poi_resource.put("condition", "券码使用条件");
        //        List<Object> coupon_return_methods = new ArrayList<>();
        //        coupon_return_methods.add(1);
        //        poi_resource.put("coupon_return_methods", JSON.toJSONString(coupon_return_methods));
        //        data.put("poi_resource", JSON.toJSONString(poi_resource));
        //        data.put("car_vin_code", "BA111111111111111");
        //        data.put("presell_config_level", "3");
        //        data.put("need_recharge_mode", "true");
        //        data.put("account_template_id", "122112");
        //        data.put("presell_delivery_type", "1");
        //        data.put("white_back_ground_pic_url", "https://p3-aio.ecombdimg.com/obj/ecom-shop-material/ZfkBZpcR_m_7b0c5176542b6228bd6cb9b26b0b6e02_sx_10411_www339-350");
        //        data.put("long_pic_url", "https://p3-aio.ecombdimg.com/obj/ecom-shop-material/ZfkBZpcR_m_7b0c5176542b6228bd6cb9b26b0b6e02_sx_10411_www339-350");
        //        //TODO 0514
        ////        data.put("after_sale_service", "'damaged_order_return': '1'");
        //        List<Object> sell_channel = new ArrayList<>();
        ////        sell_channel.add(null);
        //        data.put("sell_channel", JSON.toJSONString(sell_channel));
        //        data.put("start_sale_type", "0");
        //        Map<String, Object> delay_rule = new HashMap<>();
        //        delay_rule.put("enable", "true");
        //        delay_rule.put("config_type", "1");
        //        delay_rule.put("config_value", "1673539199");
        //        delay_rule.put("start_time", "1643040000");
        //        delay_rule.put("end_time", "1672502400");
        //        data.put("delay_rule", JSON.toJSONString(delay_rule));
        //        data.put("material_video_id", "vaaaa");
        //        data.put("pickup_method", "0");
        //        data.put("size_info_template_id", "101");
        //        data.put("substitute_goods_url", "https://p3-aio.ecombdimg.com/obj/ecom-shop-material/ZfkBZpcR_m_7b0c5176542b6228bd6cb9b26b0b6e02_sx_10411_www339-350");
        //        data.put("sale_channel_type", "sameAsOffline");
        //        Map<String, Object> recruit_info = new HashMap<>();
        //        recruit_info.put("recruit_follow_id", "122332");
        //        recruit_info.put("recruit_type", "0");
        //        data.put("recruit_info", JSON.toJSONString(recruit_info));
        //        data.put("store_id", "12345");
        //        data.put("main_product_id", "3121213121212");
        //        data.put("sale_limit_id", "123");
        //        data.put("name_prefix", "钛钢木质耳饰");
        //        data.put("reference_price", "12300");
        //        Map<String, Object> reference_price_certificate = new HashMap<>();
        //        reference_price_certificate.put("certificate_type", "1");
        //        List<Object> certificate_urls = new ArrayList<>();
        //        certificate_urls.add("http://www.aaa.com");
        //        reference_price_certificate.put("certificate_urls", JSON.toJSONString(certificate_urls));
        //        data.put("reference_price_certificate", JSON.toJSONString(reference_price_certificate));
        //        data.put("main_image_three_to_four", "img_url1|img_url2|img_url3");
        //        Map<String, Object> unit_price_info = new HashMap<>();
        //        unit_price_info.put("process_charge", "0");
        //        unit_price_info.put("price_rule_type", "1");
        //        data.put("unit_price_info", JSON.toJSONString(unit_price_info));
        //        Map<String, Object> quality_inspection_info = new HashMap<>();
        //        quality_inspection_info.put("supported", "true");
        //        quality_inspection_info.put("agency", "111");
        //        quality_inspection_info.put("certificate_code", "123");
        //        quality_inspection_info.put("mode", "2");
        //        data.put("quality_inspection_info", JSON.toJSONString(quality_inspection_info));
        //        data.put("is_c2b_switch_on", "true");
        //        data.put("micro_app_id", "abcde");
        //        data.put("is_auto_charge", "false");
        //        data.put("short_product_name", "新品牛肉干");
        //        Map<String, Object> after_sale_service_v2 = new HashMap<>();
        //        Map<String, Object> three_guarantees = new HashMap<>();
        //        three_guarantees.put("duration", "180");
        //        three_guarantees.put("service_type", "1");
        //        after_sale_service_v2.put("three_guarantees", JSON.toJSONString(three_guarantees));
        //        //1-是 0-不是
        //        after_sale_service_v2.put("is_large_product", "0");
        //        data.put("after_sale_service_v2", "{\"supply_day_return_selector\":\"7-1\"}");
        //        data.put("spec_info", "{\n" +
        //                "    \"spec_values\": [\n" +
        //                "      {\n" +
        //                "        \"property_name\": \"颜色\",\n" +
        //                "        \"values\": [\n" +
        //                "          {\n" +
        //                "            \"remark\": \"偏深\",\n" +
        //                "            \"value_name\": \"白色\",\n" +
        //                "            \"cpv_path\": [\n" +
        //                "              {\n" +
        //                "                \"cpid\": \"1234\",\n" +
        //                "                \"cpvid\": \"234\"\n" +
        //                "              }\n" +
        //                "            ],\n" +
        //                "            \"measure_info\": {\n" +
        //                "              \"template_id\": \"12345\",\n" +
        //                "              \"values\": [\n" +
        //                "                {\n" +
        //                "                  \"module_id\": \"354\",\n" +
        //                "                  \"value\": \"10\",\n" +
        //                "                  \"unit_id\": \"2\",\n" +
        //                "                  \"unit_name\": \"g\",\n" +
        //                "                  \"prefix\": \"\\\"\\\"\",\n" +
        //                "                  \"suffix\": \"\\\"-\\\"\"\n" +
        //                "                }\n" +
        //                "              ]\n" +
        //                "            },\n" +
        //                "            \"value_id\": \"2341\"\n" +
        //                "          }\n" +
        //                "        ],\n" +
        //                "        \"property_id\": \"1234\"\n" +
        //                "      }\n" +
        //                "    ]\n" +
        //                "  }");
        //
        //        data.put("spec_prices_v2", "[\n" +
        //                "    {\n" +
        //                "      \"sku_status\": \"true\",\n" +
        //                "      \"sku_type\": \"0\",\n" +
        //                "      \"stock_num\": \"10\",\n" +
        //                "      \"price\": \"100\",\n" +
        //                "      \"code\": \"xxx\",\n" +
        //                "      \"step_stock_num\": \"12\",\n" +
        //                "      \"supplier_id\": \"adb\",\n" +
        //                "      \"out_sku_id\": \"123\",\n" +
        //                "      \"outer_sku_id\": \"daf\",\n" +
        //                "      \"customs_report_info\": {\n" +
        //                "        \"hs_code\": \"adaa\",\n" +
        //                "        \"first_measure_qty\": \"12.1\",\n" +
        //                "        \"second_measure_qty\": \"12.3\",\n" +
        //                "        \"first_measure_unit\": \"ml\",\n" +
        //                "        \"second_measure_unit\": \"ml\",\n" +
        //                "        \"unit\": \"ml\",\n" +
        //                "        \"report_name\": \"名称\",\n" +
        //                "        \"report_brand_name\": \"品牌名\",\n" +
        //                "        \"usage\": \"用途\",\n" +
        //                "        \"g_model\": \"x\",\n" +
        //                "        \"bar_code\": \"xxx\"\n" +
        //                "      },\n" +
        ////                "      \"stock_num_map\": \"\\\"FOURPLTEST\\\": 10\",\n" +
        //                "      \"package_sku\": [\n" +
        //                "        {\n" +
        //                "          \"sub_product_id\": \"11\",\n" +
        //                "          \"sub_sku_id\": \"12\",\n" +
        //                "          \"combo_num\": \"2\",\n" +
        //                "          \"short_name\": \"xx套装\"\n" +
        //                "        }\n" +
        //                "      ],\n" +
        //                "      \"tax_exemption_sku_info\": {\n" +
        //                "        \"is_suit\": \"1\",\n" +
        //                "        \"suit_num\": \"2\",\n" +
        //                "        \"volume\": \"3\"\n" +
        //                "      },\n" +
        //                "      \"spec_value_lib_id\": \"0\",\n" +
        //                "      \"presell_delay\": \"5\",\n" +
        ////                "      \"barcodes\": [\n" +
        ////                "        null\n" +
        ////                "      ],\n" +
        //                "      \"cargo\": {\n" +
        //                "        \"source_type\": \"1\",\n" +
        //                "        \"cargo_id\": \"1123\"\n" +
        //                "      },\n" +
        //                "      \"delivery_infos\": [\n" +
        //                "        {\n" +
        //                "          \"info_type\": \"1\",\n" +
        //                "          \"info_value\": \"10\",\n" +
        //                "          \"info_unit\": \"g\"\n" +
        //                "        }\n" +
        //                "      ],\n" +
        //                "      \"supply_price\": \"13\",\n" +
        //                "      \"sell_properties\": [\n" +
        //                "        {\n" +
        //                "          \"property_name\": \"颜色\",\n" +
        //                "          \"value_name\": \"白色\"\n" +
        //                "        }\n" +
        //                "      ],\n" +
        //                "      \"gold_process_charge\": \"10\",\n" +
        //                "      \"sku_classification_type\": \"main_sale_single_product\"\n" +
        //                "    }\n" +
        //                "  ]");
        //
        //        data.put("with_sku_type", "true");
        //        data.put("name_suffix", "36.9度");
        //        data.put("use_brand_name", "false");
        //        Map<String, Object> open_logistics_info = new HashMap<>();
        //        open_logistics_info.put("customs_clear_type", "1");
        //        open_logistics_info.put("origin_country_id", "6251999");
        //        open_logistics_info.put("source_country_id", "2963597");
        //        open_logistics_info.put("brand_country_id", "6251999");
        //        open_logistics_info.put("tax_payer", "0");
        //        open_logistics_info.put("net_weight_qty", "1");
        //        open_logistics_info.put("cross_warehouse_id", "FOURPLTEST");
        //        data.put("open_logistics_info", JSON.toJSONString(open_logistics_info));
        //        data.put("price_has_tax", "1");
        //        data.put("biz_kind", "2");


        data.put("sign", Sign(data,Config.AppSecret));
        System.out.println(JSON.toJSONString(data));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductAddv2, data);
        System.out.println(resp);
    }

//    @Test
//    public void  DDProductEditV2() throws Exception {
//        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("appid",  Config.AppId);
//        Long timestamp = System.currentTimeMillis() / 1000;
//        data.put("timestamp", timestamp.toString());
//        data.put("seller_nick", Config.DDSellerNick);
//        data.put("product_id", "3668963015870513459");
//        data.put("name", "测试商品－请勿购买！！USB打印机商务办公小票机-test");
//        //false仅保存，true保存+提审
//        data.put("commit",false);
//
//        data.put("sign", Sign(data,Config.AppSecret));
//        // 调用服务API
//        String resp = doHttpRequest(Config.DDProductEditv2, data);
//        System.out.println(resp);
//    }

    // 根据素材ID查询素材详情
    //{"code":0,"message":"ok","data":{"material_info":{"audit_reject_desc":"","audit_status":3,"byte_url":"https://p3-aio.ecombdimg.com/obj/ecom-shop-material/ZfkBZpcR_m_7b0c5176542b6228bd6cb9b26b0b6e02_sx_10411_www339-350","create_time":"2024-05-17 09:55:41","delete_time":"","folder_id":"73691281073984637961425","material_id":"73697813761677233040425","material_type":"photo","materil_name":"test1.jpg","operate_status":1,"origin_url":"https://www.cheeli.com.cn/wp-content/themes/stratusx-child/img/product/productA1.jpg","photo_info":{"format":"jpeg","height":350,"width":339},"size":11264,"update_time":"2024-05-17 09:55:42"}}}
    @Test
    public void  DDMaterialQueryMaterialDetail() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("material_id","74484933450835233540425" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDMaterialQueryMaterialDetail, data);
        System.out.println(resp);
    }

    @Test
    public void  DDMaterialSearchMaterial() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        //        data.put("material_id", "7000291764753940780");
        //        data.put("material_name", "模糊匹配");
        //素材类型，空-不限； photo-图片；video-视频；
        data.put("material_type", "photo");
        //        data.put("operate_status", "[0,1,4]");
        //        data.put("audit_status", "[1,2,3,4]");
        data.put("create_time_start", "2024-05-16 12:00:12");
        data.put("create_time_end", "2024-05-18 12:00:12");
        //        data.put("folder_id", "6999834643465781548");
        //        data.put("material_id_list", "[7000291764753940780,7000291764753940780]");
        data.put("page_num", 1);
        data.put("page_size", 20);
        data.put("order_type", 1);
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDMaterialSearchMaterial, data);
        System.out.println(resp);
    }
    //  上传单个图片 https://www.cheeli.com.cn/wp-content/themes/stratusx-child/img/product/productA1.jpg
    //
    //{"code":0,"message":"ok","data":{"audit_status":1,"byte_url":"","folder_id":"73691281073984637961425","is_new":true,"material_id":"73697813761677233040425"}}
    @Test
    public void  DDMaterialUploadimageSync() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("folder_id","73691281073984637961425" );
        data.put("url","https://www.cheeli.com.cn/wp-content/themes/stratusx-child/img/product/productA1.jpg" );

        data.put("material_name","test3.jpg" );
        data.put("need_distinct","false" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
//        String filePath = "/Users/miller/working/demodata/item_lb_img.png";
        String filePath = "";
        String resp = doHttpRequest(Config.DDMaterialUploadimageSync, data,filePath);
        System.out.println(resp);
    }



    //批量上传图片到素材中心
    //    1、一次上传的数量限制50张图片且单个图片最大不能超过10M；
    //    2、接口上传成功，素材中心会对素材进行异步审核，审核时效：非风险图片99%在4s内完成审核，风险图片我们会在24h内审核完成；对于审核失败的素材，素材中心会在一定时间内将其删除；
    //    3、上传时需要关注文件夹属性为0-文件夹和1-图片文件夹才可以上传图片，文件夹属性可通过【/material/getFolderInfo】和【/material/searchFolder】查看。
    //{"code":0,"message":"ok","data":{"failed_map":{},"success_map":{"1234567890":{"AuditStatus":1,"ByteUrl":"","FolderId":"73691281073984637961425","IsNew":true,"MaterialId":"73698405803633216340425","Name":"UX设计","OriginUrl":"https://www.cheeli.com.cn/wp-content/themes/stratusx-child/img/product/productA2.jpg"}}}}
    @Test
    public void  DDMaterialBatchuploadImagesync() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);

        // 通过公网已经存在的图片url上传
//        data.put("materials","[\n" +
//            "    {\n" +
//            "      \"request_id\": \"1234567890\",\n" +
//            "      \"folder_id\": \"73691281073984637961425\",\n" +
//            "      \"name\": \"UX设计\",\n" +
//            "      \"url\": \"https://www.cheeli.com.cn/wp-content/themes/stratusx-child/img/product/productA2.jpg\",\n" +
//            //                "      \"file_uri\": \"tos-cn-i-7veqoeduo3/9e1df78157524c63abf7caa9bb1e88e0\",\n" +
//            "      \"material_type\": \"photo\"\n" +
//            "    }\n" +
//            "  ]" );

       // 通过本地文件上传
        data.put("materials","[\n" +
            "    {\n" +
            "      \"request_id\": \"1234567890\",\n" +
            "      \"folder_id\": \"73691281073984637961425\",\n" +
            "      \"name\": \"女上衣\",\n" +
          //  "      \"url\": \"https://www.cheeli.com.cn/wp-content/themes/stratusx-child/img/product/productA2.jpg\",\n" +
                         "      \"file_uri\": \"tos-cn-i-7veqoeduo3/8b570a86b96342b4978e01b4892aef8b\",\n" +
            "      \"material_type\": \"photo\"\n" +
            "    }\n" +
            "  ]" );



        data.put("need_distinct","false" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDMaterialBatchuploadImagesync, data);
        System.out.println(resp);
    }

    //素材中心--异步上传视频接口
    //1. 视频大小限200M;
    //2. 接口同步接受上传视频的请求，异步处理：视频的下载、格式校验、审核等过程；
    //3. 视频的审核时间根据素材中心素材数量而定，大部分视频在1分钟内能审核完，小部分视频审核时间会达到天级别，请耐心等待；
    //4、上传时需要关注文件夹属性，只有0（文件夹）和2（视频文件夹）才可以上传视频。文件夹属性可通过【/material/getFolderInfo】和【/material/searchFolder】查看。
    //5. 审核拒绝的视频将在15日内被定期清理，请慎用。
    //其他问题请参考：https://op.jinritemai.com/docs/guide-docs/171/1719
    @Test
    public void  DDMaterialUploadvideoAsync() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("folder_id","73691281073984637961425" );
        data.put("url","https://www.cheeli.com.cn/wp-content/themes/stratusx-child/img/product/productA1.jpg" );
        data.put("name","test1.jpg" );
        data.put("need_distinct","false" );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDMaterialUploadvideoAsync, data);
        System.out.println(resp);
    }

    //    @Test
    //    public void  DDMaterialUpload() throws Exception {
    //        // 1. init global config
    //        GlobalConfig.initAppKey("7054833365769291295");
    //        GlobalConfig.initAppSecret("8f81bc78-da0d-43f1-a090-86bba8f19d4c");
    //        //自用型应用填写shopid
    //        AccessToken accessToken = AccessTokenBuilder.build(4463798L);//4463798是官方测试店铺
    //        //工具型型应用填写code
    //        //AccessToken accessToken = AccessTokenBuilder.build("工具型型应用填写code");
    //
    //        // 2. read file data
    //        Path path = Paths.get("/Users/jinshaoling/Downloads/productD2.jpeg");//请填写您的图片路径
    //        byte[] bytes = Files.readAllBytes(path);
    //        BinaryMaterialUploadParam param = new BinaryMaterialUploadParam();
    //        param.setExtension(".jpeg"); // 请和path中的图片后缀类型保持一致
    //        param.setBinaryBytes(bytes);
    //        // 3. upload material
    //        BinaryMaterialUploadExecutor executor = BinaryMaterialUpload.getDefaultUpload();
    ////        System.out.println ("BinaryMaterialUpload Url:"+((BinaryMaterialUpload) executor).getConfig().getOpenRequestUrl());
    //        BinaryMaterialUploadResponseWrapper result = executor.execute(param, accessToken);
    //        System.out.println("本次请求响应信息："+result);
    //        //响应示例：{"data":{"uri":"tos-cn-i-7veqoeduo3/1e54554c856e4dc99911c9a0f4decac0.jpeg"},"log_id":"202209152116040102090820500E9557F0","code":"10000"}
    //        String fileUri = result.getData().getUri();
    //        System.out.println("获取fileUri:"+fileUri);
    //        //响应示例：tos-cn-i-7veqoeduo3/1e54554c856e4dc99911c9a0f4decac0.jpeg
    //    }



    @Test
    public void  DDMaterialUpload() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("file_extension",".jpg" );
        //upload_num <= 5
        data.put("upload_num",1);
        String base64ImageLogo = Utils.getBase64ImageFromBinary("/Users/jinshaoling/Downloads/productD2.jpg");
        List<String> list = new ArrayList();
        list.add(base64ImageLogo);
        data.put("b64_str", JSON.toJSONString(list));
        //biz_type=1 素材中心
        data.put("biz_type",1 );
        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDMaterialUpload, data);
        System.out.println(resp);
    }

//    通过前缀匹配召回品牌信息
    @Test
    public void  DDBrandGetSug() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);
        data.put("sku_id", 3398076948315650L);

        data.put("query", "apple");
        data.put("user_id", "0");
        data.put("read_old", "false");
        data.put("biz_types", "[0]");
        data.put("enable_deduplicate", "true");

//        data.put("filter_info", "{\n" +
//            "    \"brand_ids\": [\n" +
//            "      202132132342,\n" +
//            "      213123443543\n" +
//            "    ],\n" +
//            "    \"brand_category\": [\n" +
//            "      20220\n" +
//            "    ],\n" +
//            "    \"status\": \"1\",\n" +
//            "    \"related_ids\": [\n" +
//            "      12123123123,\n" +
//            "      4323423423\n" +
//            "    ],\n" +
//            "    \"trade_mark_ids\": [\n" +
//            "      \"21312323134\"\n" +
//            "    ],\n" +
//            "    \"audit_status\": [\n" +
//            "      2\n" +
//            "    ]\n" +
//            "  }");

//        data.put("extra_config", "{\n" +
//            "    \"use_origin_brand_info\": \"false\",\n" +
//            "    \"use_brand_info\": \"false\",\n" +
//            "    \"use_brand_name_deduplicate\": \"true\"\n" +
//            "  }");

        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDBrandGetSug, data);
        System.out.println(resp);
    }


    @Test
    public void  DDProductGetProductUpdateRule() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);

        data.put("category_id", 20219);
        data.put("senses", 1001);
        data.put("standard_brand_id", 20319);
        data.put("spu_id", 23291);

        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductGetProductUpdateRule, data);
        System.out.println(resp);
    }

    @Test
    public void  DDProductQualificationConfig() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);

        data.put("category_id", 20219);

        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductQualificationConfig, data);
        System.out.println(resp);
    }


    // 获取运费模板
    @Test
    public void  GetDDFreightTemplateList() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);

//        data.put("name", "模版1");
        data.put("page", "0");
        data.put("size", "10");

        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDFreightTemplateList, data);
        System.out.println(resp);
    }

    //
    @Test
    public void  DDReightTemplateCreate() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);

        data.put("template", "{\n" +
            "    \"template_name\": \"测试运费模板\",\n" +
            "    \"product_province\": \"32\",\n" +
            "    \"product_city\": \"320100\",\n" +
            "    \"calculate_type\": \"1\",\n" +
            "    \"transfer_type\": \"1\",\n" +
            "    \"rule_type\": \"0\",\n" +
            "    \"fixed_amount\": \"1\"\n" +
            "  }");
        data.put("columns", "[\n" +
            "    {\n" +
            "      \"first_weight\": \"0.1\",\n" +
            "      \"first_weight_price\": \"0.01\",\n" +
            "      \"first_num\": \"1\",\n" +
            "      \"first_num_price\": \"0.01\",\n" +
            "      \"add_weight\": \"0.1\",\n" +
            "      \"add_weight_price\": \"0.01\",\n" +
            "      \"add_num\": \"1\",\n" +
            "      \"add_num_price\": \"0.01\",\n" +
            "      \"is_default\": \"1\",\n" +
            "      \"is_limited\": \"false\",\n" +
            "      \"rule_address\": \"{\\\"11\\\":{\\\"110000\\\":{\\\"110114\\\":[110114116,110114007]}}}\",\n" +
            "      \"is_over_free\": \"false\",\n" +
            "      \"over_weight\": \"0.1\",\n" +
            "      \"over_amount\": \"10\",\n" +
            "      \"over_num\": \"1\",\n" +
            "      \"min_sku_amount\": \"100\",\n" +
            "      \"max_sku_amount\": \"500\",\n" +
            "      \"province_infos\": [\n" +
            "        {\n" +
            "          \"id\": \"22\",\n" +
            "          \"children\": [\n" +
            "            {\n" +
            "              \"id\": \"2222\",\n" +
            "              \"children\": [\n" +
            "                {\n" +
            "                  \"id\": \"22222\",\n" +
            "                  \"children\": [\n" +
            "                    {\n" +
            "                      \"id\": \"222222\"\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              ]\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]");

        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDReightTemplateCreate, data);
        System.out.println(resp);
    }



    @Test
    public void  DDProductGetRecommendName() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);

        data.put("scene", Arrays.asList("product_name_prefix"));
        data.put("category_leaf_id", 26150);
        data.put("first_cid", 20080);
        data.put("first_cid_name", "时尚饰品");
        data.put("second_cid", 20998);
        data.put("second_cid_name", "耳饰");
        data.put("third_cid", 26150);
        data.put("third_cid_name", "耳钉");
        data.put("select_property", "{\"1687\":[{\"value\":0,\"name\":\"填入品牌名\"}],\"3320\":[{\"value\":18972,\"name\":\"99新\"}]}");
        data.put("use_brand_name", false);


        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductGetRecommendName, data);
        System.out.println(resp);
    }

    @Test
    public void  DDProductGetComponentTemplate() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);

        data.put("template_type", "size_info");
        data.put("template_sub_type", "clothing");
//        data.put("template_id", 7369486293141373223L);
        data.put("shareable", true);
        data.put("page_num", 0);
        data.put("page_size", 20);


        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductGetComponentTemplate, data);
        System.out.println(resp);
    }


    @Test
    public void  DDProductGetRecommendCategory() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("seller_nick", Config.DDSellerNick);

        data.put("scene", "product_info");
        data.put("category_leaf_id", "20415");
        data.put("name", "男士上衣冬天加绒加厚保暖外套");
//        data.put("standard_brand_id", "30241");
        data.put("pic", "[\n" +
            "    {\n" +
            "      \"url\": \"https://p3-aio.ecombdimg.com/obj/ecom-shop-material/v1_GhxlaZ_70852585116381186630419_c55b8401b00e96e4114431a1dbd7c99c_sx_582346_www1000-1000\"" +
            "    }\n" +
            "  ]");


        data.put("sign", Sign(data,Config.AppSecret));
        // 调用服务API
        String resp = doHttpRequest(Config.DDProductGetRecommendCategory, data);
        System.out.println(resp);
    }




    private  String doHttpRequest(String url ,  Map<String, Object> data) {
        return doHttpRequest(url, data, "");
    }

     private  String doHttpRequest(String url ,  Map<String, Object> data, String filePath){

        String result ="";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        System.out.println("请求Url:" +url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            builder.addTextBody(entry.getKey(), entry.getValue().toString(),ContentType.create("text/plain", "UTF-8")); // 添加其他字段
        }
        // 如果有文件上传
        if (!"".equals(filePath)) {
            builder.addBinaryBody("file", new File(filePath), ContentType.DEFAULT_BINARY, new File(filePath).getName()); // 添加文件
        }
        // 设置请求体
        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);
        //发起POST请求
        try {
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
