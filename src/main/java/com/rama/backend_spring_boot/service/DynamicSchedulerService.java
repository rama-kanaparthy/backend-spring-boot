package com.rama.backend_spring_boot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@RequiredArgsConstructor
@Service
public class DynamicSchedulerService {

    private final TaskScheduler taskScheduler;
    private final Map<String, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();

    // Schedule a dynamic task
    public void scheduleTask(String taskId, Runnable task, long intervalMillis) {
        if (tasks.containsKey(taskId)) {
            throw new IllegalArgumentException("Task with ID " + taskId + " is already scheduled.");
        }
        ScheduledFuture<?> future = taskScheduler.scheduleAtFixedRate(task, intervalMillis);
        tasks.put(taskId, future);
    }

    // Cancel a scheduled task
    public void cancelTask(String taskId) {
        ScheduledFuture<?> future = tasks.get(taskId);
        if (future != null) {
            future.cancel(false);
            tasks.remove(taskId);
        }
    }

    // Check if a task is running
    public boolean isTaskRunning(String taskId) {
        ScheduledFuture<?> future = tasks.get(taskId);
        return future != null && !future.isCancelled();
    }
}
