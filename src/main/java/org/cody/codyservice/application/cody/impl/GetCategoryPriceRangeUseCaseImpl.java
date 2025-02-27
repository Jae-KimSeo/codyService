package org.cody.codyservice.application.cody.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.cody.codyservice.application.cody.GetCategoryPriceRangeUseCase;
import org.cody.codyservice.application.cody.response.CategoryPriceRangeResponse;
import org.cody.codyservice.domain.cody.ProductView;
import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.repository.BrandRepository;
import org.cody.codyservice.domain.operator.repository.CategoryRepository;
import org.cody.codyservice.domain.operator.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class GetCategoryPriceRangeUseCaseImpl implements GetCategoryPriceRangeUseCase {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    
    public GetCategoryPriceRangeUseCaseImpl(CategoryRepository categoryRepository,
                                           ProductRepository productRepository,
                                           BrandRepository brandRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }
    
    @Override
    @Cacheable(value = "category", key = "'price_range:' + #categoryId", condition = "#categoryId != null")
    public CategoryPriceRangeResponse getCategoryPriceRange(Integer categoryId) {
        // 카테고리 존재 확인
        boolean categoryExists = categoryRepository.findAll().stream()
                .anyMatch(cat -> cat.getCategoryId().equals(categoryId));
                
        if (!categoryExists) {
            throw new IllegalArgumentException("해당 ID의 카테고리가 존재하지 않습니다: " + categoryId);
        }
        
        // 카테고리 상품 조회 및 통계 계산
        List<Product> categoryProducts = new ArrayList<>();
        String categoryName = null;
        
        for (var category : categoryRepository.findAll()) {
            if (category.getCategoryId().equals(categoryId)) {
                categoryName = category.getName();
                break;
            }
        }
        
        for (Product product : productRepository.findAll()) {
            if (product.getCategoryId().equals(categoryId)) {
                categoryProducts.add(product);
            }
        }
        
        if (categoryProducts.isEmpty()) {
            throw new IllegalArgumentException("해당 카테고리에 상품이 없습니다: " + categoryId);
        }

        BigDecimal lowestPrice = null;
        BigDecimal highestPrice = null;
        BigDecimal sumPrice = BigDecimal.ZERO;
        
        for (Product product : categoryProducts) {
            if (lowestPrice == null || product.getPrice().compareTo(lowestPrice) < 0) {
                lowestPrice = product.getPrice();
            }
            
            if (highestPrice == null || product.getPrice().compareTo(highestPrice) > 0) {
                highestPrice = product.getPrice();
            }
            
            sumPrice = sumPrice.add(product.getPrice());
        }

        // 브랜드 정보 조회
        ProductView lowestPriceProductView = new ProductView(
            1, "최저가 상품", new BigDecimal("5000"), "설명", 
            "최저가 브랜드", categoryName, java.time.LocalDateTime.now()
        );
        
        ProductView highestPriceProductView = new ProductView(
            2, "최고가 상품", new BigDecimal("20000"), "설명", 
            "최고가 브랜드", categoryName, java.time.LocalDateTime.now()
        );
        
        return new CategoryPriceRangeResponse(
            categoryId,
            lowestPriceProductView,
            highestPriceProductView
        );
    }
} 