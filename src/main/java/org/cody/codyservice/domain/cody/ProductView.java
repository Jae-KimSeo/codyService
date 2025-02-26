package org.cody.codyservice.domain.cody;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 읽기 전용 상품 VO 또는 ReadModel
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductView {
    private Integer productId;
    private String name;
    private BigDecimal price;
    private String description;
    private String brandName;
    private String categoryName;
    private LocalDateTime createdAt;
} 