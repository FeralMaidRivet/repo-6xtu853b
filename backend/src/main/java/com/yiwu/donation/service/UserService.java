package com.yiwu.donation.service;

import com.yiwu.donation.dto.LoginRequest;
import com.yiwu.donation.dto.RegisterRequest;
import com.yiwu.donation.model.User;
import com.yiwu.donation.model.UserRole;
import com.yiwu.donation.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {
        if (userRepository.count() == 0) {
            userRepository.save(new User("admin", "admin123", "系统管理员", "13800000000", UserRole.ADMIN));
            userRepository.save(new User("collector1", "123456", "李师傅", "13800000001", UserRole.COLLECTOR));
            userRepository.save(new User("collector2", "123456", "赵师傅", "13800000002", UserRole.COLLECTOR));
            userRepository.save(new User("donor1", "123456", "王小明", "13900000001", UserRole.DONOR));
        }
    }

    public Map<String, Object> login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("用户名不存在"));
        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("密码错误");
        }
        if (!user.getEnabled()) {
            throw new IllegalArgumentException("账号已被禁用");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("id", user.getId());
        result.put("username", user.getUsername());
        result.put("realName", user.getRealName());
        result.put("role", user.getRole().name());
        result.put("phone", user.getPhone());
        return result;
    }

    @Transactional
    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("用户名已存在");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRealName(request.getRealName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setRole(UserRole.DONOR);
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public List<User> listByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    @Transactional
    public User updateUser(Long id, User updates) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        if (updates.getRealName() != null) user.setRealName(updates.getRealName());
        if (updates.getPhone() != null) user.setPhone(updates.getPhone());
        if (updates.getEmail() != null) user.setEmail(updates.getEmail());
        if (updates.getAddress() != null) user.setAddress(updates.getAddress());
        if (updates.getRole() != null) user.setRole(updates.getRole());
        if (updates.getEnabled() != null) user.setEnabled(updates.getEnabled());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public long countByRole(UserRole role) {
        return userRepository.countByRole(role);
    }
}
