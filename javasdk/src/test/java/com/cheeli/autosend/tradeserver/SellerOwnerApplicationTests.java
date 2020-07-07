package com.cheeli.autosend.tradeserver;

import com.cheeli.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 场景：商户自用系统
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerOwnerApplicationTests {

    @Test
    public void contextLoads() {
    }


    /**
     *  进行发货
     * @throws Exception
     */

//    @Test
//    public void SendGoods() throws Exception {
//
//        String userkey = "201572ai29km62@p"; //private key  //修改
//        String  sellernick  = "百鞋馆"; //卖家账号，店铺千牛主号  //修改
//        long sellerId = 661153176L;
//
//        String tid = "53417555356942287032";// 订单号  //修改
//        String buyer_nick = "快客22";//修改
//        String alipay_no = "20222001136191051601598";    ////修改
//        String token = Utils.MD5(userkey +  sellernick + tid );
//        String buyer_message = ""; // 修改
//        String buyer_email = "6234222@qq.com";//买家邮件      //修改
//
////        String   encryptedtid = Utils.Encrypt( tid, userkey );
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> trade = new HashMap<String, Object>();
//
//
//        trade.put("tb_seller_id",sellerId );
//        trade.put("tid", tid );
//        trade.put("token", token);
//        trade.put("buyer_nick", buyer_nick);
//        trade.put("buyer_message", buyer_message);
//        trade.put("buyer_email", buyer_email);
//        trade.put("alipay_no", alipay_no);
//
//        //子订单
//        Map<String, Object> order = new HashMap<String, Object>();
//        order.put("price", 1.40);//修改
//        order.put("num_iid", "556984715717");//修改
//        order.put("payment", "1.40");//修改
//        order.put("title", "思飞网络|国外文件主机 datafile 1G流量");//修改
//        order.put("num", 1);//修改
//        order.put("outer_iid", "Ryushare_1024_30");//修改
//        order.put("outer_sku_id", "Ryushare_1024_30");//修改
//        order.put("content", "亲爱的用户【快客】,请使用订单编号【534175553569487022】 进行使用");//修改
//
//        List<Map<?, ?>> orders = new ArrayList<Map<?, ?>>();
//        orders.add(order);
//        trade.put("orders", orders);
//        String json = mapper.writeValueAsString(trade);
//
////        String sendgoodsUrl ="http://tbapi.fw199.com/tbbridge/trade/sendgoods";
//        String sendgoodsUrl ="http://localhost:8080/trade/sendgoods";
//        String response =Utils.post(sendgoodsUrl ,json);
//        System.out.println(response);
//
//
//    }





    //普通单个店铺的对接
    @Test
    public void SendGoods() throws Exception {

//        String userKey =  "201572ai29km62@p"; // userkey  //修改
//        Long SellerId = 661153176L; //卖家ID  //修改



        String userKey="zA9xvTfeSYpOinl3";
        long SellerId=25967733L;
        String tid = "598735918774885205";// 订单号  //修改
        String buyer_nick = "supercode";//修改

        String token = Utils.MD5(userKey + tid);
        String encrypted_buyer_nick =   Utils.Encrypt(buyer_nick, userKey);
        String buyer_message = ""; // 修改
        String buyer_email = "6234222@qq.com";//买家邮件      //修改
        String alipay_no = "2019071722001136191051601598";    ////修改
        //主订单
        String   encryptedtid = Utils.Encrypt( tid, userKey );
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> trade = new HashMap<String, Object>();
        trade.put("tb_seller_id", SellerId);
        trade.put("tid", encryptedtid);
        trade.put("token", token);
        trade.put("buyer_nick", encrypted_buyer_nick);
        trade.put("buyer_message", buyer_message);
        trade.put("buyer_email", buyer_email);
        trade.put("alipay_no", alipay_no);
        trade.put("trade_status", 0);
        //子订单
        Map<String, Object> order = new HashMap<String, Object>();
        order.put("price", 1.40);//修改
        order.put("num_iid", "556984715717");//修改
        order.put("payment", "1.40");//修改
        order.put("title", "思飞网络|国外文件主机 datafile 1G流量");//修改
        order.put("num", 1);//修改
        order.put("outer_iid", "Ryushare_1024_30");//修改
        order.put("outer_sku_id", "Ryushare_1024_30");//修改
        order.put("content", "亲爱的用户【快客】,请使用订单编号【534175553569487022】 进行使用");//修改

        List<Map<?, ?>> orders = new ArrayList<Map<?, ?>>();
        orders.add(order);
        trade.put("orders", orders);
        String json = mapper.writeValueAsString(trade);
        System.out.println(json);
        System.out.println("========================== response ========================");
        String sendgoodsUrl ="http://tbapi.fw199.com/tbbridge/trade/sendgoods";
        String response =Utils.post(sendgoodsUrl ,json);
        System.out.println(response);

    }




    @Test
    public void AddMemo() throws Exception {
        String url ="http://tbapi.fw199.com/tbbridge/trade/addmemo";

        String userkey = "101572ai29km62@p"; //private key  //修改
        String  sellerid = "261153176"; //卖家ID  //修改
        String tid=  "111201410775565830";
        String   token = Utils.MD5(    userkey  + sellerid + tid );//字符串形式
        Map<String,String> map=new HashMap<>();
        map.put("tb_seller_id", sellerid);
        map.put("tid", tid);
        map.put("memo", "这一个使用API添加的备注");
        map.put("flag", "1");
        map.put("token", token);
        String response =Utils.post(url  ,map);
        System.out.println(response);
    }


    @Test
    public void getTrade() throws Exception {
        String url ="http://tbapi.fw199.com/tbbridge/trade/detail";
        String userkey = "201572ai29km62@p"; //private key  //修改
        String  sellerid = "661153176"; //卖家ID  //修改
        String tid=  "666313280002565830";
        String   token = Utils.MD5(    userkey  + sellerid  );//字符串形式
        Map<String,String> map=new HashMap<>();
        map.put("tb_seller_id", sellerid);
        map.put("tid", tid);
        map.put("token", token);
        String response =Utils.post(url  ,map);
        System.out.println(response);
    }




    }
