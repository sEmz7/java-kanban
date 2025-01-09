package Manager;
import Tasks.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int taskCount = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subtasks = new HashMap<>();


    public void updateEpicStatus() {
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

    public HashMap<Integer, Epic> getAllEpics() {
        return epics;
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
        ArrayList<SubTask> subtasks = epics.get(epicIDtoRemoveSubtask).getSubTasks();
        subtasks.clear();
        epics.get(epicIDtoRemoveSubtask).setSubTasks(subtasks);
    }

    public SubTask getSubtaskByID(int subtaskID) {
        for(SubTask subTask: subtasks.values()) {
            if (subtaskID == subTask.getTaskID()) {
                return subTask;
            }
        }
        return null;
    }

    public void createSubtask(SubTask subTask, int epicIDtoAddSubtask) {
        ArrayList<SubTask> arrayOfSubtasks = epics.get(epicIDtoAddSubtask).getSubTasks();
        taskCount++;
        subTask.setTaskID(taskCount);
        arrayOfSubtasks.add(subTask);
        epics.get(epicIDtoAddSubtask).setSubTasks(arrayOfSubtasks);
        subtasks.put(subTask.getTaskID(), subTask);
    }

    public void updateSubtask(SubTask subTask, int subtaskIdToUpdate) {
        for(Epic epic: epics.values()) {
            ArrayList<SubTask> subTasks = epic.getSubTasks();
            for(SubTask subTask_i: subTasks) {
                if(subTask_i.getTaskID() == subtaskIdToUpdate) {
                    subTasks.remove(subTask_i);
                    subTasks.add(subTask);
                    epic.setSubTasks(subTasks);
                    return;
                }
            }
        }
    }

    public boolean subtaskIsExist(int subtaskID) {
        for(Epic epic: epics.values()) {
            ArrayList<SubTask> subTasks = epic.getSubTasks();
            for(SubTask subTask: subTasks) {
                if(subTask.getTaskID() == subtaskID) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeSubtaskByID(int subtaskIDtoRemove) {
        for (Epic epic : epics.values()) {
            for (SubTask subTask : epic.getSubTasks()) {
                if (subTask.getTaskID() == subtaskIDtoRemove) {
                    ArrayList<SubTask> subTasks = epic.getSubTasks();
                    subTasks.remove(subTask);
                    epic.setSubTasks(subTasks);
                    return;
                }
            }
        }
    }

    public void changeSubtaskStatus(int subtaskIDtoUpdateStatus, String userInputedStatus) throws IllegalArgumentException {
        for (Epic epic : epics.values()) {
            ArrayList<SubTask> subTasks = epic.getSubTasks();
            for (SubTask subTask : subTasks) {
                if (subTask.getTaskID() == subtaskIDtoUpdateStatus) {
                    TaskStatus newStatus = TaskStatus.valueOf(userInputedStatus);
                    subTask.setTaskStatus(newStatus);
                    epic.setSubTasks(subTasks);
                    return;
                }
            }
        }
    }
}