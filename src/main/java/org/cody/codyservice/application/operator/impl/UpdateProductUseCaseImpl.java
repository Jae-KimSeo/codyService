package org.cody.codyservice.application.operator.impl;

import org.cody.codyservice.application.operator.UpdateProductUseCase;
import org.cody.codyservice.common.exception.BusinessException;
import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {

    private final ProductRepository productRepository;

    @Autowired
    public UpdateProductUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product updateProduct(Integer id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id);
        if (existingProduct == null) {
            throw new BusinessException("상품을 찾을 수 없습니다: " + id);
        }
        
        updatedProduct.setProductId(id);
        return productRepository.save(updatedProduct);
    }
} 