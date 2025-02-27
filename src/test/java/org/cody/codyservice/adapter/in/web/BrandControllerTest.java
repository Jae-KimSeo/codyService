package org.cody.codyservice.adapter.in.web;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.cody.codyservice.application.operator.CreateBrandUseCase;
import org.cody.codyservice.application.operator.DeleteBrandUseCase;
import org.cody.codyservice.application.operator.GetBrandsUseCase;
import org.cody.codyservice.application.operator.UpdateBrandUseCase;
import org.cody.codyservice.domain.operator.Brand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(OperatorController.class)
public class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CreateBrandUseCase createBrandUseCase;
    
    @MockBean
    private UpdateBrandUseCase updateBrandUseCase;
    
    @MockBean
    private DeleteBrandUseCase deleteBrandUseCase;
    
    @MockBean
    private GetBrandsUseCase getBrandsUseCase;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    
    @Test
    @DisplayName("브랜드 목록 조회 API가 성공적으로 응답한다")
    void getAllBrands_ShouldReturnBrandList() throws Exception {
        Brand brand1 = new Brand(1, "첫 번째 브랜드", LocalDateTime.now(), LocalDateTime.now());
        Brand brand2 = new Brand(2, "두 번째 브랜드", LocalDateTime.now(), LocalDateTime.now());
        
        List<Brand> brands = Arrays.asList(brand1, brand2);
        
        when(getBrandsUseCase.getAllBrands()).thenReturn(brands);
        
        mockMvc.perform(get("/operator/brands"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.message").value("전체 브랜드 목록 조회 성공"))
               .andExpect(jsonPath("$.data").isArray())
               .andExpect(jsonPath("$.data.length()").value(2))
               .andExpect(jsonPath("$.data[0].brandId").value(1))
               .andExpect(jsonPath("$.data[0].name").value("첫 번째 브랜드"))
               .andExpect(jsonPath("$.data[1].brandId").value(2))
               .andExpect(jsonPath("$.data[1].name").value("두 번째 브랜드"));
    }
    
    @Test
    @DisplayName("브랜드 생성 API가 성공적으로 응답한다")
    void createBrand_ShouldCreateAndReturnBrand() throws Exception {
        Brand inputBrand = new Brand(null, "새 브랜드");
        Brand createdBrand = new Brand(1, "새 브랜드", LocalDateTime.now(), LocalDateTime.now());
        
        when(createBrandUseCase.createBrand(any(Brand.class))).thenReturn(createdBrand);
        
        mockMvc.perform(post("/operator/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputBrand)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.message").value("브랜드가 생성되었습니다."))
               .andExpect(jsonPath("$.data.brandId").value(1))
               .andExpect(jsonPath("$.data.name").value("새 브랜드"));
    }
} 