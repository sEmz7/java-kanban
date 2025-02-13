package Tasks;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    static TaskManager manager;

    @BeforeEach
    void beforeEach() {
        manager = Managers.getDefaultTaskManager();
    }

    @Test
    void epicStatusShouldChange() {
        Epic epic = new Epic("Epic 1", "Desc 1", TaskStatus.NEW);
        manager.createEpic(epic);

        SubTask subTask1 = new SubTask("Sub 1", "Desc sub 1", TaskStatus.NEW);
        subTask1.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask1);

        SubTask subTask2 = new SubTask("Sub 2", "Desc sub 2", TaskStatus.NEW);
        subTask2.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask2);

        subTask2.setTaskStatus(TaskStatus.IN_PROGRESS);
        manager.updateSubtask(subTask2);

        assertEquals(TaskStatus.IN_PROGRESS, subTask2.getTaskStatus());
        assertEquals(TaskStatus.NEW, subTask1.getTaskStatus());
        assertEquals(TaskStatus.IN_PROGRESS, epic.getTaskStatus());
    }

    @Test
    void taskHeirEqualsIfSameID() {
        Epic epic1 = new Epic("Epic1", "Desc1", TaskStatus.IN_PROGRESS);
        Epic epic2 = new Epic("Epic2", "Desc2", TaskStatus.NEW);
        epic1.setTaskID(1);
        epic2.setTaskID(1);

        assertEquals(epic1, epic2);
    }

    @Test
    void shouldGetAllSubtasksInEpic() {
        Epic epic = new Epic("1", "1", TaskStatus.NEW);
        manager.createEpic(epic);

        SubTask subTask1 = new SubTask("sub1", "desc1", TaskStatus.NEW);
        subTask1.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask1);
        SubTask subTask2 = new SubTask("sub12", "desc2", TaskStatus.NEW);
        subTask2.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask2);

        ArrayList<SubTask> subtasks = manager.getAllSubtasksInEpic(epic.getTaskID());

        assertEquals(2, subtasks.size());
    }

    @Test
    void shouldRemoveAllEpics() {
        Epic epic1 = new Epic("1", "1", TaskStatus.NEW);
        manager.createEpic(epic1);
        Epic epic2 = new Epic("2", "2", TaskStatus.NEW);
        manager.createEpic(epic2);
        Epic epic3 = new Epic("3", "3", TaskStatus.NEW);
        manager.createEpic(epic3);

        manager.removeAllEpics();

        assertEquals(0, manager.getAllEpics().size());
    }

    @Test
    void shouldRemoveAllSubtasksInEpic() {
        Epic epic = new Epic("1", "1", TaskStatus.NEW);
        manager.createEpic(epic);

        SubTask subTask1 = new SubTask("sub1", "desc1", TaskStatus.NEW);
        subTask1.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask1);
        SubTask subTask2 = new SubTask("sub12", "desc2", TaskStatus.NEW);
        subTask2.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask2);

        manager.removeAllSubtasksInEpic(epic.getTaskID());

        assertEquals(0, manager.getAllSubtasksInEpic(epic.getTaskID()).size());

    }

}