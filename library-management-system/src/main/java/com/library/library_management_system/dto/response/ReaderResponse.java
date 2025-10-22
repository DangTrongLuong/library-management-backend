package com.library.library_management_system.dto.response;

import com.library.library_management_system.enums.CardType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReaderResponse {

    String readerId;

    String name;

    String numberPhone;

    String email;

    String address;

    LocalDate registrationDate;

    CardType cardType;

    LocalDate createdAt;

    LocalDate updatedAt;
}