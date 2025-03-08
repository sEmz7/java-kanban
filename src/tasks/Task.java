package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

public class Task {
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
    protected String taskName;
    protected String description;
    protected Duration duration;
    protected LocalDateTime startTime;
    protected TaskStatus taskStatus;

    protected int taskID;

    public Task(String taskName, String description, TaskStatus taskStatus) {
        this.taskName = taskName;
        this.description = description;
        this.taskStatus = taskStatus;
    }

    public Task(String taskName, String description, TaskStatus taskStatus, Duration duration, LocalDateTime startTime) {
        this.taskName = taskName;
        this.description = description;
        this.taskStatus = taskStatus;
        this.duration = duration;
        this.startTime = startTime;
    }

    protected Task(Integer taskID, String taskName, String description, TaskStatus taskStatus) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.description = description;
        this.taskStatus = taskStatus;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Task getSnapshot() {
        return new Task(this.getTaskID(), this.getTaskName(), this.getDescription(), this.getTaskStatus());
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public String getDescription() {
        return description;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTaskID() {
        return taskID;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration.toMinutes());
    }

    public Duration getDuration() {
        return duration;
    }

    public Optional<LocalDateTime> getStartTime() {
        return Optional.ofNullable(startTime);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        if (Objects.equals(taskID, task.taskID)) return true;
        return Objects.equals(taskName, task.taskName)
                && Objects.equals(description, task.description)
                && Objects.equals(taskID, task.taskID)
                && Objects.equals(taskStatus, task.taskStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, description);
    }

    @Override
    public String toString() {
        if (getStartTime().isPresent()) {
            return taskID + "," + this.getClass().getSimpleName() +
                    "," + taskName + "," + taskStatus + "," + description +
                    "," + duration.toMinutes() + "," + startTime.format(formatter) +
                    "," + getEndTime().format(formatter) + ",";
        }
        return taskID + "," + this.getClass().getSimpleName() +
                "," + taskName + "," + taskStatus + "," + description +
                ",";

    }
}
