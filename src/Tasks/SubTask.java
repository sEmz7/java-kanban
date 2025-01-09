package Tasks;

public class SubTask extends Epic {

    public SubTask(String taskName, String description, TaskStatus taskStatus) {
        super(taskName, description, taskStatus);
    }


    @Override
    public String toString() {
        return "\nID подзадачи: " + taskID +
                "\nНазвание подзадачи: " + taskName +
                "\nОписание подзадачи: " + description +
                "\nСтатус подзадачи: " + taskStatus;
    }
}
