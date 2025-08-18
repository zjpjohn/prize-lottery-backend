package com.prize.lottery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayBootStarter {

    public static void main(String[] args) {
        SpringApplication.run(GatewayBootStarter.class, args);
    }

}
