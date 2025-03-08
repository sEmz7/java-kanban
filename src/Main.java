import manager.FileBackedTaskManager;
import manager.TaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {


        FileBackedTaskManager manager = FileBackedTaskManager.loadFromFile(new File("src/data.txt"));

        Task task5 = new Task("5", "6", TaskStatus.NEW, Duration.ofMinutes(55),
                LocalDateTime.of(2025, 12, 12, 12, 12));
        manager.createTask(task5);
        Task task6 = new Task("5", "6", TaskStatus.NEW, Duration.ofMinutes(155),
                LocalDateTime.of(2077, 7, 10, 10, 10));
        manager.createTask(task6);
        Task task7 = new Task("5", "6", TaskStatus.NEW, Duration.ofMinutes(120),
                LocalDateTime.of(2022, 3, 11, 11, 11));
        manager.createTask(task7);

        Task task8 = new Task("8", "8", TaskStatus.NEW);
        manager.createTask(task8);




        Epic epic1 = new Epic("ep1", "epdes1", TaskStatus.NEW);
        manager.createEpic(epic1);
        SubTask sub1 = new SubTask("sub1", "sub1", TaskStatus.NEW, Duration.ofMinutes(155),
                LocalDateTime.of(2077, 12, 12, 12, 12), LocalDateTime.of(2099, 12, 12, 12, 12));
        sub1.setEpicID(epic1.getTaskID());
        manager.createSubtask(sub1);
        SubTask sub2 = new SubTask("sub2", "sub2", TaskStatus.NEW, Duration.ofMinutes(190),
                LocalDateTime.of(2005, 10, 7, 8, 30), LocalDateTime.of(2009, 12, 12, 12, 12));
        sub2.setEpicID(epic1.getTaskID());
        manager.createSubtask(sub2);

        /** Не будет добавлена, так как пересекается по времени **/
        SubTask sub3 = new SubTask("sub3", "sub3", TaskStatus.NEW, Duration.ofMinutes(55),
                LocalDateTime.of(2007, 10, 7, 8, 30), LocalDateTime.of(2010, 12, 12, 12, 12));
        sub3.setEpicID(epic1.getTaskID());
        manager.createSubtask(sub3);

        SubTask sub4 = new SubTask("4", "4", TaskStatus.NEW);
        sub4.setEpicID(epic1.getTaskID());
        manager.createSubtask(sub4);


        printAllTasks(manager);

        System.out.println("Отсортированные по приоритету:");
        manager.getPrioritizedTasks().forEach(System.out::println);


    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (Task task : manager.getAllSubtasksInEpic(epic.getTaskID())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("\n");

//        System.out.println("\n\n\t\t\tИстория:\n\n");
//        for (Task task : manager.getHistory()) {
//            System.out.println(task);
//        }
    }
}