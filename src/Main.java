import Manager.TaskManager;
import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        boolean isRunning = true;

        while(isRunning) {
            taskManager.checkEpicStatus();
            printMenu();
            System.out.print(">>> ");
            int taskVariant = scanner.nextInt();

            switch (taskVariant) {
                case 1: // Task
                    printTaskMenu();
                    System.out.print(">>> ");
                    int command = scanner.nextInt();
                    switch (command) {
                        case 1:
                            if (!taskManager.tasksIsEmpty()) {
                                ArrayList<Task> arrayOfTasks = taskManager.getAllTasks();
                                System.out.println("==============================");
                                for(Task task: arrayOfTasks) {
                                    System.out.println(task);
                                }
                                System.out.println("\n==============================\n");
                            } else {
                                System.out.println("Список задач пуст.\n");
                            }
                            break;
                        case 2:
                            if (!taskManager.tasksIsEmpty()) {
                                taskManager.removeAllTasks();
                                System.out.println("Список задач очищен.\n");
                            } else {
                                System.out.println("Список задач пуст.\n");
                            }
                            break;
                        case 3:
                            if(!taskManager.tasksIsEmpty()) {
                                System.out.print("\nВведите ID задачи: ");
                                int idTask = scanner.nextInt();
                                Task task = taskManager.getTaskByID(idTask);
                                if (task == null) {
                                    System.out.println("Нет задачи с таким ID.\n");
                                } else {
                                    System.out.println("==============================");
                                    System.out.println(task);
                                    System.out.println("\n==============================\n");
                                }
                            } else {
                                System.out.println("Список задач пуст.");
                            }
                            break;
                        case 4:
                            System.out.print("\nВведите название задачи: ");
                            String taskName = scanner.next();
                            System.out.print("Введите описание задачи: ");
                            String taskDescription = scanner.next();
                            taskManager.createTask(taskName, taskDescription);
                            System.out.println("Задача успешно добавлена.\n");
                            break;
                        case 5:
                            System.out.print("\nВведите ID задачи: ");
                            int taskID = scanner.nextInt();
                            if (taskManager.taskIsExist(taskID)) {
                                System.out.print("Введите новое название задачи: ");
                                String newTaskName = scanner.next();
                                System.out.print("Введите новое описание задачи: ");
                                String newTaskDescription = scanner.next();
                                taskManager.updateTask(newTaskName, newTaskDescription, taskID);
                                System.out.println("Задача обновлена.\n");
                            } else {
                                System.out.println("Нет задачи с таким ID.\n");
                            }
                            break;
                        case 6:
                            System.out.print("Введите ID задачи: ");
                            int idToRemove = scanner.nextInt();
                            if(taskManager.taskIsExist(idToRemove)) {
                                taskManager.removeTaksByID(idToRemove);
                                System.out.println("Задача удалена.\n");
                            } else {
                                System.out.println("Нет задачи с таким ID.\n");
                            }
                            break;
                        case 7:
                            System.out.print("Введите ID задачи: ");
                            int IDtoUpdateStatus = scanner.nextInt();
                            if(taskManager.taskIsExist(IDtoUpdateStatus)) {
                                System.out.print("Введите новый статус(NEW, IN_PROGRESS, DONE): ");
                                String taskStatus = scanner.next();
                                try {
                                    taskManager.changeTaskStatus(IDtoUpdateStatus, taskStatus);
                                    System.out.println("Статус изменен.\n");
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Нет такого статуса.\n");
                                }
                            } else {
                                System.out.println("Нет задачи с таким ID.\n");
                            }
                            break;
                        default:
                            System.out.println("Нет такой команды.\n");
                    }
                    break;
                case 2: // Epic
                    printEpicMenu();
                    System.out.print(">>> ");
                    int epicAction = scanner.nextInt();
                    switch (epicAction) {
                        case 1:
                            if(!taskManager.epicsIsEmpty()) {
                                HashMap<Integer, Epic> epics = taskManager.getAllEpics();
                                System.out.println("==============================");
                                for (Epic epic: epics.values()) {
                                    System.out.println(epic);
                                    System.out.println("Подзадачи эпика:");
                                    if (epic.getSubTasks().isEmpty()) {
                                        System.out.println("Нет подзадач.");
                                        System.out.println("==============================");
                                    } else {
                                        for (SubTask subTask : epic.getSubTasks()) {
                                            System.out.println(subTask);
                                        }
                                        System.out.println("==============================");
                                    }
                                }

                            } else {
                                System.out.println("\nСписок эпиков пуст.\n");
                            }
                            break;
                        case 2:
                            if(!taskManager.epicsIsEmpty()) {
                                taskManager.removeAllEpics();
                                System.out.println("Список эпиков очищен.\n");
                            } else {
                                System.out.println("Список эпиков пуст.\n");
                            }
                            break;
                        case 3:
                            System.out.print("Введите ID эпика: ");
                            int epicID = scanner.nextInt();
                            if (taskManager.epicIsExist(epicID)) {
                                Epic epicToGet = taskManager.getEpicByID(epicID);
                                System.out.println("==============================");
                                System.out.println(epicToGet);
                                System.out.println("\nПодзадачи эпика:");
                                if(epicToGet.getSubTasks().isEmpty()) {
                                    System.out.println("Нету подзадач.");
                                } else {
                                    for (SubTask subTask : epicToGet.getSubTasks()) {
                                        System.out.println(subTask);
                                    }
                                }
                                System.out.println("==============================");
                            } else {
                                System.out.println("Нет эпика с таким ID.\n");
                            }
                            break;
                        case 4:
                            System.out.print("Введите название эпика: ");
                            String epicName = scanner.next();
                            System.out.print("Введите описание эпика: ");
                            String epicDescription = scanner.next();
                            taskManager.createEpic(epicName, epicDescription);
                            System.out.println("Эпик успешно добавлен.\n");
                            break;
                        case 5:
                            System.out.print("Введите ID эпика: ");
                            int epicIDtoUpdate = scanner.nextInt();
                            if(taskManager.epicIsExist(epicIDtoUpdate)) {
                                System.out.print("Введите новое название эпика: ");
                                String newEpicName = scanner.next();
                                System.out.print("Введите новое описание эпика: ");
                                String newEpicDescription = scanner.next();
                                taskManager.updateEpic(newEpicName, newEpicDescription, epicIDtoUpdate);
                                System.out.println("Эпик обновлен.\n");
                            } else {
                                System.out.println("Нет эпика с таким ID.\n");
                            }
                            break;
                        case 6:
                            System.out.print("Введите ID эпика: ");
                            int epicIDtoRemove = scanner.nextInt();
                            if(taskManager.epicIsExist(epicIDtoRemove)) {
                                taskManager.removeEpicByID(epicIDtoRemove);
                                System.out.println("Эпик удален.\n");
                            } else {
                                System.out.println("Нет эпика с таким ID.\n");
                            }
                            break;
                        default:
                            System.out.println("Нет такой команды.\n");
                    }
                    break;
                case 3: // Subtask
                    printSubTaskMenu();
                    System.out.println(">>> ");
                    int subtaskCommand = scanner.nextInt();
                    switch (subtaskCommand) {
                        case 1:
                            System.out.print("Введите ID эпика, подзадачи которого хотите увидеть: ");
                            int epicIDtoView = scanner.nextInt();
                            if(taskManager.epicIsExist(epicIDtoView)) {
                                ArrayList<SubTask> subtasks = taskManager.getAllSubtasksInEpic(epicIDtoView);
                                System.out.print("Подзадачи эпика с ID = " + epicIDtoView + ":");
                                if (subtasks.isEmpty()) {
                                    System.out.println("\nСписок подзадач пуст.\n");
                                } else {
                                    System.out.println("\n==============================");
                                    for (SubTask subTask : subtasks) {
                                        System.out.println(subTask);
                                    }
                                    System.out.println("==============================\n");
                                }
                            } else {
                                System.out.println("Нет эпика с таким ID.\n");
                            }
                            break;
                        case 2:
                            System.out.print("Введите ID эпика, подзадачи в котором хотите удалить: ");
                            int epicIDtoRemoveSubtask = scanner.nextInt();
                            if(taskManager.epicIsExist(epicIDtoRemoveSubtask)) {
                                taskManager.removeAllSubtasksInEpic(epicIDtoRemoveSubtask);
                                System.out.println("Список подзадач очищен.\n");
                            } else {
                                System.out.println("Нет эпика с таким ID.\n");
                            }
                            break;
                        case 3:
                            System.out.print("Введите ID подзадачи: ");
                            int epicIDtoGetSubtask = scanner.nextInt();
                            SubTask subTask = taskManager.getSubtaskByID(epicIDtoGetSubtask);
                            if(subTask == null) {
                                System.out.println("Нет подзадачи с таким ID");
                            } else {
                                System.out.println(subTask + "\n");
                            }
                            break;
                        case 4:
                            System.out.print("Введите ID эпика, для которого хотите создать подзадачу: ");
                            int epicIDtoAddSubtask = scanner.nextInt();
                            if (taskManager.epicIsExist(epicIDtoAddSubtask)) {
                                System.out.print("Введите название подзадачи: ");
                                String subtaskName = scanner.next();
                                System.out.print("Введите описание подзадачи: ");
                                String subtaskDesctiprion = scanner.next();
                                taskManager.createSubtask(subtaskName, subtaskDesctiprion, epicIDtoAddSubtask);
                                System.out.println("Подзадача добавлена.\n");
                            } else {
                                System.out.println("Нет эпика с таким ID.\n");
                            }
                            break;
                        case 5:
                            System.out.print("Введите ID подзадачи: ");
                            int subtaskIdToUpdate = scanner.nextInt();
                            if(taskManager.subtaskIsExist(subtaskIdToUpdate)) {
                                System.out.print("Введите новое название подзадачи: ");
                                String newTaskName = scanner.next();
                                System.out.print("Введите новое описание подзадачи: ");
                                String newTaskDescription = scanner.next();
                                taskManager.updateSubtask(newTaskName, newTaskDescription, subtaskIdToUpdate);
                                System.out.println("Подзадача обновлена.\n");
                            } else {
                                System.out.println("Нет подзадачи с таким ID.\n");
                            }
                            break;
                        case 6:
                            System.out.print("Введите ID подзадачи: ");
                            int subtaskIDtoRemove = scanner.nextInt();
                            if(taskManager.subtaskIsExist(subtaskIDtoRemove)) {
                                taskManager.removeSubtaskByID(subtaskIDtoRemove);
                                System.out.println("Подзадача удалена.\n");

                            } else {
                                System.out.println("Нет подзадачи с таким ID.\n");
                            }
                            break;
                        case 7:
                            System.out.print("Введите ID подзадачи: ");
                            int subtaskIDtoUpdateStatus = scanner.nextInt();
                            if(taskManager.subtaskIsExist(subtaskIDtoUpdateStatus)) {
                                System.out.print("Введите новый статус подзадачи(NEW, IN_PROGRESS, DONE): ");
                                String userInputedStatus = scanner.next();
                                try {
                                    taskManager.changeSubtaskStatus(subtaskIDtoUpdateStatus, userInputedStatus);
                                    System.out.println("Новый статус установлен.\n");
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Нет такого статуса.\n");
                                }
                            } else {
                                System.out.println("Нет подзадачи с таким ID.\n");
                            }
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
                7. Изменить статус задачи
                """);
    }

    public static void printEpicMenu() {
        System.out.println("""
                1. Получение списка всех эпиков
                2. Удаление всех эпиков
                3. Получить эпик по ID
                4. Создать эпик
                5. Обновить эпик
                6. Удалить эпик по ID
                """);
    }

    public static void printSubTaskMenu() {
        System.out.println("""
                1. Получения всех подзадач в эпике
                2. Удаление всех подзадач в эпике
                3. Получить подзадачу по ID
                4. Создать подзадачу
                5. Обновить подзадачу
                6. Удалить подзадачу по ID
                7. Изменить статус подзадачи
                """);
    }
}