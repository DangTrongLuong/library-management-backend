package com.library.library_management_system.mapper;

import com.library.library_management_system.dto.request.ReaderRequest;
import com.library.library_management_system.dto.response.ReaderResponse;
import com.library.library_management_system.entity.Reader;
import org.springframework.stereotype.Component;

@Component
public class ReaderMapper {

    public Reader toEntity(ReaderRequest request) {
        Reader reader = new Reader();
        reader.setName(request.getName());
        reader.setNumberPhone(request.getNumberPhone());
        reader.setEmail(request.getEmail());
        reader.setAddress(request.getAddress());
        reader.setRegistrationDate(request.getRegistrationDate());
        reader.setCardType(request.getCardType());
        return reader;
    }

    public ReaderResponse toResponse(Reader reader) {
        ReaderResponse response = new ReaderResponse();
        response.setReaderId(reader.getReaderId());
        response.setName(reader.getName());
        response.setNumberPhone(reader.getNumberPhone());
        response.setEmail(reader.getEmail());
        response.setAddress(reader.getAddress());
        response.setRegistrationDate(reader.getRegistrationDate());
        response.setCardType(reader.getCardType());
        response.setCreatedAt(reader.getCreatedAt());
        response.setUpdatedAt(reader.getUpdatedAt());
        return response;
    }

    public void updateEntityFromRequest(ReaderRequest request, Reader reader) {
        reader.setName(request.getName());
        reader.setNumberPhone(request.getNumberPhone());
        reader.setEmail(request.getEmail());
        reader.setAddress(request.getAddress());
        reader.setRegistrationDate(request.getRegistrationDate());
        reader.setCardType(request.getCardType());
    }
}