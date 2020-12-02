package de.hska.vsmlab.microservice.product.model;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepo extends CrudRepository<Product, Long> { }
