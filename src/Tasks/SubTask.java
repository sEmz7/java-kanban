package Tasks;

public class SubTask extends Epic {
    protected int epicID;


    public SubTask(String taskName, String description, TaskStatus taskStatus) {
        super(taskName, description, taskStatus);
    }

    public int getEpicID() {
        return epicID;
    }

    public void setEpicID(int epicID) {
        this.epicID = epicID;
    }

    @Override
    public String toString() {
        return "\nID подзадачи: " + taskID +
                "\nНазвание подзадачи: " + taskName +
                "\nОписание подзадачи: " + description +
                "\nСтатус подзадачи: " + taskStatus;
    }
}
