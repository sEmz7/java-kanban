package Manager;

import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {
    void updateEpicStatus(Epic epic);

    ArrayList<Task> getAllTasks();

    void removeAllTasks();

    boolean tasksIsEmpty();

    Task getTaskByID(int idTask);

    void createTask(Task newTask);

    void updateTask(Task task);

    boolean taskIsExist(int ID);

    void removeTaskByID(int idToRemove);

    ArrayList<Epic> getAllEpics();

    boolean epicsIsEmpty();

    void removeAllEpics();

    Epic getEpicByID(int epicIDtoGet);

    boolean epicIsExist(int ID);

    void createEpic(Epic epic);

    void updateEpic(Epic epic);

    void removeEpicByID(int epicIDtoRemove);

    ArrayList<SubTask> getAllSubtasksInEpic(int epicIDtoView);

    ArrayList<SubTask> getAllSubtasks();

    void removeAllSubtasksInEpic(int epicIDtoRemoveSubtask);

    SubTask getSubtaskByID(int subtaskID);

    void createSubtask(SubTask subTask);

    void updateSubtask(SubTask newSubTask);

    boolean subtaskIsExist(int subtaskID);

    void removeSubtaskByID(int subtaskIDtoRemove);

    void removeAllSubtasks();

    void addHistory(Task task);

    List<Task> getHistory();
}
