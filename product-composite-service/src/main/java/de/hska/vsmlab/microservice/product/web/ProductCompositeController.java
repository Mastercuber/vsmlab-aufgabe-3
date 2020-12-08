package de.hska.vsmlab.microservice.product.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import de.hska.vsmlab.microservice.product.perstistence.model.Category;
import de.hska.vsmlab.microservice.product.perstistence.model.CategoryNotExistsException;
import de.hska.vsmlab.microservice.product.perstistence.model.Product;
import de.hska.vsmlab.microservice.product.perstistence.model.ProductAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@Component
public class ProductCompositeController implements IProductCompositeController {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    IProductController productCoreService;

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    ICategoryController categoryService;

    private final Map<String, List<Product>> foundProducts = new LinkedHashMap<>();


    // no fallback method, the request will just fail
    @Override
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")})
    public Product addProduct(Product newProduct) throws ProductAlreadyExistsException, CategoryNotExistsException {

        Category cat = categoryService.getCategoryById(newProduct.getCategoryId());
        if (cat == null) {
            throw new CategoryNotExistsException();
        }
        Product product = productCoreService.addProduct(newProduct);
        if (product == null) {
            throw new ProductAlreadyExistsException();
        }
        return product;
    }


    @Override
    @HystrixCommand(fallbackMethod = "findProductCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")})
    public List<Product> findProduct(final String description, Double minPrice, Double maxPrice) {
        List<Product> products;
        if (minPrice == null || minPrice < 0) {
            minPrice = (double) 0;
        }
        if (maxPrice == null || maxPrice < 0) {
            maxPrice = Double.MAX_VALUE;
        }

        if (description != null && !description.isBlank()) {
            products = productCoreService.findProductByDescAndPrice(description, minPrice, maxPrice);
            // check if the given description matches any category name
            List<Category> categories = categoryService.getAllCategories();
            for (Category category : categories) {
                if (checkWordIgnoreCase(category.getName(), description)) {
                    products.addAll(productCoreService.getAllProductsByCategoryId(category.getId()));
                }
            }
        } else {
            products = productCoreService.findProductByPrice(minPrice, maxPrice);
        }

        foundProducts.put(cacheKeyForParameters(description, minPrice, maxPrice), products);

        return products;
    }

    /**
     * return the cache key for our search function. cache key =" descriptionMinPriceMaxPrice"
     *
     * @param description the description we are searching for
     * @param minPrice    the min price we are searching for
     * @param maxPrice    the max price we are searching for
     * @return the cache key to index into the cache with
     */
    private String cacheKeyForParameters(String description, Double minPrice, Double maxPrice) {
        return description + minPrice.toString() + maxPrice.toString();
    }

    @SuppressWarnings("unused")
    public List<Product> findProductCache(final String searchDescription, final Double minPrice, final Double maxPrice) {
        // we need to find the cached products for this "searchTerm" searchTerm = "descriptionMinPriceMaxPrice"
        final List<Product> cachedProducts = this.foundProducts.get(cacheKeyForParameters(searchDescription, minPrice, maxPrice));
        System.out.println("cached!!!!");
        System.out.println(cachedProducts);
        return cachedProducts;
    }

    private boolean checkWordIgnoreCase(final String s1, final String s2) {
        return s1.toLowerCase().contains(s2.toLowerCase());
    }
}
