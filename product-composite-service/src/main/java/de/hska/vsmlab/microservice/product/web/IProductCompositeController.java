package de.hska.vsmlab.microservice.product.web;

import de.hska.vsmlab.microservice.product.perstistence.model.Category;
import de.hska.vsmlab.microservice.product.perstistence.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IProductCompositeController {

    @RequestMapping(value = "/product/add", method = RequestMethod.POST)
    @ResponseBody
    Product addProduct(String productName, double price, Category category, String details);

    @RequestMapping(value = "/product/find", method = RequestMethod.GET)
    @ResponseBody
    List<Product> findProduct (@RequestParam String description, @RequestParam Double minPrice, @RequestParam Double maxPrice);

}
