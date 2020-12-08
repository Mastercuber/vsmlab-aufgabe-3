package de.hska.vsmlab;

import de.hska.vsmlab.product.ProductServiceApplication;
import de.hska.vsmlab.product.model.Product;
import de.hska.vsmlab.product.model.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest( classes = ProductServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate client;

	@Autowired
	private ProductRepo productRepo;

	private final Product existingProduct = new Product("Milka", 1.0, 1, "schokolade");

	@BeforeEach
	private void setup() {
		client.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:" + port + "/product"));
		productRepo.deleteAll();

		productRepo.save(existingProduct);
	}

	private static void assertProductsEqual(final Product expectedProduct, final Product actualProduct) {
		assertEquals(expectedProduct.getId(), actualProduct.getId());
		assertEquals(expectedProduct.getCategoryId(), actualProduct.getCategoryId());
		assertEquals(expectedProduct.getDetails(), actualProduct.getDetails());
		assertEquals(expectedProduct.getName(), actualProduct.getName());
		assertEquals(expectedProduct.getPrice(), actualProduct.getPrice());
	}

	@Test
	void canFindProductByDescription() {
		final ResponseEntity<Product[]> response = client.getForEntity("?description=" + existingProduct.getDetails(), Product[].class);

		final Product[] matchingProducts = response.getBody();

		assert matchingProducts != null;
		assertEquals(1, matchingProducts.length);
		final Product matchingProduct = matchingProducts[0];

		assertProductsEqual(existingProduct, matchingProduct);
	}

}
