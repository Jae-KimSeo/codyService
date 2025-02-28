package org.cody.codyservice.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cody.codyservice.application.operator.CreateBrandUseCase;
import org.cody.codyservice.application.operator.CreateProductUseCase;
import org.cody.codyservice.domain.operator.Brand;
import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

@Configuration
public class DataLoader {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final ObjectMapper objectMapper;
    private final CreateBrandUseCase createBrandUseCase;
    private final CreateProductUseCase createProductUseCase;
    
    private final Map<Integer, String> categoryMap = new HashMap<>();

    public DataLoader(
            ObjectMapper objectMapper,
            CreateBrandUseCase createBrandUseCase,
            CreateProductUseCase createProductUseCase,
            @Value("${categories.data}") String categoriesData
    ) {
        this.objectMapper = objectMapper;
        this.createBrandUseCase = createBrandUseCase;
        this.createProductUseCase = createProductUseCase;
        
        // application.yml에서 카테고리 데이터 로드
        Arrays.stream(categoriesData.split(","))
              .forEach(category -> {
                  String[] parts = category.split(":");
                  categoryMap.put(Integer.parseInt(parts[0]), parts[1]);
              });
        
        logger.info("카테고리 데이터 로드: {}", categoryMap);
    }

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            try {
                loadBrands();
                loadProducts();
                logger.info("초기 데이터 로드가 완료되었습니다.");
            } catch (Exception e) {
                logger.error("초기 데이터 로드 중 오류가 발생했습니다: {}", e.getMessage());
                e.printStackTrace();
            }
        };
    }

    private void loadBrands() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/brands.json");
        try (InputStream inputStream = resource.getInputStream()) {
            List<Map<String, Object>> brandMaps = objectMapper.readValue(
                    inputStream, 
                    new TypeReference<List<Map<String, Object>>>() {}
            );
            
            logger.info("{}개의 브랜드 데이터를 로드합니다.", brandMaps.size());
            
            for (Map<String, Object> brandMap : brandMaps) {
                Brand brand = new Brand();
                brand.setBrandId((Integer) brandMap.get("id"));
                brand.setName((String) brandMap.get("name"));
                
                try {
                    createBrandUseCase.createBrand(brand);
                    logger.info("브랜드 생성 성공: {}", brand.getName());
                } catch (Exception e) {
                    logger.error("브랜드 생성 실패: {}, 원인: {}", brand.getName(), e.getMessage());
                }
            }
        }
    }

    private void loadProducts() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/products.json");
        try (InputStream inputStream = resource.getInputStream()) {
            List<Map<String, Object>> productMaps = objectMapper.readValue(
                    inputStream, 
                    new TypeReference<List<Map<String, Object>>>() {}
            );
            
            logger.info("{}개의 제품 데이터를 로드합니다.", productMaps.size());
            
            for (Map<String, Object> productMap : productMaps) {
                Product product = new Product();
                product.setProductId((Integer) productMap.get("id"));
                product.setName((String) productMap.get("name"));
                
                Object priceObj = productMap.get("price");
                if (priceObj instanceof Integer) {
                    product.setPrice(new BigDecimal((Integer) priceObj));
                } else if (priceObj instanceof Double) {
                    product.setPrice(BigDecimal.valueOf((Double) priceObj));
                } else if (priceObj instanceof String) {
                    product.setPrice(new BigDecimal((String) priceObj));
                }
                
                product.setDescription((String) productMap.get("description"));
                product.setBrandId((Integer) productMap.get("brandId"));
                product.setCategoryId((Integer) productMap.get("categoryId"));
                
                try {
                    createProductUseCase.createProduct(product);
                    logger.info("제품 생성 성공: {}", product.getName());
                } catch (Exception e) {
                    logger.error("제품 생성 실패: {}, 원인: {}", product.getName(), e.getMessage());
                }
            }
        }
    }
} 