package com.library.library_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    
    @GetMapping("/getAllLibrarian")
    public ResponseEntity<List<LibrarianResponse>> getAllLibrarians() {
        return new ResponseEntity<>(librarianService.getAllLibrarians(), HttpStatus.OK);
    }

    @GetMapping("/getLibrarian/{id}")
    public ResponseEntity<LibrarianResponse> getLibrarianById(@PathVariable String id) {
        return new ResponseEntity<>(librarianService.getLibrarianById(id), HttpStatus.OK);
    }

    @PutMapping("/updateLibrarian/{id}")
    public ResponseEntity<LibrarianResponse> updateLibrarian(@PathVariable String id, @Valid @RequestBody LibrarianRequest request) {
        return new ResponseEntity<>(librarianService.updateLibrarian(id, request), HttpStatus.OK);
    }

    @DeleteMapping("deleteLibrarian/{id}")
    public ResponseEntity<Void> deleteLibrarian(@PathVariable String id) {
        librarianService.deleteLibrarian(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}