package org.cody.codyservice.adapter.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.repository.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final Map<Integer, Product> products = new HashMap<>();
    
    @Override
    public Product findById(Integer productId) {
        return products.get(productId);
    }
    
    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Product save(Product product) {
        products.put(product.getProductId(), product);
        return product;
    }
    
    @Override
    public void deleteById(Integer productId) {
        products.remove(productId);
    }
} 