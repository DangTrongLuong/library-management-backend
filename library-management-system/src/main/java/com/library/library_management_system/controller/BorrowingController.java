package com.library.library_management_system.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.library.library_management_system.entity.Borrowing;
import com.library.library_management_system.service.BorrowingService;

@RestController
@RequestMapping("/api/borrowings")
public class BorrowingController {
    @Autowired
    private BorrowingService borrowingService;

    @GetMapping("/search")
    public List<Borrowing> searchBorrowings(
            @RequestParam(required = false) String readerCode,
            @RequestParam(required = false) String bookCode,
            @RequestParam(defaultValue = "title") String sortBy
    ) {
        return borrowingService.searchBorrowings(readerCode, bookCode, sortBy);
    }
}