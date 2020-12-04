package de.hska.vsmlab.product;

import de.hska.vsmlab.product.model.Product;
import de.hska.vsmlab.product.model.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController implements IProductController{

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    @Lazy
    private ProductRepo productRepo;

//    Move this to composite service
//    @Override
//    public ResponseEntity<String> addProduct(String productName, double price, Category category, String details) {
//
//        final Iterable<Product> products = productRepo.findAll();
//
//        // validate name, price and category
//        // Cannot add item with the same name, price and category.
//        for (Product product : products) {
//            if (product.getName().equals(productName) && product.getPrice() == price && product.getCategory() == category) {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        }
//
//        final Product newProduct = new Product(productName, price, category, details);
//        productRepo.save(newProduct);
//        return new ResponseEntity<>("Product successfully added", HttpStatus.BAD_REQUEST);
//    }

    @Override
    public ResponseEntity<List<Product>> getAllProducts() {
        final Iterable<Product> products = productRepo.findAll();
        ArrayList<Product> productsArrayList = new ArrayList<>();
        products.forEach(productsArrayList::add);
        return new ResponseEntity<>(productsArrayList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> findProduct(String description, double minPrice, double maxPrice) {
        final Iterable<Product> products = productRepo.findAll();
        ArrayList<Product> searchResults = new ArrayList<>();
        String searchWord = "\\b" + description.toLowerCase() + "\\b";
        for (Product product : products) {
            if (product.getPrice() <= maxPrice && product.getPrice() >= minPrice){
                if(product.getName().matches(searchWord) || product.getCategory().getName().matches(searchWord) || product.getDetails().matches(searchWord)){
                    searchResults.add(product);
                }
            }
        }
        return new ResponseEntity<List<Product>>(searchResults, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> getProduct(final Long productId) {
        final Optional<Product> product = productRepo.findById(productId);
        if (product.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteProduct(final long productId) {
        final Optional<Product> product = productRepo.findById(productId);
        if (product.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productRepo.deleteById(productId);
        return new ResponseEntity<>(productId + " successfully deleted.", HttpStatus.OK);
    }
}

