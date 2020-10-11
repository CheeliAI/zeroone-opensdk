package com.cheeli.datasync;

import com.alibaba.fastjson.JSON;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

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
            }
        }

        System.out.println("========onMessageEvent=========== \n " + message + "\n" );
    }

}
