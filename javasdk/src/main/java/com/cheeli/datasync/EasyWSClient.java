package com.cheeli.datasync;

import com.alibaba.fastjson.JSON;
import com.cheeli.models.SFCloudPrintWaybill;
import com.cheeli.tradeserver.model.ChangeMemoWithTrade;
import com.cheeli.tradeserver.model.TradeInfo;
import com.cheeli.tradeserver.model.TradeMemo;
import org.java_websocket.handshake.ServerHandshake;

import java.io.*;
import java.net.URI;
import java.util.Base64;

public class EasyWSClient extends ReconnectingWSClient {


    public EasyWSClient(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpenEvent(ServerHandshake handshakedata) {
        System.out.println("========onOpenEvent===========");

    }

    @Override
    public void onErrorEvent(Exception ex) {
        System.out.println("========onErrorEvent===========");
    }

    @Override
    public void onMessageEvent(String message) {

        // 强烈建议在此处先将消息message写到日志文件，以便日后消息排查！！！
        System.out.println("onMessageEvent：" + message);

        SyncTradeResponse syncTradeResponse = JSON.parseObject(message, SyncTradeResponse.class);
        if (syncTradeResponse.getCode() == 0) {

            if (syncTradeResponse.getTopic().equals("tb_push_wait_seller_send_trade")) {
                // 这里处理待发货订单的业务逻辑
                SyncTrade  syncTrade = JSON.parseObject(syncTradeResponse.getData(), SyncTrade.class);
                System.out.println("收到待发订单，sellerNick:"+ syncTrade.getSellerNick() +",订单号:" +  syncTrade.getTid() );

            } else if (syncTradeResponse.getTopic().equals("tb_push_success_trade")) {
                // 这里处理买家确认收货的订单
                SyncSuccessTrade  syncSuccessTrade = JSON.parseObject(syncTradeResponse.getData(), SyncSuccessTrade.class);
                System.out.println("收到买家确认收货订单，sellerNick:"+ syncSuccessTrade.getSellerNick() +",订单号:" +  syncSuccessTrade.getTid());
            } else if (syncTradeResponse.getTopic().equals("tb_tradememo_modified_with_trade")){
                ChangeMemoWithTrade changeMemoWithTrade = JSON.parseObject(syncTradeResponse.getData(), ChangeMemoWithTrade.class);
                String memoJson =  changeMemoWithTrade.getTradeMemo();
                TradeInfo tradeInfo =  changeMemoWithTrade.getTradeInfo();
                TradeMemo tradeMemo =   JSON.parseObject(memoJson, TradeMemo.class);
                System.out.println("订单插旗修改消息：tid:"+ tradeInfo.getTid() +  " , 旗帜:" +  tradeMemo.getSeller_flag() + ", 卖家备注：" +  tradeMemo.getSeller_memo());

            } else if (syncTradeResponse.getTopic().equals("sf_push_cloud_print_waybills")){
                sfPushCloudPrintWaybills(syncTradeResponse);
            }

            //  如果是需要确认的消息，则确认此消息，否则服务端消息将会重发。
            if ( syncTradeResponse.getUuid() != null && !syncTradeResponse.getUuid().equals("")){
                send("{\"cmd\":\"ack_sync_data\", \"seq\":\"" + syncTradeResponse.getUuid() +   "\"}");
            }

        } else {
            System.out.println("服务端返回业务错误："+ syncTradeResponse.getMsg());
        }

        System.out.println("========onMessageEvent=========== \n " + message + "\n" );
    }


    /**
     * 将电子面单写入到文件，文件格式为Pdf
     * @param response
     */
    private void sfPushCloudPrintWaybills(SyncTradeResponse response )  {

        try {

            Base64.Decoder decoder = Base64.getDecoder();
            SFCloudPrintWaybill sfCloudPrintWaybill = JSON.parseObject(response.getData(), SFCloudPrintWaybill.class);
            // 在下面单请求传入custom_data时，会原样返回过来
            System.out.println("customData:" + sfCloudPrintWaybill.getCustomData());
            System.out.println("运单号:" + sfCloudPrintWaybill.getWaybillNo());
            String fileName =  sfCloudPrintWaybill.getFileName();
            File file = new File(fileName);
            OutputStream os = new FileOutputStream(file,true);
            byte[] data = decoder.decode(sfCloudPrintWaybill.getContent());
            os.write(data, 0, data.length);
            os.flush();
            os.close();
            System.out.println("生成文件成功");

        }catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
