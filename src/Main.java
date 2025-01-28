import Manager.Managers;
import Manager.TaskManager;
import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;
import Tasks.TaskStatus;

import java.util.Scanner;

public class Main<T extends Task> {
    public static void main(String[] args) {

        /*                    Проверка истории                    */

        Scanner scanner = new Scanner(System.in);
        TaskManager manager = Managers.getDefaultTaskManager();
        Task task1 = new Task("# Task 1", "Description 1", TaskStatus.NEW);
        manager.createTask(task1);

        Epic epic1 = new Epic("# Epic 1", "Desc 1", TaskStatus.NEW);
        manager.createEpic(epic1);

        SubTask subTask1 = new SubTask("# Subtask 1", "Desc1", TaskStatus.NEW);
        subTask1.setEpicID(epic1.getTaskID());
        manager.createSubtask(subTask1);

        SubTask subTask2 = new SubTask("# Subtask 2", "Desc2", TaskStatus.NEW);
        subTask2.setEpicID(epic1.getTaskID());
        manager.createSubtask(subTask2);

        SubTask getSub = manager.getSubtaskByID(subTask2.getTaskID());
        Task getTask = manager.getTaskByID(task1.getTaskID());
        Epic getEpic = manager.getEpicByID(epic1.getTaskID());
        getTask = manager.getTaskByID(task1.getTaskID());
        getTask = manager.getTaskByID(task1.getTaskID());
        getTask = manager.getTaskByID(task1.getTaskID());
        getTask = manager.getTaskByID(task1.getTaskID());
        getTask = manager.getTaskByID(task1.getTaskID());
        getTask = manager.getTaskByID(task1.getTaskID());
        getTask = manager.getTaskByID(task1.getTaskID());
        getSub = manager.getSubtaskByID(subTask2.getTaskID());
        getSub.setDescription("updated");
        manager.updateSubtask(getSub);
        getSub = manager.getSubtaskByID(subTask2.getTaskID());

        printAllTasks(manager);

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