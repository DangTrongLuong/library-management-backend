package com.library.library_management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.library_management_system.dto.request.LibrarianRequest;
import com.library.library_management_system.dto.response.LibrarianResponse;
import com.library.library_management_system.service.LibrarianService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/librarians")
public class LibrarianController {
    @Autowired
    private LibrarianService librarianService;

    @PostMapping("/createLibrarian")
    public ResponseEntity<LibrarianResponse> createLibrarian(@Valid @RequestBody LibrarianRequest request) {
        return new ResponseEntity<>(librarianService.createLibrarian(request), HttpStatus.CREATED);
    }

}