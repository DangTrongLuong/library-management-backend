package com.library.library_management_system.mapper;

import com.library.library_management_system.dto.request.FineRequest;
import com.library.library_management_system.dto.response.FineResponse;
import com.library.library_management_system.entity.Fine;
import org.springframework.stereotype.Component;

@Component
public class FineMapper {

    public Fine toEntity(FineRequest request) {
        Fine fine = new Fine();
        fine.setReason(request.getReason());
        fine.setFineDate(request.getFineDate());
        fine.setPaymentStatus(request.getPaymentStatus());
        fine.setPaymentDate(request.getPaymentDate());
        fine.setNotes(request.getNotes());
        return fine;
    }

    public FineResponse toResponse(Fine fine) {
        return FineResponse.builder()
                .id(fine.getId())
                .borrowId(fine.getBorrow().getBorrowId())
                .readerId(fine.getBorrow().getReader().getReaderId())
                .readerName(fine.getBorrow().getReader().getName())
                .bookId(fine.getBorrow().getBook().getBookId())
                .bookTitle(fine.getBorrow().getBook().getBookTitle())
                .reason(fine.getReason())
                .reasonDescription(fine.getReason().getDescription())
                .amount(fine.getAmount())
                .fineDate(fine.getFineDate())
                .paymentStatus(fine.getPaymentStatus())
                .paymentStatusDescription(fine.getPaymentStatus().getDescription())
                .paymentDate(fine.getPaymentDate())
                .notes(fine.getNotes())
                .createdAt(fine.getCreatedAt())
                .updatedAt(fine.getUpdatedAt())
                .build();
    }

    public void updateEntityFromRequest(FineRequest request, Fine fine) {
        if (request.getReason() != null) {
            fine.setReason(request.getReason());
        }
        if (request.getFineDate() != null) {
            fine.setFineDate(request.getFineDate());
        }
        if (request.getPaymentStatus() != null) {
            fine.setPaymentStatus(request.getPaymentStatus());
        }
        if (request.getPaymentDate() != null) {
            fine.setPaymentDate(request.getPaymentDate());
        }
        if (request.getNotes() != null) {
            fine.setNotes(request.getNotes());
        }
    }
}