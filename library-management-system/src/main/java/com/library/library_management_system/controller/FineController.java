package com.library.library_management_system.controller;

import com.library.library_management_system.dto.request.FineRequest;
import com.library.library_management_system.dto.response.FineResponse;
import com.library.library_management_system.enums.FineReason;
import com.library.library_management_system.enums.PaymentStatus;
import com.library.library_management_system.service.FineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Controller cho quản lý phạt
 * Expose các API endpoints cho Fine operations
 */

@RestController
@RequestMapping("/api/fines")
@RequiredArgsConstructor
public class FineController {

    private final FineService fineService;

    // Lấy tất cả phạt
    @GetMapping
    public ResponseEntity<List<FineResponse>> getAllFines() {
        return ResponseEntity.ok(fineService.getAllFines());
    }

    // Lấy phạt theo ID
    @GetMapping("/{id}")
    public ResponseEntity<FineResponse> getFineById(@PathVariable Long id) {
        return ResponseEntity.ok(fineService.getFineById(id));
    }

    // Tạo phạt mới
    @PostMapping
    public ResponseEntity<FineResponse> createFine(@Valid @RequestBody FineRequest request) {
        FineResponse createdFine = fineService.createFine(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFine);
    }

    // Cập nhật thông tin phạt
    @PutMapping("/{id}")
    public ResponseEntity<FineResponse> updateFine(@PathVariable Long id,
                                                   @Valid @RequestBody FineRequest request) {
        return ResponseEntity.ok(fineService.updateFine(id, request));
    }

    // Xóa phạt
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFine(@PathVariable Long id) {
        fineService.deleteFine(id);
        return ResponseEntity.noContent().build();
    }

    // Cập nhật trạng thái thanh toán
    @PatchMapping("/{id}/status")
    public ResponseEntity<FineResponse> updatePaymentStatus(@PathVariable Long id,
                                                            @RequestParam PaymentStatus status) {
        return ResponseEntity.ok(fineService.updatePaymentStatus(id, status));
    }

    // Lọc theo Borrow ID
    @GetMapping("/borrow/{borrowId}")
    public ResponseEntity<List<FineResponse>> getFinesByBorrowId(@PathVariable String borrowId) {
        return ResponseEntity.ok(fineService.getFinesByBorrowId(borrowId));
    }

    // Lọc theo trạng thái thanh toán
    @GetMapping("/status")
    public ResponseEntity<List<FineResponse>> getFinesByPaymentStatus(@RequestParam PaymentStatus status) {
        return ResponseEntity.ok(fineService.getFinesByPaymentStatus(status));
    }

    // Lọc theo lý do phạt
    @GetMapping("/reason")
    public ResponseEntity<List<FineResponse>> getFinesByReason(@RequestParam FineReason reason) {
        return ResponseEntity.ok(fineService.getFinesByReason(reason));
    }

    // Lọc theo khoảng thời gian
    @GetMapping("/date-range")
    public ResponseEntity<List<FineResponse>> getFinesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(fineService.getFinesByDateRange(startDate, endDate));
    }

    // Lọc theo Reader ID
    @GetMapping("/reader/{readerId}")
    public ResponseEntity<List<FineResponse>> getFinesByReaderId(@PathVariable String readerId) {
        return ResponseEntity.ok(fineService.getFinesByReaderId(readerId));
    }
}
