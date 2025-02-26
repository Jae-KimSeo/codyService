package org.cody.codyservice.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "categories")
@Getter
@Setter
public class CategoryProperties {
    
    private String data;

    public Map<Integer, String> getCategoryMap() {
        Map<Integer, String> categoryMap = new HashMap<>();
        
        if (data == null || data.trim().isEmpty()) {
            return categoryMap;
        }
        
        String[] categoryEntries = data.split(",");
        for (String entry : categoryEntries) {
            String[] parts = entry.split(":");
            if (parts.length == 2) {
                try {
                    Integer id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    categoryMap.put(id, name);
                } catch (NumberFormatException e) {
                    // ID가 정수가 아닌 경우 무시
                }
            }
        }
        
        return categoryMap;
    }
} 