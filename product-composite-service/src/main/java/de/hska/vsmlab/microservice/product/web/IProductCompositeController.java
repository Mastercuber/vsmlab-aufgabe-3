package de.hska.vsmlab.microservice.product.web;

import de.hska.vsmlab.microservice.product.perstistence.model.CategoryDoesNotExistsException;
import de.hska.vsmlab.microservice.product.perstistence.model.Product;
import de.hska.vsmlab.microservice.product.perstistence.model.ProductAlreadyExistsException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IProductCompositeController {

    @RequestMapping(value = "/product/add", method = RequestMethod.POST)
    @ResponseBody
    Product addProduct(@RequestBody Product body) throws ProductAlreadyExistsException, CategoryDoesNotExistsException;

    @RequestMapping(value = "/product/find", method = RequestMethod.GET)
    @ResponseBody
    List<Product> findProduct (@RequestParam(required = false) String description, @RequestParam(required = false) Double minPrice, @RequestParam(required = false) Double maxPrice);

}
