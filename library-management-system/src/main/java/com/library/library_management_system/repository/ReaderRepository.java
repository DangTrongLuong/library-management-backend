package com.library.library_management_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.library_management_system.entity.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Integer> {
    boolean existsByNumberPhoneAndReaderIdNot(String numberPhone, Integer readerId);
    boolean existsByEmailAndReaderIdNot(String email, Integer readerId);
    Optional<Reader> findByReaderId(Integer readerId);

    @Query("SELECT r FROM Reader r WHERE "
            + "(:name IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND "
            + "(:phone IS NULL OR LOWER(r.numberPhone) LIKE LOWER(CONCAT('%', :phone, '%'))) AND "
            + "(:email IS NULL OR LOWER(r.email) LIKE LOWER(CONCAT('%', :email, '%')))")
    List<Reader> search(
            @Param("name") String name,
            @Param("phone") String phone,
            @Param("email") String email
    );
}
