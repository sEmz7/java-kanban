package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Epic {
    protected int epicID;

    public SubTask(String taskName, String description, TaskStatus taskStatus) {
        super(taskName, description, taskStatus);
    }

    public SubTask(int taskID, String taskName, String description, TaskStatus taskStatus) {
        super(taskID, taskName, description, taskStatus);
    }

    public SubTask(
            String taskName,
            String description,
            TaskStatus taskStatus,
            Duration duration,
            LocalDateTime startTime,
            LocalDateTime endTime) {
        super(taskName, description, taskStatus, duration, startTime, endTime);
    }

    public int getEpicID() {
        return epicID;
    }

    public void setEpicID(int epicID) {
        this.epicID = epicID;
    }

    public SubTask getSnapshot() {
        return new SubTask(this.getTaskID(), this.getTaskName(), this.getDescription(), this.getTaskStatus());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(taskID + "," + this.getClass().getSimpleName() +
                "," + taskName + "," + taskStatus + "," + description +
                "," + epicID);

        getStartTime().ifPresentOrElse(startTime -> {
            result.append("," + duration.toMinutes() + "," + startTime.format(formatter) +
                    "," + endTime.format(formatter) + ",");
        }, () -> {
            result.append(",");
        });
        return result.toString();
    }
}
