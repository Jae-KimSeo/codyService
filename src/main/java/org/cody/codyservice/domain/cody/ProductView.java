package org.cody.codyservice.domain.cody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

// 읽기 전용 상품 VO 또는 ReadModel
@Getter
@Setter
public class ProductView {
    private Integer productId;
    private String name;
    private BigDecimal price;
    private String description;
    private String brandName;
    private String categoryName;
    private LocalDateTime createdAt;
    
    // 기본 생성자
    public ProductView() {
    }
    
    // 수동 생성자
    public ProductView(Integer productId, String name, BigDecimal price, String description, 
                      String brandName, String categoryName, LocalDateTime createdAt) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.brandName = brandName;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
    }
} 