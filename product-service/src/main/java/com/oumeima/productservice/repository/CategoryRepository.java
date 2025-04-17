package com.oumeima.productservice.repository;

import com.oumeima.productservice.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
