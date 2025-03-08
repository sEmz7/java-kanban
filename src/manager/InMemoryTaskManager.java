package manager;

import tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private int taskCount = 0;
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, SubTask> subtasks = new HashMap<>();

    private final HistoryManager historyManager = Managers.getDefaultHistoryManager();

    private void addHistory(Task task) {
        historyManager.addTask(task);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void updateEpicStatus(Epic epic) {
        ArrayList<SubTask> epicSubtasks = epic.getSubTasks();
        if (epicSubtasks.isEmpty()) {
            epic.setTaskStatus(TaskStatus.NEW);
        } else {
            int counterOfNew = 0;
            int counterOfDone = 0;
            for (SubTask subTask : epicSubtasks) {
                if (subTask.getTaskStatus().equals(TaskStatus.NEW)) {
                    counterOfNew++;
                } else if (subTask.getTaskStatus().equals(TaskStatus.DONE)) {
                    counterOfDone++;
                }
            }
            if (counterOfNew == epicSubtasks.size()) {
                epic.setTaskStatus(TaskStatus.NEW);
            } else if (counterOfDone == epicSubtasks.size()) {
                epic.setTaskStatus(TaskStatus.DONE);
            } else {
                epic.setTaskStatus(TaskStatus.IN_PROGRESS);
            }
        }
        updateEpicDateTime(epic);
    }

    private void updateEpicDateTime(Epic epic) {
        ArrayList<SubTask> epicSubtasks = epic.getSubTasks();
        Duration duration = Duration.ofMinutes(0);
        LocalDateTime startTime = LocalDateTime.of(9999, 12, 12, 12, 12);
        LocalDateTime endTime = LocalDateTime.of(1, 12, 12, 12, 12);
        for (SubTask subTask : epicSubtasks) {
            duration = duration.plusMinutes(subTask.getDuration().toMinutes());
            if (startTime.isAfter(subTask.getStartTime())) {
                startTime = subTask.getStartTime();
            }
            if (endTime.isBefore(subTask.getEndTime())) {
                endTime = subTask.getEndTime();
            }
        }
        epic.setDuration(duration);
        epic.setStartTime(startTime);
        epic.setEndTime(endTime);
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public boolean tasksIsEmpty() {
        return tasks.isEmpty();
    }

    @Override
    public Task getTaskByID(int idTask) {
        Task task = tasks.get(idTask);
        addHistory(task);
        return task;
    }

    @Override
    public void createTask(Task newTask) {
        taskCount++;
        newTask.setTaskID(taskCount);
        tasks.put(newTask.getTaskID(), newTask);
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getTaskID(), task);
    }

    @Override
    public boolean taskIsExist(int id) {
        return tasks.containsKey(id);
    }

    @Override
    public void removeTaskByID(int idToRemove) {
        tasks.remove(idToRemove);
        historyManager.remove(idToRemove);
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public boolean epicsIsEmpty() {
        return epics.isEmpty();
    }

    @Override
    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Epic getEpicByID(int epicIDtoGet) {
        Epic epic = epics.get(epicIDtoGet);
        addHistory(epic);
        return epic;
    }

    @Override
    public boolean epicIsExist(int id) {
        return epics.containsKey(id);
    }

    @Override
    public void createEpic(Epic epic) {
        taskCount++;
        epic.setTaskID(taskCount);
        epics.put(epic.getTaskID(), epic);
        updateEpicStatus(epic);
    }

    @Override
    public void updateEpic(Epic epic) {
        epic.setSubTasks(epics.get(epic.getTaskID()).getSubTasks());
        epics.put(epic.getTaskID(), epic);
        updateEpicStatus(epic);
    }

    @Override
    public void removeEpicByID(int epicIDtoRemove) {
        ArrayList<SubTask> arrayOfSubtasks = epics.get(epicIDtoRemove).getSubTasks();
        for (SubTask subTask : arrayOfSubtasks) {
            if (subTask.getEpicID() == epicIDtoRemove) {
                subtasks.remove(subTask.getTaskID());
                historyManager.remove(subTask.getTaskID());
            }
        }
        epics.remove(epicIDtoRemove);
        historyManager.remove(epicIDtoRemove);
    }

    @Override
    public ArrayList<SubTask> getAllSubtasksInEpic(int epicIDtoView) {
        return epics.get(epicIDtoView).getSubTasks();
    }

    @Override
    public ArrayList<SubTask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void removeAllSubtasksInEpic(int epicIDtoRemoveSubtask) {
        ArrayList<SubTask> arrayOfSubtasks = epics.get(epicIDtoRemoveSubtask).getSubTasks();
        for (SubTask subTask : arrayOfSubtasks) {
            if (subTask.getEpicID() == epicIDtoRemoveSubtask) {
                subtasks.remove(subTask.getTaskID());
            }
        }
        epics.get(epicIDtoRemoveSubtask).setSubTasks(new ArrayList<>());
        updateEpicStatus(epics.get(epicIDtoRemoveSubtask));
    }

    @Override
    public SubTask getSubtaskByID(int subtaskID) {
        SubTask subTask = subtasks.get(subtaskID);
        addHistory(subTask);
        return subTask;
    }

    @Override
    public void createSubtask(SubTask subTask) {
        ArrayList<SubTask> arrayOfSubtasks = epics.get(subTask.getEpicID()).getSubTasks();
        taskCount++;
        subTask.setTaskID(taskCount);
        arrayOfSubtasks.add(subTask);
        epics.get(subTask.getEpicID()).setSubTasks(arrayOfSubtasks);
        subtasks.put(subTask.getTaskID(), subTask);
        updateEpicStatus(epics.get(subTask.getEpicID()));
    }

    @Override
    public void updateSubtask(SubTask newSubTask) {
        SubTask subTask = subtasks.get(newSubTask.getTaskID());
        ArrayList<SubTask> epicSubtasks = epics.get(newSubTask.getEpicID()).getSubTasks();
        epicSubtasks.remove(subTask);
        epicSubtasks.add(newSubTask);
        epics.get(newSubTask.getEpicID()).setSubTasks(epicSubtasks);
        subtasks.put(newSubTask.getTaskID(), newSubTask);
        updateEpicStatus(epics.get(subTask.getEpicID()));
    }

    @Override
    public boolean subtaskIsExist(int subtaskID) {
        return subtasks.containsKey(subtaskID);
    }

    @Override
    public void removeSubtaskByID(int subtaskIDtoRemove) {
        SubTask subTask = subtasks.get(subtaskIDtoRemove);
        ArrayList<SubTask> subtasksArray = epics.get(subTask.getEpicID()).getSubTasks();
        subtasksArray.remove(subTask);
        epics.get(subTask.getEpicID()).setSubTasks(subtasksArray);
        subtasks.remove(subtaskIDtoRemove);
        updateEpicStatus(epics.get(subTask.getEpicID()));
        historyManager.remove(subtaskIDtoRemove);
    }

    @Override
    public void removeAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.setSubTasks(new ArrayList<>());
            updateEpicStatus(epic);
        }
        subtasks.clear();
    }
}