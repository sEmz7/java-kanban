import java.util.ArrayList;
import java.util.Scanner;

public class TaskManager {
    static Scanner scanner = new Scanner(System.in);

    static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        boolean isRunning = true;

        while (isRunning) {
            printMenu();
            System.out.print("Введите номер команды: ");
            int command = scanner.nextInt();

            switch (command) {
                case 1:
                    System.out.print("""
                            ----------------------
                            1. Новая задача
                            2. Эпик
                            3. Подзадача
                            ----------------------
                            """);
                    System.out.print("Выберите тип задачи: ");
                    int taskType = scanner.nextInt();
                    switch (taskType) {
                        case 1:
                            System.out.print("\nВведите название новой задачи: ");
                            String taskName = scanner.next();
                            System.out.print("\nВведите описание задачи: ");
                            String taskDescription = scanner.next();
                            Task newTask = new Task(taskName, taskDescription);
                            tasks.add(newTask);
                            System.out.println("Задача добавлена.\n");
                            break;
                        case 2:

                            // case 2
                            break;
                        case 3:
                            System.out.println("Введите ID эпика, которому хотите добавить подзадачу: ");
                            int epicID = scanner.nextInt();
                            System.out.println("Введите новую подзадачу: ");
                            // case 3
                            break;
                    }
                    break;
                case 2:
                    for(Task task: tasks) {
                        System.out.println(task);
                    }

                case 6:
                    isRunning = false;
                    break;
            }
        }

    }

    public static void printMenu() {
        System.out.println("""
                1. Добавить новую задачу/подзадачу
                2. Посмотреть список всех задач
                3. Найти задачу по ID
                4. Обновить статус или описание задачи
                5. Удалить задачу
                6. Завершить программу
                """);
    }
}
