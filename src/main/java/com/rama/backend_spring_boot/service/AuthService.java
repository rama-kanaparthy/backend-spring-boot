package com.rama.backend_spring_boot.service;

import com.rama.backend_spring_boot.model.User;
import com.rama.backend_spring_boot.repository.UserRepository;
import com.rama.backend_spring_boot.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // BCryptPasswordEncoder will be injected
    private final JwtUtil jwtUtil;  // Utility class to generate JWT tokens

    public String register(String username, String password) {
        log.info("register user: {}", "username:::"+username+"password::: "+password);
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }
       // String encodedPassword = passwordEncoder.encode(password);  // Use passwordEncoder to hash the password
        User user = new User(username, password);
        userRepository.save(user);
        return "User registered successfully";
    }

    public String login(String username, String password) {
        log.info("login user: {}", "username:::"+username+"password::: "+password);
        Optional<User> user = userRepository.findByUsername(username);
        log.info("login user: {}", user.toString());
        if (user.isPresent() && password.contentEquals(user.get().getPassword())) {
            return jwtUtil.generateToken(user.get().getUsername());
        }
        throw new IllegalArgumentException("Invalid username or password");
    }

    public List<User> getUsers() {
        log.info("AuthService: {}","getUsers");
        return userRepository.findAll();
    }

}

