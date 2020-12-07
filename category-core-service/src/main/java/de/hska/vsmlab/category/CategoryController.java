package de.hska.vsmlab.category;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import de.hska.vsmlab.category.model.Category;
import de.hska.vsmlab.category.model.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.Map.Entry;

@RestController
@Component
public class CategoryController implements ICategoryController {
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    @Lazy
    private CategoryRepo categoryRepo;
    private final Map<Long, Category> categoryCache = new LinkedHashMap<Long,Category>();

    @Override
    @HystrixCommand(fallbackMethod = "getCategoryByIdCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    public Category getCategoryById(long categoryId) {
        final Optional<Category> category = categoryRepo.findById(categoryId);
        if (category.isEmpty()){
            return null;
        }
        Category cat = category.get();
        categoryCache.putIfAbsent(categoryId, cat);
        return cat;
    }

    public Category getCategoryByIdCache(Long categoryId) {
        return categoryCache.getOrDefault(categoryId, new Category());
    }

/*    @Override
    @HystrixCommand(fallbackMethod = "getCategoryByNameCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")})
    public Category getCategoryByName(String categoryName) {

        final Iterable<Category> categories = categoryRepo.findAll();

        for (Category category : categories) {
            if (category.getName().equals(categoryName)) {
                categoryCache.putIfAbsent(category.getId(), category);
                return category;
            }
        }
        return null;
    }*/

    // add new category
    @Override
    public Category addCategory(String categoryName) {

        final Iterable<Category> categories = categoryRepo.findAll();

        // validate name
        // Cannot add category with already existing name
        for (Category category : categories) {
            if (category.getName().equals(categoryName)) {
                return null;
            }
        }

        final Category newCategory = new Category(categoryName);
        categoryRepo.save(newCategory);
        return newCategory;
    }

    // get all categories
    @Override
    @HystrixCommand(fallbackMethod = "getAllCategoriesCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")})
    public List<Category> getAllCategories() {
        final Iterable<Category> categories = categoryRepo.findAll();
        ArrayList<Category> categoriesArrayList = new ArrayList<>();
        categories.forEach(categoriesArrayList::add);
        //save to cache
        for(Category cat:categoriesArrayList) {
            categoryCache.putIfAbsent(cat.getId(), cat);
        }
        return categoriesArrayList;
    }

    public List<Category> getAllCategoriesCache() {
        return new ArrayList<>(categoryCache.values());
    }

    // delete category
    @Override
    public boolean deleteCategory(final long categoryId) {
        final Optional<Category> category = categoryRepo.findById(categoryId);
        if (category.isEmpty()){
            return false;
        }
        categoryRepo.deleteById(categoryId);
        return true;
    }

}
