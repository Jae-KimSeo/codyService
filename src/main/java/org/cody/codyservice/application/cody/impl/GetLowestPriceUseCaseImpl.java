package org.cody.codyservice.application.cody.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.cody.codyservice.application.cody.GetLowestPriceUseCase;
import org.cody.codyservice.application.cody.response.CategoryPriceResponse;
import org.cody.codyservice.application.cody.response.CategoryPriceResponse.BrandPriceInfo;
import org.cody.codyservice.domain.cody.ProductView;
import org.cody.codyservice.domain.operator.Category;
import org.cody.codyservice.domain.operator.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class GetLowestPriceUseCaseImpl implements GetLowestPriceUseCase {

    private final CategoryRepository categoryRepository;
    
    public GetLowestPriceUseCaseImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    @Override
    public CategoryPriceResponse getCategoryLowestPriceInfo(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("카테고리를 찾을 수 없습니다: " + categoryId);
        }
        
        List<BrandPriceInfo> brandPriceInfos = new ArrayList<>();
        brandPriceInfos.add(new BrandPriceInfo("샘플 브랜드", new BigDecimal("10000")));
        
        return new CategoryPriceResponse(
            categoryId,
            category.getName(),
            "샘플 브랜드",
            new BigDecimal("10000"),
            createSampleProductView(),
            brandPriceInfos
        );
    }
    
    private ProductView createSampleProductView() {
        return new ProductView(
            1,
            "샘플 상품",
            new BigDecimal("10000"),
            "샘플 상품 설명",
            "샘플 브랜드",
            "샘플 카테고리",
            java.time.LocalDateTime.now()
        );
    }
} 