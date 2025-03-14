package org.cody.codyservice.domain.operator;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    private Integer brandId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public Brand(Integer brandId, String name) {
        this.brandId = brandId;
        this.name = name;
    }
} 