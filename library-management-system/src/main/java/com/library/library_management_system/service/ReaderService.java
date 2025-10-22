package com.library.library_management_system.service;

import com.library.library_management_system.dto.request.ReaderRequest;
import com.library.library_management_system.dto.response.ReaderResponse;
import com.library.library_management_system.entity.Reader;
import com.library.library_management_system.exception.DuplicateException;
import com.library.library_management_system.exception.NotFoundException;
import com.library.library_management_system.mapper.ReaderMapper;
import com.library.library_management_system.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ReaderService {

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private ReaderMapper readerMapper;

    @Transactional
    public ReaderResponse createReader(ReaderRequest request) {
        // Check duplicate phone and email
        if (readerRepository.existsByNumberPhone(request.getNumberPhone())) {
            throw new DuplicateException("Phone number already exists: " + request.getNumberPhone());
        }
        if (readerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateException("Email already exists: " + request.getEmail());
        }

        // Create reader entity
        Reader reader = readerMapper.toEntity(request);

        // Generate reader ID: R + 7 random digits
        String readerId;
        Random random = new Random();
        do {
            int randomNum = random.nextInt(10000000); // 0 to 9999999
            readerId = "R" + String.format("%07d", randomNum);
        } while (readerRepository.existsById(readerId));

        reader.setReaderId(readerId);

        // Set registration date if not provided
        if (reader.getRegistrationDate() == null) {
            reader.setRegistrationDate(LocalDate.now());
        }

        Reader savedReader = readerRepository.save(reader);
        return readerMapper.toResponse(savedReader);
    }

    @Transactional(readOnly = true)
    public List<ReaderResponse> getAllReaders() {
        return readerRepository.findAll().stream()
                .map(readerMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReaderResponse getReaderById(String id) {
        Reader reader = readerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reader not found with id: " + id));
        return readerMapper.toResponse(reader);
    }

    @Transactional
    public ReaderResponse updateReader(String id, ReaderRequest request) {
        Reader existingReader = readerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reader not found with id: " + id));

        // Check duplicate phone and email (except current reader)
        if (!existingReader.getNumberPhone().equals(request.getNumberPhone()) &&
                readerRepository.existsByNumberPhone(request.getNumberPhone())) {
            throw new DuplicateException("Phone number already exists: " + request.getNumberPhone());
        }
        if (!existingReader.getEmail().equals(request.getEmail()) &&
                readerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateException("Email already exists: " + request.getEmail());
        }

        // Update reader info
        readerMapper.updateEntityFromRequest(request, existingReader);

        Reader updatedReader = readerRepository.save(existingReader);
        return readerMapper.toResponse(updatedReader);
    }

    @Transactional
    public void deleteReader(String id) {
        Reader reader = readerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reader not found with id: " + id));
        readerRepository.delete(reader);
    }

    @Transactional(readOnly = true)
    public List<ReaderResponse> searchReaders(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllReaders();
        }
        return readerRepository.searchReaders(keyword).stream()
                .map(readerMapper::toResponse)
                .collect(Collectors.toList());
    }
}