package org.cody.codyservice.application.cody.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.cody.codyservice.application.cody.response.CategoryPriceResponse;
import org.cody.codyservice.domain.cody.ProductView;
import org.cody.codyservice.domain.operator.Brand;
import org.cody.codyservice.domain.operator.Category;
import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.repository.BrandRepository;
import org.cody.codyservice.domain.operator.repository.CategoryRepository;
import org.cody.codyservice.domain.operator.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetLowestPriceUseCaseImplTest {

    @Mock
    private CategoryRepository categoryRepository;
    
    @Mock
    private ProductRepository productRepository;
    
    @Mock
    private BrandRepository brandRepository;
    
    private GetLowestPriceUseCaseImpl getLowestPriceUseCase;
    
    private Category testCategory;
    private Brand testBrand;
    private Product product1;
    private Product product2;
    
    @BeforeEach
    void setUp() {
        getLowestPriceUseCase = new GetLowestPriceUseCaseImpl(categoryRepository, productRepository, brandRepository);
        
        testCategory = new Category(1, "테스트 카테고리");
        testBrand = new Brand(1, "테스트 브랜드", LocalDateTime.now(), LocalDateTime.now());
        
        LocalDateTime now = LocalDateTime.now();
        product1 = new Product(1, "저렴한 상품", new BigDecimal("5000"), "설명1", 1, 1, now, now);
        product2 = new Product(2, "비싼 상품", new BigDecimal("10000"), "설명2", 1, 1, now, now);
    }
    
    @Test
    @DisplayName("카테고리의 최저가 상품을 성공적으로 조회한다")
    void getCategoryLowestPriceInfo_ShouldReturnLowestPriceProduct() {
        when(categoryRepository.findById(1)).thenReturn(testCategory);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        when(brandRepository.findById(1)).thenReturn(testBrand);
        
        CategoryPriceResponse response = getLowestPriceUseCase.getCategoryLowestPriceInfo(1);
        
        assertNotNull(response);
        assertEquals(1, response.getCategoryId());
        assertEquals("테스트 카테고리", response.getCategoryName());
        assertEquals(new BigDecimal("5000"), response.getLowestPrice());
        
        ProductView productView = response.getLowestPriceProduct();
        assertNotNull(productView);
        assertEquals(1, productView.getProductId());
        assertEquals("저렴한 상품", productView.getName());
        assertEquals("테스트 브랜드", productView.getBrandName());
    }
    
    @Test
    @DisplayName("카테고리 ID가 null이면 IllegalArgumentException이 발생한다")
    void getCategoryLowestPriceInfo_WithNullId_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, 
            () -> getLowestPriceUseCase.getCategoryLowestPriceInfo(null));
    }
    
    @Test
    @DisplayName("존재하지 않는 카테고리 ID로 조회 시 예외가 발생한다")
    void getCategoryLowestPriceInfo_WithNonExistingCategory_ShouldThrowException() {
        when(categoryRepository.findById(99)).thenReturn(null);
        
        assertThrows(IllegalArgumentException.class, 
            () -> getLowestPriceUseCase.getCategoryLowestPriceInfo(99));
    }
} 