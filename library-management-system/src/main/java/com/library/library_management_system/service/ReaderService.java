package com.library.library_management_system.service;

import com.library.library_management_system.dto.request.ReaderRequest;
import com.library.library_management_system.dto.response.ReaderResponse;
import org.springframework.data.domain.Page;
import java.util.List;

/**
 * ReaderService
 * Định nghĩa các chức năng CRUD và tìm kiếm cho Reader.
 */
public interface ReaderService {

    /**
     * Tạo mới một Reader.
     * @param request thông tin từ client.
     * @return ReaderResponse sau khi lưu.
     */
    ReaderResponse createReader(ReaderRequest request);

    /**
     * Lấy thông tin Reader theo ID.
     * @param id Reader ID.
     * @return ReaderResponse nếu tìm thấy.
     */
    ReaderResponse getReaderById(Integer id);

    /**
     * Cập nhật thông tin Reader.
     * @param id Reader ID cần cập nhật.
     * @param request dữ liệu cập nhật.
     * @return ReaderResponse sau khi cập nhật.
     */
    ReaderResponse updateReader(Integer id, ReaderRequest request);

    /**
     * Xóa một Reader theo ID.
     * @param id Reader ID cần xóa.
     */
    void deleteReader(Integer id);

    /**
     * Tìm kiếm Reader theo các tiêu chí: tên, số điện thoại, email.
     * @param name tên cần tìm (tùy chọn)
     * @param phone số điện thoại cần tìm (tùy chọn)
     * @param email email cần tìm (tùy chọn)
     * @return danh sách ReaderResponse phù hợp
     */
    List<ReaderResponse> searchReaders(String name, String phone, String email);

    /**
     * Lấy danh sách Reader có phân trang và sắp xếp.
     * @param page trang hiện tại (bắt đầu từ 0)
     * @param size số lượng phần tử mỗi trang
     * @param sortBy trường dùng để sắp xếp
     * @param sortDir hướng sắp xếp (ASC/DESC)
     * @return Page chứa ReaderResponse
     */
    Page<ReaderResponse> getAllReaders(int page, int size, String sortBy, String sortDir);
}
