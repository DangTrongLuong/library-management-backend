package com.library.library_management_system.service;

import com.library.library_management_system.entity.Books;
import com.library.library_management_system.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Books addBook(Books book) {
        return bookRepository.save(book);
    }

    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }

    public Books getBookById(Integer id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    public Books updateBook(Integer id, Books updatedBook) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setBookTitle(updatedBook.getBookTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setPublicationYear(updatedBook.getPublicationYear());
                    book.setCategory(updatedBook.getCategory());
                    book.setNxb(updatedBook.getNxb());
                    book.setQuantity(updatedBook.getQuantity());
                    book.setImageUrl(updatedBook.getImageUrl());
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }
    public void deleteBook(Integer id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
    public List<Books> searchBooks(String title, String author, Integer categoryId) {
        return bookRepository.searchBooks(title, author, categoryId);
    }

}
