
package de.hska.vsmlab.microservice.test;

import de.hska.vsmlab.microservice.product.perstistence.model.CategoryNotExistsException;
import de.hska.vsmlab.microservice.product.perstistence.model.Product;
import de.hska.vsmlab.microservice.product.web.IProductCompositeController;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = IProductCompositeController.class)
class ProductCompositeServiceTests {

    private static final RestTemplate compositeClient = new RestTemplate();
    private static Product ankleBoots;

    @BeforeAll
    public static void setup() {
        compositeClient.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8763/product"));
        ankleBoots = new Product("Louis Vuitton Ankle Boots Test", 995.0d, 1,"Der LV Beaubourg Ankle Boot in glänzendem Monogram Canvas besitzt einen modernen Blockabsatz und vereint maskuline sowie feminine Attribute.");
    }

    @BeforeEach
    public void setupTestData() {
    }

    @AfterAll
    public static void teardown() {
        RestTemplate productClient = new RestTemplate();
        productClient.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8091/product"));
        productClient.delete("/" + ankleBoots.getId());

    }

    @Test
    void addProductTest() {
        ResponseEntity<Product> response = compositeClient.postForEntity("/add", ankleBoots, Product.class);
        assertEquals(200, response.getStatusCodeValue());
        ankleBoots = response.getBody();
    }

/*    @Test
    void addProductFailsIfCategoryDoesntExist() {
        ankleBoots.setCategoryId(100000L);
        String assertExceptionStr = "The category doesn't exists.";
        Exception ex = Assertions.assertThrows(HttpServerErrorException.class, () -> compositeClient.postForEntity("/add", ankleBoots, Product.class));
        Assertions.assertTrue(ex.getMessage().contains(assertExceptionStr));
    }*/

}

