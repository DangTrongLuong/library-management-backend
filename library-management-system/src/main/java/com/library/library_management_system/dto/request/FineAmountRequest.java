package com.library.library_management_system.dto.request;

import com.library.library_management_system.enums.FineReason;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FineAmountRequest {
    @NotBlank(message = "Borrow ID is required")
    String borrowId;

    @NotNull(message = "Fine reason is required")
    FineReason reason;
}