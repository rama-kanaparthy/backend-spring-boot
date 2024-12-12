package com.rama.backend_spring_boot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.scheduling.TaskScheduler;

import java.util.concurrent.ScheduledFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DynamicSchedulerServiceTest {

    @Mock
    private TaskScheduler taskScheduler;

    @Mock
    private ScheduledFuture mockScheduledFuture;  // Mock the generic ScheduledFuture<?> type

    private DynamicSchedulerService dynamicSchedulerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        dynamicSchedulerService = new DynamicSchedulerService(taskScheduler);
    }

    @Test
    void testScheduleTaskSuccessfully() {
        // Arrange
        String taskId = "task1";
        Runnable task = () -> System.out.println("Task running");
        long intervalMillis = 1000;

        // Mock the behavior of TaskScheduler to return a mock ScheduledFuture
        when(taskScheduler.scheduleAtFixedRate(eq(task), eq(intervalMillis)))
                .thenReturn(mockScheduledFuture);  // Return the mocked ScheduledFuture

        // Act
        dynamicSchedulerService.scheduleTask(taskId, task, intervalMillis);

        // Assert
        verify(taskScheduler).scheduleAtFixedRate(eq(task), eq(intervalMillis));  // Verify the method call
        assertTrue(dynamicSchedulerService.isTaskRunning(taskId));  // Ensure the task is running
    }

    @Test
    void testScheduleTaskWithDuplicateId() {
        // Arrange
        String taskId = "task1";
        Runnable task = () -> System.out.println("Task running");
        long intervalMillis = 1000;

        // Mock the behavior of TaskScheduler to return a mock ScheduledFuture
        when(taskScheduler.scheduleAtFixedRate(eq(task), eq(intervalMillis)))
                .thenReturn(mockScheduledFuture);  // Return the mocked future

        // Act
        dynamicSchedulerService.scheduleTask(taskId, task, intervalMillis);

        // Try scheduling the same task again and assert it throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> dynamicSchedulerService.scheduleTask(taskId, task, intervalMillis));
    }

    @Test
    void testCancelTaskSuccessfully() {
        // Arrange
        String taskId = "task1";
        Runnable task = () -> System.out.println("Task running");
        long intervalMillis = 1000;

        // Mock TaskScheduler to return a scheduled future
        when(taskScheduler.scheduleAtFixedRate(eq(task), eq(intervalMillis)))
                .thenReturn(mockScheduledFuture);  // Ensure it returns the mock scheduled future

        // Act
        dynamicSchedulerService.scheduleTask(taskId, task, intervalMillis);
        dynamicSchedulerService.cancelTask(taskId);

        // Assert
        verify(mockScheduledFuture).cancel(false);  // Ensure cancel() was called on the future
        assertFalse(dynamicSchedulerService.isTaskRunning(taskId));  // Task should no longer be running
    }

    @Test
    void testIsTaskRunningWhenTaskIsNotRunning() {
        // Arrange
        String taskId = "task1";

        // Act & Assert
        assertFalse(dynamicSchedulerService.isTaskRunning(taskId));  // Task should not exist yet
    }

    @Test
    void testIsTaskRunningWhenTaskIsRunning() {
        // Arrange
        String taskId = "task1";
        Runnable task = () -> System.out.println("Task running");
        long intervalMillis = 1000;

        // Mock TaskScheduler to return a scheduled future
        when(taskScheduler.scheduleAtFixedRate(eq(task), eq(intervalMillis)))
                .thenReturn(mockScheduledFuture);  // Ensure mock future is returned

        // Act
        dynamicSchedulerService.scheduleTask(taskId, task, intervalMillis);

        // Assert
        assertTrue(dynamicSchedulerService.isTaskRunning(taskId));  // Task should be running after scheduling
    }
}
