package com.library.library_management_system.mapper;

import com.library.library_management_system.dto.request.ReaderRequest;
import com.library.library_management_system.dto.response.ReaderResponse;
import com.library.library_management_system.entity.Reader;

public class ReaderMapper {
    public static ReaderResponse toResponse(Reader r) {
        if (r == null) return null;
        ReaderResponse resp = new ReaderResponse();
        resp.setReaderId(r.getReaderId());
        resp.setName(r.getName());
        resp.setNumberPhone(r.getNumberPhone());
        resp.setEmail(r.getEmail());
        resp.setAddress(r.getAddress());
        resp.setRegistrationDate(r.getRegistrationDate());
        resp.setCardType(r.getCardType());
        return resp;
    }

    public static void updateEntityFromRequest(Reader reader, ReaderRequest req) {
        if (reader == null || req == null) return;
        reader.setName(req.getName());
        reader.setNumberPhone(req.getNumberPhone());
        reader.setEmail(req.getEmail());
        reader.setAddress(req.getAddress());
        if (req.getRegistrationDate() != null) {
            reader.setRegistrationDate(req.getRegistrationDate());
        }
    }
}
