package com.library.library_management_system.mapper;

import com.library.library_management_system.dto.request.FineRequest;
import com.library.library_management_system.dto.response.FineResponse;
import com.library.library_management_system.entity.Borrow;
import com.library.library_management_system.entity.Fine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * MapStruct Mapper cho Fine Entity
 * Tự động generate implementation code tại compile time
 */
@Mapper(componentModel = "spring")
public interface FineMapper {

    /**
     * Convert Entity sang Response DTO
     */
    @Mapping(source = "borrow.borrowId", target = "borrowId")
    @Mapping(source = "borrow.reader.name", target = "readerName")
    @Mapping(source = "borrow.book.bookTitle", target = "bookTitle")
    @Mapping(source = "reason", target = "reasonDescription", qualifiedByName = "reasonToDescription")
    @Mapping(source = "paymentStatus", target = "paymentStatusDescription", qualifiedByName = "statusToDescription")
    FineResponse toResponse(Fine fine);

    /**
     * Convert Request DTO sang Entity (cho create)
     * Lưu ý: Borrow object cần được set manually trong Service
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "borrow", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Fine toEntity(FineRequest request);

    /**
     * Update Entity từ Request DTO (cho update)
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "borrow", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(FineRequest request, @MappingTarget Fine fine);

    /**
     * Helper method: Convert FineReason enum sang description
     */
    @Named("reasonToDescription")
    default String reasonToDescription(com.library.library_management_system.enums.FineReason reason) {
        return reason != null ? reason.getDescription() : null;
    }

    /**
     * Helper method: Convert PaymentStatus enum sang description
     */
    @Named("statusToDescription")
    default String statusToDescription(com.library.library_management_system.enums.PaymentStatus status) {
        return status != null ? status.getDescription() : null;
    }
}