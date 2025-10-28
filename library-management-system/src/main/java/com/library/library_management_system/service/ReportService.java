
package com.library.library_management_system.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.library.library_management_system.dto.request.ReportRequest;
import com.library.library_management_system.dto.response.ReportResponse;
import com.library.library_management_system.entity.Borrow;
import com.library.library_management_system.entity.Report;
import com.library.library_management_system.enums.BorrowStatus;
import com.library.library_management_system.enums.ReportType;
import com.library.library_management_system.mapper.ReportMapper;
import com.library.library_management_system.repository.BookRepository;
import com.library.library_management_system.repository.BorrowRepository;
import com.library.library_management_system.repository.FineRepository;
import com.library.library_management_system.repository.Librarian.LibrarianRepository;
import com.library.library_management_system.repository.ReaderRepository;
import com.library.library_management_system.repository.ReportRepository;

import jakarta.transaction.Transactional;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private LibrarianRepository librarianRepository;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private FineRepository fineRepository;

    public List<ReportResponse> getAllReports() {
        return reportRepository.findAll().stream()
                .map(reportMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ReportResponse getReportById(String id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        return reportMapper.toResponse(report);
    }

    @Transactional
    public ReportResponse createReport(ReportRequest request) {
        if (request.getFromDate().isAfter(request.getToDate())) {
            throw new RuntimeException("From date cannot be after To date");
        }
        String generatedContent = generateContent(request.getType(), request.getFromDate(), request.getToDate());
        request.setContent(generatedContent + (request.getContent() != null ? "\nNotes: " + request.getContent() : ""));
        Report report = reportMapper.toEntity(request);
        report = reportRepository.save(report);
        return reportMapper.toResponse(report);
    }

    @Transactional
    public ReportResponse updateReport(String id, ReportRequest request) {
        if (request.getFromDate() != null && request.getToDate() != null
                && request.getFromDate().isAfter(request.getToDate())) {
            throw new RuntimeException("From date cannot be after To date");
        }
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        reportMapper.updateEntityFromRequest(request, report);
        if (request.getType() != null || request.getFromDate() != null || request.getToDate() != null) {
            String generatedContent = generateContent(
                    request.getType() != null ? request.getType() : report.getType(),
                    request.getFromDate() != null ? request.getFromDate() : report.getFromDate(),
                    request.getToDate() != null ? request.getToDate() : report.getToDate());
            report.setContent(
                    generatedContent + (request.getContent() != null ? "\nNotes: " + request.getContent() : ""));
        }
        report = reportRepository.save(report);
        return reportMapper.toResponse(report);
    }

    @Transactional
    public void deleteReport(String id) {
        if (!reportRepository.existsById(id)) {
            throw new RuntimeException("Report not found");
        }
        reportRepository.deleteById(id);
    }

    public List<ReportResponse> searchReports(String keyword) {
        return reportRepository.searchReports(keyword).stream()
                .map(reportMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Page<ReportResponse> getReportsPaged(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return reportRepository.findAll(pageable)
                .map(reportMapper::toResponse);
    }

    public Page<ReportResponse> searchReportsPaged(String keyword, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return reportRepository.searchReports(keyword, pageable)
                .map(reportMapper::toResponse);
    }

    private String generateContent(ReportType type, LocalDate from, LocalDate to) {
        StringBuilder content = new StringBuilder();
        LocalDateTime startDateTime = from.atStartOfDay();
        LocalDateTime endDateTime = to.atTime(23, 59, 59);
        switch (type) {
            case SALARY:
                content.append("Báo cáo lương từ ").append(from).append(" đến ").append(to).append(":\n");
                BigDecimal totalPaid = fineRepository.getTotalPaidAmountBetweenDates(startDateTime, endDateTime);
                content.append("Tổng tiền phạt đã thanh toán: ").append(totalPaid).append(" VND\n");
                long activeLibrarians = librarianRepository.findAll().stream()
                        .filter(l -> "ACTIVE".equalsIgnoreCase(l.getStatus()))
                        .count();
                content.append("Số lượng thủ thư đang hoạt động: ").append(activeLibrarians);
                break;
            case READER:
                content.append("Báo cáo độc giả từ ").append(from).append(" đến ").append(to).append(":\n");
                long readers = readerRepository.findAll().stream()
                        .filter(r -> r.getRegistrationDate() != null && !r.getRegistrationDate().isBefore(from)
                                && !r.getRegistrationDate().isAfter(to))
                        .count();
                content.append("Số lượng độc giả mới đăng ký: ").append(readers).append("\n");
                long totalReaders = readerRepository.count();
                content.append("Tổng số độc giả: ").append(totalReaders);
                break;
            case BOOK:
                content.append("Báo cáo sách từ ").append(from).append(" đến ").append(to).append(":\n");
                long books = bookRepository.count();
                content.append("Tổng số sách: ").append(books).append("\n");
                long availableBooks = bookRepository.findAll().stream()
                        .filter(b -> b.getQuantity() > 0)
                        .count();
                content.append("Số sách còn sẵn: ").append(availableBooks);
                break;
            case BORROW:
                content.append("Báo cáo mượn sách từ ").append(from).append(" đến ").append(to).append(":\n");
                List<Borrow> borrows = borrowRepository.findAll().stream()
                        .filter(b -> b.getBorrowDate() != null && !b.getBorrowDate().isBefore(from)
                                && !b.getBorrowDate().isAfter(to))
                        .toList();
                long borrowed = borrows.stream().filter(b -> b.getStatus() == BorrowStatus.BORROWED).count();
                long overdue = borrows.stream().filter(b -> b.getStatus() == BorrowStatus.OVERDUE).count();
                long returned = borrows.stream().filter(b -> b.getStatus() == BorrowStatus.RETURNED).count();
                content.append("Số lượt mượn: ").append(borrowed).append("\n");
                content.append("Số lượt quá hạn: ").append(overdue).append("\n");
                content.append("Số lượt đã trả: ").append(returned);
                break;
            default:
                content.append("Không có nội dung được tạo.");
        }
        return content.toString();
    }
}
