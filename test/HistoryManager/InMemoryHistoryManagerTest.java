package HistoryManager;

import Manager.HistoryManager;
import Manager.InMemoryHistoryManager;
import Manager.Managers;
import Manager.TaskManager;
import Tasks.Task;
import Tasks.TaskStatus;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class InMemoryHistoryManagerTest {
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

}