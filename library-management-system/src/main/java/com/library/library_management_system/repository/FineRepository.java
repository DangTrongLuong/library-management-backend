package com.library.library_management_system.repository;

import com.library.library_management_system.entity.Borrow;
import com.library.library_management_system.entity.Fine;
import com.library.library_management_system.enums.FineReason;
import com.library.library_management_system.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository để thao tác với bảng Fine
 * Kế thừa JpaRepository để có sẵn các method CRUD cơ bản
 */
@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {

    /**
     * Tìm tất cả phạt theo Borrow entity
     */
    List<Fine> findByBorrow(Borrow borrow);

    /**
     * Tìm tất cả phạt theo Borrow ID
     */
    @Query("SELECT f FROM Fine f WHERE f.borrow.borrowId = :borrowId")
    List<Fine> findByBorrowId(@Param("borrowId") String borrowId);

    /**
     * Tìm phạt theo trạng thái thanh toán
     */
    List<Fine> findByPaymentStatus(PaymentStatus paymentStatus);

    /**
     * Tìm phạt theo lý do
     */
    List<Fine> findByReason(FineReason reason);

    /**
     * Tìm phạt theo khoảng thời gian
     */
    List<Fine> findByFineDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Tìm phạt theo Borrow ID và trạng thái thanh toán
     */
    @Query("SELECT f FROM Fine f WHERE f.borrow.borrowId = :borrowId AND f.paymentStatus = :status")
    List<Fine> findByBorrowIdAndPaymentStatus(
            @Param("borrowId") String borrowId,
            @Param("status") PaymentStatus status
    );

    /**
     * Tính tổng số tiền phạt chưa thanh toán theo Borrow ID
     */
    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM Fine f WHERE f.borrow.borrowId = :borrowId AND f.paymentStatus = 'UNPAID'")
    BigDecimal getTotalUnpaidAmountByBorrowId(@Param("borrowId") String borrowId);

    /**
     * Đếm số lượng phạt theo trạng thái thanh toán
     */
    long countByPaymentStatus(PaymentStatus paymentStatus);

    /**
     * Tìm các phạt có số tiền lớn hơn một giá trị
     */
    List<Fine> findByAmountGreaterThan(BigDecimal amount);

    /**
     * Kiểm tra xem Borrow ID có phạt chưa thanh toán không
     */
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Fine f WHERE f.borrow.borrowId = :borrowId AND f.paymentStatus = 'UNPAID'")
    boolean existsByBorrowIdAndUnpaidStatus(@Param("borrowId") String borrowId);

    /**
     * Tìm các phạt theo Reader ID
     */
    @Query("SELECT f FROM Fine f WHERE f.borrow.reader.readerId = :readerId")
    List<Fine> findByReaderId(@Param("readerId") String readerId);

    /**
     * Tính tổng số tiền phạt đã thanh toán trong khoảng thời gian
     */
    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM Fine f WHERE f.paymentStatus = 'PAID' AND f.paymentDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalPaidAmountBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}