package com.library.library_management_system.service.impl;

import com.library.library_management_system.entity.Books;
import com.library.library_management_system.repository.BookRepository;
import com.library.library_management_system.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Books addBook(Books book) {
        return bookRepository.save(book);
    }

    @Override
    public Books updateBook(Integer id, Books book) {
        Optional<Books> existing = bookRepository.findById(id);
        if (existing.isPresent()) {
            Books updated = existing.get();
            updated.setBookTitle(book.getBookTitle());
            updated.setAuthor(book.getAuthor());
            updated.setCategory(book.getCategory());
            updated.setPublicationYear(book.getPublicationYear());
            updated.setNxb(book.getNxb());
            updated.setQuantity(book.getQuantity());
            updated.setImageUrl(book.getImageUrl());
            return bookRepository.save(updated);
        }
        throw new RuntimeException("Book not found");
    }

    @Override
    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Books> searchBooks(String keyword) {
        return bookRepository.searchBooks(keyword);
    }
}
