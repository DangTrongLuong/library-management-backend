package com.library.library_management_system.repository;

import com.library.library_management_system.entity.Fine;
import com.library.library_management_system.enums.FineReason;
import com.library.library_management_system.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {

    Page<Fine> findAll(Pageable pageable);

    @Query("SELECT f FROM Fine f WHERE f.borrow.borrowId = :borrowId")
    List<Fine> findByBorrowId(@Param("borrowId") String borrowId);

    List<Fine> findByPaymentStatus(PaymentStatus paymentStatus);

    List<Fine> findByReason(FineReason reason);

    List<Fine> findByFineDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT f FROM Fine f WHERE (:borrowId IS NULL OR f.borrow.borrowId = :borrowId) AND (:reason IS NULL OR f.reason = :reason)")
    List<Fine> searchByBorrowIdAndReason(@Param("borrowId") String borrowId, @Param("reason") FineReason reason);

    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM Fine f WHERE f.borrow.borrowId = :borrowId AND f.paymentStatus = 'UNPAID'")
    BigDecimal getTotalUnpaidAmountByBorrowId(@Param("borrowId") String borrowId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Fine f WHERE f.borrow.borrowId = :borrowId AND f.paymentStatus = 'UNPAID'")
    boolean existsByBorrowIdAndUnpaidStatus(@Param("borrowId") String borrowId);

    @Query("SELECT f FROM Fine f WHERE f.borrow.reader.readerId = :readerId")
    List<Fine> findByReaderId(@Param("readerId") String readerId);

    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM Fine f WHERE f.paymentStatus = 'PAID' AND f.paymentDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalPaidAmountBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}