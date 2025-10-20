package com.library.library_management_system.controller;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.library.library_management_system.dto.request.ReaderRequest;
import com.library.library_management_system.dto.response.ReaderResponse;
import com.library.library_management_system.service.ReaderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/readers")
@Validated
public class ReaderController {

    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReader(@PathVariable("id") Integer id,
                                          @Valid @RequestBody ReaderRequest request) {
        try {
            ReaderResponse resp = readerService.updateReader(id, request);
            return ResponseEntity.ok(resp);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReader(@PathVariable("id") Integer id) {
        try {
            readerService.deleteReader(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
