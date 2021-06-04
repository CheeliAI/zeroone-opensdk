package com.cheeli.datasync;
import com.cheeli.Config;
import com.cheeli.utils.Utils;
import java.net.URI;


public class WSSync {

    private static String serverUrl = "wss://open.fw199.com/acc";
    private EasyWSClient   webSocketClient = null;
    public void start() {
        try {

              String token = Utils.MD5(Config.AppSecret + Config.AppId + Config.AppSecret);
              String finallyUrl = serverUrl +"?appid=" + Config.AppId + "&token=" + token +"&version=v1.1" +"&clientid=lpclient002";
              webSocketClient = new EasyWSClient(new URI(finallyUrl));
              webSocketClient.connect();

        } catch (Exception ex) {
           ex.printStackTrace();
        }

    }

}
