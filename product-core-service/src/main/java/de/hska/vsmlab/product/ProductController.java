package de.hska.vsmlab.product;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import de.hska.vsmlab.product.model.Product;
import de.hska.vsmlab.product.model.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ProductController implements IProductController {

    private Map<Long, Product> productCache = new LinkedHashMap<>();

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    @Lazy
    private ProductRepo productRepo;


    @Override
    @HystrixCommand(fallbackMethod = "getAllProductsCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    public List<Product> getAllProducts() {
        final Iterable<Product> products = productRepo.findAll();
        ArrayList<Product> productsArrayList = new ArrayList<>();
        products.forEach(productsArrayList::add);
        //save to cache
        for(Product prod:productsArrayList) {
            productCache.putIfAbsent(prod.getId(), prod);
        }
        return productsArrayList;
    }

    public List<Product> getAllProductsCache() {
        return new ArrayList<>(productCache.values());
    }


    @Override
    @HystrixCommand(fallbackMethod = "getProductCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    public Product getProduct(final Long productId) {
        final Optional<Product> productOpt = productRepo.findById(productId);
        if (productOpt.isEmpty()) {
            return null;
        }
        Product product = productOpt.get();
        productCache.putIfAbsent(productId, product);

        return product;
    }

    public Product getProductCache(final Long productId) {
        return productCache.getOrDefault(productId, new Product());
    }


    @Override
    public boolean deleteProduct(final long productId) {
        final Optional<Product> product = productRepo.findById(productId);
        if (product.isEmpty()) {
            return false;
        }
        productRepo.deleteById(productId);
        return true;
    }

    @Override
    public Product addProduct(final Product productToAdd){
        // check if product already exist
        final Iterable<Product> products = productRepo.findAll();
        for (Product product : products) {
            // check if there is already a product with the same name, price and category, otherwise add product
            if ((product.getName().equals(productToAdd.getName()) && product.getPrice() == productToAdd.getPrice() && product.getCategoryId() == productToAdd.getCategoryId())) {
                return null;
            }
        }
        productRepo.save(productToAdd);
        return productToAdd;
    }

    @Override
    @HystrixCommand(fallbackMethod = "findProductByPriceCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    public List<Product> findProductByPrice(double minPrice, double maxPrice) {
        final Iterable<Product> products = productRepo.findAll();
        ArrayList<Product> searchResults = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice() <= maxPrice && product.getPrice() >= minPrice) {
                searchResults.add(product);
            }
        }
        return searchResults;
    }

    public List<Product> findProductByPriceCache(double minPrice, double maxPrice) {
        return productCache.values().stream().filter((Product p) -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice).collect(Collectors.toList());

    }

    @Override
    @HystrixCommand(fallbackMethod = "findProductByDescAndPriceCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    public List<Product> findProductByDescAndPrice(String description, double minPrice, double maxPrice) {
        final Iterable<Product> products = productRepo.findAll();
        ArrayList<Product> searchResults = new ArrayList<>();
        for (Product product : products) {
            if (this.matchesDescriptionOrPrice(product, description, minPrice, maxPrice)) {
                searchResults.add(product);
            }
        }
        return searchResults;
    }

    @Override
    @HystrixCommand(fallbackMethod = "getAllProductsByCategoryIdCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    public List<Product> getAllProductsByCategoryId(long categoryId) {
        final Iterable<Product> products = productRepo.findAll();
        ArrayList<Product> results = new ArrayList<>();
        for(Product product: products){
            if(product.getCategoryId() == categoryId){
                results.add(product);
            }
        }
        return results;
    }

    public List<Product> getAllProductsByCategoryIdCache(long categoryId) {
        ArrayList<Product> resultsFromCache = new ArrayList<>();
        for(Product product: productCache.values()){
            if(product.getCategoryId() == categoryId){
                resultsFromCache.add(product);
            }
        }
        return resultsFromCache;
    }

    public List<Product> findProductByDescAndPriceCache(String desc, double minPrice, double maxPrice) {

        return productCache.values().stream().filter((Product p) -> this.matchesDescriptionOrPrice(p, desc, minPrice, maxPrice)).collect(Collectors.toList());
    }

    private boolean matchesDescriptionOrPrice(Product product, String description, double minPrice, double maxPrice) {
        final String searchWord = description.toLowerCase();
        return product.getPrice() >= minPrice && product.getPrice() <= maxPrice && (product.getName().matches(searchWord) || product.getDetails().matches(searchWord));
    }
}

