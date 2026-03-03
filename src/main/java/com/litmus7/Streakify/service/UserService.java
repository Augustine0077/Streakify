package com.litmus7.Streakify.service;

import com.litmus7.Streakify.dto.UserDTO;
import com.litmus7.Streakify.dto.UserRequestDTO;
import com.litmus7.Streakify.entity.User;
import com.litmus7.Streakify.exception.ResourceNotFoundException;
import com.litmus7.Streakify.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
@Data
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE USER
    public UserDTO createUser(UserRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        User savedUser = userRepository.save(user);

        return mapToDTO(savedUser);
    }

    // GET USER
    public UserDTO getUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return mapToDTO(user);
    }

    // DELETE USER
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }

        userRepository.deleteById(id);
    }

    // ENTITY → DTO
    private UserDTO mapToDTO(User user) {

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        return dto;
    }
}