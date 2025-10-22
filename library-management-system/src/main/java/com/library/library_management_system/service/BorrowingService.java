package com.library.library_management_system.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.library.library_management_system.entity.Borrowing;
import com.library.library_management_system.repository.BorrowingRepository;

@Service
public class BorrowingService {
    @Autowired
    private BorrowingRepository borrowingRepository;

    public List<Borrowing> searchBorrowings(String readerCode, String bookCode, String sortBy) {
        Sort sort = Sort.by(sortBy).ascending();
        return borrowingRepository.findByReaderCodeContainingAndBookCodeContaining(
                readerCode != null ? readerCode : "",
                bookCode != null ? bookCode : "",
                sort
        );
    }
}