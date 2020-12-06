package de.hska.vsmlab;

import de.hska.vsmlab.category.CategoryServiceApplication;
import de.hska.vsmlab.category.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = CategoryServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryServiceApplicationTests {

    @Autowired
    private TestRestTemplate client;

    @LocalServerPort
    private int port;

    private final String newCategoryName = "Shoes";

    @BeforeEach
    public void setUp() {
        client.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:" + port + "/category"));
    }


    @Test
    public void getCategoryByIdTest() {
        final ResponseEntity<Category> response = client.getForEntity("/"+1, Category.class);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Food",response.getBody().getName());
    }

    @Test
    public void getAllCategoriesTest(){
        final ResponseEntity<List> response = client.getForEntity("/", List.class);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void getCategoryByNameTest(){
        final ResponseEntity<Category> response = client.getForEntity("?categoryName=Food", Category.class);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    public void addCategoryTest() {
        final ResponseEntity<Category> response = client.postForEntity("", newCategoryName, Category.class);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(newCategoryName, Objects.requireNonNull(response.getBody()).getName());
    }

}
