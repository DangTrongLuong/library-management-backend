package com.library.library_management_system.service;

import com.library.library_management_system.dto.request.FineRequest;
import com.library.library_management_system.dto.response.FineResponse;
import com.library.library_management_system.entity.Borrow;
import com.library.library_management_system.entity.Fine;
import com.library.library_management_system.enums.FineReason;
import com.library.library_management_system.enums.PaymentStatus;
import com.library.library_management_system.exception.NotFoundException;
import com.library.library_management_system.mapper.FineMapper;
import com.library.library_management_system.repository.BorrowRepository;
import com.library.library_management_system.repository.FineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FineService {

    private final FineRepository fineRepository;
    private final BorrowRepository borrowRepository;
    private final FineMapper fineMapper;

    @Transactional(readOnly = true)
    public Page<FineResponse> getAllFines(Pageable pageable) {
        log.info("Fetching all fines with pagination");
        Page<Fine> fines = fineRepository.findAll(pageable);
        return fines.map(fineMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public FineResponse getFineById(Long id) {
        log.info("Fetching fine with id: {}", id);
        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fine not found with id: " + id));
        return fineMapper.toResponse(fine);
    }

    @Transactional
    public FineResponse createFine(FineRequest request) {
        log.info("Creating new fine for borrow id: {}", request.getBorrowId());

        Borrow borrow = borrowRepository.findById(request.getBorrowId())
                .orElseThrow(() -> new NotFoundException("Borrow not found with id: " + request.getBorrowId()));

        Fine fine = fineMapper.toEntity(request);
        fine.setBorrow(borrow);

        BigDecimal amount = calculateAmount(borrow, request.getReason());
        fine.setAmount(amount);

        if (fine.getFineDate() == null) {
            fine.setFineDate(LocalDateTime.now());
        }

        Fine savedFine = fineRepository.save(fine);
        log.info("Fine created successfully with id: {}", savedFine.getId());
        return fineMapper.toResponse(savedFine);
    }

    @Transactional
    public FineResponse updateFine(Long id, FineRequest request) {
        log.info("Updating fine with id: {}", id);

        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fine not found with id: " + id));

        fineMapper.updateEntityFromRequest(request, fine);

        if (request.getReason() != null) {
            BigDecimal newAmount = calculateAmount(fine.getBorrow(), request.getReason());
            fine.setAmount(newAmount);
        }

        Fine updatedFine = fineRepository.save(fine);
        log.info("Fine updated successfully with id: {}", id);
        return fineMapper.toResponse(updatedFine);
    }

    @Transactional
    public void deleteFine(Long id) {
        log.info("Deleting fine with id: {}", id);
        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fine not found with id: " + id));
        fineRepository.delete(fine);
        log.info("Fine deleted successfully with id: {}", id);
    }

    @Transactional(readOnly = true)
    public List<FineResponse> searchFines(String borrowId, FineReason reason) {
        log.info("Searching fines with borrowId: {}, reason: {}", borrowId, reason);
        List<Fine> fines = fineRepository.searchByBorrowIdAndReason(borrowId, reason);
        return fines.stream()
                .map(fineMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public FineResponse updatePaymentStatus(Long id, PaymentStatus paymentStatus) {
        log.info("Updating payment status for fine id: {} to {}", id, paymentStatus);

        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fine not found with id: " + id));

        fine.setPaymentStatus(paymentStatus);

        if (paymentStatus == PaymentStatus.PAID && fine.getPaymentDate() == null) {
            fine.setPaymentDate(LocalDateTime.now());
        }

        Fine updatedFine = fineRepository.save(fine);
        log.info("Payment status updated successfully for fine id: {}", id);
        return fineMapper.toResponse(updatedFine);
    }

    private BigDecimal calculateAmount(Borrow borrow, FineReason reason) {
        BigDecimal bookPrice = borrow.getBook().getPrice();
        BigDecimal borrowPrice = borrow.getBorrowPrice();
        LocalDate now = LocalDate.now();

        switch (reason) {
            case LOST_BOOK:
                return bookPrice.add(borrowPrice);
            case DAMAGED_BOOK:
                return bookPrice.divide(BigDecimal.valueOf(2), 2, BigDecimal.ROUND_HALF_UP).add(borrowPrice);
            case OVERDUE:
                long daysLate = ChronoUnit.DAYS.between(borrow.getDueDate(), now);
                if (daysLate < 0) daysLate = 0;
                return borrowPrice.multiply(BigDecimal.valueOf(daysLate));
            default:
                throw new IllegalArgumentException("Invalid fine reason: " + reason);
        }
    }

    // Thêm vào FineService.java
@Transactional(readOnly = true)
public BigDecimal calculateFineAmount(String borrowId, FineReason reason) {
    log.info("Calculating fine amount for borrow id: {} and reason: {}", borrowId, reason);

    Borrow borrow = borrowRepository.findById(borrowId)
            .orElseThrow(() -> new NotFoundException("Borrow not found with id: " + borrowId));

    return calculateAmount(borrow, reason);
}
}