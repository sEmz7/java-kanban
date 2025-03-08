package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    protected LocalDateTime endTime = LocalDateTime.parse("0001:11:11 11:11:11", formatter);

    protected ArrayList<SubTask> subTasks = new ArrayList<>();

    public Epic(String taskName, String description, TaskStatus taskStatus) {
        super(taskName, description, taskStatus);
    }

    public Epic(int taskID, String taskName, String description, TaskStatus taskStatus) {
        super(taskID, taskName, description, taskStatus);
    }

    public Epic(String taskName,
                String description,
                TaskStatus taskStatus,
                Duration duration,
                LocalDateTime startTime,
                LocalDateTime endTime) {
        super(taskName, description, taskStatus, duration, startTime);
        this.endTime = endTime;
    }

    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks);
    }

    public TaskStatus getTaskStatus() {
        return this.taskStatus;
    }

    @Override
    public LocalDateTime getEndTime() {
        for (SubTask subTask : subTasks) {
            duration = duration.plusMinutes(subTask.getDuration().toMinutes());
            if (startTime.isAfter(subTask.getStartTime())) {
                startTime = subTask.getStartTime();
            }
            if (endTime.isBefore(subTask.getEndTime())) {
                endTime = subTask.getEndTime();
            }
        }
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Epic getSnapshot() {
        return new Epic(this.getTaskID(), this.getTaskName(), this.getDescription(), this.getTaskStatus());
    }

    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    @Override
    public String toString() {
        return taskID + "," + this.getClass().getSimpleName() +
                "," + taskName + "," + taskStatus + "," + description +
                "," + duration.toMinutes() + "," + startTime.format(formatter) + "," + endTime.format(formatter) + ",";
    }
}
