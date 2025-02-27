package org.cody.codyservice.integration;

import org.cody.codyservice.domain.operator.Brand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BrandIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    
    @Test
    @DisplayName("브랜드 생성 후 조회가 정상적으로 동작한다")
    void createAndGetBrand_ShouldWorkCorrectly() throws Exception {
        Brand newBrand = new Brand();
        newBrand.setName("통합테스트 브랜드");
        
        // 브랜드 생성 요청 및 검증
        String response = mockMvc.perform(post("/operator/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBrand)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.data.brandId").isNotEmpty())
               .andReturn().getResponse().getContentAsString();
        
        System.out.println("생성 응답: " + response);
        
        // ID 추출을 위한 처리
        int brandId = objectMapper.readTree(response).path("data").path("brandId").asInt();
        
        // 생성된 브랜드 조회 및 검증
        mockMvc.perform(get("/operator/brands/" + brandId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.data.name").value("통합테스트 브랜드"));
        
        // 전체 브랜드 목록에도 반영되는지 확인
        mockMvc.perform(get("/operator/brands"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.data[?(@.brandId == " + brandId + ")].name").value("통합테스트 브랜드"));
    }
} 