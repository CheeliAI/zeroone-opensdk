package com.cheeli.taobao;

import com.alibaba.fastjson.JSON;
import com.cheeli.Config;
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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 场景：合作伙伴使用，可管理其他授权的店铺。即可以管理多家店铺
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenAlibabaItemTests.class )
public class OpenAlibabaItemTests {

    @Test
    public void contextLoads() {
    }



    @Test
    public void  AlibabaItemPublishSchemaGet() throws Exception {
        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("market", "taobao" );
        data.put("cat_id", "50000436" );
        // images多个用逗号隔开
//       data.put("images", "https://img.alicdn.com/imgextra/i3/520557274/O1CN01noA5I023bXcMGWJXQ_!!0-item_pic.jpg,https://img.alicdn.com/imgextra/i3/520557274/O1CN01noA5I023bXcMGWJXQ_!!0-item_pic.jpg" );
         data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        String rsp = doHttpRequest(Config.AlibabaItemPublishSchemaGetUrl ,data);
        // 需要进行编码转换，比如 "\u003c" 转成  "<"   ，  "\u003e" 转成 ">" 等
        String unescapeRsp = StringEscapeUtils.unescapeJavaScript(rsp);
    }


    /**
     * 商品级联属性信息获取
     * @throws Exception
     */

    @Test
    public void  AlibabaItemPublishPropsGet() throws Exception {
        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("market", "taobao" );
        data.put("cat_id", "50021288" );
        data.put("barcode", "6932529211107" );
        String schema = "<itemSchema> <field id=\"catProp\" name=\"类目属性\" type=\"complex\"> <complex-value> <field id=\"p-20000\" name=\"品牌\" type=\"singleCheck\"> <value>1128204128</value> </field> </complex-value> <fields> <field id=\"p-20000\" name=\"品牌\" type=\"singleCheck\"> </field> </fields> </field></itemSchema>";
        data.put("schema", schema);
        data.put("prop_id", "20000");
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        String rsp =  doHttpRequest(Config.AlibabaItemPublishPropsGetUrl ,data);
        // 需要进行编码转换，比如 "\u003c" 转成  "<"   ，  "\u003e" 转成 ">" 等
        String unescapeRsp = StringEscapeUtils.unescapeJavaScript(rsp);
    }


    /**
     * 商品发布
     * @throws Exception
     */

