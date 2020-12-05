package de.hska.vsmlab.category;

import de.hska.vsmlab.category.model.Category;
import de.hska.vsmlab.category.model.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Category> getCategory(final Long categoryId) {
        final Optional<Category> category = categoryRepo.findById(categoryId);
        if (category.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category.get(), HttpStatus.OK);
    }

    // add new category
    @Override
    public ResponseEntity<String> addCategory(String categoryName) {

        final Iterable<Category> categories = categoryRepo.findAll();

        // validate name
        // Cannot add category with already existing name
        for (Category category : categories) {
            if (category.getName().equals(categoryName)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        final Category newCategory = new Category(categoryName);
        categoryRepo.save(newCategory);
        return new ResponseEntity<>("Category successfully added", HttpStatus.BAD_REQUEST);
    }

    // get all categories
    @Override
    public ResponseEntity<List<Category>> getAllCategories() {
        final Iterable<Category> categories = categoryRepo.findAll();
        ArrayList<Category> categoriesArrayList = new ArrayList<>();
        categories.forEach(categoriesArrayList::add);
        return new ResponseEntity<>(categoriesArrayList, HttpStatus.OK);
    }

    // delete category
    @Override
    public ResponseEntity<String> deleteCategory(final long categoryId) {
        final Optional<Category> category = categoryRepo.findById(categoryId);
        if (category.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        categoryRepo.deleteById(categoryId);
        return new ResponseEntity<>(categoryId + " successfully deleted.", HttpStatus.OK);
    }

}
