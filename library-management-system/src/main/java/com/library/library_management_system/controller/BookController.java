package com.library.library_management_system.controller;

import com.library.library_management_system.dto.response.BookResponse;
import com.library.library_management_system.entity.Books;
import com.library.library_management_system.entity.Category;
import com.library.library_management_system.repository.CategoryRepository;
import com.library.library_management_system.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryRepository categoryRepository;

    // ✅ Thêm sách
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addBook(
            @RequestParam String bookTitle,
            @RequestParam String author,
            @RequestParam Integer publicationYear,
            @RequestParam String nxb,
            @RequestParam Integer quantity,
            @RequestParam Integer categoryId,
            @RequestPart(required = false) MultipartFile image) {
        try {
            Books book = new Books();
            book.setBookTitle(bookTitle);
            book.setAuthor(author);
            book.setPublicationYear(publicationYear);
            book.setNxb(nxb);
            book.setQuantity(quantity);

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            book.setCategory(category);

            if (image != null && !image.isEmpty()) {
                String uploadDir = System.getProperty("user.dir") + "/uploads/";
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();
                String filePath = uploadDir + image.getOriginalFilename();
                image.transferTo(new File(filePath));
                book.setImageUrl("http://localhost:8080/uploads/" + image.getOriginalFilename());
            }

            Books savedBook = bookService.addBook(book);
            return ResponseEntity.ok(bookService.mapToResponse(savedBook));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi thêm sách: " + e.getMessage());
        }
    }

    // ✅ Lấy tất cả sách
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // ✅ Lấy sách theo ID
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    // ✅ Tìm kiếm sách
    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer categoryId) {
        return ResponseEntity.ok(bookService.searchBooks(title, author, categoryId));
    }

    // ✅ Cập nhật sách
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateBook(
            @PathVariable Integer id,
            @RequestParam String bookTitle,
            @RequestParam String author,
            @RequestParam Integer publicationYear,
            @RequestParam String nxb,
            @RequestParam Integer quantity,
            @RequestParam Integer categoryId,
            @RequestPart(required = false) MultipartFile image) {
        try {
            Books book = bookService.getBookEntityById(id);
            book.setBookTitle(bookTitle);
            book.setAuthor(author);
            book.setPublicationYear(publicationYear);
            book.setNxb(nxb);
            book.setQuantity(quantity);

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            book.setCategory(category);

            if (image != null && !image.isEmpty()) {
                String uploadDir = System.getProperty("user.dir") + "/uploads/";
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();
                String filePath = uploadDir + image.getOriginalFilename();
                image.transferTo(new File(filePath));
                book.setImageUrl("http://localhost:8080/uploads/" + image.getOriginalFilename());
            }

            Books updatedBook = bookService.addBook(book);
            return ResponseEntity.ok(bookService.mapToResponse(updatedBook)); // ✅ Trả về DTO
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi cập nhật sách: " + e.getMessage());
        }
    }

    // ✅ Xóa sách
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }
}