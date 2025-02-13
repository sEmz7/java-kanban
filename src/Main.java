import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

public class Main {
    public static void main(String[] args) {

        /*                    Проверка истории                    */

        TaskManager manager = Managers.getDefaultTaskManager();
        Task task1 = new Task("# Task 1", "Description 1", TaskStatus.NEW);
        manager.createTask(task1);

        Task task2 = new Task("# Task 2", "Description 2", TaskStatus.NEW);
        manager.createTask(task2);

        Epic epic1 = new Epic("# Epic 1", "Desc 1", TaskStatus.NEW);
        manager.createEpic(epic1);

        SubTask subTask1 = new SubTask("# Subtask 1", "Desc1", TaskStatus.NEW);
        subTask1.setEpicID(epic1.getTaskID());
        manager.createSubtask(subTask1);

        SubTask subTask2 = new SubTask("# Subtask 2", "Desc2", TaskStatus.NEW);
        subTask2.setEpicID(epic1.getTaskID());
        manager.createSubtask(subTask2);

        Epic epic2 = new Epic("# Epic 2", "Desc 2", TaskStatus.NEW);
        manager.createEpic(epic2);

        manager.getSubtaskByID(subTask1.getTaskID());
        manager.getTaskByID(task1.getTaskID());
        manager.getEpicByID(epic1.getTaskID());
        manager.getSubtaskByID(subTask1.getTaskID());
        manager.getEpicByID(epic2.getTaskID());
        manager.getTaskByID(task2.getTaskID());
        manager.getTaskByID(task1.getTaskID());
        manager.removeEpicByID(epic1.getTaskID());

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