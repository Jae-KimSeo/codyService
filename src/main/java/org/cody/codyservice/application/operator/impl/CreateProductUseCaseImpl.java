package org.cody.codyservice.application.operator.impl;

import org.cody.codyservice.application.operator.CreateProductUseCase;
import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateProductUseCaseImpl implements CreateProductUseCase {
    
    private final ProductRepository productRepository;
    
    public CreateProductUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
} 