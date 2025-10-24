package com.library.library_management_system.controller;

import com.library.library_management_system.dto.request.BorrowRequest;
import com.library.library_management_system.dto.response.BorrowResponse;
import com.library.library_management_system.service.BorrowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/borrows")
public class BorrowController {
    
    @Autowired
    private BorrowService borrowService;

    @PostMapping("/createBorrow")
    public ResponseEntity<BorrowResponse> createBorrow(@Valid @RequestBody BorrowRequest request) {
        return new ResponseEntity<>(borrowService.createBorrow(request), HttpStatus.CREATED);
    }

    @GetMapping("/getAllBorrows")
    public ResponseEntity<List<BorrowResponse>> getAllBorrows() {
        return new ResponseEntity<>(borrowService.getAllBorrows(), HttpStatus.OK);
    }

    @GetMapping("/getBorrow/{id}")
    public ResponseEntity<BorrowResponse> getBorrowById(@PathVariable String id) {
        return new ResponseEntity<>(borrowService.getBorrowById(id), HttpStatus.OK);
    }

    @PutMapping("/updateBorrow/{id}")
    public ResponseEntity<BorrowResponse> updateBorrow(
            @PathVariable String id,
            @Valid @RequestBody BorrowRequest request) {
        return new ResponseEntity<>(borrowService.updateBorrow(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/deleteBorrow/{id}")
    public ResponseEntity<Void> deleteBorrow(@PathVariable String id) {
        borrowService.deleteBorrow(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BorrowResponse>> searchBorrows(
            @RequestParam(required = false) String keyword) {
        return new ResponseEntity<>(borrowService.searchBorrows(keyword), HttpStatus.OK);
    }

    @PutMapping("/returnBook/{id}")
    public ResponseEntity<BorrowResponse> returnBook(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate) {
        return new ResponseEntity<>(borrowService.returnBook(id, returnDate), HttpStatus.OK);
    }
}