package tasks;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskTest {

    static TaskManager manager;

    @BeforeEach
    void beforeEach() {
        manager = Managers.getDefaultTaskManager();
    }

    @Test
    void taskEqualsIfSameID() {
        Task task1 = new Task("Task1", "Desc1", TaskStatus.NEW);
        Task task2 = new Task("Task2", "Desc2", TaskStatus.NEW);
        task1.setTaskID(1);
        task2.setTaskID(1);

        assertEquals(task1, task2);
    }

    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", TaskStatus.NEW);
        manager.createTask(task);

        final Task savedTask = manager.getTaskByID(task.getTaskID());

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = manager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void managerShouldDeleteTask() {
        Task task = new Task("1", "1", TaskStatus.NEW);
        manager.createTask(task);
        manager.removeTaskByID(task.getTaskID());

        assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    void managerShouldGenerateIdForEachTask() {
        Task task = new Task("1", "1", TaskStatus.NEW);
        manager.createTask(task);
        Task task2 = new Task("2", "2", TaskStatus.NEW);
        manager.createTask(task2);

        assertEquals(1, task.getTaskID());
        assertEquals(2, task2.getTaskID());
    }

    @Test
    void shouldGetAllTasks() {
        Task task1 = new Task("1", "1", TaskStatus.NEW);
        Task task2 = new Task("2", "2", TaskStatus.NEW);
        manager.createTask(task1);
        manager.createTask(task2);
        ArrayList<Task> tasks = manager.getAllTasks();

        assertEquals(2, tasks.size());
    }

    @Test
    void shouldRemoveAllTasks() {
        Task task1 = new Task("1", "1", TaskStatus.NEW);
        Task task2 = new Task("2", "2", TaskStatus.NEW);
        Task task3 = new Task("3", "3", TaskStatus.NEW);
        manager.createTask(task1);
        manager.createTask(task2);
        manager.createTask(task3);
        manager.removeAllTasks();

        assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    void shouldCreateAndGetTaskByID() {
        Task task = new Task("1", "1", TaskStatus.NEW);
        manager.createTask(task);
        Task getTask = manager.getTaskByID(task.getTaskID());

        assertEquals(task, getTask);
    }

    @Test
    void shouldUpdateTask() {
        Task task = new Task("1", "1", TaskStatus.NEW);
        manager.createTask(task);
        task.setTaskName("upd");
        task.setDescription("upd");
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        manager.updateTask(task);

        assertEquals("upd", manager.getTaskByID(task.getTaskID()).getTaskName());
        assertEquals("upd", manager.getTaskByID(task.getTaskID()).getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, manager.getTaskByID(task.getTaskID()).getTaskStatus());
    }

    @Test
    void shouldRemoveByID() {
        Task task = new Task("1", "1", TaskStatus.NEW);
        manager.createTask(task);
        manager.removeTaskByID(task.getTaskID());

        assertEquals(0, manager.getAllTasks().size());
    }
}