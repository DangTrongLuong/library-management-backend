package com.library.library_management_system.mapper;

import java.math.BigDecimal;
import java.time.YearMonth;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.library.library_management_system.dto.request.LibrarianRequest;
import com.library.library_management_system.dto.response.LibrarianResponse;
import com.library.library_management_system.entity.Librarian.Librarian;
import com.library.library_management_system.entity.Librarian.Salary;

@Mapper(componentModel = "spring")
public interface LibrarianMapper {

    @Mapping(target = "shiftId", source = "shift.shiftId")
    @Mapping(target = "shiftName", source = "shift.shiftName")
    @Mapping(target = "timeShift", source = "shift.timeShift")
    @Mapping(target = "hoursPerDay", source = "shift.hoursPerDay")
    @Mapping(target = "hourlyWage", source = "hourlyWage")
    @Mapping(target = "salaryMonth", source = "salaryMonth")
    @Mapping(target = "totalSalary", source = "totalSalary")
    LibrarianResponse toResponse(Librarian librarian, @MappingTarget Salary salary, BigDecimal hourlyWage, YearMonth salaryMonth, BigDecimal totalSalary);

    @Mapping(target = "librarianId", ignore = true)
    @Mapping(target = "shift", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Librarian toEntity(LibrarianRequest request);

    @Mapping(target = "librarianId", ignore = true)
    @Mapping(target = "shift", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(LibrarianRequest request, @MappingTarget Librarian librarian);
}