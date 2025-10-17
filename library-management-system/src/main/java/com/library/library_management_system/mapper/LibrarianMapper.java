package com.library.library_management_system.mapper;

import com.library.library_management_system.dto.request.LibrarianRequest;
import com.library.library_management_system.dto.response.LibrarianResponse;
import com.library.library_management_system.entity.Librarian;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LibrarianMapper {

    LibrarianResponse toResponse(Librarian librarian);

    @Mapping(target = "librarianId", ignore = true)
    @Mapping(target = "timeShift", ignore = true)
    @Mapping(target = "salary", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Librarian toEntity(LibrarianRequest request);

    @Mapping(target = "librarianId", ignore = true)
    @Mapping(target = "timeShift", ignore = true)
    @Mapping(target = "salary", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(LibrarianRequest request, @MappingTarget Librarian librarian);
}