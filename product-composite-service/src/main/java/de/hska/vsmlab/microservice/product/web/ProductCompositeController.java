package de.hska.vsmlab.microservice.product.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import de.hska.vsmlab.microservice.product.perstistence.model.Category;
import de.hska.vsmlab.microservice.product.perstistence.model.CategoryDoesNotExistsException;
import de.hska.vsmlab.microservice.product.perstistence.model.Product;
import de.hska.vsmlab.microservice.product.perstistence.model.ProductAlreadyExistsException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Component
public class ProductCompositeController implements IProductCompositeController {

    @Autowired
    IProductController productCoreService;

    @Autowired
    ICategoryController categoryService;

    private final Map<Long, Product> productCache = new LinkedHashMap<>();

    // no fallback method, the request will just fail
/*    @Override
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")})*/
    public Product addProduct(Product newProduct) throws ProductAlreadyExistsException, CategoryDoesNotExistsException {

        Category cat = categoryService.getCategoryById(newProduct.getCategoryId());
        if (cat == null) {
            throw new CategoryDoesNotExistsException();
        }
        Product product= productCoreService.addProduct(newProduct);
        if (product == null) {
            throw new ProductAlreadyExistsException();
        }
        return product;
    }


    @Override
    @HystrixCommand(fallbackMethod = "findProductCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")})
    public List<Product> findProduct(String searchDescription, Double minPrice, Double maxPrice) {
        List<Product> products;
        if (minPrice == null || minPrice < 0 ){
            minPrice = (double) 0;
        }
        if(maxPrice == null || maxPrice < 0 ) {
            maxPrice = Double.MAX_VALUE;
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

    public List<Product> findProductCache(String searchDescription, Double minPrice, Double maxPrice){
        return productCache.values().stream().filter((Product p) -> this.matchesDescriptionOrPrice(p, searchDescription, minPrice, maxPrice)).collect(Collectors.toList());
    }

    private boolean matchesDescriptionOrPrice(Product product, String description, double minPrice, double maxPrice) {
        String searchWord = "\\b" + description.toLowerCase() + "\\b";
        return product.getPrice() >= minPrice && product.getPrice() <= maxPrice && (product.getName().matches(searchWord) || product.getDetails().matches(searchWord));
    }
}
