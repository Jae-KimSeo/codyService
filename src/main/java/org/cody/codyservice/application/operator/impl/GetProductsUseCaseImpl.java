package org.cody.codyservice.application.operator.impl;

import java.util.List;

import org.cody.codyservice.application.operator.GetProductsUseCase;
import org.cody.codyservice.common.exception.BusinessException;
import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class GetProductsUseCaseImpl implements GetProductsUseCase {

    private final ProductRepository productRepository;
    
    public GetProductsUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Override
    public Product getProductById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("상품 ID는 필수입니다.");
        }
        
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new BusinessException("상품을 찾을 수 없습니다: " + id);
        }
        return product;
    }
    
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
} 