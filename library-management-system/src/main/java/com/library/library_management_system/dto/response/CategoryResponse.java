package com.library.library_management_system.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {
    private Integer categoryId;
    private String typeName;
    private String description;
    private String shelfPosition;
    private String note;
}

