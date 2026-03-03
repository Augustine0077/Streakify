package com.litmus7.Streakify.controller;

import com.litmus7.Streakify.dto.UserDTO;
import com.litmus7.Streakify.dto.UserRequestDTO;
import com.litmus7.Streakify.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE USER
    @PostMapping
    public ResponseEntity<UserDTO> createUser(
            @RequestBody UserRequestDTO request) {

        return ResponseEntity.ok(userService.createUser(request));
    }

    // GET USER
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUser(id));
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted");
    }
}