package taskManagerTests;

import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import tasks.Task;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class HistoryManagerTest {
    static TaskManager manager;
    static HistoryManager historyManager;

    @BeforeEach
    void beforeEach() {
        historyManager = Managers.getDefaultHistoryManager();
        manager = Managers.getDefaultTaskManager();
    }

    @Test
    void shouldSaveOnlyLastGetTask() {
        Task task1 = new Task("# Task 1", "Description 1", TaskStatus.NEW);
        manager.createTask(task1);
        historyManager.addTask(task1);

        Task task2 = new Task("# Task 2", "Description 2", TaskStatus.NEW);
        manager.createTask(task2);
        historyManager.addTask(task2);
        manager.getTaskByID(task1.getTaskID());
        manager.getTaskByID(task2.getTaskID());
        manager.getTaskByID(task1.getTaskID());

        assertEquals(2, historyManager.getHistory().size());
        assertEquals(task1, historyManager.getHistory().getFirst());
        assertEquals(task2, historyManager.getHistory().get(1));
    }

    @Test
    void shouldRemoveFromHistory() {
        Task task1 = new Task("# Task 1", "Description 1", TaskStatus.NEW);
        manager.createTask(task1);
        Task task2 = new Task("# Task 2", "Description 2", TaskStatus.NEW);
        manager.createTask(task2);

        manager.getTaskByID(task1.getTaskID());
        manager.getTaskByID(task2.getTaskID());
        manager.removeTaskByID(task1.getTaskID());

        assertEquals(1, manager.getHistory().size());
    }

    @Test
    void removedTaskShouldNotSaveData() {
        Task task1 = new Task("# Task 1", "Description 1", TaskStatus.NEW);
        manager.createTask(task1);
        Task task2 = new Task("# Task 2", "Description 2", TaskStatus.NEW);
        manager.createTask(task2);
        manager.getTaskByID(task1.getTaskID());
        manager.getTaskByID(task2.getTaskID());
    }

    @Test
    void emptyHistory() {
        assertEquals(0, manager.getHistory().size());
    }

    @Test
    void shouldBeOneIfDuplicateGetTask() {
        Task task1 = new Task("# Task 1", "Description 1", TaskStatus.NEW);
        manager.createTask(task1);

        manager.getTaskByID(task1.getTaskID());
        manager.getTaskByID(task1.getTaskID());

        assertEquals(1, manager.getHistory().size());
    }

    @Test
    void checkRemovesInHistory() {
        Task task1 = new Task("# Task 1", "Description 1", TaskStatus.NEW);
        manager.createTask(task1);
        Task task2 = new Task("# Task 2", "Description 2", TaskStatus.NEW);
        manager.createTask(task2);
        Task task3 = new Task("# Task 3", "Description 3", TaskStatus.NEW);
        manager.createTask(task3);
        Task task4 = new Task("# Task 4", "Description 4", TaskStatus.NEW);
        manager.createTask(task4);
        Task task5 = new Task("# Task 5", "Description 5", TaskStatus.NEW);
        manager.createTask(task5);

        manager.getTaskByID(task1.getTaskID());
        manager.getTaskByID(task2.getTaskID());
        manager.getTaskByID(task3.getTaskID());
        manager.getTaskByID(task4.getTaskID());
        manager.getTaskByID(task5.getTaskID());

        manager.removeTaskByID(task1.getTaskID());
        manager.removeTaskByID(task3.getTaskID());
        manager.removeTaskByID(task5.getTaskID());

        assertEquals(2, manager.getHistory().size());
        assertEquals(task2, manager.getHistory().getFirst());
        assertEquals(task4, manager.getHistory().getLast());

    }

}