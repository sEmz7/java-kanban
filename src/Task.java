public class Task {
    // test 2
    protected String taskName;
    protected String description;
    protected int taskID = 0;
    protected TaskStatus taskStatus;


    public Task(String taskName, String description) {
        this.taskName = taskName;
        this.description = description;
        taskID++;
        this.taskStatus = TaskStatus.NEW;
    }


    @Override
    public String toString() {
        return "Тип задачи: Обычная задача" +
                "\nID задачи: " + taskID +
                "\nНазвание задачи: " + taskName +
                "\nОписание задачи: " + description +
                "\nСтатус задачи: " + taskStatus;

    }
}
