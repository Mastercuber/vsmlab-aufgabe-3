package de.hska.vsmlab.product;

import de.hska.vsmlab.product.model.Category;
import de.hska.vsmlab.product.model.CategoryRepo;
import de.hska.vsmlab.product.model.Product;
import de.hska.vsmlab.product.model.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductServiceApplication {

    final Logger logger = LoggerFactory.getLogger(ProductServiceApplication.class);

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private ProductRepo productRepo;

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private CategoryRepo categoryRepo;


    public static void main(String[] args) {
        SpringApplication.run(de.hska.vsmlab.product.ProductServiceApplication.class, args);
    }

    @PostConstruct
    public void generateTestData() {
        // Add LV Shoes
        final Category shoewear = new Category("Shoewear");
        categoryRepo.save(shoewear);
        final Product lvShoes = new Product("Louis Vuitton Madeleine Pumps Monogram Canvas", 730, shoewear, "jetzt online verfügbar.");
        productRepo.save(lvShoes);
        logger.error("LV_SHOES: " + lvShoes.getId());

        // Add YSL Shoes
        final Product yslShoes = new Product("Opyum Sandale aus Lackleder mit Goldfarbenem Absatz", 975, shoewear, "jetzt online verfügbar. Durchschnittliche Lieferzeit: 1 - 3 Arbeitstage");
        productRepo.save(yslShoes);
        logger.error("YSL_SHOES: " + yslShoes.getId());
    }
}
