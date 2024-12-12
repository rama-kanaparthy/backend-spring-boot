package com.rama.backend_spring_boot.controller;

import com.rama.backend_spring_boot.service.DynamicSchedulerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DynamicTaskControllerTest {

    @Mock
    private DynamicSchedulerService schedulerService;

    private DynamicTaskController dynamicTaskController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dynamicTaskController = new DynamicTaskController(schedulerService);
        mockMvc = MockMvcBuilders.standaloneSetup(dynamicTaskController).build();
    }

    @Test
    void testStartTaskSuccessfully() throws Exception {
        // Arrange
        String taskId = "task1";
        long intervalMillis = 1000;

        // Act & Assert
        mockMvc.perform(post("/dynamic-task/start/{taskId}", taskId)
                        .param("intervalMillis", String.valueOf(intervalMillis)))
                .andExpect(status().isOk())
                .andExpect(content().string("Task task1 scheduled with an interval of 1000 ms."));

        // Verify that scheduleTask was called with correct arguments
        verify(schedulerService).scheduleTask(eq(taskId), any(Runnable.class), eq(intervalMillis));
    }

    @Test
    void testStartTaskWithDuplicateId() throws Exception {
        // Arrange
        String taskId = "task1";
        long intervalMillis = 1000;

        // Simulate that the task with the same ID is already scheduled
        doThrow(new IllegalArgumentException("Task with ID task1 is already scheduled."))
                .when(schedulerService).scheduleTask(eq(taskId), any(Runnable.class), eq(intervalMillis));

        // Act & Assert
        mockMvc.perform(post("/dynamic-task/start/{taskId}", taskId)
                        .param("intervalMillis", String.valueOf(intervalMillis)))
                .andExpect(status().isOk())
                .andExpect(content().string("Task with ID task1 is already scheduled."));

        // Verify that scheduleTask was called
        verify(schedulerService).scheduleTask(eq(taskId), any(Runnable.class), eq(intervalMillis));
    }

    @Test
    void testStopTaskSuccessfully() throws Exception {
        // Arrange
        String taskId = "task1";

        // Act & Assert
        mockMvc.perform(delete("/dynamic-task/stop/{taskId}", taskId))
                .andExpect(status().isOk())
                .andExpect(content().string("Task task1 stopped."));

        // Verify that cancelTask was called with correct taskId
        verify(schedulerService).cancelTask(eq(taskId));
    }

    @Test
    void testTaskStatusWhenRunning() throws Exception {
        // Arrange
        String taskId = "task1";
        when(schedulerService.isTaskRunning(taskId)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(get("/dynamic-task/status/{taskId}", taskId))
                .andExpect(status().isOk())
                .andExpect(content().string("Task task1 is running."));

        // Verify that isTaskRunning was called with correct taskId
        verify(schedulerService).isTaskRunning(eq(taskId));
    }

    @Test
    void testTaskStatusWhenNotRunning() throws Exception {
        // Arrange
        String taskId = "task1";
        when(schedulerService.isTaskRunning(taskId)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(get("/dynamic-task/status/{taskId}", taskId))
                .andExpect(status().isOk())
                .andExpect(content().string("Task task1 is not running."));

        // Verify that isTaskRunning was called with correct taskId
        verify(schedulerService).isTaskRunning(eq(taskId));
    }
}
