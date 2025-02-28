package org.cody.codyservice.adapter.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.repository.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final Map<Integer, Product> products = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);
    
    @Override
    public Product findById(Integer id) {
        return products.get(id);
    }
    
    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Product save(Product product) {
        if (product.getProductId() == null) {
            product.setProductId(idCounter.incrementAndGet());
        }
        products.put(product.getProductId(), product);
        return product;
    }
    
    @Override
    public void deleteById(Integer id) {
        products.remove(id);
    }
} 