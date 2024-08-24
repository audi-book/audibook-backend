package com.example.audibook.controller;

import com.example.audibook.dto.*;
import com.example.audibook.entity.User;
import com.example.audibook.exception.CustomException;
import com.example.audibook.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<User>> register(
            @RequestBody RegisterDTO request
    ) {
        try {
            User user = service.saveUser(request);
            ResponseDTO<User> response = new ResponseDTO<>(true, user, "User registered successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<User> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<User> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<LoginResponseDTO>> login(
            @RequestBody LoginDTO request
    ) {
        try {
            LoginResponseDTO token = service.login(request);
            ResponseDTO<LoginResponseDTO> response = new ResponseDTO<>(true, token, "User logged in successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<LoginResponseDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<LoginResponseDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<User>> getUserById(@PathVariable int id) {
        try {
            User user = service.getUserById(id);
            ResponseDTO<User> response = new ResponseDTO<>(true, user, "User retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<User> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseDTO<User> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<User>>> getAllUsers() {
        try {
            List<User> users = service.getAllUsers();
            ResponseDTO<List<User>> response = new ResponseDTO<>(true, users, "Users retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<List<User>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<User>> updateUser(@RequestBody UpdateUserRequestDTO userDTO) {
        try {
            User updatedUser = service.UpdateUser(userDTO);
            ResponseDTO<User> response = new ResponseDTO<>(true, updatedUser, "User updated successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<User> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseDTO<User> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ResponseDTO<User>> resetPassword(@RequestBody ResetPasswordRequestDTO userDTO) {
        try {
            User user = service.RestPassword(userDTO);
            ResponseDTO<User> response = new ResponseDTO<>(true, user, "Password reset successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<User> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<User> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteUser(@PathVariable int id) {
        try {
            service.deleteUser(id);
            ResponseDTO<Void> response = new ResponseDTO<>(true, null, "User deleted successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Void> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseDTO<Void> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
