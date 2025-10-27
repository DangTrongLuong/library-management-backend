package com.library.library_management_system.controller;

import com.library.library_management_system.dto.request.ReaderRequest;
import com.library.library_management_system.dto.response.ReaderResponse;
import com.library.library_management_system.dto.response.ReportResponse;
import com.library.library_management_system.service.ReaderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/readers")
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @PostMapping("/createReader")
    public ResponseEntity<ReaderResponse> createReader(@Valid @RequestBody ReaderRequest request) {
        return new ResponseEntity<>(readerService.createReader(request), HttpStatus.CREATED);
    }

    @GetMapping("/getReader/{id}")
    public ResponseEntity<ReaderResponse> getReaderById(@PathVariable String id) {
        return new ResponseEntity<>(readerService.getReaderById(id), HttpStatus.OK);
    }

    @PutMapping("/updateReader/{id}")
    public ResponseEntity<ReaderResponse> updateReader(
            @PathVariable String id,
            @Valid @RequestBody ReaderRequest request) {
        return new ResponseEntity<>(readerService.updateReader(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/deleteReader/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable String id) {
        readerService.deleteReader(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ReaderResponse>> searchReaders(
            @RequestParam(required = false) String keyword) {
        return new ResponseEntity<>(readerService.searchReaders(keyword), HttpStatus.OK);
    }
}