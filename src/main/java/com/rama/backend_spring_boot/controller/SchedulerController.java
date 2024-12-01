package com.rama.backend_spring_boot.controller;

import com.rama.backend_spring_boot.component.ScheduledTaskManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/scheduler")
public class SchedulerController {

    private final ScheduledTaskManager taskManager;

    @Autowired
    public SchedulerController(ScheduledTaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startTask(@RequestParam long intervalMillis) {
        Runnable task = () -> log.info("Executing task at: " + LocalDateTime.now());
        taskManager.start(task, intervalMillis);
        return ResponseEntity.ok("Task started with interval: " + intervalMillis + " ms.");
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopTask() {
        taskManager.stop();
        return ResponseEntity.ok("Task stopped.");
    }

    @GetMapping("/status")
    public ResponseEntity<String> getTaskStatus() {
        return ResponseEntity.ok(taskManager.isRunning() ? "Task is running." : "Task is stopped.");
    }
}
