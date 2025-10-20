package com.library.library_management_system.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.library_management_system.dto.request.ReaderRequest;
import com.library.library_management_system.dto.response.ReaderResponse;
import com.library.library_management_system.entity.Reader;
import com.library.library_management_system.mapper.ReaderMapper;
import com.library.library_management_system.repository.ReaderRepository;

@Service
public class ReaderService {

    private final ReaderRepository readerRepository;

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Transactional
    public ReaderResponse updateReader(Integer id, ReaderRequest request) {
        Reader existing = readerRepository.findByReaderId(id)
                .orElseThrow(() -> new NoSuchElementException("Reader not found with id: " + id));

        if (request.getNumberPhone() != null &&
            readerRepository.existsByNumberPhoneAndReaderIdNot(request.getNumberPhone(), id)) {
            throw new IllegalArgumentException("Phone is already in use");
        }

        if (request.getEmail() != null && !request.getEmail().isBlank() &&
            readerRepository.existsByEmailAndReaderIdNot(request.getEmail(), id)) {
            throw new IllegalArgumentException("Email is already in use");
        }

        ReaderMapper.updateEntityFromRequest(existing, request);
        Reader saved = readerRepository.save(existing);
        return ReaderMapper.toResponse(saved);
    }
    @Transactional
    public void deleteReader(Integer id) {
        Reader existing = readerRepository.findByReaderId(id)
                .orElseThrow(() -> new NoSuchElementException("Reader not found with id: " + id));
        readerRepository.delete(existing);
    }
}
