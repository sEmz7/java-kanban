package taskManagerTests;

import manager.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T manager;

    @Test
    void subtasksShouldBeConnectedToEpic() {
        Epic epic = new Epic("Epic1", "Epic description", TaskStatus.NEW);
        manager.createEpic(epic);

        SubTask subTask1 = new SubTask("SubTask1", "Description 1", TaskStatus.DONE);
        subTask1.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask1);

        SubTask subTask2 = new SubTask("SubTask2", "Description 2", TaskStatus.NEW);
        subTask2.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask2);

        assertEquals(epic.getTaskID(), subTask1.getEpicID());
        assertEquals(epic.getTaskID(), subTask2.getEpicID());
    }

    @Test
    void epicStatusShouldChange() {
        Epic epic = new Epic("Epic1", "Epic description", TaskStatus.NEW);
        manager.createEpic(epic);

        SubTask subTask1 = new SubTask("SubTask1", "Description 1", TaskStatus.NEW);
        subTask1.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask1);

        SubTask subTask2 = new SubTask("SubTask2", "Description 2", TaskStatus.NEW);
        subTask2.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask2);

        subTask2.setTaskStatus(TaskStatus.IN_PROGRESS);
        manager.updateSubtask(subTask2);

        assertEquals(TaskStatus.IN_PROGRESS, subTask2.getTaskStatus());
        assertEquals(TaskStatus.NEW, subTask1.getTaskStatus());
        assertEquals(TaskStatus.IN_PROGRESS, epic.getTaskStatus());
    }
}
