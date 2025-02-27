package manager;

import exceptions.ManagerSaveException;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.*;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {

    public static FileBackedTaskManager loadFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            FileBackedTaskManager manager = new FileBackedTaskManager();
            while (br.ready()) {
                String line = br.readLine();
                if (line.isEmpty()) {
                    continue;
                }
                Object object = manager.fromString(line);
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

    public Object fromString(String value) {
        String[] taskParts = value.split(",");
        switch (taskParts[1]) {
            case "Task" -> {
                return new Task(taskParts[2], taskParts[4], TaskStatus.valueOf(taskParts[3]));
            }
            case "Epic" -> {
                return new Epic(taskParts[2], taskParts[4], TaskStatus.valueOf(taskParts[3]));
            }
            case "SubTask" -> {
                SubTask subTask = new SubTask(taskParts[2], taskParts[4], TaskStatus.valueOf(taskParts[3]));
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
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeTaskByID(int idToRemove) {
        super.removeTaskByID(idToRemove);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeEpicByID(int epicIDtoRemove) {
        super.removeEpicByID(epicIDtoRemove);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeAllSubtasksInEpic(int epicIDtoRemoveSubtask) {
        super.removeAllSubtasksInEpic(epicIDtoRemoveSubtask);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void createSubtask(SubTask subTask) {
        super.createSubtask(subTask);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateSubtask(SubTask newSubTask) {
        super.updateSubtask(newSubTask);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeSubtaskByID(int subtaskIDtoRemove) {
        super.removeSubtaskByID(subtaskIDtoRemove);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }
}
