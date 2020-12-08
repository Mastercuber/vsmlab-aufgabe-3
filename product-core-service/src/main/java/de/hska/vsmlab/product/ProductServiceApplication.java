package de.hska.vsmlab.product;

import de.hska.vsmlab.product.model.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class ProductServiceApplication {

    final Logger logger = LoggerFactory.getLogger(ProductServiceApplication.class);

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private ProductRepo productRepo;


    public static void main(String[] args) {
        SpringApplication.run(de.hska.vsmlab.product.ProductServiceApplication.class, args);
    }

}
