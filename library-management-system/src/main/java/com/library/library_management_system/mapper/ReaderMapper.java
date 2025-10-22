package com.library.library_management_system.mapper;

import com.library.library_management_system.dto.request.ReaderRequest;
import com.library.library_management_system.dto.response.ReaderResponse;
import com.library.library_management_system.entity.Reader;
import com.library.library_management_system.enums.CardType;

public class ReaderMapper {

    // 1. Entity → Response
    public static ReaderResponse toResponse(Reader r) {
        if (r == null) return null;
        ReaderResponse resp = new ReaderResponse();
        resp.setReaderId(r.getReaderId());
        resp.setName(r.getName());
        resp.setNumberPhone(r.getNumberPhone());
        resp.setEmail(r.getEmail());
        resp.setAddress(r.getAddress());
        resp.setRegistrationDate(r.getRegistrationDate());
        resp.setCardType(r.getCardType() != null ? r.getCardType().name() : null);
        return resp;
    }

    // 2. Request → Entity (tạo mới)
    public static Reader toEntity(ReaderRequest req) {
        if (req == null) return null;
        Reader reader = new Reader();
        reader.setName(req.getName());
        reader.setNumberPhone(req.getNumberPhone());
        reader.setEmail(req.getEmail());
        reader.setAddress(req.getAddress());
        reader.setRegistrationDate(req.getRegistrationDate());

        if (req.getCardType() != null) {
            try {
                reader.setCardType(CardType.fromString(req.getCardType()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid card type: " + req.getCardType());
            }
        }
        return reader;
    }

    // 3. Cập nhật Entity từ Request (update)
    public static void updateEntityFromRequest(Reader reader, ReaderRequest req) {
        if (reader == null || req == null) return;
        reader.setName(req.getName());
        reader.setNumberPhone(req.getNumberPhone());
        reader.setEmail(req.getEmail());
        reader.setAddress(req.getAddress());
        if (req.getRegistrationDate() != null) {
            reader.setRegistrationDate(req.getRegistrationDate());
        }

        if (req.getCardType() != null) {
            try {
                reader.setCardType(CardType.fromString(req.getCardType()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid card type: " + req.getCardType());
            }
        }
    }
}