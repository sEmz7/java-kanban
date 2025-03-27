package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    boolean checkIntersection(Task newTask, Task task);

    void savePrioritizedTask(Task task);

    void removePrioritizedTask(Task task);

    List<Task> getPrioritizedTasks();

    ArrayList<Task> getAllTasks();

    void removeAllTasks();

    boolean tasksIsEmpty();

    Task getTaskByID(int idTask);

    void createTask(Task newTask);

    void updateTask(Task task);

    boolean taskIsExist(int id);

    void removeTaskByID(int idToRemove);

    ArrayList<Epic> getAllEpics();

    boolean epicsIsEmpty();

    void removeAllEpics();

    Epic getEpicByID(int epicIDtoGet);

    boolean epicIsExist(int id);

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

    List<Task> getHistory();
}
