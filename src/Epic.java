import java.util.ArrayList;

public class Epic extends Task {

    protected ArrayList<SubTask> subTasks = new ArrayList<>();

    public Epic(String taskName, String description) {
        super(taskName, description);
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    public TaskStatus getTaskStatus() {
        return this.taskStatus;
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
