package com.library.library_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.library.library_management_system.dto.request.BookRequest;
import com.library.library_management_system.dto.response.BookResponse;
import com.library.library_management_system.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping(value = "/createBook", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookResponse> createBook(
            @Valid @ModelAttribute BookRequest request,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        return new ResponseEntity<>(bookService.createBook(request, image), HttpStatus.CREATED);
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/getBook/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable String id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/updateBook/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable String id,
            @Valid @ModelAttribute BookRequest request,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        return new ResponseEntity<>(bookService.updateBook(id, request, image), HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> searchBooks(
            @RequestParam(required = false) String keyword) {
        return new ResponseEntity<>(bookService.searchBooks(keyword), HttpStatus.OK);
    }
}