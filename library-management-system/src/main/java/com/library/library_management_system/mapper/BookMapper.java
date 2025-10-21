package com.library.library_management_system.mapper;

import org.springframework.stereotype.Component;

import com.library.library_management_system.dto.request.BookRequest;
import com.library.library_management_system.dto.response.BookResponse;
import com.library.library_management_system.entity.Books;

@Component
public class BookMapper {

    public Books toEntity(BookRequest request) {
        Books book = new Books();
        book.setBookTitle(request.getBookTitle());
        book.setAuthor(request.getAuthor());
        book.setPublicationYear(request.getPublicationYear());
        book.setNxb(request.getNxb());
        book.setQuantity(request.getQuantity());
        return book;
    }

    public BookResponse toResponse(Books book) {
        BookResponse response = new BookResponse();
        response.setBookId(book.getBookId().toString());
        response.setBookTitle(book.getBookTitle());
        response.setAuthor(book.getAuthor());
        response.setPublicationYear(book.getPublicationYear());

        if (book.getCategory() != null) {
            response.setCategoryId(book.getCategory().getCategoryId());
            response.setCategoryName(book.getCategory().getTypeName());
        }

        response.setNxb(book.getNxb());
        response.setQuantity(book.getQuantity());
        response.setImageUrl(book.getImageUrl());

        return response;
    }

    public void updateEntityFromRequest(BookRequest request, Books book) {
        book.setBookTitle(request.getBookTitle());
        book.setAuthor(request.getAuthor());
        book.setPublicationYear(request.getPublicationYear());
        book.setNxb(request.getNxb());
        book.setQuantity(request.getQuantity());
    }
}