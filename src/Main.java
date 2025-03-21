import api.HttpTaskServer;
import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefaultTaskManager();

        Epic epic = new Epic("epic1", "desc1", TaskStatus.NEW,
                Duration.ofMinutes(123),
                LocalDateTime.of(0, 12, 12, 12, 12, 12),
                LocalDateTime.of(0, 12, 12, 12, 12, 12));
        manager.createEpic(epic);

        SubTask subTask1 = new SubTask("sub1", "dess1",TaskStatus.NEW, Duration.ofMinutes(44),
                LocalDateTime.of(2020, 12, 12, 12, 12, 12),
                LocalDateTime.of(2077, 11, 11, 11, 11, 11));
        subTask1.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask1);

        SubTask subTask2 = new SubTask("sub2", "dess2",TaskStatus.NEW, Duration.ofMinutes(44),
                LocalDateTime.of(2097, 12, 12, 12, 12, 12),
                LocalDateTime.of(2099, 11, 11, 11, 11, 11));
        subTask2.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask2);

        HttpTaskServer httpTaskServer = new HttpTaskServer(manager);
        httpTaskServer.start();
    }
}