package de.hska.vsmlab.microservice.product.web;


import de.hska.vsmlab.microservice.product.perstistence.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    Product addProduct(@RequestParam String name, @RequestParam double price, @RequestParam long categoryId, @RequestParam String details);

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    @ResponseBody
    List<Product> findProductByDescription(@RequestParam String description);

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    @ResponseBody
    List<Product> findProductByPrice(@RequestParam double minPrice, @RequestParam double maxPrice);

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    @ResponseBody
    List<Product> findProductByDescAndPrice(@RequestParam String description, @RequestParam double minPrice, @RequestParam double maxPrice);
}
