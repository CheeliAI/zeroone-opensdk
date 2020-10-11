package com.cheeli.autosend.tradeserver;

import com.cheeli.datasync.WSSync;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TradeServerApplication {

    public static void main(String[] args) {

        WSSync wsSync = new WSSync();
        wsSync.start();
        SpringApplication.run(TradeServerApplication.class, args);


    }

}
