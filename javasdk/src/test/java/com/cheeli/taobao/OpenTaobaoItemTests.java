package com.cheeli.taobao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cheeli.Config;
import com.cheeli.models.LogisticesBatchSendItem;
import com.cheeli.utils.Utils;
import org.apache.commons.lang.StringEscapeUtils;
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
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 场景：合作伙伴使用，可管理其他授权的店铺。即可以管理多家店铺
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenTaobaoItemTests.class )
public class OpenTaobaoItemTests {

    @Test
    public void contextLoads() {
    }


    /**
     * 获取后台供卖家发布商品的标准商品类目
     * @throws Exception
     */
    @Test
    public void  getItemCats() throws Exception {
        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        // 如果
        data.put("datetime", "2020-01-01 22:00:00" );

        // 如果使用默认值，则不要传入参数cids
//        data.put("cids", "");
        // 如果使用默认值，则不要传入参数fields
//        data.put("fields", "cid,parent_cid,name,is_parent");
        // 如果使用默认值， 则不要传入参数parent_cid
//        data.put("parent_cid", "50011740");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoItemCatGet ,data);

    }

    /**
     *  获取标准商品类目属性
     * @throws Exception
     */
    @Test
    public void  getItemProps() throws Exception {


        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
         //  叶子类目ID，必填，如果只传cid，则只返回一级属性,通过"获取卖家发布商品标准类目"接口获得叶子类目ID
        data.put("cid", "50012907");
        // 如果使用默认值，则不要传入参数fields
        data.put("fields", "pid, name, must, multi, prop_values");
        // 如果使用默认值， 则不要传入参数parent_cid
//        data.put("parent_cid", "");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoItemPropsGet ,data);

    }

    /**
     * 获取标准类目属性值
     * @throws Exception
     */
    @Test
    public void  getItemPropsValues() throws Exception {


        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("datetime", "2020-01-01 22:00:00" );

        //  叶子类目ID，必填， 如果只传cid，则只返回一级属性,通过taobao.itemcats.get获得叶子类目ID
        data.put("cid", "50012907");

        // 需要返回的字段
        data.put("fields", "cid,pid,prop_name,vid,name,name_alias,status,sort_order");

        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoItemPropsValuesGet ,data);

    }

    @Test
    public void  taoBaoTmallItemCatsAuthorizeGet() throws Exception {


        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());


        // 需要返回的字段
        data.put("fields", "brand.vid, brand.name,\n" +
                "item_cat.cid, item_cat.name, item_cat.status,item_cat.sort_order,item_cat.parent_cid,item_cat.is_parent,\n" +
                "xinpin_item_cat.cid,\n" +
                "xinpin_item_cat.name,\n" +
                "xinpin_item_cat.status,\n" +
                "xinpin_item_cat.sort_order,\n" +
                "xinpin_item_cat.parent_cid,\n" +
                "xinpin_item_cat.is_parent");

        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoTmallItemCatsAuthorizeGetUrl ,data);

    }





    /**
     * 发布一个淘宝商品
     * @throws Exception
     */
    @Test
    public void  addItem() throws Exception {


        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        //    必填
        data.put("location.state", "浙江");
//        // 所在地城市。如杭州 。
//        data.put("location.city", "杭州");
//        data.put("num", "40");
//        data.put("type", "fixed");
//        data.put("stuff_status", "new");
//        data.put("title", "真皮男士增高鞋男高帮工装鞋-api");
//        data.put("desc", "这是一个好商品,但是不要拍");
//        data.put("cid", "50012907"); // 鞋类类目
//        data.put("price", "2000");
//        //颜色，尺码，品牌,鞋头款式  闭合方式 鞋面材质 帮面材质 帮面内里材质 上市年份季节 里料材质
//        data.put("props", "1627207:28321;20549:10145050;20000:519854451;122216351:30233;20490:115481;122216640:3323086;124128491:19597500;139520082:3323086;122216347:1586027483;122216587:6474787");
//        data.put("sku_properties", "1627207:28321;20549:10145050");
//
//        data.put("cid", "50012907"); // 鞋类类目
//        data.put("sku_prices", "100");
//        data.put("sku_quantities", "20");
//
//
//        // 上传主图，3M 以内
//        String base64ImageLogo = Utils.getBase64ImageFromBinary("/Users/miller/Downloads/pdd.jpeg");
//        data.put("main_img", base64ImageLogo);

        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoItemAddUrl ,data);

    }

    /**
     * 删除一个淘宝商品
     * @throws Exception
     */
    @Test
    public void  deleteItem() throws Exception {


        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        // 必填
        data.put("num_iid", "635946082358");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoItemDeleteUrl ,data);

    }


