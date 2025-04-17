package com.oumeima.productservice.mapper;

import com.oumeima.productservice.file.FileUtils;
import com.oumeima.productservice.model.Category;
import com.oumeima.productservice.model.Product;
import com.oumeima.productservice.repository.CategoryRepository;
import com.oumeima.productservice.request.ProductRequest;
import com.oumeima.productservice.response.ProductPurchaseResponse;
import com.oumeima.productservice.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ProductMapper {
    private final CategoryRepository categoryRepository;
    public Product toProduct(ProductRequest request) {
        Category fullCategory = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return Product.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .availableQuantity(request.availableQuantity())
                .price(request.price())
                .category(fullCategory)
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),

                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),

                product.getCategory().getDescription(),
                FileUtils.readFileFromLocation(product.getProductCover())
        );
    }

    public ProductPurchaseResponse toproductPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
