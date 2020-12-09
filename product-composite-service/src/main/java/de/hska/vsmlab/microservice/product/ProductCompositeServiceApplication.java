package de.hska.vsmlab.microservice.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@RibbonClient("product-composite-service")
@EnableFeignClients
public class ProductCompositeServiceApplication {

    final Logger logger = LoggerFactory.getLogger(ProductCompositeServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProductCompositeServiceApplication.class, args);
    }

    @PostConstruct
    public void generateTestData() {
//        // Add LV Shoes
//        final Category shoewear = new Category("Shoewear");
//        categoryRepo.save(shoewear);
//        final Product lvShoes = new Product("Louis Vuitton Madeleine Pumps Monogram Canvas", 730, shoewear, "jetzt online verfügbar.");
//        productRepo.save(lvShoes);
//        logger.info("LV_SHOES: " + lvShoes.getId());
//
//        // Add YSL Shoes
//        final Product yslShoes = new Product("Opyum Sandale aus Lackleder mit Goldfarbenem Absatz", 975, shoewear, "jetzt online verfügbar. Durchschnittliche Lieferzeit: 1 - 3 Arbeitstage");
//        productRepo.save(yslShoes);
//        logger.info("YSL_SHOES: " + yslShoes.getId());
    }
}
