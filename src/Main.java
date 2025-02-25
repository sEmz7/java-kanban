import manager.FileBackedTaskManager;
import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.File;

public class Main {
    public static void main(String[] args) {

        FileBackedTaskManager manager = Managers.getDefaultFileBackedTaskManager();
        manager.loadFromFile(new File("src/data.txt"));

        Task task5 = new Task("5", "6", TaskStatus.NEW);
        manager.createTask(task5);
        Task task6 = new Task("5", "6", TaskStatus.NEW);
        manager.createTask(task6);
        Task task7 = new Task("5", "6", TaskStatus.NEW);
        manager.createTask(task7);

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

        System.out.println("\n\n\t\t\tИстория:\n\n");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}