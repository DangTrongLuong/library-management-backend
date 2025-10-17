package com.library.library_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank(message = "Type name is required")
    @Size(max = 100)
    private String typeName;

    private String description;

    @NotBlank(message = "Shelf position is required")
    @Size(max = 50)
    private String shelfPosition;

    private String note;
}

