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
import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.repository.BrandRepository;
import org.cody.codyservice.domain.operator.repository.CategoryRepository;
import org.cody.codyservice.domain.operator.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class GetBrandPriceStatsUseCaseImpl implements GetBrandPriceStatsUseCase {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    
    @Autowired
    public GetBrandPriceStatsUseCaseImpl(BrandRepository brandRepository,
                                         CategoryRepository categoryRepository,
                                         ProductRepository productRepository) {
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable(value = "brand", key = "#brandId + ':price_stats'", condition = "#brandId != null")
    public BrandAllCategoriesResponse getBrandLowestPriceForAllCategories(Integer brandId) {
        Brand brand = brandRepository.findById(brandId);
        if (brand == null) {
            throw new IllegalArgumentException("브랜드를 찾을 수 없습니다: " + brandId);
        }
        
        List<CategoryPriceInfo> categoryPriceInfos = new ArrayList<>();
        
        // 각 카테고리별 최저가 상품 찾기
        for (Category category : categoryRepository.findAll()) {
            Product lowestPriceProduct = findLowestPriceProductByBrandAndCategory(brandId, category.getCategoryId());
            boolean hasProduct = (lowestPriceProduct != null);
            
            ProductView productView = null;
            BigDecimal lowestPrice = null;
            
            if (hasProduct) {
                lowestPrice = lowestPriceProduct.getPrice();
                productView = new ProductView(
                    lowestPriceProduct.getProductId(),
                    lowestPriceProduct.getName(),
                    lowestPriceProduct.getPrice(),
                    lowestPriceProduct.getDescription(),
                    brand.getName(),
                    category.getName(),
                    null  // createdAt을 null로 설정
                );
            }
            
            categoryPriceInfos.add(new CategoryPriceInfo(
                category.getCategoryId(),
                category.getName(),
                lowestPrice,
                productView,
                hasProduct
            ));
        }
        
        return new BrandAllCategoriesResponse(brandId, brand.getName(), categoryPriceInfos);
    }
    
    // 브랜드와 카테고리로 최저가 상품 찾기
    private Product findLowestPriceProductByBrandAndCategory(Integer brandId, Integer categoryId) {
        Product lowestProduct = null;
        BigDecimal lowestPrice = null;
        
        for (Product product : productRepository.findAll()) {
            if (product.getBrandId().equals(brandId) && product.getCategoryId().equals(categoryId)) {
                if (lowestPrice == null || product.getPrice().compareTo(lowestPrice) < 0) {
                    lowestPrice = product.getPrice();
                    lowestProduct = product;
                }
            }
        }
        
        return lowestProduct;
    }
} 