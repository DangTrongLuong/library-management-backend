package com.library.library_management_system.service;

import com.library.library_management_system.entity.Books;
import com.library.library_management_system.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Books addBook(Books book) {
        return bookRepository.save(book);
    }


}
