import api.HttpTaskServer;
import manager.file.FileBackedTaskManager;
import manager.TaskManager;
import manager.memory.InMemoryTaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        TaskManager manager = new InMemoryTaskManager();
        manager.createTask(new Task("task1", "desc1", TaskStatus.NEW, Duration.ofMinutes(13), LocalDateTime.of(2022, 12, 12, 12, 12, 12 )));
        manager.createTask(new Task("task2", "desc2", TaskStatus.NEW, Duration.ofMinutes(13), LocalDateTime.of(2022, 12, 12, 12, 12, 12 )));
        HttpTaskServer httpTaskServer = new HttpTaskServer(manager);
        httpTaskServer.start();
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        manager.getAllTasks().forEach(System.out::println);

        System.out.println("\nЭпики:");
        manager.getAllEpics().forEach(epic -> {
            System.out.println(epic);
            manager.getAllSubtasksInEpic(epic.getTaskID())
                    .forEach(subTask -> System.out.println("--> " + subTask));
        });

        System.out.println("\nПодзадачи:");
        manager.getAllSubtasks().forEach(System.out::println);

        System.out.println("\n");

//        System.out.println("\n\n\t\t\tИстория:\n\n");
//        for (Task task : manager.getHistory()) {
//            System.out.println(task);
//        }
    }
}