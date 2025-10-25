package com.library.library_management_system.mapper;

import com.library.library_management_system.dto.response.ReportResponse;
import com.library.library_management_system.entity.Report;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReportMapper {
    public static ReportResponse toResponse(Report r) {
        if (r == null) return null;
        ReportResponse resp = new ReportResponse();
        resp.setReportId(r.getReportId());
        resp.setReportType(r.getReportType());
        resp.setStartDate(r.getStartDate());
        resp.setEndDate(r.getEndDate());
        resp.setContent(r.getContent());
        // use extractor which now falls back to "admin"
        String creatorId = extractCreatorId(r);
        resp.setCreatorId(creatorId);
        resp.setCreatedAt(r.getCreatedAt());
        resp.setUpdatedAt(r.getUpdatedAt());
        return resp;
    }
    // safe extraction of creator id with reflection (tries common method/field names)
    // Default to "admin" when creator is missing or id cannot be resolved
    public static String extractCreatorId(Report r) {
        if (r == null) return "admin";
        Object creator = r.getCreator();
        if (creator == null) return "admin"; // default creator

        try {
            Method m = creator.getClass().getMethod("getId");
            Object id = m.invoke(creator);
            if (id != null) return id.toString();
        } catch (Exception e) {
            // try some common alternatives
        }
        try {
            Method m = creator.getClass().getMethod("getUserId");
            Object id = m.invoke(creator);
            if (id != null) return id.toString();
        } catch (Exception e) {
            // fallback to fields
        }
        try {
            Field f = creator.getClass().getDeclaredField("id");
            f.setAccessible(true);
            Object id = f.get(creator);
            if (id != null) return id.toString();
        } catch (Exception e) {
            // last attempt
        }
        try {
            Field f = creator.getClass().getDeclaredField("userId");
            f.setAccessible(true);
            Object id = f.get(creator);
            if (id != null) return id.toString();
        } catch (Exception e) {
            // unable to extract id
        }
        // fallback default when no id found
        return "admin";
    }
}
