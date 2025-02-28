package org.cody.codyservice.domain.operator.repository;

import java.util.List;

import org.cody.codyservice.domain.operator.Product;

public interface ProductRepository {
    Product findById(Integer id);
    List<Product> findAll();
    Product save(Product product);
    void deleteById(Integer id);
} 