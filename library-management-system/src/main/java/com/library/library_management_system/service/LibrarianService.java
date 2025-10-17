package com.library.library_management_system.service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.library_management_system.dto.request.LibrarianRequest;
import com.library.library_management_system.dto.response.LibrarianResponse;
import com.library.library_management_system.entity.Librarian.Librarian;
import com.library.library_management_system.entity.Librarian.Salary;
import com.library.library_management_system.entity.Librarian.Shift;
import com.library.library_management_system.mapper.LibrarianMapper;
import com.library.library_management_system.repository.Librarian.LibrarianRepository;
import com.library.library_management_system.repository.Librarian.SalaryRepository;
import com.library.library_management_system.repository.Librarian.ShiftRepository;

import com.library.library_management_system.exception.NotFoundException;


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
}