package com.library.library_management_system.service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.library_management_system.dto.request.LibrarianRequest;
import com.library.library_management_system.dto.response.LibrarianResponse;
import com.library.library_management_system.entity.Librarian.Librarian;
import com.library.library_management_system.entity.Librarian.Salary;
import com.library.library_management_system.entity.Librarian.Shift;
import com.library.library_management_system.exception.DuplicateException;
import com.library.library_management_system.exception.NotFoundException;
import com.library.library_management_system.mapper.LibrarianMapper;
import com.library.library_management_system.repository.Librarian.LibrarianRepository;
import com.library.library_management_system.repository.Librarian.SalaryRepository;
import com.library.library_management_system.repository.Librarian.ShiftRepository;


@Service
public class LibrarianService {
    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private LibrarianMapper librarianMapper;

    @Transactional
    public LibrarianResponse createLibrarian(LibrarianRequest request) {
        
        if (librarianRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateException("Số điện thoại đã tồn tại: " + request.getPhone());
        }
        if (librarianRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateException("Email đã tồn tại: " + request.getEmail());
        }

        Shift shift = shiftRepository.findById(request.getShiftId())
                .orElseThrow(() -> new NotFoundException("Ca làm việc không tồn tại với id: " + request.getShiftId()));
        
        Librarian librarian = librarianMapper.toEntity(request);
        String id;
        Random random = new Random();
        do {
            int randomNum = random.nextInt(1000000);
            id = "NV" + String.format("%06d", randomNum);
        } while (librarianRepository.existsById(id));
        librarian.setLibrarianId(id);
        librarian.setShift(shift);
        
        Librarian savedLibrarian = librarianRepository.save(librarian);

        BigDecimal workDaysPerMonth = new BigDecimal("26");
        BigDecimal totalSalary = request.getHourlyWage()
                .multiply(shift.getHoursPerDay())
                .multiply(workDaysPerMonth)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        Salary salary = new Salary(null, savedLibrarian, request.getHourlyWage(), YearMonth.now(), totalSalary);
        salaryRepository.save(salary);

        return librarianMapper.toResponse(savedLibrarian, salary.getHourlyWage(), salary.getMonth(), salary.getTotalSalary());
    }

    @Transactional(readOnly = true)
    public List<LibrarianResponse> getAllLibrarians() {
        List<Librarian> librarians = librarianRepository.findAll();
        return librarians.stream().map(librarian -> {
            Salary latestSalary = salaryRepository.findTopByLibrarianOrderByMonthDesc(librarian)
                    .orElse(new Salary(null, librarian, BigDecimal.ZERO, YearMonth.now(), BigDecimal.ZERO));
            return librarianMapper.toResponse(librarian, latestSalary.getHourlyWage(), latestSalary.getMonth(), latestSalary.getTotalSalary());
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LibrarianResponse getLibrarianById(String id) {
        Librarian librarian = librarianRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Thủ thư không tồn tại với id: " + id));
        Salary latestSalary = salaryRepository.findTopByLibrarianOrderByMonthDesc(librarian)
                .orElse(new Salary(null, librarian, BigDecimal.ZERO, YearMonth.now(), BigDecimal.ZERO));
        return librarianMapper.toResponse(librarian, latestSalary.getHourlyWage(), latestSalary.getMonth(), latestSalary.getTotalSalary());
    }

    @Transactional
public LibrarianResponse updateLibrarian(String id, LibrarianRequest request) {
    Librarian existingLibrarian = librarianRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Librarian not found with id: " + id));

    // Check duplicate phone & email
    if (!existingLibrarian.getPhone().equals(request.getPhone()) && 
        librarianRepository.existsByPhone(request.getPhone())) {
        throw new DuplicateException("Phone already exists: " + request.getPhone());
    }
    if (!existingLibrarian.getEmail().equals(request.getEmail()) && 
        librarianRepository.existsByEmail(request.getEmail())) {
        throw new DuplicateException("Email already exists: " + request.getEmail());
    }

    Shift shift = shiftRepository.findById(request.getShiftId())
            .orElseThrow(() -> new NotFoundException("Shift not found with id: " + request.getShiftId()));

    Salary latestSalary = salaryRepository.findTopByLibrarianOrderByMonthDesc(existingLibrarian)
            .orElse(new Salary(null, existingLibrarian, BigDecimal.ZERO, YearMonth.now(), BigDecimal.ZERO));
    
    boolean shiftChanged = !existingLibrarian.getShift().getShiftId().equals(request.getShiftId());
    boolean wageChanged = !latestSalary.getHourlyWage().equals(request.getHourlyWage());

    librarianMapper.updateEntityFromRequest(request, existingLibrarian);
    existingLibrarian.setShift(shift);
    Librarian updatedLibrarian = librarianRepository.save(existingLibrarian);

    // Chỉ update salary nếu thay đổi wage hoặc shift, không thêm mới mỗi lần
    if (shiftChanged || wageChanged) {
        // Update bản cũ thay vì tạo mới
        latestSalary.setHourlyWage(request.getHourlyWage());
        latestSalary.setMonth(YearMonth.now());
        
        BigDecimal workDaysPerMonth = new BigDecimal("26");
        BigDecimal totalSalary = request.getHourlyWage()
                .multiply(shift.getHoursPerDay())
                .multiply(workDaysPerMonth)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        latestSalary.setTotalSalary(totalSalary);
        
        salaryRepository.save(latestSalary);
        return librarianMapper.toResponse(updatedLibrarian, latestSalary.getHourlyWage(), latestSalary.getMonth(), latestSalary.getTotalSalary());
    } else {
        return librarianMapper.toResponse(updatedLibrarian, latestSalary.getHourlyWage(), latestSalary.getMonth(), latestSalary.getTotalSalary());
    }
}

    @Transactional
    public void deleteLibrarian(String id) {
        Librarian librarian = librarianRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Thủ thư không tồn tại với id: " + id));

        salaryRepository.deleteByLibrarian(librarian);
        // Xóa Librarian
        librarianRepository.delete(librarian);
    }
}