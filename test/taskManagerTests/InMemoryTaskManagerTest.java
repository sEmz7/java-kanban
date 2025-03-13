package taskManagerTests;

import manager.InMemoryTaskManager;
import manager.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    void setup() {
        manager = Managers.getDefaultTaskManager();
    }

    @Test
    void shouldCreateDifferentTasksAndFindTasks() {
        Task task1 = new Task("1", "1", TaskStatus.NEW);
        manager.createTask(task1);
        Epic epic1 = new Epic("2", "2", TaskStatus.NEW);
        manager.createEpic(epic1);
        SubTask subTask1 = new SubTask("3", "3", TaskStatus.NEW);
        subTask1.setEpicID(epic1.getTaskID());
        manager.createSubtask(subTask1);

        assertEquals(task1, manager.getTaskByID(task1.getTaskID()));
        assertEquals(epic1, manager.getEpicByID(epic1.getTaskID()));
        assertEquals(subTask1, manager.getSubtaskByID(subTask1.getTaskID()));

        assertNull(manager.getSubtaskByID(9999));
    }

    @Test
    void shouldNotConflictWithRandomID() {
        Task taskManualID = new Task("1", "2", TaskStatus.IN_PROGRESS);
        Task taskGeneratedID = new Task("2", "3", TaskStatus.NEW);
        taskManualID.setTaskID(1);
        manager.createTask(taskManualID);
        manager.createTask(taskGeneratedID);

        assertEquals(taskManualID, manager.getTaskByID(taskManualID.getTaskID()));
        assertNotEquals(taskManualID.getTaskID(), taskGeneratedID.getTaskID());
        assertEquals(taskGeneratedID, manager.getTaskByID(taskGeneratedID.getTaskID()));
    }
}
