package com.example.audibook.service;

import com.example.audibook.config.JwtService;
import com.example.audibook.dto.*;
import com.example.audibook.entity.User;
import com.example.audibook.exception.CustomException;
import com.example.audibook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public User saveUser(RegisterDTO request) {
        boolean emailExists = repository.findByEmail(request.getEmail()).isPresent();
        if (emailExists) {
            throw new CustomException("This email already exists");
        }
        User user = User.build(
                0,
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getContact(),
                request.getRole()
        );

        return repository.save(user);
    }

    public LoginResponseDTO login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException("User not found"));
        String jwtToken = jwtService.generateToken(user);

        return  new LoginResponseDTO(jwtToken, user);
    }

    public User getUserById(int id) {
        return repository.findById(id).orElseThrow(() -> new CustomException("User not found"));
    }
    public List<User> getAllUsers() {
        return repository.findAll();
    }
    public User UpdateUser( UpdateUserRequestDTO userDTO) {
        User user = repository.findById(userDTO.getId())
                .orElseThrow(() -> new CustomException("User not found"));

        user.setName(userDTO.getName());
        return repository.save(user);
    }
    public User RestPassword( ResetPasswordRequestDTO userDTO) {
        User user = repository.findById(userDTO.getId())
                .orElseThrow(() -> new CustomException("User not found"));

        if (!passwordEncoder.matches(userDTO.getOldPassword(), user.getPassword())) {
            throw new CustomException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return repository.save(user);
    }

    public void deleteUser(int id) {
        User user = repository.findById(id).orElseThrow(() -> new CustomException("User not found"));
        repository.delete(user);
    }


}