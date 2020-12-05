package de.hska.vsmlab.product;

import de.hska.vsmlab.product.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public interface IProductController {


    @RequestMapping(value= "/products", method = RequestMethod.GET)
    @ResponseBody
    List<Product> getAllProducts();

    @RequestMapping(value= "/product/find", method = RequestMethod.GET)
    @ResponseBody
    List<Product> findProduct(@PathVariable String description, double minPrice, double maxPrice);

    @RequestMapping(value= "/product/{productId}", method = RequestMethod.GET)
    @ResponseBody
    Product getProduct(@PathVariable Long productId);

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.DELETE)
    boolean deleteProduct(@PathVariable long productId);
}
