package com.library.library_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.library.library_management_system.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByTypeName(String typeName);
    boolean existsByShelfPosition(String shelfPosition);
}
