package org.cody.codyservice.application.operator.impl;

import org.cody.codyservice.application.operator.CreateProductUseCase;
import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateProductUseCaseImpl implements CreateProductUseCase {
    
    private final ProductRepository productRepository;
    
    public CreateProductUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Override
    public Product createProduct(Product product) {
        // 현재 시간 설정
        LocalDateTime now = LocalDateTime.now();
        product.setCreatedAt(now);
        product.setUpdatedAt(now);
        
        return productRepository.save(product);
    }
} 