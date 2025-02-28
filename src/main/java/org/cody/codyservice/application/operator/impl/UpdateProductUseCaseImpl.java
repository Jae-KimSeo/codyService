package org.cody.codyservice.application.operator.impl;

import org.cody.codyservice.application.operator.UpdateProductUseCase;
import org.cody.codyservice.common.exception.BusinessException;
import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {

    private final ProductRepository productRepository;

    @Autowired
    public UpdateProductUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product updateProduct(Integer id, Product updatedProduct) {
        // Optional 대신 직접 null 체크
        Product existingProduct = productRepository.findById(id);
        if (existingProduct == null) {
            throw new BusinessException("제품을 찾을 수 없습니다: " + id);
        }
        
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setBrandId(updatedProduct.getBrandId());
        existingProduct.setCategoryId(updatedProduct.getCategoryId());
        
        existingProduct.setUpdatedAt(LocalDateTime.now());
        
        return productRepository.save(existingProduct);
    }
} 