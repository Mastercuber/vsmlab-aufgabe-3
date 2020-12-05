package de.hska.vsmlab.category;

import de.hska.vsmlab.category.model.Category;
import de.hska.vsmlab.category.model.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController implements ICategoryController {
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    @Lazy
    private CategoryRepo categoryRepo;

    @Override
    public Category getCategoryById(long categoryId) {
        final Optional<Category> category = categoryRepo.findById(categoryId);
        if (category.isEmpty()){
            return null;
        }
        return category.get();
    }

    @Override
    public Category getCategoryByName(String categoryName) {

        final Iterable<Category> categories = categoryRepo.findAll();

        for (Category category : categories) {
            if (category.getName().equals(categoryName)) {
                return category;
            }
        }
        return null;
    }

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
    public List<Category> getAllCategories() {
        final Iterable<Category> categories = categoryRepo.findAll();
        ArrayList<Category> categoriesArrayList = new ArrayList<>();
        categories.forEach(categoriesArrayList::add);
        return categoriesArrayList;
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
