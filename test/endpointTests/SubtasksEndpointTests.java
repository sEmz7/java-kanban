package endpointTests;

import api.typeToken.SubtaskListTypeToken;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.TaskStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SubtasksEndpointTests extends BaseEndpointTests {

    @Test
    void shouldReturnSubtasks() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(150),
                LocalDateTime.of(2020, 12, 12, 12, 12),
                LocalDateTime.of(2021, 12, 12, 12, 12));
        manager.createEpic(epic);
        SubTask subTask = new SubTask("Sub 1", "Desc1", TaskStatus.NEW,
                Duration.ofMinutes(15),
                LocalDateTime.of(2019, 12, 12, 12, 12),
                LocalDateTime.of(2025, 12, 12, 12, 12));
        subTask.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask);

        SubTask subTask2 = new SubTask("Sub 2", "Desc2", TaskStatus.NEW,
                Duration.ofMinutes(155),
                LocalDateTime.of(2027, 12, 12, 12, 12),
                LocalDateTime.of(2029, 12, 12, 12, 12));
        subTask2.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask2);

        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        List<SubTask> subTasks = gson.fromJson(response.body(), new SubtaskListTypeToken().getType());

        assertNotNull(subTasks);
        assertEquals(2, subTasks.size());
        assertEquals("Sub 1", subTasks.getFirst().getTaskName());
    }

    @Test
    void shouldReturnSubtaskById() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(150),
                LocalDateTime.of(2020, 12, 12, 12, 12),
                LocalDateTime.of(2021, 12, 12, 12, 12));
        manager.createEpic(epic);
        SubTask subTask = new SubTask("Sub 1", "Desc1", TaskStatus.NEW,
                Duration.ofMinutes(15),
                LocalDateTime.of(2019, 12, 12, 12, 12),
                LocalDateTime.of(2025, 12, 12, 12, 12));
        subTask.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask);

        URI url = URI.create("http://localhost:8080/subtasks/2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        SubTask returnedSubtask = gson.fromJson(response.body(), SubTask.class);

        assertNotNull(returnedSubtask);
        assertEquals(subTask, returnedSubtask);
    }

    @Test
    void shouldCreateSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(150),
                LocalDateTime.of(2020, 12, 12, 12, 12),
                LocalDateTime.of(2021, 12, 12, 12, 12));
        manager.createEpic(epic);

        SubTask subTask = new SubTask("Sub 1", "Desc1", TaskStatus.NEW,
                Duration.ofMinutes(15),
                LocalDateTime.of(2019, 12, 12, 12, 12),
                LocalDateTime.of(2025, 12, 12, 12, 12));
        subTask.setEpicID(epic.getTaskID());

        String subtaskJson = gson.toJson(subTask);

        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<SubTask> epicSubtasks = manager.getAllSubtasksInEpic(epic.getTaskID());

        assertNotNull(epicSubtasks);
        assertEquals(1, epicSubtasks.size());
        assertEquals("Sub 1", epicSubtasks.getFirst().getTaskName());
    }

    @Test
    void shouldUpdateSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(150),
                LocalDateTime.of(2020, 12, 12, 12, 12),
                LocalDateTime.of(2021, 12, 12, 12, 12));
        manager.createEpic(epic);

        SubTask subTask = new SubTask("Sub 1", "Desc1", TaskStatus.NEW,
                Duration.ofMinutes(15),
                LocalDateTime.of(2019, 12, 12, 12, 12),
                LocalDateTime.of(2025, 12, 12, 12, 12));
        subTask.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask);

        SubTask subTask2 = new SubTask("Sub 1 updated", "Desc1 updated", TaskStatus.NEW,
                Duration.ofMinutes(155),
                LocalDateTime.of(2027, 12, 12, 12, 12),
                LocalDateTime.of(2029, 12, 12, 12, 12));
        subTask2.setEpicID(epic.getTaskID());
        subTask2.setTaskID(subTask.getTaskID());

        String subtaskJson = gson.toJson(subTask2);

        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<SubTask> epicSubtasks = manager.getAllSubtasksInEpic(epic.getTaskID());

        assertNotNull(epicSubtasks);
        assertEquals(1, epicSubtasks.size());
        assertEquals("Sub 1 updated", epicSubtasks.getFirst().getTaskName());
    }

    @Test
    void shouldDeleteSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(150),
                LocalDateTime.of(2020, 12, 12, 12, 12),
                LocalDateTime.of(2021, 12, 12, 12, 12));
        manager.createEpic(epic);

        SubTask subTask = new SubTask("Sub 1", "Desc1", TaskStatus.NEW,
                Duration.ofMinutes(15),
                LocalDateTime.of(2019, 12, 12, 12, 12),
                LocalDateTime.of(2025, 12, 12, 12, 12));
        subTask.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask);

        URI url = URI.create("http://localhost:8080/subtasks/2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(0, manager.getAllSubtasksInEpic(epic.getTaskID()).size());
    }
}
