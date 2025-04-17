package com.oumeima.productservice.repository;

import com.oumeima.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findAllByIdInOrderById(List<String> ids);
}
