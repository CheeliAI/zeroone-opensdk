package com.cheeli.datasync;

import com.cheeli.Config;
import com.cheeli.utils.Utils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class WSSync {


//    private static String serverUrl = "ws://127.0.0.1:9095/acc";
    private static String serverUrl = "wss://open.fw199.com/acc";
    private EasyWSClient   webSocketClient = null;
    public void start() {

        try {

              String token = Utils.MD5(Config.AppSecret + Config.AppId + Config.AppSecret);
              String finallyUrl = serverUrl +"?appid=" + Config.AppId + "&token=" + token;
              webSocketClient = new EasyWSClient(new URI(finallyUrl));
              webSocketClient.connect();

        } catch (Exception ex) {
           ex.printStackTrace();
        }

    }


}
