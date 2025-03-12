package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    protected LocalDateTime endTime;

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

    public void updateEndTime() {
        Duration duration = Duration.ofMinutes(0);
        LocalDateTime startTime = LocalDateTime.of(9999, 12, 12, 12, 12);
        LocalDateTime endTime = LocalDateTime.of(1, 12, 12, 12, 12);
        for (SubTask subTask : subTasks) {
            if (subTask.getStartTime().isPresent()) { // Не понимаю как тут использовать ifPresent(),
                // я же не смогу прибавлять к duration минуты в консюмере, поскольку будет ошибка так как duration не effectively final или final
                duration = duration.plusMinutes(subTask.getDuration().toMinutes());
                if (startTime.isAfter(subTask.getStartTime().get())) {
                    startTime = subTask.getStartTime().get();
                }
                if (endTime.isBefore(subTask.getEndTime())) {
                    endTime = subTask.getEndTime();
                }
            }
        }
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Epic getSnapshot() {
        return new Epic(this.getTaskID(), this.getTaskName(), this.getDescription(), this.getTaskStatus());
    }

    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(taskID + "," + this.getClass().getSimpleName() +
                "," + taskName + "," + taskStatus + "," + description);
        getStartTime().ifPresentOrElse(
                startTime -> {
                    result.append("," + duration.toMinutes() + "," + startTime.format(formatter) + "," + endTime.format(formatter) + ",");
                },
                () -> {
                    result.append(",");
                }
        );
        return result.toString();
    }
}
