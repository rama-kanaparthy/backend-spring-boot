package com.rama.backend_spring_boot.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

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
            System.out.println("Task started.");
        } else {
            System.out.println("Task is already running.");
        }
    }

    public void stop() {
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(false);
            System.out.println("Task stopped.");
        } else {
            System.out.println("No running task to stop.");
        }
    }

    public boolean isRunning() {
        return scheduledFuture != null && !scheduledFuture.isCancelled();
    }
}

