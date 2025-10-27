package com.library.library_management_system.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.library_management_system.entity.Report;


@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByReportTypeContainingIgnoreCase(String type);

    List<Report> findByCreator_Id(Long creatorId); // Đổi từ String sang Long

    List<Report> findByStartDateBetween(LocalDate start, LocalDate end);

    boolean existsById(Long reportId);
}
