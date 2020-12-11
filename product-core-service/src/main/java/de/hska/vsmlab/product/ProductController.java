package de.hska.vsmlab.product;

import de.hska.vsmlab.product.model.Product;
import de.hska.vsmlab.product.model.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ProductController implements IProductController {

    private Map<Long, Product> productCache = new LinkedHashMap<>();

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    @Lazy
    private ProductRepo productRepo;


    @Override
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

    @Override
    public Product getProduct(final Long productId) {
        final Optional<Product> productOpt = productRepo.findById(productId);
        if (productOpt.isEmpty()) {
            return null;
        }
        Product product = productOpt.get();
        productCache.putIfAbsent(productId, product);

        return product;
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

    @Override
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

    private boolean matchesDescriptionOrPrice(Product product, String description, double minPrice, double maxPrice) {
        final String searchWord = description.toLowerCase();
        return product.getPrice() >= minPrice && product.getPrice() <= maxPrice && (product.getName().matches(searchWord) || product.getDetails().matches(searchWord));
    }
}

