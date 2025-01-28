package Tasks;

public class SubTask extends Epic {
    protected int epicID;


    public SubTask(String taskName, String description, TaskStatus taskStatus) {
        super(taskName, description, taskStatus);
    }

    public SubTask(int taskID, String taskName, String description, TaskStatus taskStatus) {
        super(taskID, taskName, description, taskStatus);
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
        return  "\nТип задачи: подзадача" +
                "\nID подзадачи: " + taskID +
                "\nНазвание подзадачи: " + taskName +
                "\nОписание подзадачи: " + description +
                "\nСтатус подзадачи: " + taskStatus;
    }
}
