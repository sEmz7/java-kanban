package taskManagerTests;

import exceptions.ManagerSaveException;
import manager.Managers;
import manager.file.FileBackedTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskStatus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {
    private final Path path = Paths.get("src/data.txt");

    @BeforeEach
    void setup() {
        manager = Managers.getDefaultFileBackedTaskManager();
        clearFile();
    }

    @AfterEach
    void tearDown() {
        clearFile();
    }

    @Test
    void shouldLoadEmptyAndSafe() {
        FileBackedTaskManager.loadFromFile(new File("src/data.txt"));
        Task task1 = new Task("task1", "desc1", TaskStatus.NEW);
        manager.createTask(task1);
        List<String> list = null;

        try (BufferedReader br = new BufferedReader(new FileReader("src/data.txt"))) {
            list = new ArrayList<>();
            while (br.ready()) {
                String line = br.readLine();
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(1, list.size());

    }

    @Test
    void shouldCheckDateTimeIntersections() {
        Task task1 = new Task("task1", "1", TaskStatus.NEW, Duration.ofMinutes(180),
                LocalDateTime.of(2000, 1, 1, 1, 1, 1));
        manager.createTask(task1);

        Task task2 = new Task("task2", "2", TaskStatus.NEW, Duration.ofMinutes(360),
                LocalDateTime.of(2000, 1, 1, 2, 1, 1));
        manager.createTask(task1);

        // вторая задача не добавится в список, так как пересекается по времени
        assertEquals(1, manager.getPrioritizedTasks().size());
    }

    void clearFile() {
        try {
            Files.writeString(path, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testManagerSaveException() {
        assertThrows(ManagerSaveException.class, () -> {
            FileBackedTaskManager.fromString("aaa,aaa");
        }, "Нету типа задачи (aaa) ");
    }
}
