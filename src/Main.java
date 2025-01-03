import Manager.TaskManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager(scanner);
        boolean isRunning = true;

        while(isRunning) {
            taskManager.checkEpicStatus();
            taskManager.printMenu();
            System.out.print(">>> ");
            int taskVariant = scanner.nextInt();

            switch (taskVariant) {
                case 1: // Task
                    taskManager.printTaskMenu();
                    System.out.print(">>> ");
                    int command = scanner.nextInt();
                    switch (command) {
                        case 1:
                            taskManager.printAllTasks();
                            break;
                        case 2:
                            taskManager.removeAllTasks();
                            break;
                        case 3:
                            taskManager.printTaskByID();
                            break;
                        case 4:
                            taskManager.createTask();
                            break;
                        case 5:
                            taskManager.updateTask();
                            break;
                        case 6:
                            taskManager.removeTaksByID();
                            break;
                        case 7:
                            taskManager.changeTaskStatus();
                            break;
                        default:
                            System.out.println("Нет такой команды.\n");
                    }
                    break;
                case 2: // Epic
                    taskManager.printEpicMenu();
                    System.out.print(">>> ");
                    int epicAction = scanner.nextInt();
                    switch (epicAction) {
                        case 1:
                            taskManager.printAllEpics();
                            break;
                        case 2:
                            taskManager.removeAllEpics();
                            break;
                        case 3:
                            taskManager.getEpicByID();
                            break;
                        case 4:
                            taskManager.createEpic();
                            break;
                        case 5:
                            taskManager.updateEpic();
                            break;
                        case 6:
                            taskManager.removeEpicByID();
                            break;
                        default:
                            System.out.println("Нет такой команды.\n");
                    }
                    break;
                case 3: // Subtask
                    taskManager.printSubTaskMenu();
                    System.out.println(">>> ");
                    int subtaskCommand = scanner.nextInt();
                    switch (subtaskCommand) {
                        case 1:
                            taskManager.printAllSubtasksInEpic();
                            break;
                        case 2:
                            taskManager.removeAllSubtasksInEpic();
                            break;
                        case 3:
                            taskManager.printSubtaskByID();
                            break;
                        case 4:
                            taskManager.createSubtask();
                            break;
                        case 5:
                            taskManager.updateSubtask();
                            break;
                        case 6:
                            taskManager.removeSubtaskByID();
                            break;
                        case 7:
                            taskManager.changeSubtaskStatus();
                            break;
                        default:
                            System.out.println("Нет такой команды.\n");
                    }
                    break;
                case 4:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Нет такой команды.\n");
            }
        }
    }
}
