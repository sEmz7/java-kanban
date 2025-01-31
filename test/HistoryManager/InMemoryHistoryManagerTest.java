package HistoryManager;

import Manager.HistoryManager;
import Manager.Managers;
import Manager.TaskManager;
import Tasks.Task;
import Tasks.TaskStatus;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class InMemoryHistoryManagerTest {
    static TaskManager manager;
    static HistoryManager historyManager;

    @BeforeEach
    void beforeEach() {
        historyManager = Managers.getDefaultHistoryManager();
        manager = Managers.getDefaultTaskManager();
    }

    @Test
    void shouldSaveLastVersionOfTask() {

        Task task = new Task("Task 1", "Desc 1", TaskStatus.NEW);
        manager.createTask(task);
        historyManager.addTask(task); // сохранили первую версию task
        task.setTaskName("Updated task1 name");
        manager.updateTask(task);
        historyManager.addTask(task); // сохранили вторую версию task
        List<Task> history = historyManager.getHistory();
        Task firstVersion = history.get(0);
        Task secondVersion = history.get(1);

        assertNotNull(history);
        assertEquals(2, history.size());
        assertNotEquals(firstVersion.getTaskName(), secondVersion.getTaskName()); // первая версия != вторая
    }

    @Test
    void shouldAddInHistoryMax10Tasks() {
        Task task = new Task("Task 1", "Desc 1", TaskStatus.NEW);
        manager.createTask(task);
        manager.getTaskByID(task.getTaskID()); // 1
        manager.getTaskByID(task.getTaskID()); // 2
        manager.getTaskByID(task.getTaskID()); // 3
        manager.getTaskByID(task.getTaskID()); // 4
        manager.getTaskByID(task.getTaskID()); // 5
        manager.getTaskByID(task.getTaskID()); // 6
        manager.getTaskByID(task.getTaskID()); // 7
        manager.getTaskByID(task.getTaskID()); // 8
        manager.getTaskByID(task.getTaskID()); // 9
        manager.getTaskByID(task.getTaskID()); // 10
        manager.getTaskByID(task.getTaskID()); // 11

        assertEquals(10, manager.getHistory().size());

    }
}