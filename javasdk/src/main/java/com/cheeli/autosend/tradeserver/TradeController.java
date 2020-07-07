package com.cheeli.autosend.tradeserver;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.cheeli.Config;
import com.cheeli.tradeserver.model.OceanusNotice;
import com.cheeli.tradeserver.model.OceanusPayTrade;
import com.cheeli.utils.Utils;
import com.cheeli.tradeserver.model.PushTradeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


@RestController
public class TradeController {



    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 订单推送的接收入口
     * @param jsonData
     * @param response
     * @return
     */
    @RequestMapping("/trade")
    @ResponseBody
    public Map trade(@RequestBody String jsonData, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            jsonData = URLDecoder.decode(jsonData, "utf-8");
            System.out.println(jsonData);

            PushTradeEntity tradeEntity = JSON.parseObject(jsonData,PushTradeEntity.class);
            String pushtoken = tradeEntity.token;

            // 解密获取明文的订单号
            String sourceTid  = tradeEntity.tid.replaceAll(" ", "+");
            String oktid = Utils.Decrypt(  sourceTid , Config.AppSecret );// 注意去掉前后空格
            String token = Utils.MD5(Config.AppSecret  + oktid  );
            if( !pushtoken.equals(token)){
                map.put("StatusCode", -1);
                map.put("Msg", "token无效");
                return map;
            }
            // 解密获取买家昵称
            String buyerNick =  Utils.Decrypt(tradeEntity.buyer_nick,Config.AppSecret );
            //请根据实际业务处理订单
            System.out.println("received trade,tid:" +  oktid + " seller_id:" + tradeEntity.tb_seller_id + " buyer nick:" +  buyerNick);

        } catch (Exception ex) {
            ex.printStackTrace();
            //日志处理
        }

        //返回结果，statusCode =0 表示已收到推送的数据
        map.put("StatusCode", 0);
        map.put("Msg", "成功");
        return map;
    }





    /**
     * 支付回调
     * @param jsonData
     * @param response
     * @return
     */
    @RequestMapping("/paynotice")
    @ResponseBody
    public String  paynotice(@RequestBody String jsonData , HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {

                logger.info("原始数据：" + jsonData);
                OceanusNotice oceanusNotice  = JSON.parseObject(jsonData, OceanusNotice.class);
                Map<String, String> dataMap = new HashMap<String, String>();
                dataMap.put("code", String.valueOf(oceanusNotice.getCode()));
                dataMap.put("message", oceanusNotice.getMessage() );
                dataMap.put("action",   oceanusNotice.getAction()  );
                dataMap.put("data",  oceanusNotice.getData()  );
                dataMap.put("timestamp",oceanusNotice.getTimestamp()  );

                //参数签名
                String serverSign = oceanusNotice.getSign();
                if( !Utils.Sign(dataMap, Config.AppSecret ).equals(serverSign) ){
                    System.out.println("签名不相等, server " +  serverSign + "  local:" +  Utils.Sign(dataMap, Config.AppSecret )   );
                    return  "ERROR，签名不相等";
                }

                JSONObject   jsonObject  =  JSON.parseObject(oceanusNotice.getData());
                // 支付宝微信的交易订单号
                String alipayTradeNO = jsonObject.getString("tppay_trade_no");
                // QuickPay侧的订单号
                String quickpayTradeNo = jsonObject.getString("quickpay_trade_no");
                // 开发者之前请求传入的商户订单号， 原样返回。
                String OutTradeNo  = jsonObject.getString("out_trade_no");
                // 付款时间
                String payTime  = jsonObject.getString("pay_time");
                // 支付金额,单位分！！！！
                String totalFee  = jsonObject.getString("total_fee");
                // 扩展参数，  开发者之前请求传入的参数，原样返回。
                String Attach  = jsonObject.getString("attach");
                // 支付渠道
                String payChannel  = jsonObject.getString("pay_channel");

                //处理业务
                String msg= "收到订单:::->第三方订单号:"+  alipayTradeNO   +  ",quickpayTradeNo:" +  quickpayTradeNo +  ",OutTradeNo:" +
                        OutTradeNo   + ",Amount:" + totalFee  +  ",attach:" +  Attach +",payChannel:" +payChannel;
                System.out.println( msg );
                logger.info( msg );
                return  "SUCCESS";  //处理成功后，一定返回 SUCCESS



        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
            //日志处理
        }

        return  "SUCCESS";  //处理成功后，一定返回 SUCCESS
    }




    //状态测试
    @RequestMapping("/status")
    @ResponseBody
    public String  status(  HttpServletResponse response) {

        return  "OK";
    }


}