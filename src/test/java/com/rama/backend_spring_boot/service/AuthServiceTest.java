package com.rama.backend_spring_boot.service;

import com.rama.backend_spring_boot.model.User;
import com.rama.backend_spring_boot.repository.UserRepository;
import com.rama.backend_spring_boot.util.JwtUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void register() {
        String username = "testUser";
        String password = "password123";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn("hashedPassword");

        String result = authService.register(username, password);

        assertEquals("User registered successfully", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerThrowsExceptionWhenUsernameExists() {
        String username = "testUser";
        String password = "password123";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.register(username, password)
        );

        assertEquals("Username already taken", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login() {
        String username = "testUser";
        String password = "password123";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(password);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
        when(jwtUtil.generateToken(username)).thenReturn("mockJwtToken");

        String token = authService.login(username, password);

        assertEquals("mockJwtToken", token);
        verify(jwtUtil, times(1)).generateToken(username);
    }

    @Test
    void loginThrowsExceptionForInvalidCredentials() {
        String username = "testUser";
        String password = "wrongPassword";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword("password123");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login(username, password)
        );

        assertEquals("Invalid username or password", exception.getMessage());
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void getUsers() {
        User user1 = new User();
        user1.setUsername("user1");
        User user2 = new User();
        user2.setUsername("user2");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> users = authService.getUsers();

        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user2", users.get(1).getUsername());
        verify(userRepository, times(1)).findAll();
    }
}
