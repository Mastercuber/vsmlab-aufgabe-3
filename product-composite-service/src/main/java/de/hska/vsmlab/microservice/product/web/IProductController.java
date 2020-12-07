package de.hska.vsmlab.microservice.product.web;


import de.hska.vsmlab.microservice.product.perstistence.model.Product;
import de.hska.vsmlab.microservice.product.perstistence.model.ProductAlreadyExistsException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("product-service")
public interface IProductController {

    @RequestMapping(value= "/product", method = RequestMethod.GET)
    @ResponseBody
    List<Product> getAllProducts();

    @RequestMapping(value= "/product/{productId}", method = RequestMethod.GET)
    @ResponseBody
    Product getProduct(@PathVariable Long productId);

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.DELETE)
    boolean deleteProduct(@PathVariable long productId);

    @RequestMapping(value= "/product", method = RequestMethod.POST)
    Product addProduct(@RequestBody Product productToAdd) throws ProductAlreadyExistsException;

    @RequestMapping(value = "/product", method = RequestMethod.GET, params = {"minPrice", "maxPrice"})
    @ResponseBody
    List<Product> findProductByPrice(@RequestParam double minPrice, @RequestParam double maxPrice);

    @RequestMapping(value = "/product", method = RequestMethod.GET, params = {"description", "minPrice", "maxPrice"})
    @ResponseBody
    List<Product> findProductByDescAndPrice(@RequestParam String description, @RequestParam double minPrice, @RequestParam double maxPrice);
}
