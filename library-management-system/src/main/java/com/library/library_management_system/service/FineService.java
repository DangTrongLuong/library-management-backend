package com.library.library_management_system.service;

import com.library.library_management_system.dto.request.FineRequest;
import com.library.library_management_system.dto.response.FineResponse;
import com.library.library_management_system.entity.Borrow;
import com.library.library_management_system.entity.Fine;
import com.library.library_management_system.enums.FineReason;
import com.library.library_management_system.enums.PaymentStatus;
import com.library.library_management_system.mapper.FineMapper;
import com.library.library_management_system.repository.BorrowRepository;
import com.library.library_management_system.repository.FineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service xử lý logic nghiệp vụ cho Fine
 * Chứa các business logic và orchestration
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FineService {

    private final FineRepository fineRepository;
    private final BorrowRepository borrowRepository;
    private final FineMapper fineMapper;

    /**
     * Lấy danh sách tất cả phạt
     */
    @Transactional(readOnly = true)
    public List<FineResponse> getAllFines() {
        log.info("Fetching all fines");
        List<Fine> fines = fineRepository.findAll();
        return fines.stream()
                .map(fineMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lấy thông tin phạt theo ID
     */
    @Transactional(readOnly = true)
    public FineResponse getFineById(Long id) {
        log.info("Fetching fine with id: {}", id);
        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fine not found with id: " + id));
        return fineMapper.toResponse(fine);
    }

    /**
     * Tạo phạt mới
     */
    @Transactional
    public FineResponse createFine(FineRequest request) {
        log.info("Creating new fine for borrow id: {}", request.getBorrowId());

        // Validate và lấy Borrow entity
        Borrow borrow = borrowRepository.findById(request.getBorrowId())
                .orElseThrow(() -> new RuntimeException("Borrow not found with id: " + request.getBorrowId()));

        // Map request to entity
        Fine fine = fineMapper.toEntity(request);
        fine.setBorrow(borrow);

        // Save
        Fine savedFine = fineRepository.save(fine);
        log.info("Fine created successfully with id: {}", savedFine.getId());

        return fineMapper.toResponse(savedFine);
    }

    /**
     * Cập nhật thông tin phạt
     */
    @Transactional
    public FineResponse updateFine(Long id, FineRequest request) {
        log.info("Updating fine with id: {}", id);

        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fine not found with id: " + id));

        // Update fields từ request
        fineMapper.updateEntity(request, fine);

        // Nếu borrowId thay đổi, cần update borrow relationship
        if (!fine.getBorrow().getBorrowId().equals(request.getBorrowId())) {
            Borrow newBorrow = borrowRepository.findById(request.getBorrowId())
                    .orElseThrow(() -> new RuntimeException("Borrow not found with id: " + request.getBorrowId()));
            fine.setBorrow(newBorrow);
        }

        Fine updatedFine = fineRepository.save(fine);
        log.info("Fine updated successfully with id: {}", id);

        return fineMapper.toResponse(updatedFine);
    }

    /**
     * Xóa phạt
     */
    @Transactional
    public void deleteFine(Long id) {
        log.info("Deleting fine with id: {}", id);

        if (!fineRepository.existsById(id)) {
            throw new RuntimeException("Fine not found with id: " + id);
        }

        fineRepository.deleteById(id);
        log.info("Fine deleted successfully with id: {}", id);
    }

    /**
     * Lấy danh sách phạt theo Borrow ID
     */
    @Transactional(readOnly = true)
    public List<FineResponse> getFinesByBorrowId(String borrowId) {
        log.info("Fetching fines for borrow id: {}", borrowId);
        List<Fine> fines = fineRepository.findByBorrowId(borrowId);
        return fines.stream()
                .map(fineMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lấy danh sách phạt theo trạng thái thanh toán
     */
    @Transactional(readOnly = true)
    public List<FineResponse> getFinesByPaymentStatus(PaymentStatus paymentStatus) {
        log.info("Fetching fines with payment status: {}", paymentStatus);
        List<Fine> fines = fineRepository.findByPaymentStatus(paymentStatus);
        return fines.stream()
                .map(fineMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lấy danh sách phạt theo lý do
     */
    @Transactional(readOnly = true)
    public List<FineResponse> getFinesByReason(FineReason reason) {
        log.info("Fetching fines with reason: {}", reason);
        List<Fine> fines = fineRepository.findByReason(reason);
        return fines.stream()
                .map(fineMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lấy danh sách phạt theo khoảng thời gian
     */
    @Transactional(readOnly = true)
    public List<FineResponse> getFinesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Fetching fines between {} and {}", startDate, endDate);
        List<Fine> fines = fineRepository.findByFineDateBetween(startDate, endDate);
        return fines.stream()
                .map(fineMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lấy danh sách phạt theo Reader ID
     */
    @Transactional(readOnly = true)
    public List<FineResponse> getFinesByReaderId(String readerId) {
        log.info("Fetching fines for reader id: {}", readerId);
        List<Fine> fines = fineRepository.findByReaderId(readerId);
        return fines.stream()
                .map(fineMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Cập nhật trạng thái thanh toán
     */
    @Transactional
    public FineResponse updatePaymentStatus(Long id, PaymentStatus paymentStatus) {
        log.info("Updating payment status for fine id: {} to {}", id, paymentStatus);

        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fine not found with id: " + id));

        fine.setPaymentStatus(paymentStatus);

        // Nếu chuyển sang PAID, set payment date
        if (paymentStatus == PaymentStatus.PAID && fine.getPaymentDate() == null) {
            fine.setPaymentDate(LocalDateTime.now());
        }

        Fine updatedFine = fineRepository.save(fine);
        log.info("Payment status updated successfully for fine id: {}", id);

        return fineMapper.toResponse(updatedFine);
    }

    /**
     * Tính tổng số tiền phạt chưa thanh toán theo Borrow ID
     */
    @Transactional(readOnly = true)
    public BigDecimal getTotalUnpaidAmount(String borrowId) {
        log.info("Calculating total unpaid amount for borrow id: {}", borrowId);
        return fineRepository.getTotalUnpaidAmountByBorrowId(borrowId);
    }

    /**
     * Kiểm tra xem Borrow ID có phạt chưa thanh toán không
     */
    @Transactional(readOnly = true)
    public boolean hasUnpaidFines(String borrowId) {
        log.info("Checking unpaid fines for borrow id: {}", borrowId);
        return fineRepository.existsByBorrowIdAndUnpaidStatus(borrowId);
    }

    /**
     * Tính tổng doanh thu từ phạt đã thanh toán trong khoảng thời gian
     */
    @Transactional(readOnly = true)
    public BigDecimal getTotalPaidAmountBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Calculating total paid amount between {} and {}", startDate, endDate);
        return fineRepository.getTotalPaidAmountBetweenDates(startDate, endDate);
    }
}
