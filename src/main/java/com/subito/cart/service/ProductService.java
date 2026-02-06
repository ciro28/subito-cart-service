package com.subito.cart.service;

import com.subito.cart.dto.product.CreateProductRequest;
import com.subito.cart.dto.product.ProductResponse;
import com.subito.cart.entity.ProductEntity;
import com.subito.cart.exception.NotFoundException;
import com.subito.cart.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(CreateProductRequest request) {
        ProductEntity product = productRepository.findByNameAndDeletedIsFalse(request.name());
        if(product != null) {
            throw new IllegalArgumentException("Product Name just exists");
        }
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(request.name());
        productEntity.setPrice(request.price());
        productEntity.setVat(request.vat());

        ProductEntity savedProductEntity = productRepository.save(productEntity);

        return new ProductResponse(savedProductEntity.getId(), savedProductEntity.getName(), savedProductEntity.getPrice(), savedProductEntity.getVat());
    }

    public ProductResponse getProductById(Long productId) {
        ProductEntity product = checkProduct(productId);

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getVat()
        );
    }

    public ProductResponse updateProduct(Long productId, CreateProductRequest request) {
        ProductEntity productEntity = checkProduct(productId);

        if(!productEntity.getName().equals(request.name())) {
            ProductEntity productName = productRepository.findByNameAndDeletedIsFalse(request.name());
            if(productName != null) {
                throw new IllegalArgumentException("Product Name just exists");
            }
        }

        productEntity.setName(request.name());
        productEntity.setPrice(request.price());
        productEntity.setVat(request.vat());

        ProductEntity savedProductEntity = productRepository.save(productEntity);

        return new ProductResponse(savedProductEntity.getId(), savedProductEntity.getName(), savedProductEntity.getPrice(), savedProductEntity.getVat());
    }

    public Page<ProductResponse> getProducts(Pageable pageable) {
        return productRepository.findByDeletedIsFalse(pageable)
                .map(p -> new ProductResponse(
                        p.getId(), p.getName(), p.getPrice(), p.getVat()
                ));
    }

    public void deleteProduct(Long productId) {
        ProductEntity productEntity = checkProduct(productId);
        productEntity.setDeleted(true);
        productRepository.save(productEntity);
    }

    private ProductEntity checkProduct(Long productId) {
        if(productId == null) {
            throw new IllegalArgumentException("Product Id is null");
        }
        return productRepository.findByIdAndDeletedIsFalse(productId).orElseThrow(() -> new NotFoundException("Product not found"));
    }
}
