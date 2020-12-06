package de.hska.vsmlab.microservice.test;

import de.hska.vsmlab.microservice.product.web.IProductCompositeController;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = IProductCompositeController.class)
class UserServiceTests {
    private static RestTemplate client = new RestTemplate();

    @BeforeAll
    public static void setup() {
        client.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8763"));
    }

    @AfterAll
    public static void teardown() { }

    @Test
    void loginUser() {
    }
}
