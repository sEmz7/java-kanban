package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

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
        if (!subTasks.isEmpty()) {
            this.duration = this.subTasks.stream()
                    .map(SubTask::getDuration)
                    .filter(subtaskDuration -> !Objects.isNull(subtaskDuration))
                    .reduce(Duration.ZERO, Duration::plus);
            this.startTime = this.subTasks.stream()
                    .flatMap(subtask -> subtask.getStartTime().stream())
                    .reduce(LocalDateTime.MAX, (lhs, rhs) -> lhs.isAfter(rhs) ? rhs : lhs);
            this.endTime = this.subTasks.stream()
                    .filter(subTask -> subTask.getStartTime().isPresent())
                    .map(Epic::getEndTime)
                    .reduce(LocalDateTime.MIN, (lhs, rhs) -> lhs.isBefore(rhs) ? rhs : lhs);
        }
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
