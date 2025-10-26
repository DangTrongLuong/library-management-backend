package com.library.library_management_system.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.library.library_management_system.entity.Books;
import com.library.library_management_system.entity.Category;
import com.library.library_management_system.entity.Librarian.Shift;
import com.library.library_management_system.repository.BookRepository;
import com.library.library_management_system.repository.CategoryRepository;
import com.library.library_management_system.repository.Librarian.ShiftRepository;


@Configuration
public class ExampleData {

    @Bean
    @Order(1)
    CommandLineRunner initDatabase(CategoryRepository categoryRepository) {
        return args -> {
            if (categoryRepository.count() == 0) {
                categoryRepository.save(new Category(null, "Science Fiction", "Stories about science and the future", "A1", "Sample data"));
                categoryRepository.save(new Category(null, "Romance", "Love stories and relationships", "A2", "Sample data"));
                categoryRepository.save(new Category(null, "Horror", "Ghost stories and creepy tales", "A3", "Sample data"));
                categoryRepository.save(new Category(null, "Adventure", "Exploration and adventure stories", "A4", "Sample data"));
                categoryRepository.save(new Category(null, "Psychology", "Stories analyzing human psychology", "A5", "Sample data"));
                categoryRepository.save(new Category(null, "Manga", "Japanese comic books", "B1", "Sample data"));
                categoryRepository.save(new Category(null, "Light Novel", "Japanese light novels", "B2", "Sample data"));
                categoryRepository.save(new Category(null, "Poetry", "Poetry from Vietnam and the world", "B3", "Sample data"));
                categoryRepository.save(new Category(null, "Science and Technology", "Modern science and technology", "B4", "Sample data"));
                categoryRepository.save(new Category(null, "Drama", "Plays and dramatic literature", "B5", "Sample data"));
                categoryRepository.save(new Category(null, "Programming", "Books about programming and coding", "C1", "Sample data"));
                categoryRepository.save(new Category(null, "Textbooks", "Educational textbooks for various subjects", "C2", "Sample data"));
                categoryRepository.save(new Category(null, "Humor", "Funny stories and comedy", "C3", "Sample data"));
                categoryRepository.save(new Category(null, "Philosophy of Life", "Books about life philosophy and wisdom", "C4", "Sample data"));
                categoryRepository.save(new Category(null, "History", "Historical events and biographies", "C5", "Sample data"));
                categoryRepository.save(new Category(null, "Philosophy", "Philosophical theories and thinkers", "D1", "Sample data"));
                categoryRepository.save(new Category(null, "Detective", "Mystery and detective stories", "D2", "Sample data"));
                categoryRepository.save(new Category(null, "Religion", "Books about religions and beliefs", "D3", "Sample data"));
                categoryRepository.save(new Category(null, "Cooking", "Cookbooks and culinary guides", "D4", "Sample data"));
                categoryRepository.save(new Category(null, "Fables", "Traditional fables and moral stories", "D5", "Sample data"));
                categoryRepository.save(new Category(null, "Anatomy Biology", "Books about human anatomy and biology", "E1", "Sample data"));
                categoryRepository.save(new Category(null, "Business Management", "Books about business administration and management", "E2", "Sample data"));
                System.out.println("Dữ liệu mẫu thể loại đã được thêm vào cơ sở dữ liệu.");
            }
        };
    }

    @Bean
    @Order(2)
    CommandLineRunner initDatabaseShift(ShiftRepository shiftRepository) {
        return args -> {
            if (shiftRepository.count() == 0) {
                shiftRepository.save(new Shift(null, "Morning", "8h-12h", BigDecimal.valueOf(4.0) ));
                shiftRepository.save(new Shift(null, "Afternoon", "13h-17h", BigDecimal.valueOf(4.0) ));
                shiftRepository.save(new Shift(null, "Evening", "19h-23h", BigDecimal.valueOf(4.0) ));
                shiftRepository.save(new Shift(null, "Fulltime", "8h-17h", BigDecimal.valueOf(8.0) ));
                System.out.println("Dữ liệu mẫu thể loại đã được thêm vào cơ sở dữ liệu.");
            }
        };
    }
    @Bean
    @Order(3)
    CommandLineRunner initBookData(BookRepository bookRepository, CategoryRepository categoryRepository) {
        return args -> {
            if (bookRepository.count() == 0) {
            bookRepository.save(createSampleBook(
                    "B001",
                    "Fate/Stay Night",
                    "Kinoko Nasu",
                    2004,
                    "Type-Moon",
                    100,
                    "/uploads/books/img.png",
                    7,categoryRepository
            ));

            bookRepository.save(createSampleBook(
                    "B002",
                    "Không tồn tại giữa anh và em",
                    "Nguyễn Nhật Ánh",
                    2024,
                    "NXB Trẻ",
                    65,
                    "uploads/books/img_1.png",
                    5,categoryRepository
            ));

            bookRepository.save(createSampleBook(
                    "B003",
                    "Dế Mèn Phiêu Lưu Ký",
                    "Tô Hoài",
                    2020,
                    "NXB Kim Đồng",
                    97,
                    "uploads/books/img_2.png",
                    4,categoryRepository
            ));
            bookRepository.save(createSampleBook("B004", "Don Quixote", "Miguel de Cervantes", 1605, "Francisco de Robles", 34, "uploads/books/img_3.png", 4,categoryRepository));
            bookRepository.save(createSampleBook("B005", "Đại Việt Sử Ký Toàn Thư", "Ngô Sĩ Liên", 1697, "Quốc Sử Quán", 540, "uploads/books/img_4.png",15, categoryRepository));
            bookRepository.save(createSampleBook("B006", "Wadanohara and the great blue sea", "Cute And Horror",2014 , "Cute and horror team", 500, "uploads/books/img_5.jpg",10, categoryRepository));
            bookRepository.save(createSampleBook("B007", "Bạn nai Nokotan", "Unknow",2020 , "Unknow", 500, "uploads/books/img_5.png",6, categoryRepository));
            bookRepository.save(createSampleBook("B008", "Triết học Mac- Lenin", "Triet hoc the gioi",2010 , "Nha xuat ban su that", 350, "uploads/books/img_6.png",16, categoryRepository));


            System.out.println("✅ Dữ liệu mẫu sách đã được thêm vào cơ sở dữ liệu.");
    }
    };
    }
    private Books createSampleBook(String id, String title, String author, int year, String publisher, int quantity, String imageUrl, int categoryId, CategoryRepository categoryRepository) {
        Books book = new Books();
        book.setBookId(id);
        book.setBookTitle(title);
        book.setAuthor(author);
        book.setPublicationYear(year);
        book.setNxb(publisher);
        book.setQuantity(quantity);
        book.setImageUrl(imageUrl);

        // Lấy Category từ DB
        Category category = categoryRepository.getReferenceById(categoryId);
        book.setCategory(category);

        return book;
    }

}
