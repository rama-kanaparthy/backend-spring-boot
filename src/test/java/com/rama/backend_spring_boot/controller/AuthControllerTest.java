package com.rama.backend_spring_boot.controller;

import com.rama.backend_spring_boot.model.dtos.UserDTO;
import com.rama.backend_spring_boot.service.AuthService;
import com.rama.backend_spring_boot.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @MockBean
    private AuthService authService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testRegister() throws Exception {
        // Mock behavior of AuthService
        String successMessage = "User registered successfully!";
        when(authService.register("testuser", "password")).thenReturn(successMessage);

        // Test the /register endpoint
        mockMvc.perform(post("/auth/register")
                        .param("username", "testuser")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(content().string(successMessage));
    }

    @Test
    public void testLogin_Success() throws Exception {
        // Mock behavior of AuthService
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("password");

        String token = "some-jwt-token";
        when(authService.login(userDTO.getUsername(), userDTO.getPassword())).thenReturn(token);

        // Test the /login endpoint with valid credentials
        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content("{\"username\": \"testuser\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(token));
    }

    @Test
    public void testLogin_Failure() throws Exception {
        // Mock behavior of AuthService for invalid login
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("wrongpassword");
        when(authService.login(userDTO.getUsername(), userDTO.getPassword())).thenReturn(null);

        // Test the /login endpoint with invalid credentials
        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content("{\"username\": \"testuser\", \"password\": \"wrongpassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password"));
    }

    @Test
    public void testGetUsers() throws Exception {
        // Mock behavior of AuthService to return a list of users
        User user1 = new User();
        user1.setUsername("testuser1");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setUsername("testuser2");
        user2.setPassword("password2");

        List<User> users = Arrays.asList(user1, user2);
        when(authService.getUsers()).thenReturn(users);

        // Test the /users endpoint
        mockMvc.perform(get("/auth/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testuser1"))
                .andExpect(jsonPath("$[1].username").value("testuser2"));
    }

    @Test
    public void testHello() throws Exception {
        // Test the /hello endpoint
        mockMvc.perform(get("/auth/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello "));
    }
}
