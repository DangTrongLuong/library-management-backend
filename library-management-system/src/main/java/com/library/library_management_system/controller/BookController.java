 package com.library.library_management_system.controller;

import com.library.library_management_system.entity.Books;
import com.library.library_management_system.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {

    @Autowired
    private BookService bookService;

    // Thêm sách
    @PostMapping
    public ResponseEntity<Books> addBook(@RequestBody Books book) {
        Books newBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }


    // Lấy danh sách tất cả sách
    @GetMapping
    public ResponseEntity<List<Books>> getAllBooks() {
        List<Books> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // Lấy chi tiết sách theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Books> getBookById(@PathVariable Integer id) {
        Books book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    // Sửa sách
    @PutMapping("/{id}")
    public ResponseEntity<Books> updateBook(@PathVariable Integer id, @RequestBody Books book) {
        Books updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }
    // Xóa sách theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Books>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer categoryId) {

        List<Books> result = bookService.searchBooks(title, author, categoryId);
        return ResponseEntity.ok(result);
    }
}