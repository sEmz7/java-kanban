package Tasks;

import java.util.Objects;

public class Task {
    protected String taskName;
    protected String description;

    protected int taskID;
    protected TaskStatus taskStatus;

    public Task(String taskName, String description, TaskStatus taskStatus) {
        this.taskName = taskName;
        this.description = description;
        this.taskStatus = taskStatus;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
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
        return "\nТип задачи: Обычная задача" +
                "\nID задачи: " + taskID +
                "\nНазвание задачи: " + taskName +
                "\nОписание задачи: " + description +
                "\nСтатус задачи: " + taskStatus;

    }
}
