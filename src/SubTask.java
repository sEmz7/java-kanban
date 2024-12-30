public class SubTask extends Epic {

    public SubTask(String taskName, String description) {
        super(taskName, description);
    }


    @Override
    public String toString() {
        return "\nID подзадачи: " + taskID +
                "\nНазвание подзадачи: " + taskName +
                "\nОписание подзадачи: " + description +
                "\nСтатус подзадачи: " + taskStatus;
    }
}
