package com.library.library_management_system.customValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FutureOrPresentYearValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureOrPresentYear {
    String message() default "Publication year cannot exceed current year";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
