package Manager;
import Tasks.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int taskCount = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subtasks = new HashMap<>();


    public void updateEpicStatus(Epic epic) {
        ArrayList<SubTask> epicSubtasks = epic.getSubTasks();
        if (epicSubtasks.isEmpty()) {
            epic.setTaskStatus(TaskStatus.NEW);
        } else {
            int counterOfNew = 0;
            int counterOfDone = 0;
            for(SubTask subTask: epicSubtasks) {
                if(subTask.getTaskStatus().equals(TaskStatus.NEW)) {
                    counterOfNew++;
                } else if (subTask.getTaskStatus().equals(TaskStatus.DONE)) {
                    counterOfDone++;
                }
            }
            if(counterOfNew == epicSubtasks.size()) {
                epic.setTaskStatus(TaskStatus.NEW);
            } else if (counterOfDone == epicSubtasks.size()) {
                epic.setTaskStatus(TaskStatus.DONE);
            } else {
                epic.setTaskStatus(TaskStatus.IN_PROGRESS);
            }
        }
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public boolean tasksIsEmpty() {
        return tasks.isEmpty();
    }

    public Task getTaskByID(int idTask) {
        return tasks.get(idTask);
    }

    public void createTask(Task newTask) {
        taskCount++;
        newTask.setTaskID(taskCount);
        tasks.put(newTask.getTaskID(), newTask);
    }

    public void updateTask(Task task) {
        tasks.put(task.getTaskID(), task);
    }

    public boolean taskIsExist(int ID) {
        return tasks.containsKey(ID);
    }

    public void removeTaskByID(int idToRemove) {
        tasks.remove(idToRemove);
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public boolean epicsIsEmpty() {
        return epics.isEmpty();
    }

    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public Epic getEpicByID(int epicIDtoGet) {
        return epics.get(epicIDtoGet);
    }

    public boolean epicIsExist(int ID) {
        return epics.containsKey(ID);
    }

    public void createEpic(Epic epic) {
        taskCount++;
        epic.setTaskID(taskCount);
        epics.put(epic.getTaskID(), epic);
        updateEpicStatus(epic);
    }

    public void updateEpic(Epic epic) {
        epic.setSubTasks(epics.get(epic.getTaskID()).getSubTasks());
        epics.put(epic.getTaskID(), epic);
        updateEpicStatus(epic);
    }

    public void removeEpicByID(int epicIDtoRemove) {
        ArrayList<SubTask> arrayOfSubtasks = epics.get(epicIDtoRemove).getSubTasks();
        for(SubTask subTask: arrayOfSubtasks) {
            if (subTask.getEpicID() == epicIDtoRemove) {
                subtasks.remove(subTask.getTaskID());
            }
        }
        epics.remove(epicIDtoRemove);
    }

    public ArrayList<SubTask> getAllSubtasksInEpic(int epicIDtoView) {
        return epics.get(epicIDtoView).getSubTasks();
    }

    public void removeAllSubtasksInEpic(int epicIDtoRemoveSubtask) {
        ArrayList<SubTask> arrayOfSubtasks = epics.get(epicIDtoRemoveSubtask).getSubTasks();
        for(SubTask subTask: arrayOfSubtasks) {
            if (subTask.getEpicID() == epicIDtoRemoveSubtask) {
                subtasks.remove(subTask.getTaskID());
            }
        }
        epics.get(epicIDtoRemoveSubtask).setSubTasks(new ArrayList<>());
        updateEpicStatus(epics.get(epicIDtoRemoveSubtask));
    }

    public SubTask getSubtaskByID(int subtaskID) {
        return subtasks.get(subtaskID);
    }

    public void createSubtask(SubTask subTask) {
        ArrayList<SubTask> arrayOfSubtasks = epics.get(subTask.getEpicID()).getSubTasks();
        taskCount++;
        subTask.setTaskID(taskCount);
        arrayOfSubtasks.add(subTask);
        epics.get(subTask.getEpicID()).setSubTasks(arrayOfSubtasks);
        subtasks.put(subTask.getTaskID(), subTask);
        updateEpicStatus(epics.get(subTask.getEpicID()));
    }

    public void updateSubtask(SubTask newSubTask) {
        SubTask subTask = subtasks.get(newSubTask.getTaskID());
        ArrayList<SubTask> epicSubtasks = epics.get(newSubTask.getEpicID()).getSubTasks();
        epicSubtasks.remove(subTask);
        epicSubtasks.add(newSubTask);
        epics.get(newSubTask.getEpicID()).setSubTasks(epicSubtasks);
        subtasks.put(newSubTask.getTaskID(), newSubTask);
        updateEpicStatus(epics.get(subTask.getEpicID()));
    }

    public boolean subtaskIsExist(int subtaskID) {
        return subtasks.containsKey(subtaskID);
    }

    public void removeSubtaskByID(int subtaskIDtoRemove) {
        SubTask subTask = subtasks.get(subtaskIDtoRemove);
        ArrayList<SubTask> subtasksArray = epics.get(subTask.getEpicID()).getSubTasks();
        subtasksArray.remove(subTask);
        epics.get(subTask.getEpicID()).setSubTasks(subtasksArray);
        subtasks.remove(subtaskIDtoRemove);
        updateEpicStatus(epics.get(subTask.getEpicID()));
    }

    public void removeAllSubtasks() {
        for(Epic epic: epics.values()) {
            epic.setSubTasks(new ArrayList<>());
            updateEpicStatus(epic);
        }
        subtasks.clear();
    }
}