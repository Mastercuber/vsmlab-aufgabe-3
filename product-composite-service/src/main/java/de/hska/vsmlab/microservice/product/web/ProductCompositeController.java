package de.hska.vsmlab.microservice.product.web;

import com.netflix.discovery.converters.Auto;
import de.hska.vsmlab.microservice.product.perstistence.model.Category;
import de.hska.vsmlab.microservice.product.perstistence.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ProductCompositeController implements IProductCompositeController {

    @Autowired
    IProductController productCoreService;

    @Autowired
    ICategoryController categoryService;

    @Override
    public Product addProduct(String productName, double price, Category category, String details) {

        // check if product already exists

        // validate name, price and category
        // Cannot add item with the same name, price and category.
//        for (Product product : products) {
//            if (product.getName().equals(productName) && product.getPrice() == price && product.getCategory() == category) {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        }

        productCoreService.


        final Product newProduct = new Product(productName, price, category, details);
        productRepo.save(newProduct);
        return new ResponseEntity<>("Product successfully added", HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<Product> findProduct(String searchDescription, double minPrice, double maxPrice) {
        return null;
    }
}
