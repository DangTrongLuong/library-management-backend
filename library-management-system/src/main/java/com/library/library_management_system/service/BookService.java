package com.library.library_management_system.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.library.library_management_system.dto.request.BookRequest;
import com.library.library_management_system.dto.response.BookResponse;
import com.library.library_management_system.entity.Books;
import com.library.library_management_system.entity.Category;
import com.library.library_management_system.exception.DuplicateException;
import com.library.library_management_system.exception.FileStorageException;
import com.library.library_management_system.exception.NotFoundException;
import com.library.library_management_system.mapper.BookMapper;
import com.library.library_management_system.repository.BookRepository;
import com.library.library_management_system.repository.CategoryRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookMapper bookMapper;

    private final String UPLOAD_DIR = "uploads/books/";

    @Transactional
    public BookResponse createBook(BookRequest request, MultipartFile image) {
        // Validate category
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + request.getCategoryId()));

        // Check duplicate book title
        if (bookRepository.existsByBookTitle(request.getBookTitle())) {
            throw new DuplicateException("Book title already exists: " + request.getBookTitle());
        }

        // Create book entity
        Books book = bookMapper.toEntity(request);

        // Generate book ID: BOOK + 5 random digits
        String bookId;
        Random random = new Random();
        do {
            int randomNum = random.nextInt(100000);
            bookId = "BOOK" + String.format("%05d", randomNum);
        } while (bookRepository.existsById(bookId));

        // Set ID and category
        book.setBookId(bookId);
        book.setCategory(category);

        // Handle image upload
        if (image != null && !image.isEmpty()) {
            String imageUrl = saveImage(image);
            book.setImageUrl(imageUrl);
        }

        Books savedBook = bookRepository.save(book);
        return bookMapper.toResponse(savedBook);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookResponse getBookById(String id) {
        Books book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + id));
        return bookMapper.toResponse(book);
    }

    @Transactional
    public BookResponse updateBook(String id, BookRequest request, MultipartFile image) {
        Books existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + id));

        // Validate category
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + request.getCategoryId()));

        // Check duplicate book title (except current book)
        if (!existingBook.getBookTitle().equals(request.getBookTitle()) &&
                bookRepository.existsByBookTitle(request.getBookTitle())) {
            throw new DuplicateException("Book title already exists: " + request.getBookTitle());
        }

        // Update book info
        bookMapper.updateEntityFromRequest(request, existingBook);
        existingBook.setCategory(category);

        // Handle new image upload
        if (image != null && !image.isEmpty()) {
            // Delete old image if exists
            if (existingBook.getImageUrl() != null) {
                deleteImage(existingBook.getImageUrl());
            }
            // Save new image
            String imageUrl = saveImage(image);
            existingBook.setImageUrl(imageUrl);
        }

        Books updatedBook = bookRepository.save(existingBook);
        return bookMapper.toResponse(updatedBook);
    }

    @Transactional
    public void deleteBook(String id) {
        Books book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + id));

        // Delete image if exists
        if (book.getImageUrl() != null) {
            deleteImage(book.getImageUrl());
        }

        bookRepository.delete(book);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllBooks();
        }
        return bookRepository.searchBooks(keyword).stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Helper methods for image handling
    private String saveImage(MultipartFile file) {
        try {
            // Create upload directory if not exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            // Copy file to upload directory
            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return UPLOAD_DIR + uniqueFilename;
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file: " + e.getMessage());
        }
    }

    private void deleteImage(String imageUrl) {
        try {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Path filePath = Paths.get(imageUrl);
                Files.deleteIfExists(filePath);
            }
        } catch (IOException e) {
            // Log error but don't throw exception
            System.err.println("Failed to delete file: " + imageUrl);
        }
    }
}