package com.library.library_management_system.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.library.library_management_system.entity.Category;
import com.library.library_management_system.repository.CategoryRepository;

@Configuration
public class ExampleData {

    @Bean
    CommandLineRunner initDatabase(CategoryRepository categoryRepository) {
        return args -> {
            if (categoryRepository.count() == 0) {
                categoryRepository.save(new Category(null, "Khoa học viễn tưởng", "Thể loại về khoa học và tương lai", "A1", "Dữ liệu mẫu"));
                categoryRepository.save(new Category(null, "Lãng mạn", "Chuyện tình yêu", "B2", "Dữ liệu mẫu"));
                categoryRepository.save(new Category(null, "Kinh dị", "Truyện ma, rùng rợn", "C3", "Dữ liệu mẫu"));
                categoryRepository.save(new Category(null, "Phiêu lưu", "Các câu chuyện khám phá, mạo hiểm", "D4", "Dữ liệu mẫu"));
                categoryRepository.save(new Category(null, "Tâm lý", "Truyện phân tích tâm lý con người", "E5", "Dữ liệu mẫu"));

                System.out.println("Dữ liệu mẫu thể loại đã được thêm vào cơ sở dữ liệu.");
            }
        };
    }
}
