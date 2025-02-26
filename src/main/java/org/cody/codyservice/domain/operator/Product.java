package org.cody.codyservice.domain.operator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer productId;
    private String name;
    private BigDecimal price;
    private String description;
    private Integer brandId;
    private Integer categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 