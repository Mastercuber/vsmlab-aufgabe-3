package de.hska.vsmlab.microservice.composite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@RibbonClient("login-system-composite-service")
@EnableResourceServer
public class LoginSystemCompositeServiceApplication {

    final Logger logger = LoggerFactory.getLogger(LoginSystemCompositeServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LoginSystemCompositeServiceApplication.class, args);
    }
}
