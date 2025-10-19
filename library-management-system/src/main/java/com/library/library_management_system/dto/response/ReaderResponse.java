package com.library.library_management_system.dto.response;

import java.time.LocalDate;

import com.library.library_management_system.enums.CardType;
import lombok.Data;

@Data
public class ReaderResponse {
    Integer readerId;
    String name;
    String numberPhone;
    String email;
    String address;
    LocalDate registrationDate;
    CardType cardType;
}
