package com.cheeli.utils;

import com.alibaba.fastjson.JSON;
import com.cheeli.Config;
import com.cheeli.models.taobao.trade.Trade;
import com.cheeli.models.taobao.trade.TradeResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.HashMap;
import java.util.Map;

public class TradeUtil {


    public static Trade getTBTrade(String tid) throws Exception{
        String tb_seller_nick = Config.TBSellerNick; //要查询支付宝的淘宝商家
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", Config.AppId);
         data.put("tb_seller_nick", tb_seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        data.put("tids", tid);// bxg
        data.put("sign", Utils.Sign(data, Config.AppSecret));
        // 调用服务API
        String resp = Utils.doHttpRequest(Config.TaoBaoOrderDetailBatchUrl, data);
        TradeResponse tradeResponse = JSON.parseObject(resp, TradeResponse.class);
        if (tradeResponse == null) {
            System.out.println("json解析错误:" + resp);
        }

        if (tradeResponse.getCode() != 0) {
            String msg = String.format("code:%d, msg:%s, ", tradeResponse.getCode(), tradeResponse.getMessage());
            System.out.println(msg);
        } else {

            for (com.cheeli.models.taobao.trade.Data dataItem : tradeResponse.getData()) {
                Trade trade = dataItem.getTrade();
                return trade;
            }

        }
        return null;
    }


    public static com.cheeli.models.pdd.TradeResponse getPddTrade(String tid) throws Exception{
        String tb_seller_nick = Config.PddSellerNick;
        String result ="";
        String seller_nick = Config.PddSellerNick ; // 拼多多卖家账号
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( Config.PddOrderDetailUrl );
        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid",  Config.AppId);
        data.put("seller_nick", seller_nick);
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        //  必填 ，订单号
        data.put("order_sn",tid);
        // 参数签名
        data.put("sign", Utils.Sign(data,Config.AppSecret));
        String resp = Utils.doHttpRequest(Config.PddOrderDetailUrl, data);
        com.cheeli.models.pdd.TradeResponse pddTradeResponse = JSON.parseObject(resp, com.cheeli.models.pdd.TradeResponse.class);

        return pddTradeResponse;
    }



}
