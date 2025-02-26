package org.cody.codyservice.application.cody.impl;

import java.math.BigDecimal;

import org.cody.codyservice.application.cody.GetCategoryPriceRangeUseCase;
import org.cody.codyservice.application.cody.response.CategoryPriceRangeResponse;
import org.cody.codyservice.domain.cody.ProductView;
import org.cody.codyservice.domain.operator.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class GetCategoryPriceRangeUseCaseImpl implements GetCategoryPriceRangeUseCase {

    private final CategoryRepository categoryRepository;
    
    public GetCategoryPriceRangeUseCaseImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    @Override
    public CategoryPriceRangeResponse getCategoryPriceRange(String categoryName) {
        // 카테고리 존재 확인
        boolean categoryExists = categoryRepository.findAll().stream()
                .anyMatch(cat -> cat.getName().equals(categoryName));
                
        if (!categoryExists) {
            throw new IllegalArgumentException("해당 이름의 카테고리가 존재하지 않습니다: " + categoryName);
        }
        
        ProductView lowestPriceProductView = new ProductView(
            1, "최저가 상품", new BigDecimal("5000"), "설명", 
            "최저가 브랜드", categoryName, java.time.LocalDateTime.now()
        );
        
        ProductView highestPriceProductView = new ProductView(
            2, "최고가 상품", new BigDecimal("20000"), "설명", 
            "최고가 브랜드", categoryName, java.time.LocalDateTime.now()
        );
        
        return new CategoryPriceRangeResponse(
            categoryName,
            new BigDecimal("5000"),
            new BigDecimal("20000"),
            "최저가 브랜드",
            "최고가 브랜드",
            lowestPriceProductView,
            highestPriceProductView,
            new BigDecimal("12500"),
            10
        );
    }
} 