package com.library.library_management_system.customValidation;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FutureOrPresentYearValidator implements ConstraintValidator<FutureOrPresentYear, Integer> {
    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        if (year == null) {
            return true; 
        }
        int currentYear = LocalDate.now().getYear();
        return year <= currentYear;
    }
}