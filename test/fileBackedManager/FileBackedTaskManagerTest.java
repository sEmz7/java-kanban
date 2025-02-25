package fileBackedManager;

import manager.FileBackedTaskManager;
import manager.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskStatus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FileBackedTaskManagerTest {
    static FileBackedTaskManager manager;

    @BeforeEach
    void beforeEach() {
        manager = Managers.getDefaultFileBackedTaskManager();
    }

    @Test
    void shouldLoadEmptyAndSafe() {
        Path path = Paths.get("src/data.txt");

        clearFile(path);

        manager.loadFromFile(new File("src/data.txt"));
        Task task1 = new Task("task1",  "desc1", TaskStatus.NEW);
        manager.createTask(task1);
        List<String> list = null;

        try (BufferedReader br = new BufferedReader(new FileReader("src/data.txt"))) {
            list = new ArrayList<>();
            while(br.ready()) {
                String line = br.readLine();
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(1, list.size());

        clearFile(path);
    }

    void clearFile(Path path) {
        try {
            Files.writeString(path, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
