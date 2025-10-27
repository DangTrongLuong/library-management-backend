package com.library.library_management_system.mapper;

import org.springframework.stereotype.Component;

import com.library.library_management_system.dto.request.BorrowRequest;
import com.library.library_management_system.dto.response.BorrowResponse;
import com.library.library_management_system.entity.Borrow;

@Component
public class BorrowMapper {
    public Borrow toEntity(BorrowRequest request) {
        Borrow borrow = new Borrow();
        borrow.setBorrowId(request.getBorrowId());
        borrow.setBorrowDate(request.getBorrowDate());
        borrow.setDueDate(request.getDueDate());
        borrow.setReturnDate(request.getReturnDate());
        borrow.setStatus(request.getStatus());
        borrow.setBorrowPrice(request.getBorrowPrice());
        borrow.setNotes(request.getNotes());
        return borrow;
    }

    public BorrowResponse toResponse(Borrow borrow) {
        BorrowResponse response = new BorrowResponse();
        response.setBorrowId(borrow.getBorrowId());
        
        // Reader info
        if (borrow.getReader() != null) {
            response.setReaderId(borrow.getReader().getReaderId());
            response.setReaderName(borrow.getReader().getName());
            response.setReaderPhone(borrow.getReader().getNumberPhone());
            response.setReaderEmail(borrow.getReader().getEmail());
        }
        
        // Book info
        if (borrow.getBook() != null) {
            response.setBookId(borrow.getBook().getBookId());
            response.setBookTitle(borrow.getBook().getBookTitle());
            response.setImageUrl(borrow.getBook().getImageUrl());
            response.setBookAuthor(borrow.getBook().getAuthor());
            if (borrow.getBook().getCategory() != null) {
                response.setCategoryName(borrow.getBook().getCategory().getTypeName());
            }
        }
        
        response.setBorrowDate(borrow.getBorrowDate());
        response.setDueDate(borrow.getDueDate());
        response.setReturnDate(borrow.getReturnDate());
        response.setStatus(borrow.getStatus());
        response.setBorrowPrice(borrow.getBorrowPrice());
        response.setNotes(borrow.getNotes());
        response.setCreatedAt(borrow.getCreatedAt());
        response.setUpdatedAt(borrow.getUpdatedAt());
        
        return response;
    }

    public void updateEntityFromRequest(BorrowRequest request, Borrow borrow) {
        borrow.setBorrowDate(request.getBorrowDate());
        borrow.setDueDate(request.getDueDate());
        borrow.setReturnDate(request.getReturnDate());
        borrow.setStatus(request.getStatus());
        borrow.setBorrowPrice(request.getBorrowPrice());
        borrow.setNotes(request.getNotes());
    }

    
}