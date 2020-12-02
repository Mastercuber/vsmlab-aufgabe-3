package de.hska.vsmlab.microservice.edge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
// siehe: https://cloud.spring.io/spring-cloud-netflix/multi/multi__router_and_filter_zuul.html#_enablezuulproxy_vs_enablezuulserver
@EnableZuulProxy
public class EdgeServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdgeServerApplication.class, args);
    }

}
