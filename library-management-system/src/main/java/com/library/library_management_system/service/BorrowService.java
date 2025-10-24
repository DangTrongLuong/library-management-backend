package com.library.library_management_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.library.library_management_system.entity.Reader;
import com.library.library_management_system.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.library_management_system.dto.request.BorrowRequest;
import com.library.library_management_system.dto.response.BorrowResponse;
import com.library.library_management_system.entity.Books;
import com.library.library_management_system.entity.Borrow;
import com.library.library_management_system.enums.BorrowStatus;
import com.library.library_management_system.exception.InsufficientStockException;
import com.library.library_management_system.mapper.BorrowMapper;
import com.library.library_management_system.repository.BookRepository;
import com.library.library_management_system.repository.BorrowRepository;
import com.library.library_management_system.repository.ReaderRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowMapper borrowMapper;

    @Transactional
    public BorrowResponse createBorrow(BorrowRequest request) {
        // Validate reader exists
        Reader reader = readerRepository.findById(request.getReaderId())
                .orElseThrow(() -> new NotFoundException("Reader not found with id: " + request.getReaderId()));

        // Validate book exists
        Books book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + request.getBookId()));

        // Check book availability using status count
        long borrowedCount = borrowRepository.countByBookAndStatus(book, BorrowStatus.BORROWED);
        long overdueCount = borrowRepository.countByBookAndStatus(book, BorrowStatus.OVERDUE);
        
        // Tổng số sách đang được mượn = BORROWED + OVERDUE
        long currentlyBorrowed = borrowedCount + overdueCount;
        
        // Kiểm tra xem số sách đang mượn có vượt quá số lượng tồn kho không
        if (currentlyBorrowed >= book.getQuantity()) {
            throw new InsufficientStockException(
                "Book is currently unavailable. All " + book.getQuantity() + 
                " copies are borrowed. Book: " + book.getBookTitle()
            );
        }

        // Validate dates
        if (request.getDueDate().isBefore(request.getBorrowDate())) {
            throw new IllegalArgumentException("Due date cannot be before borrow date");
        }

        // **GENERATE BORROW ID: BR + 7 chữ số ngẫu nhiên**
        String borrowId;
        Random random = new Random();
        do {
            int randomNum = random.nextInt(10000000); // 0 đến 9999999
            borrowId = "BR" + String.format("%07d", randomNum); // BR0000000 - BR9999999
        } while (borrowRepository.existsById(borrowId));

        // Create borrow entity
        Borrow borrow = borrowMapper.toEntity(request);
        borrow.setReader(reader);
        borrow.setBook(book);
        borrow.setBorrowId(borrowId); // Set generated ID
        borrow.setStatus(BorrowStatus.BORROWED); // Default status

        Borrow savedBorrow = borrowRepository.save(borrow);
        return borrowMapper.toResponse(savedBorrow);
    }

    @Transactional(readOnly = true)
    public List<BorrowResponse> getAllBorrows() {
        return borrowRepository.findAll().stream()
                .map(borrowMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BorrowResponse getBorrowById(String id) { // String thay vì Long
        Borrow borrow = borrowRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Borrow not found with id: " + id));
        return borrowMapper.toResponse(borrow);
    }

    @Transactional
    public BorrowResponse updateBorrow(String id, BorrowRequest request) { // String thay vì Long
        Borrow existingBorrow = borrowRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Borrow not found with id: " + id));

        // If changing book
        if (request.getBookId() != null && !existingBorrow.getBook().getBookId().equals(request.getBookId())) {
            Books newBook = bookRepository.findById(request.getBookId())
                    .orElseThrow(() -> new NotFoundException("Book not found with id: " + request.getBookId()));

            // Check new book availability
            long borrowedCount = borrowRepository.countByBookAndStatus(newBook, BorrowStatus.BORROWED);
            long overdueCount = borrowRepository.countByBookAndStatus(newBook, BorrowStatus.OVERDUE);
            if (borrowedCount + overdueCount >= newBook.getQuantity()) {
                throw new InsufficientStockException(
                    "New book is currently unavailable. All copies are borrowed. Book: " + newBook.getBookTitle()
                );
            }

            // Update book reference
            existingBorrow.setBook(newBook);
        }

        // Validate dates
        if (request.getDueDate() != null && request.getBorrowDate() != null && 
            request.getDueDate().isBefore(request.getBorrowDate())) {
            throw new IllegalArgumentException("Due date cannot be before borrow date");
        }

        // Update borrow info
        borrowMapper.updateEntityFromRequest(request, existingBorrow);

        Borrow updatedBorrow = borrowRepository.save(existingBorrow);
        return borrowMapper.toResponse(updatedBorrow);
    }

    @Transactional
    public void deleteBorrow(String id) { // String thay vì Long
        Borrow borrow = borrowRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Borrow not found with id: " + id));

        // If book is not returned yet (BORROWED or OVERDUE), cannot delete
        if (borrow.getStatus() == BorrowStatus.BORROWED || borrow.getStatus() == BorrowStatus.OVERDUE) {
            throw new IllegalArgumentException("Cannot delete active borrow record. Please return the book first.");
        }

        borrowRepository.delete(borrow);
    }

    @Transactional(readOnly = true)
    public List<BorrowResponse> searchBorrows(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllBorrows();
        }
        return borrowRepository.searchBorrows(keyword).stream()
                .map(borrowMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public BorrowResponse returnBook(String id, LocalDate returnDate) { // String thay vì Long
        Borrow borrow = borrowRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Borrow not found with id: " + id));

        // Check if book is already returned
        if (borrow.getReturnDate() != null) {
            throw new IllegalArgumentException("Book already returned on " + borrow.getReturnDate());
        }

        // Only BORROWED or OVERDUE can be returned
        if (borrow.getStatus() != BorrowStatus.BORROWED && borrow.getStatus() != BorrowStatus.OVERDUE) {
            throw new IllegalArgumentException("Book is not currently borrowed");
        }

        // Set return date and status
        borrow.setReturnDate(returnDate);
        borrow.setStatus(BorrowStatus.RETURNED);

        Borrow updatedBorrow = borrowRepository.save(borrow);
        return borrowMapper.toResponse(updatedBorrow);
    }
    
}