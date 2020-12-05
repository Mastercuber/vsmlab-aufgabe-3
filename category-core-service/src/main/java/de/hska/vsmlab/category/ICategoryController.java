package de.hska.vsmlab.category;

import de.hska.vsmlab.category.model.Category;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public interface ICategoryController {
    @RequestMapping(value= "/category/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    Category getCategory(@PathVariable Long categoryId);

    // add new category
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    @ResponseBody
    Category addCategory(String categoryName);

    // get all categories
    @RequestMapping(value= "/categories", method = RequestMethod.GET)
    @ResponseBody
    List<Category> getAllCategories();

    // delete category
    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.DELETE)
    boolean deleteCategory(@PathVariable long categoryId);


}
