package de.hska.vsmlab.microservice.product.web;


import de.hska.vsmlab.microservice.product.perstistence.model.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("category-service")
public interface ICategoryController {

    // get a category by id
    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    Category getCategoryById(@PathVariable long categoryId);

/*    // get one category
    @RequestMapping(value = "/category", method = RequestMethod.GET, params = {"categoryName"})
    @ResponseBody
    Category getCategoryByName(@RequestParam String categoryName);*/

    // add new category
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    @ResponseBody
    Category addCategory(@RequestBody String categoryName);

    // get all categories
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    @ResponseBody
    List<Category> getAllCategories();

    // delete category
    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.DELETE)
    boolean deleteCategory(@PathVariable long categoryId);
}
