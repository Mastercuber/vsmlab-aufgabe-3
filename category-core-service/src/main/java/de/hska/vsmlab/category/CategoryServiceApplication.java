package de.hska.vsmlab.category;

import de.hska.vsmlab.category.model.Category;
import de.hska.vsmlab.category.model.CategoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
public class CategoryServiceApplication {

    final Logger logger = LoggerFactory.getLogger(CategoryServiceApplication.class);

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private CategoryRepo categoryRepo;


    public static void main(String[] args) {
        SpringApplication.run(de.hska.vsmlab.category.CategoryServiceApplication.class, args);
    }

    @PostConstruct
    public void generateTestData() {
        Category foodCategory = new Category();
        foodCategory.setName("Food");

        categoryRepo.save(foodCategory);

        logger.info("CATEGORY_ID: " + foodCategory.getId());
    }
}
