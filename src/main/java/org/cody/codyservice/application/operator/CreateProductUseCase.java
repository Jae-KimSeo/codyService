package org.cody.codyservice.application.operator;

import org.cody.codyservice.domain.operator.Product;

public interface CreateProductUseCase {
    Product createProduct(Product product);
} 