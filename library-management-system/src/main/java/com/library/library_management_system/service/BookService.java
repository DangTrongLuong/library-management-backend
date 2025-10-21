package com.library.library_management_system.service;

import com.library.library_management_system.dto.response.BookResponse;
import com.library.library_management_system.entity.Books;
import com.library.library_management_system.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // ✅ Chuyển Entity -> DTO
    public BookResponse mapToResponse(Books book) {
        return BookResponse.builder()
                .bookId(book.getBookId())
                .bookTitle(book.getBookTitle())
                .author(book.getAuthor())
                .publicationYear(book.getPublicationYear())
                .nxb(book.getNxb())
                .quantity(book.getQuantity())
                .imageUrl(book.getImageUrl())
                .categoryName(book.getCategory() != null ? book.getCategory().getTypeName() : "Không có")
                .build();
    }

    // ✅ Lấy entity theo ID (dùng cho update)
    public Books getBookEntityById(Integer id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    // ✅ Thêm sách (trả về entity)
    public Books addBook(Books book) {
        return bookRepository.save(book);
    }

    // ✅ Lấy tất cả sách (trả về DTO)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ Lấy sách theo ID (trả về DTO)
    public BookResponse getBookById(Integer id) {
        Books book = getBookEntityById(id);
        return mapToResponse(book);
    }

    // ✅ Cập nhật sách (trả về entity)
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

    // ✅ Xóa sách
    public void deleteBook(Integer id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        try {
            bookRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Không thể xóa sách vì đang được sử dụng trong bảng khác!");
        }
    }

    // ✅ Tìm kiếm sách (trả về DTO)
    public List<BookResponse> searchBooks(String title, String author, Integer categoryId) {
        return bookRepository.searchBooks(title, author, categoryId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}