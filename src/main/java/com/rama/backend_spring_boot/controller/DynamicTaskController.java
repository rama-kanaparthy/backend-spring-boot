package com.rama.backend_spring_boot.controller;

import com.rama.backend_spring_boot.service.DynamicSchedulerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dynamic-task")
public class DynamicTaskController {

    private final DynamicSchedulerService schedulerService;

    public DynamicTaskController(DynamicSchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @PostMapping("/start/{taskId}")
    public String startTask(@PathVariable String taskId, @RequestParam long intervalMillis) {
        try {
            Runnable task = () -> System.out.println("Dynamic Task [" + taskId + "] executed at " + System.currentTimeMillis());
            schedulerService.scheduleTask(taskId, task, intervalMillis);
            return "Task " + taskId + " scheduled with an interval of " + intervalMillis + " ms.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @DeleteMapping("/stop/{taskId}")
    public String stopTask(@PathVariable String taskId) {
        schedulerService.cancelTask(taskId);
        return "Task " + taskId + " stopped.";
    }

    @GetMapping("/status/{taskId}")
    public String taskStatus(@PathVariable String taskId) {
        boolean isRunning = schedulerService.isTaskRunning(taskId);
        return isRunning ? "Task " + taskId + " is running." : "Task " + taskId + " is not running.";
    }
}

