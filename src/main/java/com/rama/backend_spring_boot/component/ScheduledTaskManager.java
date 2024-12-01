package com.rama.backend_spring_boot.component;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
public class ScheduledTaskManager {

    private ScheduledFuture<?> scheduledFuture;
    private final TaskScheduler taskScheduler;

    @Autowired
    public ScheduledTaskManager(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void start(Runnable task, long intervalMillis) {
        if (scheduledFuture == null || scheduledFuture.isCancelled()) {
            scheduledFuture = taskScheduler.scheduleAtFixedRate(task, intervalMillis);
            log.info("Task started.");
        } else {
            log.info("Task is already running.");
        }
    }

    public void stop() {
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(false);
            log.info("Task stopped.");
        } else {
            log.info("No running task to stop.");
        }
    }

    public boolean isRunning() {
        return scheduledFuture != null && !scheduledFuture.isCancelled();
    }
}

