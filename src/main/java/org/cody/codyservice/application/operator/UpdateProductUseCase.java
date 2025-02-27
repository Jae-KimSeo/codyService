package org.cody.codyservice.application.operator;

import org.cody.codyservice.domain.operator.Product;

public interface UpdateProductUseCase {
    Product updateProduct(Integer id, Product product);
} 