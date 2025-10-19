package com.library.library_management_system.service;

import com.library.library_management_system.entity.Books;
import java.util.List;

public interface BookService {
    List<Books> getAllBooks();
    Books addBook(Books book);
    Books updateBook(Integer id, Books book);
    void deleteBook(Integer id);
    List<Books> searchBooks(String keyword);
}
