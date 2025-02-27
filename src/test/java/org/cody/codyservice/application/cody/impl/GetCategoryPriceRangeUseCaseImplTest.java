package org.cody.codyservice.application.cody.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.cody.codyservice.application.cody.response.CategoryPriceRangeResponse;
import org.cody.codyservice.domain.operator.Category;
import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.repository.BrandRepository;
import org.cody.codyservice.domain.operator.repository.CategoryRepository;
import org.cody.codyservice.domain.operator.repository.ProductRepository;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetCategoryPriceRangeUseCaseImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private GetCategoryPriceRangeUseCaseImpl getCategoryPriceRangeUseCase;

    private Category category;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        category = new Category(1, "의류");

        LocalDateTime now = LocalDateTime.now();
        products = Arrays.asList(
            new Product(1, "저가 제품", new BigDecimal("5000"), "저가 제품 설명", 1, 1, now, now),
            new Product(2, "중가 제품", new BigDecimal("15000"), "중가 제품 설명", 1, 2, now, now),
            new Product(3, "고가 제품", new BigDecimal("50000"), "고가 제품 설명", 1, 3, now, now)
        );
    }

    @Test
    @DisplayName("카테고리 ID로 가격 범위 조회 성공")
    void getCategoryPriceRange_Success() {
        // given
        Integer categoryId = 1;
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(category));
        when(productRepository.findAll()).thenReturn(products);

        // when
        CategoryPriceRangeResponse response = getCategoryPriceRangeUseCase.getCategoryPriceRange(categoryId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getCategoryId()).isEqualTo(categoryId);

        assertThat(response.getLowestPriceProductView()).isNotNull();
        assertThat(response.getHighestPriceProductView()).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 카테고리 ID로 조회 시 예외 발생")
    void getCategoryPriceRange_InvalidCategoryId() {
        // given
        Integer invalidCategoryId = 999;
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(category));

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> getCategoryPriceRangeUseCase.getCategoryPriceRange(invalidCategoryId));
        
        assertThat(exception.getMessage()).contains("해당 ID의 카테고리가 존재하지 않습니다");
    }

    @Test
    @DisplayName("카테고리에 상품이 없을 때 예외 발생")
    void getCategoryPriceRange_NoProducts() {
        // given
        Integer categoryId = 1;
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(category));
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> getCategoryPriceRangeUseCase.getCategoryPriceRange(categoryId));
        
        assertThat(exception.getMessage()).contains("해당 카테고리에 상품이 없습니다");
    }
} 