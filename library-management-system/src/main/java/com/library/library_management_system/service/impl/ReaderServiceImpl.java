package com.library.library_management_system.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.library_management_system.dto.request.ReaderRequest;
import com.library.library_management_system.dto.response.ReaderResponse;
import com.library.library_management_system.entity.Reader;
import com.library.library_management_system.mapper.ReaderMapper;
import com.library.library_management_system.repository.ReaderRepository;
import com.library.library_management_system.service.ReaderService;

@Service
public class ReaderServiceImpl implements ReaderService {

    private final ReaderRepository readerRepository;

    public ReaderServiceImpl(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    // -------------------- CREATE --------------------
    @Override
    @Transactional
    public ReaderResponse createReader(ReaderRequest request) {
        if (request.getNumberPhone() != null &&
                readerRepository.existsByNumberPhoneAndReaderIdNot(request.getNumberPhone(), null)) {
            throw new IllegalArgumentException("Phone number is already in use");
        }

        if (request.getEmail() != null && !request.getEmail().isBlank() &&
                readerRepository.existsByEmailAndReaderIdNot(request.getEmail(), null)) {
            throw new IllegalArgumentException("Email is already in use");
        }

        Reader reader = ReaderMapper.toEntity(request);
        Reader saved = readerRepository.save(reader);
        return ReaderMapper.toResponse(saved);
    }

    // -------------------- READ BY ID --------------------
    @Override
    @Transactional(readOnly = true)
    public ReaderResponse getReaderById(Integer id) {
        Reader reader = readerRepository.findByReaderId(id)
                .orElseThrow(() -> new NoSuchElementException("Reader not found with id: " + id));
        return ReaderMapper.toResponse(reader);
    }

    // -------------------- UPDATE --------------------
    @Override
    @Transactional
    public ReaderResponse updateReader(Integer id, ReaderRequest request) {
        Reader existing = readerRepository.findByReaderId(id)
                .orElseThrow(() -> new NoSuchElementException("Reader not found with id: " + id));

        if (request.getNumberPhone() != null &&
                readerRepository.existsByNumberPhoneAndReaderIdNot(request.getNumberPhone(), id)) {
            throw new IllegalArgumentException("Phone number is already in use");
        }

        if (request.getEmail() != null && !request.getEmail().isBlank() &&
                readerRepository.existsByEmailAndReaderIdNot(request.getEmail(), id)) {
            throw new IllegalArgumentException("Email is already in use");
        }

        ReaderMapper.updateEntityFromRequest(existing, request);
        Reader saved = readerRepository.save(existing);
        return ReaderMapper.toResponse(saved);
    }

    // -------------------- DELETE --------------------
    @Override
    @Transactional
    public void deleteReader(Integer id) {
        Reader existing = readerRepository.findByReaderId(id)
                .orElseThrow(() -> new NoSuchElementException("Reader not found with id: " + id));
        readerRepository.delete(existing);
    }

    // -------------------- SEARCH --------------------
    @Override
    @Transactional(readOnly = true)
    public List<ReaderResponse> searchReaders(String name, String phone, String email) {
        String n = (name == null || name.isBlank()) ? null : name.trim();
        String p = (phone == null || phone.isBlank()) ? null : phone.trim();
        String e = (email == null || email.isBlank()) ? null : email.trim();

        return readerRepository.search(n, p, e).stream()
                .map(ReaderMapper::toResponse)
                .collect(Collectors.toList());
    }

    // -------------------- PAGINATION --------------------
    @Override
    @Transactional(readOnly = true)
    public Page<ReaderResponse> getAllReaders(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Reader> readerPage = readerRepository.findAll(pageable);

        List<ReaderResponse> responses = readerPage.getContent()
                .stream()
                .map(ReaderMapper::toResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, readerPage.getTotalElements());
    }
}
