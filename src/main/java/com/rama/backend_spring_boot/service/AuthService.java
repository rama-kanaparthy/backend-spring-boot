package com.rama.backend_spring_boot.service;

import com.rama.backend_spring_boot.model.User;
import com.rama.backend_spring_boot.repository.UserRepository;
import com.rama.backend_spring_boot.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // BCryptPasswordEncoder will be injected
    private final JwtUtil jwtUtil;  // Utility class to generate JWT tokens

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }
        String encodedPassword = passwordEncoder.encode(password);  // Use passwordEncoder to hash the password
        User user = new User(username, encodedPassword);
        userRepository.save(user);
        return "User registered successfully";
    }

    public String login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return jwtUtil.generateToken(user.get().getUsername());
        }
        throw new IllegalArgumentException("Invalid username or password");
    }

}

