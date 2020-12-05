package de.hska.vsmlab.microservice.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LoginSystemCompositeServiceApplication {

    final Logger logger = LoggerFactory.getLogger(LoginSystemCompositeServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LoginSystemCompositeServiceApplication.class, args);
    }
}
