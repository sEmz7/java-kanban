package managers;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagersTest {
    static TaskManager manager;

    @BeforeEach
    void beforeEach() {
        manager = Managers.getDefaultTaskManager();
    }

    @Test
    void managersShouldReturnReadyTaskManager() {
        Task newTask = new Task("1", "2", TaskStatus.NEW);
        manager.createTask(newTask);
        Task task1 = manager.getTaskByID(newTask.getTaskID());

        assertEquals(task1, manager.getTaskByID(newTask.getTaskID()));
    }


    @Test
    void managerShouldNotChangeField() {
        Task task1 = new Task("Task1", "Description1", TaskStatus.NEW);
        manager.createTask(task1);

        assertEquals("Task1", task1.getTaskName());
        assertEquals("Description1", task1.getDescription());
        assertEquals(TaskStatus.NEW, task1.getTaskStatus());
    }
}