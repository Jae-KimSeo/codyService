package org.cody.codyservice.application.operator.impl;

import org.cody.codyservice.application.operator.DeleteProductUseCase;
import org.cody.codyservice.common.exception.BusinessException;
import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteProductUseCaseImpl implements DeleteProductUseCase {
    
    private final ProductRepository productRepository;
    
    @Autowired
    public DeleteProductUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Override
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new BusinessException("삭제할 상품을 찾을 수 없습니다: " + id);
        }
        
        productRepository.deleteById(id);
    }
} 