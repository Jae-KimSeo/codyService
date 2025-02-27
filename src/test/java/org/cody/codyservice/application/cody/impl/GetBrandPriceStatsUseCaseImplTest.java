package org.cody.codyservice.application.cody.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.cody.codyservice.application.cody.response.BrandAllCategoriesResponse;
import org.cody.codyservice.application.cody.response.BrandAllCategoriesResponse.CategoryPriceInfo;
import org.cody.codyservice.domain.operator.Brand;
import org.cody.codyservice.domain.operator.Category;
import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.repository.BrandRepository;
import org.cody.codyservice.domain.operator.repository.CategoryRepository;
import org.cody.codyservice.domain.operator.repository.ProductRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetBrandPriceStatsUseCaseImplTest {

    @Mock
    private BrandRepository brandRepository;
    
    @Mock
    private CategoryRepository categoryRepository;
    
    @Mock
    private ProductRepository productRepository;
    
    private GetBrandPriceStatsUseCaseImpl getBrandPriceStatsUseCase;
    
    private Brand testBrand;
    private Category category1;
    private Category category2;
    private Product product1;
    private Product product2;
    private Product product3;
    
    @BeforeEach
    void setUp() {
        getBrandPriceStatsUseCase = new GetBrandPriceStatsUseCaseImpl(
            brandRepository, categoryRepository, productRepository);
        
        LocalDateTime now = LocalDateTime.now();
        testBrand = new Brand(1, "테스트 브랜드", now, now);
        
        category1 = new Category(1, "카테고리1");
        category2 = new Category(2, "카테고리2");
        
        product1 = new Product(1, "상품1", new BigDecimal("5000"), "설명1", 1, 1, now, now);
        product2 = new Product(2, "상품2", new BigDecimal("8000"), "설명2", 1, 1, now, now);
        product3 = new Product(3, "상품3", new BigDecimal("12000"), "설명3", 1, 2, now, now);
    }
    
    @Test
    @DisplayName("브랜드의 모든 카테고리별 최저가 정보를 성공적으로 조회한다")
    void getBrandLowestPriceForAllCategories_ShouldReturnAllCategories() {
        when(brandRepository.findById(1)).thenReturn(testBrand);
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2, product3));
        
        BrandAllCategoriesResponse response = getBrandPriceStatsUseCase.getBrandLowestPriceForAllCategories(1);
        
        assertNotNull(response);
        assertEquals(1, response.getBrandId());
        assertEquals("테스트 브랜드", response.getBrandName());
        
        List<CategoryPriceInfo> categoryInfos = response.getCategoryPriceInfos();
        assertEquals(2, categoryInfos.size());

        CategoryPriceInfo info1 = categoryInfos.get(0);
        assertEquals(1, info1.getCategoryId());
        assertEquals("카테고리1", info1.getCategoryName());
        assertEquals(new BigDecimal("5000"), info1.getLowestPrice());
        assertTrue(info1.isHasProduct());

        CategoryPriceInfo info2 = categoryInfos.get(1);
        assertEquals(2, info2.getCategoryId());
        assertEquals("카테고리2", info2.getCategoryName());
        assertEquals(new BigDecimal("12000"), info2.getLowestPrice());
        assertTrue(info2.isHasProduct());
    }
    
    @Test
    @DisplayName("존재하지 않는 브랜드 ID로 조회 시 예외가 발생한다")
    void getBrandLowestPriceForAllCategories_WithNonExistingBrand_ShouldThrowException() {
        when(brandRepository.findById(99)).thenReturn(null);
        
        assertThrows(IllegalArgumentException.class, 
            () -> getBrandPriceStatsUseCase.getBrandLowestPriceForAllCategories(99));
    }
} 