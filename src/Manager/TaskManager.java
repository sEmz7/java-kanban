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
            int counterOfNew = 0;
            for (SubTask subTask : epic.getSubTasks()) {
                if (subTask.getTaskStatus().equals(TaskStatus.DONE)) {
                    counterOfDone++;
                } else if(subTask.getTaskStatus().equals(TaskStatus.NEW)) {
                    counterOfNew++;
                }
            }
            if (sizeOfSubtasks == counterOfDone) {
                currentEpicStatus = TaskStatus.DONE;
                epic.setTaskStatus(currentEpicStatus);
                return;
            }  else if (sizeOfSubtasks == counterOfNew) {
                currentEpicStatus = TaskStatus.NEW;
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
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getTaskID(), epic);
    }

    public void removeEpicByID(int epicIDtoRemove) {
        epics.remove(epicIDtoRemove);
    }

    public ArrayList<SubTask> getAllSubtasksInEpic(int epicIDtoView) {
        return epics.get(epicIDtoView).getSubTasks();
    }

    public void removeAllSubtasksInEpic(int epicIDtoRemoveSubtask) {
        epics.get(epicIDtoRemoveSubtask).setSubTasks(new ArrayList<>());
    }

    public SubTask getSubtaskByID(int subtaskID) {
        return subtasks.get(subtaskID);
    }

    public void createSubtask(SubTask subTask, int epicIDtoAddSubtask) {
        ArrayList<SubTask> arrayOfSubtasks = epics.get(epicIDtoAddSubtask).getSubTasks();
        taskCount++;
        subTask.setTaskID(taskCount);
        arrayOfSubtasks.add(subTask);
        epics.get(epicIDtoAddSubtask).setSubTasks(arrayOfSubtasks);
        subtasks.put(subTask.getTaskID(), subTask);
    }

    public void updateSubtask(SubTask newSubTask, int subtaskIdToUpdate) {
        SubTask subTask = subtasks.get(subtaskIdToUpdate);
        ArrayList<SubTask> epicSubtasks = epics.get(newSubTask.getEpicID()).getSubTasks();
        epicSubtasks.remove(subTask);
        epicSubtasks.add(newSubTask);
        epics.get(newSubTask.getEpicID()).setSubTasks(epicSubtasks);
        subtasks.put(subtaskIdToUpdate, newSubTask);
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
    }

    public void changeSubtaskStatus(int subtaskIDtoUpdateStatus, String userInputedStatus) throws IllegalArgumentException {
        SubTask subTask = subtasks.get(subtaskIDtoUpdateStatus);
        ArrayList<SubTask> subTasks = epics.get(subTask.getEpicID()).getSubTasks();
        TaskStatus newStatus = TaskStatus.valueOf(userInputedStatus);
        subTask.setTaskStatus(newStatus);
        epics.get(subTask.getEpicID()).setSubTasks(subTasks);
        subtasks.put(subtaskIDtoUpdateStatus, subTask);
    }

    public void removeAllSubtasks() {
        for(Epic epic: epics.values()) {
            epic.setSubTasks(new ArrayList<>());
        }
        subtasks.clear();
    }
}