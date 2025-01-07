package Manager;
import Tasks.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    public int taskCount = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();


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
        return tasks.getOrDefault(idTask, null);
    }

    public void createTask(String taskName, String taskDescription) {
        taskCount++;
        Task newTask = new Task(taskName, taskDescription, taskCount);
        tasks.put(newTask.getTaskID(), newTask);
    }

    public void updateTask(String newTaskName, String newTaskDescription, int taskID) {
        Task taskToUpdate = tasks.get(taskID);
        taskToUpdate.setTaskName(newTaskName);
        taskToUpdate.setDescription(newTaskDescription);
    }

    public boolean taskIsExist(int ID) {
        return tasks.containsKey(ID);
    }

    public void removeTaksByID(int idToRemove) {
        tasks.remove(idToRemove);
    }

    public void changeTaskStatus(int IDtoUpdateStatus, String taskStatus) throws IllegalArgumentException{
        TaskStatus newTaskStatus = TaskStatus.valueOf(taskStatus);
        tasks.get(IDtoUpdateStatus).setTaskStatus(newTaskStatus);
    }


    public HashMap<Integer, Epic> getAllEpics() {
        return new HashMap<>(epics);
    }

    public boolean epicsIsEmpty() {
        return epics.isEmpty();
    }

    public void removeAllEpics() {
        epics.clear();
    }

    public Epic getEpicByID(int epicIDtoGet) {
        return epics.get(epicIDtoGet);
    }

    public boolean epicIsExist(int ID) {
        return epics.containsKey(ID);
    }

    public void createEpic(String epicName, String epicDescription) {
        taskCount++;
        Epic newEpic = new Epic(epicName, epicDescription, taskCount);
        epics.put(newEpic.getTaskID(), newEpic);
    }

    public void updateEpic(String newEpicName, String newEpicDescription, int epicIDtoUpdate) {
        Epic epicToUpdate = epics.get(epicIDtoUpdate);
        epicToUpdate.setTaskName(newEpicName);
        epicToUpdate.setDescription(newEpicDescription);
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

    public SubTask getSubtaskByID(int epicIDtoGetSubtask) {
        for (Epic epic : epics.values()) {
            for (SubTask subTask : epic.getSubTasks()) {
                if (subTask.getTaskID() == epicIDtoGetSubtask) {
                    return subTask;
                }
            }
        }
        return null;
    }

    public void createSubtask(String subtaskName, String subtaskDesctiprion, int epicIDtoAddSubtask) {
        ArrayList<SubTask> arrayOfSubtasks = epics.get(epicIDtoAddSubtask).getSubTasks();
        taskCount++;
        arrayOfSubtasks.add(new SubTask(subtaskName, subtaskDesctiprion, taskCount));
        epics.get(epicIDtoAddSubtask).setSubTasks(arrayOfSubtasks);
    }

    public void updateSubtask(String newTaskName, String newTaskDescription, int subtaskIdToUpdate) {
        for(Epic epic: epics.values()) {
            ArrayList<SubTask> subTasks = epic.getSubTasks();
            for(SubTask subTask: subTasks) {
                if(subTask.getTaskID() == subtaskIdToUpdate) {
                    subTask.setTaskName(newTaskName);
                    subTask.setDescription(newTaskDescription);
                    epic.setSubTasks(subTasks);
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