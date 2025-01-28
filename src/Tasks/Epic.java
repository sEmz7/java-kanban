package Tasks;

import java.util.ArrayList;

public class Epic extends Task {

    protected ArrayList<SubTask> subTasks = new ArrayList<>();

    public Epic(String taskName, String description, TaskStatus taskStatus) {
        super(taskName, description, taskStatus);
    }

    public Epic(int taskID, String taskName, String description, TaskStatus taskStatus) {
        super(taskID, taskName, description, taskStatus);
    }

    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks);
    }

    public TaskStatus getTaskStatus() {
        return this.taskStatus;
    }

    public Epic getSnapshot() {
        return new Epic(this.getTaskID(), this.getTaskName(), this.getDescription(), this.getTaskStatus());
    }

    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    @Override
    public String toString() {
        return "\nТип задачи: Эпик" +
                "\nID эпика: " + taskID +
                "\nНазвание эпика: " + taskName +
                "\nСтатус эпика: " + taskStatus;
    }
}
