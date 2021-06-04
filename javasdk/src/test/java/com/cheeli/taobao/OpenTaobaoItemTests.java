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
        data.put("datetime", "2019-01-01 22:00:00" );

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
        data.put("cid", "50015766");
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

    /**
     * 添加sku
     * @throws Exception
     */
    @Test
    public void itemAddSku() throws Exception {


        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());

        // Sku所属商品数字id。必填
        data.put("num_iid", "544876335798" );
        // 必填, Sku属性串。格式:pid:vid;pid:vid,如:1627207:3232483;1630696:3284570,表示:机身颜色:军绿色;手机套餐:一电一充。
        data.put("properties", "544876335798:223232" );

        //  必填，Sku的库存数量。sku的总数量应该小于等于商品总数量(Item的NUM)。取值范围:大于零的整数
        data.put("quantity", "22");
        // Sku的销售价格。商品的价格要在商品所有的sku的价格之间。精确到2位小数;单位:元。如:200.07，表示:200元7分
        data.put("price", "52.18");

       // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoItemAddSku ,data);

    }


    /**
     * 查询商家被授权品牌列表和类目列表
     * @throws Exception
     */
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

        // 详情页描述(HTML)
        String desc = "<p data-spm-anchor-id=\"a2126o.11854294.0.i4.48a94831s8bOar\">这是一个好商品,但是不要拍，&nbsp;<img align=\"absmiddle\" src=\"https://img.alicdn.com/imgextra/i2/661153176/TB2_4DUopuWBuNjSspnXXX1NVXa_!!661153176.jpg\" style=\"max-width:750px\" /> ，不错啊</p>";
        //    必填
        data.put("location.state", "浙江");
        // 所在地城市。如杭州 。
        data.put("location.city", "杭州");
        data.put("num", "40");
        data.put("type", "fixed");
        data.put("stuff_status", "new");
        data.put("title", "真皮男士增高鞋男高帮工装鞋-api23");
        data.put("desc",desc);
        data.put("cid", "50012907"); // 鞋类类目
        data.put("price", "2000");
        //颜色，尺码，品牌,鞋头款式  闭合方式 鞋面材质 帮面材质 帮面内里材质 上市年份季节 里料材质
        data.put("props", "1627207:28321;20549:10145050;20000:519854451;122216351:30233;20490:115481;122216640:3323086;124128491:19597500;139520082:3323086;122216347:1586027483;122216587:6474787");
        data.put("sku_properties", "1627207:28321;20549:10145050");

        data.put("cid", "50012907"); // 鞋类类目
        data.put("sku_prices", "100");
        data.put("sku_quantities", "20");


        // 上传主图，3M 以内
        String base64ImageLogo = Utils.getBase64ImageFromBinary("/Users/miller/Downloads/pdd.jpeg");
        data.put("main_img", base64ImageLogo);

        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoItemAddUrl ,data);

    }

    /**
     * 添加商品图片
     * @throws Exception
     */
    @Test
    public void  itemUpdateImage() throws Exception {

        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tb_seller_nick", tb_seller_nick);
        // 宝贝Id (必须）
        data.put("num_iid", "544876335798");
        // 图片序号（非必须）
        data.put("position", "1");
        // 是否将该图片设为主图,（非必须） , 可选值:true,false;默认值:false(非主图)
        data.put("is_major", "false");

         // 添加一张商品图片到num_iid指定的商品中 传入的num_iid所对应的商品必须属于当前会话的用 ,  商品图片内容类型:JPG;最大:3M 。支持的文件类型：jpg,jpeg,png
        String base64ImageLogo = Utils.getBase64ImageFromBinary("/Users/miller/Downloads/pdd.jpeg");
        data.put("item_img", base64ImageLogo);

        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoItemUploadImage ,data);

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


    /**
     *  新增图片分类信息
     * @throws Exception
     */
    @Test
    public void  TaoBaoPictureCategoryAdd() throws Exception {


        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        // 必填 图片分类名称，最大长度20字符，中文字符算2个字符，不能为空
        data.put("picture_category_name", "abc");

        // 非必填， 图片分类的父分类,一级分类的parent_id为0,二级分类的则为其父分类的picture_category_id
//        data.put("parent_id", "0");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoPictureCategoryAdd ,data);

    }


    /**
     *  没有权限
     * @throws Exception
     */
    @Test
    public void  TaoBaoPictureUpload() throws Exception {


        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        // 必填 图片分类名称，最大长度20字符，中文字符算2个字符，不能为空
        data.put("picture_category_id", "0");
        data.put("image_input_title", "pdd.jpg");
        // 添加一张商品图片到num_iid指定的商品中 传入的num_iid所对应的商品必须属于当前会话的用 ,  商品图片内容类型:JPG;最大:3M 。支持的文件类型：jpg,jpeg,png
        String base64ImageLogo = Utils.getBase64ImageFromBinary("/Users/miller/Downloads/pdd.jpeg");
        data.put("base64_image", base64ImageLogo);
        // 非必填， 图片分类的父分类,一级分类的parent_id为0,二级分类的则为其父分类的picture_category_id
//        data.put("parent_id", "0");
        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoPictureUpload ,data);

    }

    /**
     * 添加或修改属性图片
     * @throws Exception
     */
    @Test
    public void  TaoBaoItemPropImgUpload() throws Exception {


        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        // 必填
        data.put("num_iid", "544876335798");
        data.put("properties", "1627207:28326");
        // 添加一张商品图片到num_iid指定的商品中 传入的num_iid所对应的商品必须属于当前会话的用 ,  商品图片内容类型:JPG;最大:3M 。支持的文件类型：jpg,jpeg,png
        String base64ImageLogo = Utils.getBase64ImageFromBinary("/Users/miller/Downloads/pdd.jpeg");
        data.put("base64_image", base64ImageLogo);

        // 签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.TaoBaoItemPropImgUploadUrl ,data);

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
