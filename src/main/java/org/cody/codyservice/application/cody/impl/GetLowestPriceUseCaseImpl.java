package org.cody.codyservice.application.cody.impl;

import java.math.BigDecimal;

import org.cody.codyservice.application.cody.GetLowestPriceUseCase;
import org.cody.codyservice.application.cody.response.CategoryPriceResponse;
import org.cody.codyservice.domain.cody.ProductView;
import org.cody.codyservice.domain.operator.Category;
import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.repository.BrandRepository;
import org.cody.codyservice.domain.operator.repository.CategoryRepository;
import org.cody.codyservice.domain.operator.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class GetLowestPriceUseCaseImpl implements GetLowestPriceUseCase {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    
    public GetLowestPriceUseCaseImpl(CategoryRepository categoryRepository, 
                                    ProductRepository productRepository,
                                    BrandRepository brandRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
    }
    
    @Override
    @Cacheable(value = "category", key = "#categoryId + ':lowest_price'", condition = "#categoryId != null")
    public CategoryPriceResponse getCategoryLowestPriceInfo(Integer categoryId) {
        if (categoryId == null) {
            throw new IllegalArgumentException("카테고리 ID는 필수입니다.");
        }
        
        // 카테고리 존재 여부 확인
        Category category = categoryRepository.findById(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다: " + categoryId);
        }
        
        // 카테고리에 속한 모든 상품 조회 후 최저가 계산
        Product lowestPriceProduct = findLowestPriceProduct(categoryId);
        if (lowestPriceProduct == null) {
            return new CategoryPriceResponse(
                categoryId,
                category.getName(),
                null,   // 최저가 없음
                null    // 상품 정보 없음
            );
        }
        
        // Brand 정보 조회
        String brandName = brandRepository.findById(lowestPriceProduct.getBrandId()) != null 
            ? brandRepository.findById(lowestPriceProduct.getBrandId()).getName() 
            : "Unknown Brand";
        
        // ProductView 객체 생성
        ProductView productView = new ProductView(
            lowestPriceProduct.getProductId(),
            lowestPriceProduct.getName(),
            lowestPriceProduct.getPrice(),
            lowestPriceProduct.getDescription(),
            brandName,
            category.getName(),
            null  // createdAt 필드를 null로 설정
        );
        
        return new CategoryPriceResponse(
            categoryId,
            category.getName(),
            lowestPriceProduct.getPrice(),
            productView
        );
    }
    
    // 특정 카테고리의 최저가 상품 찾기
    private Product findLowestPriceProduct(Integer categoryId) {
        Product lowestProduct = null;
        BigDecimal lowestPrice = null;
        
        for (Product product : productRepository.findAll()) {
            if (product.getCategoryId().equals(categoryId)) {
                if (lowestPrice == null || product.getPrice().compareTo(lowestPrice) < 0) {
                    lowestPrice = product.getPrice();
                    lowestProduct = product;
                }
            }
        }
        
        return lowestProduct;
    }
} 