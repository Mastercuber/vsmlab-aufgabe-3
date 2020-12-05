package de.hska.vsmlab.microservice.product.web;


import de.hska.vsmlab.microservice.product.perstistence.model.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("category-service")
public interface ICategoryController {
    @RequestMapping(value= "/category/{categoryId}", method = RequestMethod.GET)
    ResponseEntity<Category> getCategory(@PathVariable Long categoryId);

    // add new category
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    ResponseEntity<String> addCategory(String categoryName);

    // get all categories
    @RequestMapping(value= "/categories", method = RequestMethod.GET)
    ResponseEntity<List<Category>> getAllCategories();

    // delete category
    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.DELETE)
    ResponseEntity<String> deleteCategory(@PathVariable long categoryId);

}
