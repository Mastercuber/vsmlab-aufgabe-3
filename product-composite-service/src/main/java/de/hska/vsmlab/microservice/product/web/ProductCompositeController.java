package de.hska.vsmlab.microservice.product.web;

import de.hska.vsmlab.microservice.product.perstistence.model.Category;
import de.hska.vsmlab.microservice.product.perstistence.model.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductCompositeController implements IProductCompositeController {

    @Autowired
    IProductController productCoreService;

    @Autowired
    ICategoryController categoryService;

    @Override
    public Product addProduct(String productName, double price, Category category, String details) {
        return productCoreService.addProduct(productName, price, category.getId(), details);
    }

    @Override
    public List<Product> findProduct(String searchDescription, Double minPrice, Double maxPrice) {
        List<Product> products;
        if (minPrice == null || minPrice < 0 ){
            minPrice = Double.valueOf(0);
        }
        if(maxPrice == null || maxPrice < 0 ) {
            maxPrice = Double.valueOf(Double.MAX_VALUE);
        }

        if (searchDescription != null && !searchDescription.isBlank() ) {

            products = productCoreService.findProductByDescAndPrice(searchDescription, minPrice, maxPrice);
        }
        else
        {
            products = productCoreService.findProductByPrice(minPrice, maxPrice);
        }

        return products;
    }
}
