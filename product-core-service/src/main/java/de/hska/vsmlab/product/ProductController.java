package de.hska.vsmlab.product;

import de.hska.vsmlab.product.model.Product;
import de.hska.vsmlab.product.model.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController implements IProductController {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    @Lazy
    private ProductRepo productRepo;

    @Override
    public List<Product> getAllProducts() {
        final Iterable<Product> products = productRepo.findAll();
        ArrayList<Product> productsArrayList = new ArrayList<>();
        products.forEach(productsArrayList::add);
        return productsArrayList;
    }

    @Override
    public Product getProduct(final Long productId) {
        final Optional<Product> product = productRepo.findById(productId);
        if (product.isEmpty()) {
            return null;
        }
        return product.get();
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
    public Product addProduct(String productName, double price, long categoryId, String details) {
        // check if product already exist
        final Iterable<Product> products = productRepo.findAll();
        for (Product product : products) {
            // check if there is already a product with the same name, price and category, otherwise add product
            if (!(product.getName().equals(productName) && product.getPrice() == price && product.getCategoryId() == categoryId)) {
                return new Product(productName, price, categoryId, details);
            }
        }
        return null;
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
        String searchWord = "\\b" + description.toLowerCase() + "\\b";
        for (Product product : products) {
            if (product.getPrice() <= maxPrice && product.getPrice() >= minPrice) {
                if (product.getName().matches(searchWord) || product.getDetails().matches(searchWord)) {
                    searchResults.add(product);
                }
            }
        }
        return searchResults;
    }
}

