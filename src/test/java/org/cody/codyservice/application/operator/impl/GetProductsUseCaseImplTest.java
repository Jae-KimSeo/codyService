package org.cody.codyservice.application.operator.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.cody.codyservice.common.exception.BusinessException;
import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetProductsUseCaseImplTest {

    @Mock
    private ProductRepository productRepository;
    
    private GetProductsUseCaseImpl getProductsUseCase;
    
    private Product sampleProduct;
    
    @BeforeEach
    void setUp() {
        // 테스트 대상 객체 초기화
        getProductsUseCase = new GetProductsUseCaseImpl(productRepository);
        
        // 테스트용 샘플 상품 데이터 생성
        sampleProduct = new Product(
            1,
            "테스트 상품",
            new BigDecimal("10000"),
            "테스트 상품 설명",
            1,
            1,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }
    
    @Test
    @DisplayName("상품 ID로 상품을 성공적으로 조회한다")
    void getProductById_WhenProductExists_ShouldReturnProduct() {
        // productRepository.findById()가 호출될 때 sampleProduct를 반환하도록 설정
        when(productRepository.findById(1)).thenReturn(sampleProduct);

        // 테스트 대상 메서드 실행
        Product result = getProductsUseCase.getProductById(1);

        // 반환된 상품이 예상대로인지 검증
        assertNotNull(result);
        assertEquals(1, result.getProductId());
        assertEquals("테스트 상품", result.getName());
        
        // productRepository의 findById 메서드가 정확히 1번 호출되었는지 검증
        verify(productRepository, times(1)).findById(1);
    }
    
    @Test
    @DisplayName("존재하지 않는 상품 ID로 조회 시 BusinessException이 발생한다")
    void getProductById_WhenProductDoesNotExist_ShouldThrowBusinessException() {
        // productRepository.findById()가 호출될 때 null을 반환하도록 설정
        when(productRepository.findById(99)).thenReturn(null);

        // 예외가 발생하는지 확인하고 예외 메시지 검증
        BusinessException exception = assertThrows(
            BusinessException.class, 
            () -> getProductsUseCase.getProductById(99)
        );
        
        assertEquals("상품을 찾을 수 없습니다: 99", exception.getMessage());
        verify(productRepository, times(1)).findById(99);
    }
    
    @Test
    @DisplayName("상품 ID가 null인 경우 IllegalArgumentException이 발생한다")
    void getProductById_WhenIdIsNull_ShouldThrowIllegalArgumentException() {
        // null ID로 호출 시 IllegalArgumentException이 발생하는지 검증
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> getProductsUseCase.getProductById(null)
        );
        
        assertEquals("상품 ID는 필수입니다.", exception.getMessage());
        // productRepository의 메서드가 호출되지 않았는지 검증
        verify(productRepository, never()).findById(any());
    }
    
    @Test
    @DisplayName("전체 상품 목록을 성공적으로 조회한다")
    void getAllProducts_ShouldReturnAllProducts() {
        // 두 개의 상품이 있는 리스트를 반환하도록 설정
        List<Product> products = Arrays.asList(
            sampleProduct,
            new Product(2, "두 번째 상품", new BigDecimal("20000"), "설명", 1, 2, 
                       LocalDateTime.now(), LocalDateTime.now())
        );
        when(productRepository.findAll()).thenReturn(products);

        // 테스트 대상 메서드 실행
        List<Product> result = getProductsUseCase.getAllProducts();

        // 반환된 목록이 예상과 일치하는지 검증
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getProductId());
        assertEquals(2, result.get(1).getProductId());
        
        verify(productRepository, times(1)).findAll();
    }
    
    @Test
    @DisplayName("상품이 없을 때 빈 목록을 반환한다")
    void getAllProducts_WhenNoProducts_ShouldReturnEmptyList() {
        // 빈 리스트를 반환하도록 설정
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<Product> result = getProductsUseCase.getAllProducts();

        // 빈 목록이 반환되는지 검증
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        verify(productRepository, times(1)).findAll();
    }
} 