package manager;

import exceptions.ManagerSaveException;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");

    public boolean checkIntersection(Task newTask, Task task) {
        LocalDateTime newTaskStartTime = newTask.getStartTime().get();
        LocalDateTime newTaskEndTime = newTask.getEndTime();
        if (newTaskStartTime.isAfter(task.getEndTime()) || newTaskEndTime.isBefore(task.getStartTime().get())) {
            return false;
        }
        return true;
    }

    public void savePrioritizedTask(Task task) {
        if (task.getStartTime().isPresent()) {
            List<Task> tasks = getPrioritizedTasks();
            boolean isIntersected = tasks.stream()
                    .anyMatch(existingTask -> checkIntersection(existingTask, task));
            if (!isIntersected) {
                prioritizedTasks.add(task);
            }
        }
    }

    public void removeEpicInPrioritizedTasks(int epicId) {
        prioritizedTasks.removeIf(task -> task.getTaskID() == epicId);
        ArrayList<SubTask> epicSubtasks = epics.get(epicId).getSubTasks();
        prioritizedTasks.removeIf(epicSubtasks::contains);
    }


    public void removePrioritizedTask(Task task) {
        prioritizedTasks.remove(task);
    }

    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            FileBackedTaskManager manager = new FileBackedTaskManager();
            while (br.ready()) {
                String line = br.readLine();
                if (line.isEmpty()) {
                    continue;
                }
                Object object = fromString(line);
                if (object instanceof SubTask subTask) {
                    manager.createSubtask(subTask);
                } else if (object instanceof Epic epic) {
                    manager.createEpic(epic);
                } else if (object instanceof Task task) {
                    manager.createTask(task);
                }
            }
            return manager;
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось прочитать файл.", e);
        }
    }

    public void save() throws ManagerSaveException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/data.txt"))) {
            for (Task task : tasks.values()) {
                bw.write(task.toString() + "\n");
            }
            for (Epic epic : epics.values()) {
                bw.write(epic.toString() + "\n");
            }
            for (SubTask subTask : subtasks.values()) {
                bw.write(subTask.toString() + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось записать в файл.", e);
        }
    }

    public static Object fromString(String value) {
        String[] taskParts = value.split(",");
        switch (taskParts[1]) {
            case "Task" -> {
                if (taskParts.length == 5) {
                    return new Task(taskParts[2], taskParts[4], TaskStatus.valueOf(taskParts[3]));
                }
                return new Task(taskParts[2], taskParts[4], TaskStatus.valueOf(taskParts[3]),
                        Duration.ofMinutes(Long.parseLong(taskParts[5])), LocalDateTime.parse(taskParts[6], formatter));
            }
            case "Epic" -> {
                if (taskParts.length == 5) {
                    return new Epic(taskParts[2], taskParts[4], TaskStatus.valueOf(taskParts[3]));
                }
                return new Epic(taskParts[2], taskParts[4], TaskStatus.valueOf(taskParts[3]),
                        Duration.ofMinutes(Long.parseLong(taskParts[5])), LocalDateTime.parse(taskParts[6], formatter),
                        LocalDateTime.parse(taskParts[7], formatter));
            }
            case "SubTask" -> {
                if (taskParts.length == 6) {
                    SubTask subTask = new SubTask(taskParts[2], taskParts[4], TaskStatus.valueOf(taskParts[3]));
                    subTask.setEpicID(Integer.parseInt(taskParts[5]));
                    return subTask;
                }
                SubTask subTask = new SubTask(taskParts[2], taskParts[4], TaskStatus.valueOf(taskParts[3]),
                        Duration.ofMinutes(Long.parseLong(taskParts[6])), LocalDateTime.parse(taskParts[7], formatter),
                        LocalDateTime.parse(taskParts[8], formatter));
                subTask.setEpicID(Integer.parseInt(taskParts[5]));
                return subTask;
            }
            default -> {
                throw new ManagerSaveException("Ошибка преобразования из строки в Task (fromString).");
            }
        }
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
        savePrioritizedTask(task);
    }

    @Override
    public void removeAllTasks() {
        prioritizedTasks
                .removeIf(task -> task.getClass().getSimpleName().equals("Task"));
        super.removeAllTasks();
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void removeTaskByID(int idToRemove) {
        removePrioritizedTask(tasks.get(idToRemove));
        super.removeTaskByID(idToRemove);
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void removeEpicByID(int epicIDtoRemove) {
        removeEpicInPrioritizedTasks(epicIDtoRemove);
        super.removeEpicByID(epicIDtoRemove);
        save();
    }

    @Override
    public void removeAllSubtasksInEpic(int epicIDtoRemoveSubtask) {
        prioritizedTasks
                .removeIf(subtask -> epics.get(epicIDtoRemoveSubtask).getSubTasks().contains(subtask));
        super.removeAllSubtasksInEpic(epicIDtoRemoveSubtask);
        save();
    }

    @Override
    public void createSubtask(SubTask subTask) {
        super.createSubtask(subTask);
        save();
        savePrioritizedTask(subTask);
    }

    @Override
    public void updateSubtask(SubTask newSubTask) {
        super.updateSubtask(newSubTask);
        save();
    }

    @Override
    public void removeSubtaskByID(int subtaskIDtoRemove) {
        removePrioritizedTask(subtasks.get(subtaskIDtoRemove));
        super.removeSubtaskByID(subtaskIDtoRemove);
        save();
    }

    @Override
    public void removeAllSubtasks() {
        prioritizedTasks
                .removeIf(task -> task.getClass().getSimpleName().equals("SubTask"));
        super.removeAllSubtasks();
        save();
    }
}