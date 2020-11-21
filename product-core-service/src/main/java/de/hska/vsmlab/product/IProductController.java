package de.hska.vsmlab.product;

import de.hska.vsmlab.product.model.Category;
import de.hska.vsmlab.product.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface IProductController {

    @RequestMapping(value= "/product/{productId}", method = RequestMethod.GET)
    ResponseEntity<Product> getProduct(@PathVariable Long productId);

    @RequestMapping(value= "/products", method = RequestMethod.GET)
    ResponseEntity<List<Product>> getAllProducts();

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.POST)
    // hier auch mit PathVariable??
    ResponseEntity<String> addProduct(String productName, double price, Category category, String details);

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.DELETE)
    ResponseEntity<String> deleteProduct(@PathVariable long productId);
}
