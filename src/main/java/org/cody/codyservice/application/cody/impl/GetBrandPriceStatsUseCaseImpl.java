package org.cody.codyservice.application.cody.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.cody.codyservice.application.cody.GetBrandPriceStatsUseCase;
import org.cody.codyservice.application.cody.response.BrandAllCategoriesResponse;
import org.cody.codyservice.application.cody.response.BrandAllCategoriesResponse.CategoryPriceInfo;
import org.cody.codyservice.domain.cody.ProductView;
import org.cody.codyservice.domain.operator.Brand;
import org.cody.codyservice.domain.operator.Category;
import org.cody.codyservice.domain.operator.repository.BrandRepository;
import org.cody.codyservice.domain.operator.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class GetBrandPriceStatsUseCaseImpl implements GetBrandPriceStatsUseCase {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    
    public GetBrandPriceStatsUseCaseImpl(BrandRepository brandRepository,
                                         CategoryRepository categoryRepository) {
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public BrandAllCategoriesResponse getBrandLowestPriceForAllCategories(Integer brandId) {
        Brand brand = brandRepository.findById(brandId);
        if (brand == null) {
            throw new IllegalArgumentException("브랜드를 찾을 수 없습니다: " + brandId);
        }
        
        List<CategoryPriceInfo> categoryPriceInfos = new ArrayList<>();
        
        for (Category category : categoryRepository.findAll()) {
            ProductView sampleProductView = new ProductView(
                1, 
                "샘플 상품", 
                new BigDecimal("10000"), 
                "샘플 설명", 
                brand.getName(), 
                category.getName(), 
                java.time.LocalDateTime.now()
            );
            
            categoryPriceInfos.add(new CategoryPriceInfo(
                category.getCategoryId(),
                category.getName(),
                new BigDecimal("10000"),
                sampleProductView,
                true
            ));
        }
        
        return new BrandAllCategoriesResponse(brandId, brand.getName(), categoryPriceInfos);
    }
} 