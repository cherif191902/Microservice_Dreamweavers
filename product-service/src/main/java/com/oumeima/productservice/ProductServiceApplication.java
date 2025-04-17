package com.oumeima.productservice;

import com.oumeima.productservice.model.Category;
import com.oumeima.productservice.model.Product;
import com.oumeima.productservice.repository.CategoryRepository;
import com.oumeima.productservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner initDatabase(CategoryRepository categoryRepository, ProductRepository productRepository) {
		return args -> {
			if (categoryRepository.count() == 0) { // Prevent duplicate seeding
				Category electronics = categoryRepository.save(new Category(null, "Electronics", "Devices and gadgets"));
				Category clothing = categoryRepository.save(new Category(null, "Clothing", "Fashion and apparel"));

				productRepository.saveAll(List.of(
						new Product(null, "Laptop", "Gaming laptop", 10, new BigDecimal("1500.00"), electronics,null),
						new Product(null, "Smartphone", "Latest Android phone", 20, new BigDecimal("800.00"), electronics,null),
						new Product(null, "T-Shirt", "Cotton T-shirt", 50, new BigDecimal("20.00"), clothing,null)
				));
			}
		};
	}
}
