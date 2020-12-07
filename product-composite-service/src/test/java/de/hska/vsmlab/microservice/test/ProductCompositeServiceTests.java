//package de.hska.vsmlab.microservice.test;
//
//import de.hska.vsmlab.microservice.product.perstistence.model.Product;
//import de.hska.vsmlab.microservice.product.web.IProductCompositeController;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.DefaultUriBuilderFactory;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//@SpringBootTest(classes = IProductCompositeController.class)
//class ProductCompositeServiceTests {
//
//    private static final RestTemplate compositeClient = new RestTemplate();
//    private Product ankleBoots;
//
//    @BeforeAll
//    public static void setup() {
//        compositeClient.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8763/product"));
//    }
//
//    @BeforeEach
//    public void setupTestData() {
//        ankleBoots = new Product("Louis Vuitton Ankle Boots", 995.0d, 1,"Der LV Beaubourg Ankle Boot in glänzendem Monogram Canvas besitzt einen modernen Blockabsatz und vereint maskuline sowie feminine Attribute.");
//    }
//
//    @AfterAll
//    public static void teardown() { }
//
//    @Test
//    void addProductTest() {
//        ResponseEntity<Product> response = compositeClient.postForEntity("/add", ankleBoots, Product.class);
//        assertEquals(200, response.getStatusCodeValue());
//    }
//
//    @Test
//    void addProductFailsIfCategoryDoesntExist() {
//        ankleBoots.setCategoryId(100000L);
//        ResponseEntity<Product> response = compositeClient.postForEntity("/add", ankleBoots, Product.class);
//        assertEquals(400, response.getStatusCodeValue());
//    }
//
//}
