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
            checkEpicStatus();
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
                                for (Task i : tasks.values()) {
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
                            for (Task task_i : tasks.values()) {
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
                                for (Task task_i : tasks.values()) {
                                    if (taskToUpdate.getDescription().equals(newTaskDescription) && taskToUpdate.getTaskName().equals(newTaskName)) {
                                        System.out.println("Такая задача уже есть.");
                                        isExists = true;
                                        break;
                                    }
                                }
                                if (!isExists) {
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
                        case 7:
                            System.out.print("Введите ID задачи: ");
                            int IDtoUpdateStatus = scanner.nextInt();
                            if (tasks.containsKey(IDtoUpdateStatus)) {
                                System.out.print("Введите новый статус(NEW, IN_PROGRESS, DONE): ");
                                String taskStatus = scanner.next();
                                try {
                                    TaskStatus newTaskStatus = TaskStatus.valueOf(taskStatus);
                                    tasks.get(IDtoUpdateStatus).setTaskStatus(newTaskStatus);
                                    System.out.println("Статус изменен.\n");
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Нет такого статуса.\n");
                                }
                            } else {
                                System.out.println("Нет задачи с таким ID.\n");
                            }
                            break;
                    }
                    break;
                case 2:
                    printEpicMenu();
                    System.out.print(">>> ");
                    int epicCommand = scanner.nextInt();

                    switch (epicCommand) {
                        case 1:
                            if (epics.isEmpty()) {
                                System.out.println("\nСписок эпиков пуст.\n");
                            } else {
                                System.out.println("==============================");
                                for (Integer id : epics.keySet()) {
                                    System.out.println(epics.get(id));
                                    System.out.println("Подзадачи эпика:");
                                    if (epics.get(id).getSubTasks().isEmpty()) {
                                        System.out.println("Нет подзадач.");
                                        System.out.println("==============================");
                                    } else {
                                        for (SubTask subTask : epics.get(id).getSubTasks()) {
                                            System.out.println(subTask);
                                        }
                                        System.out.println("==============================");
                                    }
                                }
                            }
                            break;
                        case 2:
                            if (epics.isEmpty()) {
                                System.out.println("Список эпиков пуст.\n");
                            } else {
                                epics.clear();
                                System.out.println("Список эпиков очищен.\n");
                            }
                            break;
                        case 3:
                            System.out.print("Введите ID эпика: ");
                            int epicIDtoGet = scanner.nextInt();
                            if (epics.containsKey(epicIDtoGet)) {
                                System.out.println("==============================");
                                Epic epicToGet = epics.get(epicIDtoGet);
                                System.out.println(epicToGet);
                                System.out.println("\nПодзадачи эпика:");
                                for (SubTask subTask : epicToGet.getSubTasks()) {
                                    System.out.println(subTask);
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
                            Epic newEpic = new Epic(epicName, epicDescription);
                            if (epics.containsValue(newEpic)) {
                                System.out.println("Такой эпик уже есть.");
                            } else {
                                epics.put(newEpic.getTaskID(), newEpic);
                                System.out.println("Эпик успешно добавлен.\n");
                            }
                            break;
                        case 5:
                            isExists = false;
                            System.out.print("Введите ID эпика: ");
                            int epicIDtoUpdate = scanner.nextInt();
                            if (epics.containsKey(epicIDtoUpdate)) {
                                System.out.print("Введите новое название эпика: ");
                                String newEpicName = scanner.next();
                                System.out.print("Введите новое описание эпика: ");
                                String newEpicDescription = scanner.next();
                                Epic epicToUpdate = epics.get(epicIDtoUpdate);
                                for (Epic epic : epics.values()) {
                                    if (epic.getDescription().equals(newEpicDescription) && epic.getTaskName()
                                            .equals(newEpicName)) {
                                        System.out.println("Придумайте новое описание или название эпика.\n");
                                        isExists = true;
                                    }
                                }
                                if (!isExists) {
                                    epicToUpdate.setTaskName(newEpicName);
                                    epicToUpdate.setDescription(newEpicDescription);
                                    System.out.println("Эпик обновлен.\n");
                                }
                            } else {
                                System.out.println("Нет эпика с таким ID.\n");
                            }
                            break;
                        case 6:
                            System.out.print("Введите ID эпика: ");
                            int epicIDtoRemove = scanner.nextInt();
                            if (epics.containsKey(epicIDtoRemove)) {
                                epics.remove(epicIDtoRemove);
                                System.out.println("Эпик удален.\n");
                            } else {
                                System.out.println("Нет эпика с таким ID.\n");
                            }
                            break;
                    }
                    break;
                case 3:
                    printSubTaskMenu();
                    System.out.print(">>> ");
                    int subtaskCommand = scanner.nextInt();
                    switch (subtaskCommand) {
                        case 1:
                            System.out.print("Введите ID эпика, подзадачи которого хотите увидеть: ");
                            int epicIDtoView = scanner.nextInt();
                            if (epics.containsKey(epicIDtoView)) {
                                ArrayList<SubTask> subtasks = epics.get(epicIDtoView).getSubTasks();
                                System.out.print("Подзадачи эпика с ID = " + epicIDtoView + ":");
                                if (subtasks.isEmpty()) {
                                    System.out.println("\nСписок подзадач пуст.\n");
                                } else {
                                    for (SubTask subTask : subtasks) {
                                        System.out.print("\n==============================");
                                        System.out.println(subTask);
                                        System.out.println("==============================\n");
                                    }
                                }
                            } else {
                                System.out.println("Нет эпика с таким ID.\n");
                            }
                            break;
                        case 2:
                            System.out.print("Введите ID эпика, подзадачи в котором хотите удалить: ");
                            int epicIDtoRemoveSubtask = scanner.nextInt();
                            if (epics.containsKey(epicIDtoRemoveSubtask)) {
                                ArrayList<SubTask> subtasks = epics.get(epicIDtoRemoveSubtask).getSubTasks();
                                if (subtasks.isEmpty()) {
                                    System.out.println("Список подзадач пуст.\n");
                                } else {
                                    subtasks.clear();
                                    System.out.println("Список подзадач очищен.\n");
                                }
                            } else {
                                System.out.println("Нет эпика с таким ID.\n");
                            }
                            break;
                        case 3:
                            isExists = false;
                            System.out.print("Введите ID подзадачи: ");
                            int epicIDtoGetSubtask = scanner.nextInt();
                            for (Epic epic : epics.values()) {
                                for (SubTask subTask : epic.getSubTasks()) {
                                    if (subTask.getTaskID() == epicIDtoGetSubtask) {
                                        System.out.println(subTask);
                                        isExists = true;
                                    }
                                }
                            }
                            if (!isExists) {
                                System.out.println("Нет подзадачи с таким ID.\n");
                            }
                            break;
                        case 4:
                            isExists = false;
                            System.out.print("Введите ID эпика, для которого хотите создать подзадачу: ");
                            int epicIDtoAddSubtask = scanner.nextInt();
                            if (epics.containsKey(epicIDtoAddSubtask)) {
                                System.out.print("Введите название подзадачи: ");
                                String subtaskName = scanner.next();
                                System.out.print("Введите описание подзадачи: ");
                                String subtaskDesctiprion = scanner.next();
                                ArrayList<SubTask> arrayOfSubtasks = epics.get(epicIDtoAddSubtask).getSubTasks();
                                for (SubTask subTask : arrayOfSubtasks) {
                                    if (subTask.getTaskName().equals(subtaskName) && subTask.getDescription().equals(subtaskDesctiprion)) {
                                        System.out.println("Такая подзадача уже есть.\n");
                                        isExists = true;
                                    }
                                }
                                if (!isExists) {
                                    arrayOfSubtasks.add(new SubTask(subtaskName, subtaskDesctiprion));
                                    System.out.println("Подзадача добавлена.\n");
                                }
                            } else {
                                System.out.println("Нет эпика с таким ID.\n");
                            }
                            break;
                        case 5:
                            System.out.print("Введите ID подзадачи: ");
                            int epicIDtoUpdate = scanner.nextInt();
                            SubTask subtaskToUpdate = null;
                            for (Epic epic : epics.values()) {
                                for (SubTask subTask : epic.getSubTasks()) {
                                    if (subTask.getTaskID() == epicIDtoUpdate) {
                                        subtaskToUpdate = subTask;
                                    }
                                }
                            }
                            if (subtaskToUpdate == null) {
                                System.out.println("Нет подзадачи с таким ID.\n");
                            } else {
                                System.out.print("Введите новое название подзадачи: ");
                                String newSubtaskName = scanner.next();
                                System.out.print("Введите новое описание подзадачи: ");
                                String newSubtaskDescription = scanner.next();
                                if (subtaskToUpdate.getTaskName().equals(newSubtaskName) &&
                                        subtaskToUpdate.getDescription().equals(newSubtaskDescription)) {
                                    System.out.println("Придумайте новое описание или название подзадаче.\n");
                                } else {
                                    subtaskToUpdate.setTaskName(newSubtaskName);
                                    subtaskToUpdate.setDescription(newSubtaskDescription);
                                    System.out.println("Подзадача обновлена.\n");
                                }
                            }
                            break;
                        case 6:
                            System.out.print("Введите ID подзадачи: ");
                            int subtaskIDtoRemove = scanner.nextInt();
                            isExists = false;
                            for (Epic epic : epics.values()) {
                                for (SubTask subTask : epic.getSubTasks()) {
                                    if (subTask.getTaskID() == subtaskIDtoRemove) {
                                        isExists = true;
                                        epic.getSubTasks().remove(subTask);
                                        System.out.println("Подзадача удалена.\n");
                                        break;
                                    }
                                }
                            }
                            if (!isExists) {
                                System.out.println("Нет подзадачи с таким ID.\n");
                            }
                            break;
                        case 7:
                            System.out.print("Введите ID подзадачи: ");
                            int subtaskIDtoUpdateStatus = scanner.nextInt();
                            isExists = false;
                            for (Epic epic : epics.values()) {
                                for (SubTask subTask : epic.getSubTasks()) {
                                    if (subTask.getTaskID() == subtaskIDtoUpdateStatus) {
                                        isExists = true;
                                        if(subTask.getTaskStatus().equals(TaskStatus.DONE)) {
                                            System.out.println("Нельзя изменить статус, подзадача уже завершена. Попробуйте создать новую.\n");
                                            break;
                                        }
                                        System.out.print("Введите новый статус подзадачи(NEW, IN_PROGRESS, DONE): ");
                                        String userInputedStatus = scanner.next();
                                        try {
                                            TaskStatus newStatus = TaskStatus.valueOf(userInputedStatus);
                                            subTask.setTaskStatus(newStatus);
                                            System.out.println("Новый статус установлен.\n");
                                        } catch (IllegalArgumentException e) {
                                            System.out.println("Нет такого статуса.\n");
                                        }
                                        break;
                                    }
                                }
                            }
                            if (!isExists) {
                                System.out.println("Нет подзадачи с таким ID.\n");
                            }
                            break;
                    }
                    break;
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

    public static void checkEpicStatus() {
        for (Epic epic : epics.values()) {
            TaskStatus currentEpicStatus = epic.taskStatus;
            if (currentEpicStatus.equals(TaskStatus.NEW)) {
                for (SubTask subTask : epic.getSubTasks()) {
                    if (!subTask.getTaskStatus().equals(TaskStatus.NEW)) {
                        currentEpicStatus = TaskStatus.IN_PROGRESS;
                        epic.setTaskStatus(currentEpicStatus);
                    }
                }
            } if (currentEpicStatus.equals(TaskStatus.IN_PROGRESS)) {
                int sizeOfSubtasks = epic.getSubTasks().size();
                int counterOfDone = 0;
                for (SubTask subTask : epic.getSubTasks()) {
                    if (subTask.getTaskStatus().equals(TaskStatus.DONE)) {
                        counterOfDone++;
                    }
                }
                if (sizeOfSubtasks == counterOfDone) {
                    currentEpicStatus = TaskStatus.DONE;
                    epic.setTaskStatus(currentEpicStatus);
                    return;
                }
            } else if (currentEpicStatus == TaskStatus.DONE) {
                for (SubTask subTask : epic.getSubTasks()) {
                    if (!subTask.getTaskStatus().equals(TaskStatus.DONE)) {
                        epic.setTaskStatus(TaskStatus.IN_PROGRESS);
                        return;
                    }
                }
            }
        }
    }
}