    @Test
    public void  AlibabaItemPublishSubmitRequest() throws Exception {
        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("market", "taobao" );
        data.put("cat_id", "50008899" );
//      data.put("barcode", "6932529211107" );
//      data.put("spu_id", "32323" );
        // 由于发布商品schema比较大，放到了文件
//        Path path = Paths.get("/Users/miller/itemSchema.txt");
        Path path = Paths.get("/Users/miller/working/temp/xml.txt");
        byte[] fileContent = Files.readAllBytes(path);
        String schema = new String(fileContent, "utf-8");
        data.put("schema", schema);
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.AlibabaItemPublishSubmitUrl ,data);

    }


    /**
     *  商品编辑提交schema信息
     * @throws Exception
     */
  @Test
    public void  AlibabaItemEditSchemaGet() throws Exception {
        String tb_seller_nick = Config.TBSellerNick ;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
//      data.put("biz_type", "taobao/1.0.0/brandAsyncRenderEnable" );
        data.put("item_id", "657712047100" );
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        // 调用服务API
      String rsp = doHttpRequest(Config.AlibabaItemEditSchemaGetUrl, data);
      // 需要进行编码转换，比如 "\u003c" 转成  "<"   ，  "\u003e" 转成 ">" 等
      String unescapeRsp = StringEscapeUtils.unescapeJavaScript(rsp);

    }




    /**
     * 淘宝商品编辑获取schema信息
     * @throws Exception
     */
    @Test
    public void AlibabaItemEditSubmit () throws Exception {
        String tb_seller_nick = Config.TBSellerNick;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("item_id", "657645255129");
        String schema = "<itemSchema> \n" +
                "<field id=\"desc\" name=\"PC端详情描述\" type=\"input\">\n" +
                "          <value>这是一个pc的说明，不要拍, 我是被更新过了</value>\n" +
                "    </field>\n" +
                "</itemSchema>";
        data.put("schema", schema);
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.AlibabaItemEditSubmitUrl, data);

    }


    /**
     *  商品编辑增量更新
     * @throws Exception
     */
    @Test
    public void AlibabaItemEditFastUpdate () throws Exception {
        String tb_seller_nick = Config.TBSellerNick;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("item_id", "657645255129");
        String schema = "<itemSchema>\n" +
                "    <field id=\"title\" type=\"input\">\n" +
                "        <value>测试宝贝连衣裙 2021</value>\n" +
                "    </field>\n" +
                "</itemSchema>";
        data.put("schema", schema);
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.AlibabaItemEditFastUpdateUrl, data);

    }


  /**
     *  商品上架
     * @throws Exception
     */
    @Test
    public void AlibabaItemOperateUpshelf() throws Exception {
        String tb_seller_nick = Config.TBSellerNick;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("item_id", "657645255129");
        data.put("quantity", "0");
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.AlibabaItemOperateUpshelfUrl, data);

    }


  /**
     *  商品下架
     * @throws Exception
     */
    @Test
    public void AlibabaItemOperateDownshelf() throws Exception {
        String tb_seller_nick = Config.TBSellerNick;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("item_id", "657645255129");
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.AlibabaItemOperateDownshelfUrl, data);

    }

     /**
     *  商品删除
     * @throws Exception
     */
    @Test
    public void AlibabaItemOperateDelete() throws Exception {
        String tb_seller_nick = Config.TBSellerNick;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("item_id", "657645255129");
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        doHttpRequest(Config.AlibabaItemOperateDeleteUrl, data);

    }

    /**
     *  获取匹配产品规则
     * @throws Exception
     */
    @Test
    public void TmallProductMatchSchemaGetRequest() throws Exception {
        String tb_seller_nick = Config.TBSellerNick;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("category_id", "50010731");
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        String rsp = doHttpRequest(Config.TmallProductMatchSchemaGetRequestUrl, data);
        // 需要进行编码转换，比如 "\u003c" 转成  "<"   ，  "\u003e" 转成 ">" 等
        String unescapeRsp = StringEscapeUtils.unescapeJavaScript(rsp);
        System.out.println(unescapeRsp);
    }


    /**
     *   产品匹配
     * @throws Exception
     */
    @Test
    public void TmallProductSchemaMatchRequest() throws Exception {
        String tb_seller_nick = Config.TBSellerNick;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("category_id", "50010731");
         String pv =  "<itemRule> \t\n" +
                "    <field id=\"prop_20000\" name=\"品牌\" type=\"singleCheck\">\n" +
                "        <value>13525264</value> \t\n" +
                "    </field> \n" +
                "<field id=\"prop_6362646\" name=\"型号\" type=\"singleCheck\">\n" +
                "        <value>693238758</value> \n" +
                "    </field>\n" +
                "</itemRule>";
        data.put("propvalues", pv);
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        String rsp = doHttpRequest(Config.TmallProductSchemaMatchRequestUrl, data);
        // 需要进行编码转换，比如 "\u003c" 转成  "<"   ，  "\u003e" 转成 ">" 等
        String unescapeRsp = StringEscapeUtils.unescapeJavaScript(rsp);
        System.out.println(unescapeRsp);
    }





    /**
     *   产品信息获取schema获取 1
     * @throws Exception
     */
    @Test
    public void TmallProductSchemaGetRequestUrl() throws Exception {
        String tb_seller_nick = Config.TBSellerNick;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("product_id", "337259102");

        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        String rsp =   doHttpRequest(Config.TmallProductSchemaGetRequestUrl, data);
        // 需要进行编码转换，比如 "\u003c" 转成  "<"   ，  "\u003e" 转成 ">" 等
        String unescapeRsp = StringEscapeUtils.unescapeJavaScript(rsp);
        System.out.println(unescapeRsp);
    }


    /**
     *   产品发布规则获取接口   2
     * @throws Exception
     */
    @Test
    public void TmallProductAddSchemaGetRequest() throws Exception {
        String tb_seller_nick = Config.TBSellerNick;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("category_id", "201241307");
//        data.put("brand_id", "10000497");
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        String rsp =   doHttpRequest(Config.TmallProductAddSchemaGetRequestUrl, data);
        // 需要进行编码转换，比如 "\u003c" 转成  "<"   ，  "\u003e" 转成 ">" 等
        String unescapeRsp = StringEscapeUtils.unescapeJavaScript(rsp);
        System.out.println(unescapeRsp);
    }


    /**
     *    使用Schema文件发布一个产品
     * @throws Exception
     */
    @Test
    public void TmallProductSchemaAddRequestUrl() throws Exception {
        String tb_seller_nick = Config.TBSellerNick;
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
        data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("category_id", "201241307");
//        data.put("brand_id", "10000497");
        String xmlData = "<itemRule>\n" +
                "    <field id=\"prop_20000\" name=\"品牌\" type=\"singleCheck\">\n" +
                "        <rules>\n" +
                "            <rule name=\"requiredRule\" value=\"true\"/>\n" +
                "        </rules>\n" +
                "            <value>222</value>\n" +
                "\n" +
                "    </field>\n" +
                "    <field id=\"prop_13021751\" name=\"货号\" type=\"input\">\n" +
                "        <rules>\n" +
                "            <rule name=\"requiredRule\" value=\"true\"/>\n" +
                "        </rules>\n" +
                "         <value>hh100</value>\n" +
                "    </field>\n" +
                "    <field id=\"prop_122216347\" name=\"上市年份季节\" type=\"singleCheck\">\n" +
                "      <value>828914261</value> \n" +
                "    </field>\n" +
                "    <field id=\"prop_13328588\" name=\"成分含量\" type=\"singleCheck\">\n" +
                "        <rules>\n" +
                "            <rule name=\"requiredRule\" value=\"true\"/>\n" +
                "        </rules> \n" +
                "          <value>145656297</value>\n" +
                "    </field>\n" +
                "    <field id=\"prop_122216586\" name=\"服装版型\" type=\"singleCheck\">\n" +
                "        <rules>\n" +
                "            <rule name=\"requiredRule\" value=\"true\"/>\n" +
                "        </rules>\n" +
                "  \n" +
                "          <value>3267162</value>\n" +
                "    </field>\n" +
                "    <field id=\"prop_122216562\" name=\"衣长\" type=\"singleCheck\">\n" +
                "        <rules>\n" +
                "            <rule name=\"requiredRule\" value=\"true\"/>\n" +
                "        </rules>\n" +
                "           <value>22561361</value>\n" +
                "       \n" +
                "    </field>\n" +
                "    <field id=\"prop_122216348\" name=\"袖长\" type=\"singleCheck\">\n" +
                "        <rules>\n" +
                "            <rule name=\"requiredRule\" value=\"true\"/>\n" +
                "        </rules>\n" +
                "         <value>29444</value>\n" +
                "    \n" +
                "    </field>\n" +
                "    <field id=\"prop_20021\" name=\"面料\" type=\"singleCheck\">\n" +
                "        <rules>\n" +
                "            <rule name=\"requiredRule\" value=\"true\"/>\n" +
                "        </rules>\n" +
                "          <value>105255</value>\n" +
                "        \n" +
                "    </field>\n" +
                "    <field id=\"product_images\" name=\"产品图片\" type=\"complex\">\n" +
                "        <fields>\n" +
                "            <field id=\"product_image_0\" name=\"产品图片\" type=\"input\">\n" +
                "                <rules>\n" +
                "                    <rule name=\"valueTypeRule\" value=\"url\"/>\n" +
                "                    <rule name=\"requiredRule\" value=\"true\"/>\n" +
                "                </rules> \n" +
                "                  <value>https://img.alicdn.com/imgextra/i2/661153176/TB2_4DUopuWBuNjSspnXXX1NVXa_!!661153176.jpg</value>\n" +
                "\n" +
                "            </field>\n" +
                "            <field id=\"product_image_1\" name=\"产品图片\" type=\"input\">\n" +
                "                <rules>\n" +
                "                    <rule name=\"valueTypeRule\" value=\"url\"/>\n" +
                "                </rules>\n" +
                "            </field>\n" +
                "            <field id=\"product_image_2\" name=\"产品图片\" type=\"input\">\n" +
                "                <rules>\n" +
                "                    <rule name=\"valueTypeRule\" value=\"url\"/>\n" +
                "                </rules>\n" +
                "            </field>\n" +
                "            <field id=\"product_image_3\" name=\"产品图片\" type=\"input\">\n" +
                "                <rules>\n" +
                "                    <rule name=\"valueTypeRule\" value=\"url\"/>\n" +
                "                </rules>\n" +
                "            </field>\n" +
                "            <field id=\"product_image_4\" name=\"产品图片\" type=\"input\">\n" +
                "                <rules>\n" +
                "                    <rule name=\"valueTypeRule\" value=\"url\"/>\n" +
                "                </rules>\n" +
                "            </field>\n" +
                "        </fields>\n" +
                "    </field>\n" +
                "</itemRule>";
        data.put("xml_data", xmlData);
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        String rsp =   doHttpRequest(Config.TmallProductSchemaAddRequestUrl, data);
        // 需要进行编码转换，比如 "\u003c" 转成  "<"   ，  "\u003e" 转成 ">" 等
        String unescapeRsp = StringEscapeUtils.unescapeJavaScript(rsp);
        System.out.println(unescapeRsp);
    }


    private  String  doHttpRequest(String url ,  Map<String, String> data ){
        System.out.println("请求Url:" +url);
        System.out.println("请求数据:" + JSON.toJSONString(data));
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
