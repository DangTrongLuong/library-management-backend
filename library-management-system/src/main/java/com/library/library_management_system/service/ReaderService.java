package com.library.library_management_system.service;

import com.library.library_management_system.dto.request.ReaderRequest;
import com.library.library_management_system.dto.response.ReaderResponse;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * ReaderService
 * Định nghĩa các chức năng CRUD và tìm kiếm cho Reader.
 */
public interface ReaderService {

    ReaderResponse createReader(ReaderRequest request);

    ReaderResponse getReaderById(Integer id);

    ReaderResponse updateReader(Integer id, ReaderRequest request);

    void deleteReader(Integer id);

    List<ReaderResponse> searchReaders(String name, String numberPhone, String email);

    Page<ReaderResponse> getAllReaders(int page, int size, String sortBy, String sortDir);
}