//    /**
//     * 获取匹配产品规则
//     * @throws Exception
//     */
//    @Test
//    public void   matchSchemaGet() throws Exception {
//
//
//        String tb_seller_nick = Config.TBSellerNick ;
//        //业务参数
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("appid",  Config.AppId);
//        data.put("tb_seller_nick", tb_seller_nick);
//        Long timestamp = System.currentTimeMillis() / 1000;
//        data.put("timestamp", timestamp.toString());
//
//        // 必填 商品发布的目标类目，必须是叶子类目
//        data.put("category_id", "50011744");
//        // 签名
//        data.put("sign", Utils.Sign(data,Config.AppSecret));
//        // 调用服务API
//        String result = doHttpRequest(Config.TaoBaoProductMatchSchemaGetUrl ,data);
//
//
//        // 对返回的data需要进行unescapeHtml处理
//        JSONObject object = JSON.parseObject(result);
//        String ruleData = object.getString("data");
//        System.out.println(StringEscapeUtils.unescapeHtml(ruleData));
//
//    }

//    @Test
//    public void  matchSchema2() throws Exception {
//
//
//        String tb_seller_nick = Config.TBSellerNick ;
//        //业务参数
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("appid",  Config.AppId);
//        data.put("tb_seller_nick", tb_seller_nick);
//        Long timestamp = System.currentTimeMillis() / 1000;
//        data.put("timestamp", timestamp.toString());
//
//        // 必填 商品发布的目标类目，必须是叶子类目
//        data.put("category_id", "50011744");
//        // 必填
//
////        String pv = StringEscapeUtils.escapeHtml("<itemRule><field id=\"prop_20000\" name=\"品牌\" type=\"singleCheck\"><rules><rule name=\"requiredRule\" value=\"true\"/></rules><options><option displayName=\"简迪欧\" value=\"1953558155\"/></options></field><field id=\"prop_13021751\" name=\"货号\" type=\"input\"><rules><rule name=\"requiredRule\" value=\"true\"/></rules></field></itemRule>");
//        String pv =  "<itemRule> <field id=\"prop_13021751\" name=\"货号\" type=\"input\"><value>123</value> </field> <field id=\"prop_20000\" name=\"品牌\" type=\"singleCheck\"><value>1953558155</value> </field> </itemRule>";
//        data.put("propvalues", pv);
//        // 签名
//        data.put("sign", Utils.Sign(data,Config.AppSecret));
//        // 调用服务API
//        doHttpRequest(Config.TaoBaoTmallProductSchemaMatchUrl ,data);
//
//
//        // 对返回的data需要进行unescapeHtml处理
////        JSONObject object = JSON.parseObject(result);
////        String ruleData = object.getString("data");
////        System.out.println(StringEscapeUtils.unescapeHtml(ruleData));
//
//    }
//
//    @Test
//    public void  taoBaoTmallItemAddSchemaGet () throws Exception {
//
//
//        String tb_seller_nick = Config.TBSellerNick ;
//        //业务参数
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("appid",  Config.AppId);
//        data.put("tb_seller_nick", tb_seller_nick);
//        Long timestamp = System.currentTimeMillis() / 1000;
//        data.put("timestamp", timestamp.toString());
//
//        // 必填 商品发布的目标类目，必须是叶子类目
//        data.put("category_id", "50011744");
//        // 必填
//        data.put("product_id", "0");
//        data.put("isv_init", "false");
//        // 签名
//        data.put("sign", Utils.Sign(data,Config.AppSecret));
//        // 调用服务API
//       String result =  doHttpRequest(Config.TaoBaoTmallItemAddSchemaGetUrl ,data);
//
//
//        // 对返回的data需要进行unescapeHtml处理
//        JSONObject object = JSON.parseObject(result);
//        String ruleData = object.getString("data");
//        System.out.println(StringEscapeUtils.unescapeHtml(ruleData));
//
//    }
//
//
//
//
//
//
//
//    /**
//     * 天猫根据规则发布商品
//     * @throws Exception
//     */
//    @Test
//    public void  tmallSchemaAdd() throws Exception {
//
//
//        String tb_seller_nick = Config.TBSellerNick ;
//        //业务参数
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("appid",  Config.AppId);
//        data.put("tb_seller_nick", tb_seller_nick);
//        Long timestamp = System.currentTimeMillis() / 1000;
//        data.put("timestamp", timestamp.toString());
//
//        // 必填 商品发布的目标类目，必须是叶子类目
//        data.put("category_id", "50012907");
//        data.put("product_id", "0");
//        data.put("xml_data", "<itemRule><field id=\"prop_20000\" name=\"品牌\" type=\"singleCheck\"><rules><rule name=\"requiredRule\" value=\"true\"/></rules><options><option displayName=\"简迪欧\" value=\"1953558155\"/></options></field><field id=\"prop_13021751\" name=\"货号\" type=\"input\"><rules><rule name=\"requiredRule\" value=\"true\"/></rules></field></itemRule>");
//        // 签名
//        data.put("sign", Utils.Sign(data,Config.AppSecret));
//        // 调用服务API
//        doHttpRequest(Config.TaoBaoTmallItemSchemaAddUrl ,data);
//
//    }



    private  String  doHttpRequest(String url ,  Map<String, String> data ){
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
        return result;
    }


}
