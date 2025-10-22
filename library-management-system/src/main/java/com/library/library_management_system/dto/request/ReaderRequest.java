package com.library.library_management_system.dto.request;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ReaderRequest {
    private String name;
    private String numberPhone;
    private String email;
    private String address;
    @JsonFormat(pattern = "dd/MM/yyyy") //
    private LocalDate registrationDate;
    private String CardType;



    // Getters và Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        this.CardType = cardType;
    }
}
