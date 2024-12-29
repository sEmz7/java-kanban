import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TaskManager {
    static Scanner scanner = new Scanner(System.in);

    static HashMap<Integer, Task> tasks = new HashMap<>();
    static HashMap<Integer, Epic> epics = new HashMap<>();

    public static void main(String[] args) {
        boolean isRunning = true;
        boolean isExists;

        while (isRunning) {
            printMenu();
            System.out.print(">>> ");
            int taskVariant = scanner.nextInt();

            switch (taskVariant) {
                case 1:
                    printTaskMenu();
                    System.out.print(">>> ");
                    int command = scanner.nextInt();
                    switch (command) {
                        case 1:
                            if (tasks.isEmpty()) {
                                System.out.println("\nСписок задач пуст.\n");
                            } else {
                                System.out.println("==============================");
                                for(Task i: tasks.values()) {
                                    System.out.println(i);
                                }
                                System.out.println("\n==============================\n");
                            }

                            break;
                        case 2:
                            if (tasks.isEmpty()) {
                                System.out.println("\nСписок задач пуст.\n");
                            } else {
                                tasks.clear();
                                System.out.println("\nВсе задачи удалены.\n");
                            }
                            break;
                        case 3:
                            if (tasks.isEmpty()) {
                                System.out.println("\nСписок задач пуст.\n");
                            } else {
                                System.out.print("\nВведите ID задачи: ");
                                int idTask = scanner.nextInt();
                                if (tasks.containsKey(idTask)) {
                                    System.out.println("==============================");
                                    System.out.println(tasks.get(idTask));
                                    System.out.println("\n==============================\n");
                                } else {
                                    System.out.println("Нет задачи с таким ID.\n");
                                }
                            }
                            break;
                        case 4:
                            isExists = false;
                            System.out.print("\nВведите название задачи: ");
                            String taskName = scanner.next();
                            System.out.print("Введите описание задачи: ");
                            String taskDescription = scanner.next();
                            Task newTask = new Task(taskName, taskDescription);
                            for (Task task_i: tasks.values()) {
                                if (newTask.equals(task_i)) {
                                    System.out.println("Такая задача уже есть.\n");
                                    Task.taskCount--;
                                    isExists = true;
                                    break;
                                }
                            }
                            if (!isExists) {
                                tasks.put(newTask.getTaskID(), newTask);
                                System.out.println("Задача успешно добавлена.\n");
                            }
                            break;
                        case 5:
                            isExists = false;
                            System.out.print("\nВведите ID задачи: ");
                            int taskID = scanner.nextInt();
                            if (tasks.containsKey(taskID)) {
                                System.out.print("Введите новое название задачи: ");
                                String newTaskName = scanner.next();
                                System.out.print("Введите новое описание задачи: ");
                                String newTaskDescription = scanner.next();
                                Task taskToUpdate = tasks.get(taskID);
                                for(Task task_i: tasks.values()) {
                                    if (taskToUpdate.getDescription().equals(newTaskDescription) && taskToUpdate.getTaskName().equals(newTaskName)) {
                                        System.out.println("Такая задача уже есть.");
                                        isExists = true;
                                        break;
                                    }
                                }
                                if(!isExists) {
                                    taskToUpdate.setTaskName(newTaskName);
                                    taskToUpdate.setDescription(newTaskDescription);
                                    System.out.println("Задача обновлена.\n");
                                }
                            } else {
                                System.out.println("Нет задачи с таким ID.\n");
                            }
                            break;
                        case 6:
                            System.out.print("Введите ID задачи: ");
                            int idToRemove = scanner.nextInt();
                            if (tasks.containsKey(idToRemove)) {
                                tasks.remove(idToRemove);
                                System.out.println("Задача удалена.\n");
                            } else {
                                System.out.println("Нет задачи с таким ID.\n");
                            }
                            break;
                    }
                case 4:
                    isRunning = false;
                    break;
            }
        }
    }

    public static void printMenu() {
        System.out.println("""
                Выберите тип задачи для просмотра действий:
                1. Задача
                2. Эпик
                3. Подзадача
                4. Завершить программу
                """);
    }

    public static void printTaskMenu() {
        System.out.println("""
                1. Получение списка всех задач
                2. Удаление всех задач
                3. Получить задачу по ID
                4. Создать задачу
                5. Обновить задачу
                6. Удалить задачу по ID
                """);
    }
}