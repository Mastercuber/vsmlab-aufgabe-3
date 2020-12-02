package de.hska.vsmlab.microservice.product;

import de.hska.vsmlab.microservice.product.model.Category;
import de.hska.vsmlab.microservice.product.model.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
}
