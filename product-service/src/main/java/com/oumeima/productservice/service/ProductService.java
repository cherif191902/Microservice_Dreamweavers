package com.oumeima.productservice.service;

import com.oumeima.productservice.exception.ProductNotFoundException;
import com.oumeima.productservice.file.FileStorageService;
import com.oumeima.productservice.mapper.ProductMapper;
import com.oumeima.productservice.model.Category;
import com.oumeima.productservice.model.Product;
import com.oumeima.productservice.repository.CategoryRepository;
import com.oumeima.productservice.repository.ProductRepository;
import com.oumeima.productservice.request.ProductPurchaseRequest;
import com.oumeima.productservice.request.ProductRequest;
import com.oumeima.productservice.response.ProductPurchaseResponse;
import com.oumeima.productservice.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final FileStorageService fileStorageService;
    private final CategoryRepository categoryRepository;
    public String createProduct(ProductRequest request) {
        var product = this.repository.save(mapper.toProduct(request));
        return product.getId();

    }

    public ProductResponse findById(String productId) {
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID:: " + productId));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Transactional()
    public List<ProductPurchaseResponse> purchaseProducts(
            List<ProductPurchaseRequest> request
    ) {
        var productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts = repository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new RuntimeException("One or more products does not exist");
        }
        var sortedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = sortedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new RuntimeException("Insufficient stock quantity for product with ID:: " + productRequest.productId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toproductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }

    public void uploadBookCoverPicture(MultipartFile file, String productId) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new RuntimeException("No product found with ID:: " + productId));

        var profilePicture = fileStorageService.saveFile(file, productId);
        product.setProductCover(profilePicture);
        repository.save(product);
    }

    public void deleteProduct(String productId) {
        repository.deleteById(productId);
    }

    public void updateProduct(String productId, ProductRequest request) {
        Product existingProduct = repository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingProduct.setName(request.name());
        existingProduct.setDescription(request.description());
        existingProduct.setAvailableQuantity(request.availableQuantity());
        existingProduct.setPrice(request.price());
        existingProduct.setCategory(category);

       repository.save(existingProduct);
    }
}
