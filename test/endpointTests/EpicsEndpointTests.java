package endpointTests;

import api.typeToken.EpicListTypeToken;
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

public class EpicsEndpointTests extends BaseEndpointTests {

    @Test
    void shouldReturnEpics() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(150),
                LocalDateTime.of(2020, 12, 12, 12, 12),
                LocalDateTime.of(2021, 12, 12, 12, 12));
        manager.createEpic(epic);

        Epic epic2 = new Epic("Epic 2", "Desc 2", TaskStatus.NEW,
                Duration.ofMinutes(30),
                LocalDateTime.of(2025, 12, 12, 12, 12),
                LocalDateTime.of(2026, 12, 12, 12, 12));
        manager.createEpic(epic2);

        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        String epicsJson = response.body();
        List<Epic> epics = gson.fromJson(epicsJson, new EpicListTypeToken().getType());

        assertNotNull(epics, "Эпики не вернулись");
        assertEquals(2, epics.size());
        assertEquals("Epic 2", epics.getLast().getTaskName());
    }

    @Test
    void shouldReturnEpicByID() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(150),
                LocalDateTime.of(2020, 12, 12, 12, 12),
                LocalDateTime.of(2021, 12, 12, 12, 12));
        manager.createEpic(epic);

        Epic epic2 = new Epic("Epic 2", "Desc 2", TaskStatus.NEW,
                Duration.ofMinutes(30),
                LocalDateTime.of(2025, 12, 12, 12, 12),
                LocalDateTime.of(2026, 12, 12, 12, 12));
        manager.createEpic(epic2);

        URI url = URI.create("http://localhost:8080/epics/2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        String epicJson = response.body();

        Epic returnedEpic = gson.fromJson(epicJson, Epic.class);

        assertNotNull(returnedEpic, "Эпик не вернулся");
        assertEquals(epic2, returnedEpic);
    }

    @Test
    void shouldReturnEpicSubtasks() throws IOException, InterruptedException {
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

        URI url = URI.create("http://localhost:8080/epics/1/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        List<SubTask> subtasks = gson.fromJson(response.body(), new SubtaskListTypeToken().getType());

        assertNotNull(subtasks);
        assertEquals(2, subtasks.size());
        assertEquals("Sub 1", subtasks.getFirst().getTaskName());
    }

    @Test
    void shouldCreateEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(150),
                LocalDateTime.of(2020, 12, 12, 12, 12),
                LocalDateTime.of(2021, 12, 12, 12, 12));
        String epicJson = gson.toJson(epic);


        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(epicJson))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Epic> epics = manager.getAllEpics();

        assertNotNull(epics, "Эпик не добавился");
        assertEquals(1, epics.size());
    }

    @Test
    void shouldUpdateEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(150),
                LocalDateTime.of(2020, 12, 12, 12, 12),
                LocalDateTime.of(2021, 12, 12, 12, 12));
        manager.createEpic(epic);
        Epic epic2 = new Epic("Epic 1 updated", "Desc 1 upd", TaskStatus.NEW,
                Duration.ofMinutes(150),
                LocalDateTime.of(2025, 12, 12, 12, 12),
                LocalDateTime.of(2027, 12, 12, 12, 12));
        epic2.setTaskID(1);

        String epicJson = gson.toJson(epic2);

        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(epicJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        assertEquals(1, manager.getAllEpics().size());
        assertEquals("Epic 1 updated", manager.getAllEpics().getFirst().getTaskName());
    }

    @Test
    void shouldDeleteEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(150),
                LocalDateTime.of(2020, 12, 12, 12, 12),
                LocalDateTime.of(2021, 12, 12, 12, 12));
        manager.createEpic(epic);
        URI url = URI.create("http://localhost:8080/epics/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        assertEquals(0, manager.getAllEpics().size());
    }
}
