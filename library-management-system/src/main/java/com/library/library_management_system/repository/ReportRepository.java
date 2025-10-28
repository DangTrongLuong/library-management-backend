package com.library.library_management_system.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.library_management_system.entity.Report;
@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
@Query("SELECT r FROM Report r WHERE (:keyword IS NULL OR LOWER(r.createdBy) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(CAST(r.type AS string)) LIKE LOWER(CONCAT('%', :keyword, '%')))")
Page<Report> searchReports(@Param("keyword") String keyword, Pageable pageable);
@Query("SELECT r FROM Report r WHERE (:keyword IS NULL OR LOWER(r.createdBy) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(CAST(r.type AS string)) LIKE LOWER(CONCAT('%', :keyword, '%')))")
List<Report> searchReports(@Param("keyword") String keyword);
}