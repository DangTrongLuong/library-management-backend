package com.library.library_management_system.controller;

import com.library.library_management_system.dto.request.FineRequest;
import com.library.library_management_system.dto.request.FineAmountRequest;
import com.library.library_management_system.dto.response.FineResponse;
import com.library.library_management_system.enums.FineReason;
import com.library.library_management_system.enums.PaymentStatus;
import com.library.library_management_system.service.FineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/fines")
public class FineController {

    @Autowired
    private FineService fineService;

    @GetMapping
    public ResponseEntity<Page<FineResponse>> getAllFines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), "fineDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(fineService.getAllFines(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FineResponse> getFineById(@PathVariable Long id) {
        return ResponseEntity.ok(fineService.getFineById(id));
    }

    @PostMapping
    public ResponseEntity<FineResponse> createFine(@Valid @RequestBody FineRequest request) {
        FineResponse createdFine = fineService.createFine(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FineResponse> updateFine(@PathVariable Long id,
                                                  @Valid @RequestBody FineRequest request) {
        return ResponseEntity.ok(fineService.updateFine(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFine(@PathVariable Long id) {
        fineService.deleteFine(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<FineResponse> updatePaymentStatus(@PathVariable Long id,
                                                           @RequestParam PaymentStatus status) {
        return ResponseEntity.ok(fineService.updatePaymentStatus(id, status));
    }

    @GetMapping("/search")
    public ResponseEntity<List<FineResponse>> searchFines(
            @RequestParam(required = false) String borrowId,
            @RequestParam(required = false) FineReason reason) {
        return ResponseEntity.ok(fineService.searchFines(borrowId, reason));
    }

    
@PostMapping("/calculate-amount")
public ResponseEntity<BigDecimal> calculateFineAmount(@RequestBody FineAmountRequest request) {
    BigDecimal amount = fineService.calculateFineAmount(request.getBorrowId(), request.getReason());
    return ResponseEntity.ok(amount);
}
}