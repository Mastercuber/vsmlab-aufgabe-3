package de.hska.vsmlab.microservice.product.web;

import de.hska.vsmlab.microservice.product.perstistence.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("product-service")
public interface IProductController {

//    Move this to composite service
//    @RequestMapping(value = "/product", method = RequestMethod.POST)
//    ResponseEntity<String> addProduct(String productName, double price, Category category, String details);

    @RequestMapping(value= "/products", method = RequestMethod.GET)
    ResponseEntity<List<Product>> getAllProducts();

    @RequestMapping(value= "/product/find", method = RequestMethod.GET)
    ResponseEntity<List<Product>> findProduct(@PathVariable String description, double minPrice, double maxPrice);

    @RequestMapping(value= "/product/{productId}", method = RequestMethod.GET)
    ResponseEntity<Product> getProduct(@PathVariable Long productId);

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.DELETE)
    ResponseEntity<String> deleteProduct(@PathVariable long productId);
}
