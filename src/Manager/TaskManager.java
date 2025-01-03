package Manager;
import Tasks.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TaskManager {
    private final Scanner scanner;
    public TaskManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public static int taskCount = 0;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private boolean isExists;

    public void printMenu() {
        System.out.println("""
                Выберите тип задачи для просмотра действий:
                1. Задача
                2. Эпик
                3. Подзадача
                4. Завершить программу
                """);
    }

    public void printTaskMenu() {
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

    public void printEpicMenu() {
        System.out.println("""
                1. Получение списка всех эпиков
                2. Удаление всех эпиков
                3. Получить эпик по ID
                4. Создать эпик
                5. Обновить эпик
                6. Удалить эпик по ID
                """);
    }

    public void printSubTaskMenu() {
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

    public void checkEpicStatus() {
        for (Epic epic : epics.values()) {
            TaskStatus currentEpicStatus = epic.getTaskStatus();
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

    public void printAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\nСписок задач пуст.\n");
        } else {
            System.out.println("==============================");
            for (Task i : tasks.values()) {
                System.out.println(i);
            }
            System.out.println("\n==============================\n");
        }
    }

    public void removeAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\nСписок задач пуст.\n");
        } else {
            tasks.clear();
            System.out.println("\nВсе задачи удалены.\n");
        }
    }

    public void printTaskByID() {
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
    }

    public void createTask() {
        isExists = false;
        System.out.print("\nВведите название задачи: ");
        String taskName = scanner.next();
        System.out.print("Введите описание задачи: ");
        String taskDescription = scanner.next();
        for (Task task_i : tasks.values()) {
            if (task_i.getTaskName().equals(taskName) && task_i.getDescription().equals(taskDescription)) {
                System.out.println("Такая задача уже есть.\n");
                isExists = true;
                break;
            }
        }
        if (!isExists) {
            taskCount++;
            Task newTask = new Task(taskName, taskDescription);
            tasks.put(newTask.getTaskID(), newTask);
            System.out.println("Задача успешно добавлена.\n");
        }
    }

    public void updateTask() {
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
    }

    public void removeTaksByID() {
        System.out.print("Введите ID задачи: ");
        int idToRemove = scanner.nextInt();
        if (tasks.containsKey(idToRemove)) {
            tasks.remove(idToRemove);
            System.out.println("Задача удалена.\n");
        } else {
            System.out.println("Нет задачи с таким ID.\n");
        }
    }

    public void changeTaskStatus() {
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
    }


    public void printAllEpics() {
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
    }

    public void removeAllEpics() {
        if (epics.isEmpty()) {
            System.out.println("Список эпиков пуст.\n");
        } else {
            epics.clear();
            System.out.println("Список эпиков очищен.\n");
        }
    }

    public void getEpicByID() {
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
    }

    public void createEpic() {
        System.out.print("Введите название эпика: ");
        String epicName = scanner.next();
        System.out.print("Введите описание эпика: ");
        String epicDescription = scanner.next();
        for(Epic epic: epics.values()) {
            if (epic.getTaskName().equals(epicName) && epic.getDescription().equals(epicDescription)) {
                System.out.println("Такой эпик уже есть.");
                return;
            }
        }
        taskCount++;
        Epic newEpic = new Epic(epicName, epicDescription);
        epics.put(newEpic.getTaskID(), newEpic);
        System.out.println("Эпик успешно добавлен.\n");

    }

    public void updateEpic() {
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
    }

    public void removeEpicByID() {
        System.out.print("Введите ID эпика: ");
        int epicIDtoRemove = scanner.nextInt();
        if (epics.containsKey(epicIDtoRemove)) {
            epics.remove(epicIDtoRemove);
            System.out.println("Эпик удален.\n");
        } else {
            System.out.println("Нет эпика с таким ID.\n");
        }
    }

    public void printAllSubtasksInEpic() {
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
    }

    public void removeAllSubtasksInEpic() {
        System.out.print("Введите ID эпика, подзадачи в котором хотите удалить: ");
        int epicIDtoRemoveSubtask = scanner.nextInt();
        if (epics.containsKey(epicIDtoRemoveSubtask)) {
            ArrayList<SubTask> subtasks = epics.get(epicIDtoRemoveSubtask).getSubTasks();
            if (subtasks.isEmpty()) {
                System.out.println("Список подзадач пуст.\n");
            } else {
                subtasks.clear();
                epics.get(epicIDtoRemoveSubtask).setSubTasks(subtasks);
                System.out.println("Список подзадач очищен.\n");
            }
        } else {
            System.out.println("Нет эпика с таким ID.\n");
        }
    }

    public void printSubtaskByID() {
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
    }

    public void createSubtask() {
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
                taskCount++;
                arrayOfSubtasks.add(new SubTask(subtaskName, subtaskDesctiprion));
                epics.get(epicIDtoAddSubtask).setSubTasks(arrayOfSubtasks);
                System.out.println("Подзадача добавлена.\n");
            }
        } else {
            System.out.println("Нет эпика с таким ID.\n");
        }
    }

    public void updateSubtask() {
        System.out.print("Введите ID подзадачи: ");
        int subtaskIdToUpdate = scanner.nextInt();
        for(Epic epic: epics.values()) {
            ArrayList<SubTask> subTasks = epic.getSubTasks();
            for(SubTask subTask: subTasks) {
                if(subTask.getTaskID() == subtaskIdToUpdate) {
                    System.out.print("Введите новое название подзадачи: ");
                    String newTaskName = scanner.next();
                    System.out.print("Введите новое описание подзадачи: ");
                    String newTaskDescription = scanner.next();
                    if(subTask.getTaskName().equals(newTaskName) && subTask.getDescription().equals(newTaskDescription)) {
                        System.out.println("Придумайте новое название или описание подзадачи.\n");
                        return;
                    } else {
                        subTask.setTaskName(newTaskName);
                        subTask.setDescription(newTaskDescription);
                        epic.setSubTasks(subTasks);
                    }
                }
            }
        }
        System.out.println("Нет подзадачи с таким ID.\n");


//        SubTask subtaskToUpdate = null;
//        for (Epic epic : epics.values()) {
//            for (SubTask subTask : epic.getSubTasks()) {
//                if (subTask.getTaskID() == epicIDtoUpdate) {
//                    subtaskToUpdate = subTask;
//                }
//            }
//        }
//        if (subtaskToUpdate == null) {
//            System.out.println("Нет подзадачи с таким ID.\n");
//        } else {
//            System.out.print("Введите новое название подзадачи: ");
//            String newSubtaskName = scanner.next();
//            System.out.print("Введите новое описание подзадачи: ");
//            String newSubtaskDescription = scanner.next();
//            if (subtaskToUpdate.getTaskName().equals(newSubtaskName) &&
//                    subtaskToUpdate.getDescription().equals(newSubtaskDescription)) {
//                System.out.println("Придумайте новое описание или название подзадаче.\n");
//            } else {
//                subtaskToUpdate.setTaskName(newSubtaskName);
//                subtaskToUpdate.setDescription(newSubtaskDescription);
//                System.out.println("Подзадача обновлена.\n");
//            }
//        }
    }

    public void removeSubtaskByID() {
        System.out.print("Введите ID подзадачи: ");
        int subtaskIDtoRemove = scanner.nextInt();
        isExists = false;
        for (Epic epic : epics.values()) {
            for (SubTask subTask : epic.getSubTasks()) {
                if (subTask.getTaskID() == subtaskIDtoRemove) {
                    isExists = true;
                    ArrayList<SubTask> subTasks = epic.getSubTasks();
                    subTasks.remove(subTask);
                    epic.setSubTasks(subTasks);
                    System.out.println("Подзадача удалена.\n");
                    break;
                }
            }
        }
        if (!isExists) {
            System.out.println("Нет подзадачи с таким ID.\n");
        }
    }

    public void changeSubtaskStatus() {
        System.out.print("Введите ID подзадачи: ");
        int subtaskIDtoUpdateStatus = scanner.nextInt();
        isExists = false;
        for (Epic epic : epics.values()) {
            ArrayList<SubTask> subTasks = epic.getSubTasks();
            for (SubTask subTask : subTasks) {
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
                        epic.setSubTasks(subTasks);
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
    }
}