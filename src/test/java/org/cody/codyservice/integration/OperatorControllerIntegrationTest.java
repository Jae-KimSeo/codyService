package org.cody.codyservice.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.cody.codyservice.domain.operator.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// Integration Test는 @Transactional 어노테이션으로 테스트 후 롤백됩니다.
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OperatorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    
    @Test
    @DisplayName("상품 생성 후 조회가 정상적으로 동작한다")
    void createAndGetProduct_ShouldWorkCorrectly() throws Exception {
        // 새 상품 데이터 준비
        Product newProduct = new Product();
        newProduct.setProductId(1); // ID 설정
        newProduct.setName("통합테스트 상품");
        newProduct.setPrice(new BigDecimal("15000"));
        newProduct.setDescription("통합테스트용 상품 설명");
        newProduct.setBrandId(1);
        newProduct.setCategoryId(1);
        
        // 상품 생성 요청 및 검증
        String response = mockMvc.perform(post("/operator/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProduct)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.data.productId").value(1))
               .andReturn().getResponse().getContentAsString();
        
        System.out.println("생성 응답: " + response);
        
        // 생성된 상품 조회 및 검증
        mockMvc.perform(get("/operator/products/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.data.name").value("통합테스트 상품"))
               .andExpect(jsonPath("$.data.price").value(15000));
        
        // 전체 상품 목록에도 반영되는지 확인
        mockMvc.perform(get("/operator/products"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.data[0].productId").value(1));
    }
    
    @Test
    @DisplayName("존재하지 않는 상품 ID로 조회시 오류 응답을 반환한다")
    void getProduct_WithInvalidId_ShouldReturnError() throws Exception {
        // 존재하지 않는 ID로 조회
        mockMvc.perform(get("/operator/products/999"))
               .andExpect(status().isBadRequest())  // 400 Bad Request 응답
               .andExpect(jsonPath("$.success").value(false))
               .andExpect(jsonPath("$.message").isString()); // 오류 메시지 존재 확인
    }
} 