package com.cheeli;

import com.alibaba.fastjson.JSON;
import com.cheeli.models.AddressItem;
import com.cheeli.utils.Utils;
import com.taobao.pac.sdk.cp.PacClient;
import com.taobao.pac.sdk.cp.SendSysParams;
import com.taobao.pac.sdk.cp.dataobject.request.ADDRLIB_DIV_FULLPARSE.AddrlibDivFullparseRequest;
import com.taobao.pac.sdk.cp.dataobject.response.ADDRLIB_DIV_FULLPARSE.AddrlibDivFullparseResponse;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.Checksum;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AddrTests.class)
public class AddrTests {



    @Test
    public void contextLoads() {
    }





    @Test
    public void addrResolve() throws Exception {
//        String requestData = "[{" +
//                "        \"object_id\": \"001\"," +
//                "        \"resolve_address\": \"袁月青 131 7622 8763 四川省成都市高新西区百叶路1号电子科技大学成都学院计算机(分院)\"" +
//                "    }," +
//                "    {" +
//                "        \"object_id\": \"002\"," +
//                "        \"resolve_address\": \"深圳市盐田区万科东海岸21-102，收件人：叶侠，电话：131 7622 8764\"" +
//                "    }," +
//                "    {" +
//                "        \"object_id\": \"003\"," +
//                "        \"resolve_address\": \"江西南昌市青山湖区广兰大道418号东华理工大学核工系南区9栋1112室 13176228768 孙东明\"" +
//                "    }," +
//                "    {" +
//                "        \"object_id\": \"004\"," +
//                "        \"resolve_address\": \"陈思燕 17694800022 610726199307251717广东省梅州市蕉岭县邹邹路5803号邹邹小区8单元22403室\"" +
//                "    }, " +
//                "    {" +
//                "        \"object_id\": \"005\"," +
//                "        \"resolve_address\": \"盐田区山海四季城F栋1B，卢燕13129871122\"" +
//                "    },"  +
//                    "    {" +
//                "        \"object_id\": \"006\"," +
//                "        \"resolve_address\": \"四川省 成都市 锦江区 书院街街道东城拐下街8号附15号<原百世快递>(菜鸟驿站:13658012627)\"" +
//                "    } " +
//                "]";

          String source ="广东省中山市火炬开**街道**村*街**号\n";

//                  "陕西省 宝鸡市 金台区 东风路街道宝鸡金台区惠邻购物广场服务台\n" +
//                  "河南省 南阳市 镇平县 涅阳街道西关慕庄\n" +
//                  "广东省 佛山市 南海区 大沥镇钟边路，佛山市拓健口腔医疗没备厂，旁15米";
        //                  "重庆 重庆市 北碚区 北温泉街道学府江山A区2栋\n" +
//                  "湖北省 武汉市 新洲区 阳逻街道阳逻经济开发区华中产业园啊A1-1808\n" +
//                  "广东省 深圳市 光明区 玉塘街道田明街68号美宜佳田寮市场店\n" +
//                  "黑龙江省 哈尔滨市 道里区 群力街道丽江路海富禧园3号楼一单元903室\n" +
//                  "福建省 宁德市 福鼎市 嵛山镇嵛山岛渔人闲居酒店\n" +
//                  "天津 天津市 静海区 大邱庄镇王虎庄三区六排兔喜超市\n" +
//                  "江西省 新余市 渝水区 城南街道观下新村妈妈驿站\n" +
//                  "河北省 邯郸市 邯山区 南堡乡河北省邯郸市邯山区雪驰路春风小区10号楼\n" +
//                  "福建省 宁德市 福安市 溪柄镇兴龙街245号\n" +
//                  "广东省 珠海市 斗门区 井岸镇西埔村口半记火锅\n" +
//                  "重庆 重庆市 沙坪坝区 井口街道金杜阳光10栋\n" +
//                  "江苏省 无锡市 江阴市 澄江街道虹桥三村28-201\n" +
//                  "江苏省 泰州市 姜堰区 梁徐镇黄村三组7号\n" +
//                  "吉林省 吉林市 丰满区 小白山乡松江南路488号松花江智慧新城6区5号楼\n" +
//                  "广东省 东莞市  寮步镇下岭贝步百业西二路睿泳五金制品有限公司\n" +
//                  "福建省 莆田市 秀屿区 东峤镇上塘珠宝城\n" +
//                  "广东省 惠州市 惠城区 陈江街道育才街31号积庆里茶业\n" +
//                  "广东省 东莞市  虎门镇龙眼路37号东三路九巷七号\n" +
//                  "安徽省 宿州市 萧县 刘套镇安徽省宿州市萧县刘套镇陈屯大队肖场自然村\n" +
//                  "浙江省 丽水市 莲都区 紫金街道东升南区31栋3楼\n" +
//                  "安徽省 芜湖市 南陵县 许镇镇育才路菜鸟驿站一号店\n" +
//                  "四川省 成都市 金牛区 茶店子街道营康西路428号一品天下b区\n" +
//                  "广东省 佛山市 高明区 荷城街道荷富路勤天汇10座，必须放到驿站，一律不准私自签收\n" +
//                  "江西省 上饶市 信州区 朝阳镇728县道王家山建材五金";

//                  "河南省 濮阳市 濮阳县 濮阳县白罡乡关庄村超市\n" +
//                  "吉林省 白山市 浑江区 新建街道宜宾社区西新城小区一号楼6单元\n" +
//                  "吉林省 延边朝鲜族自治州 安图县    二道白河镇 民心家园一期 11号楼 2单元\n" +
//                  "陕西省 西安市 莲湖区 桃园路街道西斜路三路华府御城12号楼\n" +
//                  "湖北省 武汉市 蔡甸区 蔡甸街道阳光星城1栋二单元503\n" +
//                  "江西省 赣州市 宁都县 梅江镇88888泛华小区11栋\n" +
//                  "山西省 吕梁市 孝义市 新义街道建东街老干局宿舍西门2单元\n" +
//                  "内蒙古自治区 呼和浩特市 赛罕区 乌兰察布东路街道鄂尔多斯东街尚东枫景小区14＃楼东单元（放快递柜）\n" +
//                  "广西壮族自治区 桂林市 资源县 车田苗族乡田头水下大湾\n" +
//                  "山西省 临汾市 霍州市 大张镇福奥小区\n" +
//                  "广西壮族自治区 南宁市 横县 横州镇城司路238号佳禧广告策划（龙池湖原玉林高佬火锅城旁）\n" +
//                  "内蒙古自治区 赤峰市 翁牛特旗 乌丹镇（孰邸连云二号楼）\n" +
//                  "山东省 济南市 天桥区 北园街道国贸花园E区东单元\n" +
//                  "山东省 济南市 天桥区 北园街道国贸花园E区东单元\n" +
//                  "浙江省 丽水市 莲都区 紫金街道东升南区31栋3楼\n" +
//                  "内蒙古自治区 乌兰察布市 察哈尔右翼前旗 土贵乌拉镇乌兰擦布擦右前旗红星小区\n" +
//                  "陕西省 咸阳市 长武县 丁家镇丁陶公路五里铺工业园\n" +
//                  "山东省 临沂市 蒙阴县 蒙阴街道云蒙路168号富乐国际小区\n" +
//                  "浙江省 丽水市 莲都区 岩泉街道金笔街25号\n" +
//                  "广东省 茂名市 电白区 坡心镇广东省茂名市茂港区坡心镇\n" +
//                  "江苏省 南京市 溧水区 永阳镇天生桥大道688号\n" +
//                  "江西省 宜春市 奉新县 宋埠镇三洪村\n" +
//                  "山东省 滨州市 无棣县 小泊头镇北高家庄村\n" +
//                  "河北省 唐山市 路北区 河北路街道河北新人家\n" +
//                  "湖北省 恩施土家族苗族自治州 恩施市 舞阳坝街道施州大道傅家坡组，晨风超市\n" +
//                  "湖南省 郴州市 安仁县 永乐江镇建设街道建设南路老公安局\n" +
//                  "浙江省 宁波市 鄞州区 塘溪镇东西岙村的西岙村世纪购物小店旁边（红色阳门）\n" +
//                  "湖北省 恩施土家族苗族自治州 恩施市 龙凤镇大市场圆通快递（电话 自取）\n" +
//                  "云南省红河哈尼族彝族自治州个旧市锡城镇明大雅筑2幢2单元401冯艳13577339644";

            String[] rows = source.split("\n");


        List<AddressItem> list = new ArrayList<>();
        int i = 1;
        for(String item : rows) {
            AddressItem  addressItem   = new AddressItem();
            addressItem.setObjectId(String.valueOf(i));
            addressItem.setResolveAddress(item);
            list.add(addressItem);
        }

      //  String requestData = JSON.toJSONString(list);
//        String requestData = "[{" +
//                "        \"object_id\": \"001\"," +
//                "        \"resolve_address\": \"袁月青 131 7622 8763 四川省成都市高新西区百叶路1号电子科技大学成都学院计算机(分院)\"" +
//                "    }," +
//                "    {" +
//                "        \"object_id\": \"002\"," +
//                "        \"resolve_address\": \"深圳市盐田区万科东海岸21-102，收件人：叶侠，电话：131 7622 8764\"" +
//                "    }," +
//                "    {" +
//                "        \"object_id\": \"003\"," +
//                "        \"resolve_address\": \"江西南昌市青山湖区广兰大道418号东华理工大学核工系南区9栋1112室 13176228768 孙东明\"" +
//                "    }," +
//                "    {" +
//                "        \"object_id\": \"004\"," +
//                "        \"resolve_address\": \"陈思燕 17694800022 610726199307251717广东省梅州市蕉岭县邹邹路5803号邹邹小区8单元22403室\"" +
//                "    }, " +
//                "    {" +
//                "        \"object_id\": \"005\"," +
//                "        \"resolve_address\": \"盐田区山海四季城F栋1B，卢燕13129871122\"" +
//                "    }, " +
//                "    {" +
//                "        \"object_id\": \"006\"," +
//                "        \"resolve_address\": \"2106632881250832143袁月青 1317628763 河南省开封市兰考县车站路济阳学校对面\"" +
//                "    }, " +
//                "    {" +
//                "        \"object_id\": \"007\"," +
//                "        \"resolve_address\": \"210913-221438288790278 袁月青1317628763河南省开封市兰考县车站路济阳学校对面\"" +
//                "    } ," +
//                "    {" +
//                "        \"object_id\": \"008\"," +
//                "        \"resolve_address\": \"13524535235广东410303197905255969省广州市天河区稥稥路4575号稥稥小区15单元668室 孟仲杨\"" +
//                "    } " +
//                "]";//
//
                String requestData = "[{" +
                "        \"object_id\": \"001\"," +
                "        \"resolve_address\": \"广东省中山市火炬开**街道**村*街**号\"" +
                "    } " +

                "]";



        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("request_data",requestData );
       // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        doHttpRequest(Config.AddrResolveUrl, data);


    }


    private  void doHttpRequest(String url ,  Map<String, String> data ){
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
    }








}
