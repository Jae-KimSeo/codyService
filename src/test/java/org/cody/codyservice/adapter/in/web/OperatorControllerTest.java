package org.cody.codyservice.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import org.cody.codyservice.application.operator.CreateProductUseCase;
import org.cody.codyservice.application.operator.DeleteProductUseCase;
import org.cody.codyservice.application.operator.GetProductsUseCase;
import org.cody.codyservice.application.operator.UpdateProductUseCase;
import org.cody.codyservice.common.exception.BusinessException;
import org.cody.codyservice.domain.operator.Brand;
import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.repository.BrandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(OperatorController.class)
public class OperatorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CreateProductUseCase createProductUseCase;
    
    @MockBean
    private UpdateProductUseCase updateProductUseCase;
    
    @MockBean
    private DeleteProductUseCase deleteProductUseCase;
    
    @MockBean
    private GetProductsUseCase getProductsUseCase;
    
    @MockBean
    private BrandRepository brandRepository;
    
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    
    @Test
    @DisplayName("상품 목록 조회 API가 성공적으로 응답한다")
    void getAllProducts_ShouldReturnProductList() throws Exception {
        // 테스트용 상품 데이터 생성
        Product product1 = new Product(1, "첫 번째 상품", BigDecimal.valueOf(10000), "설명", 1, 1, 
                                    LocalDateTime.now(), LocalDateTime.now());
        Product product2 = new Product(2, "두 번째 상품", BigDecimal.valueOf(20000), "설명", 1, 2,
                                    LocalDateTime.now(), LocalDateTime.now());
        
        // getProductsUseCase.getAllProducts() 호출 시 위에서 생성한 상품 리스트 반환하도록 설정
        when(getProductsUseCase.getAllProducts()).thenReturn(Arrays.asList(product1, product2));

        // GET /operator/products 요청을 수행하고 결과 검증
        mockMvc.perform(get("/operator/products"))
               .andExpect(status().isOk()) // HTTP 200 상태 코드 확인
               .andExpect(jsonPath("$.success").value(true)) // 성공 플래그 확인
               .andExpect(jsonPath("$.message").value("전체 상품 목록 조회 성공")) // 메시지 확인
               .andExpect(jsonPath("$.data").isArray()) // data가 배열인지 확인
               .andExpect(jsonPath("$.data.length()").value(2)) // 배열 길이 확인
               .andExpect(jsonPath("$.data[0].productId").value(1)) // 첫 번째 상품 ID 확인
               .andExpect(jsonPath("$.data[1].productId").value(2)); // 두 번째 상품 ID 확인
    }
    
    @Test
    @DisplayName("상품이 없을 때 빈 목록을 반환한다")
    void getAllProducts_WhenNoProducts_ShouldReturnEmptyList() throws Exception {
        // 빈 리스트 반환하도록 설정
        when(getProductsUseCase.getAllProducts()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/operator/products"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.data").isArray())
               .andExpect(jsonPath("$.data.length()").value(0)); // 빈 배열 확인
    }
    
    @Test
    @DisplayName("특정 상품 조회 API가 성공적으로 응답한다")
    void getProduct_ShouldReturnProduct() throws Exception {
        Product product = new Product(1, "테스트 상품", BigDecimal.valueOf(10000), "설명", 1, 1, 
                                   LocalDateTime.now(), LocalDateTime.now());
        
        when(getProductsUseCase.getProductById(1)).thenReturn(product);

        mockMvc.perform(get("/operator/products/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.message").value("상품 조회 성공"))
               .andExpect(jsonPath("$.data.productId").value(1))
               .andExpect(jsonPath("$.data.name").value("테스트 상품"));
    }
    
    @Test
    @DisplayName("존재하지 않는 상품 ID로 조회 시 BusinessException이 발생하고 적절히 처리된다")
    void getProduct_WhenProductNotFound_ShouldReturnNotFound() throws Exception {
        // 예외를 던지도록 설정
        when(getProductsUseCase.getProductById(99)).thenThrow(new BusinessException("상품을 찾을 수 없습니다: 99"));

        // 예외 처리가 전역 예외 핸들러에 의해 처리되는지 확인
        mockMvc.perform(get("/operator/products/99"))
               .andExpect(status().isBadRequest()) // HTTP 400 Bad Request 기대
               .andExpect(jsonPath("$.success").value(false))
               .andExpect(jsonPath("$.message").value("상품을 찾을 수 없습니다: 99"));
    }
    
    @Test
    @DisplayName("상품 생성 API가 성공적으로 응답한다")
    void createProduct_ShouldCreateAndReturnProduct() throws Exception {
        Product newProduct = new Product(null, "새 상품", BigDecimal.valueOf(15000), "설명", 1, 1,
                                     null, null);
        Product createdProduct = new Product(3, "새 상품", BigDecimal.valueOf(15000), "설명", 1, 1,
                                         LocalDateTime.now(), LocalDateTime.now());
        
        when(createProductUseCase.createProduct(any(Product.class))).thenReturn(createdProduct);

        mockMvc.perform(post("/operator/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProduct)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.message").value("상품이 생성되었습니다."))
               .andExpect(jsonPath("$.data.productId").value(3));
    }
    
    @Test
    @DisplayName("상품 수정 API가 성공적으로 응답한다")
    void updateProduct_ShouldUpdateAndReturnProduct() throws Exception {
        Product updateProduct = new Product(null, "수정된 상품", BigDecimal.valueOf(25000), "수정된 설명", 1, 1,
                                       null, null);
        Product updatedProduct = new Product(1, "수정된 상품", BigDecimal.valueOf(25000), "수정된 설명", 1, 1,
                                        LocalDateTime.now(), LocalDateTime.now());
        
        when(updateProductUseCase.updateProduct(any(Integer.class), any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/operator/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProduct)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.message").value("상품이 수정되었습니다."))
               .andExpect(jsonPath("$.data.name").value("수정된 상품"))
               .andExpect(jsonPath("$.data.price").value(25000));
    }
    
    @Test
    @DisplayName("상품 삭제 API가 성공적으로 응답한다")
    void deleteProduct_ShouldDeleteAndReturnSuccess() throws Exception {
        mockMvc.perform(delete("/operator/products/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.message").value("상품이 삭제되었습니다."));
    }
} 