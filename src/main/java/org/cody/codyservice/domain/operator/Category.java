package org.cody.codyservice.domain.operator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Category {
    private Integer categoryId;
    private String name;
    
    public Category(Integer categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }
} 