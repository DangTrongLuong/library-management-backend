package com.library.library_management_system.service;

import com.library.library_management_system.entity.User;
import com.library.library_management_system.repository.UserRepository;
import com.library.library_management_system.dto.request.UserRequest;
import com.library.library_management_system.dto.response.UserResponse;
import com.library.library_management_system.mapper.UserMapper;
import com.library.library_management_system.exception.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Khởi tạo tài khoản Admin khi ứng dụng khởi động
    @EventListener(ApplicationReadyEvent.class)
    public void initAdmin() {
        Optional<User> adminUser = userRepository.findByUsername("admin");

        if (adminUser.isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@gmail.com");
            admin.setRole("ADMIN");

            userRepository.save(admin);
            System.out.println("✓ Admin account created successfully");
        }
    }

    // API Login
    public UserResponse login(UserRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Username hoặc password không chính xác"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Username hoặc password không chính xác");
        }

        return userMapper.toResponse(user);
    }
}