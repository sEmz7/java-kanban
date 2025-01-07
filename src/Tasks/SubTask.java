package Tasks;

public class SubTask extends Epic {

    public SubTask(String taskName, String description, int taskCount) {
        super(taskName, description, taskCount);
    }


    @Override
    public String toString() {
        return "\nID подзадачи: " + taskID +
                "\nНазвание подзадачи: " + taskName +
                "\nОписание подзадачи: " + description +
                "\nСтатус подзадачи: " + taskStatus;
    }
}
