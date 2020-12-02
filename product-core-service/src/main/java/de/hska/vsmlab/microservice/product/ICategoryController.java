package de.hska.vsmlab.microservice.product;

import de.hska.vsmlab.microservice.product.model.Category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface ICategoryController {
    @RequestMapping(value= "/category/{categoryId}", method = RequestMethod.GET)
    ResponseEntity<Category> getCategory(@PathVariable Long categoryId);
}
