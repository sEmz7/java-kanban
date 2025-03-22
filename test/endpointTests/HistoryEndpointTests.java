package endpointTests;

import api.typeToken.TaskListTypeToken;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryEndpointTests extends BaseEndpointTests {

    @Test
    void shouldGetHistory() throws IOException, InterruptedException {
        Task task = new Task("Task 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(15), LocalDateTime.of(2022, 12, 12, 12, 12));
        manager.createTask(task);

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

        URI url = URI.create("http://localhost:8080/tasks/1");
        URI url2 = URI.create("http://localhost:8080/epics/2");
        URI url3 = URI.create("http://localhost:8080/subtasks/3");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpRequest request2 = HttpRequest.newBuilder().uri(url2).GET().build();
        HttpRequest request3 = HttpRequest.newBuilder().uri(url3).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(200, response2.statusCode());
        assertEquals(200, response3.statusCode());

        URI urlHistory = URI.create("http://localhost:8080/history");
        HttpRequest requestHistory = HttpRequest.newBuilder().uri(urlHistory).GET().build();
        HttpResponse<String> responseHistory = client.send(requestHistory, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, responseHistory.statusCode());
        List<Task> history = gson.fromJson(responseHistory.body(), new TaskListTypeToken().getType());

        assertEquals(3, history.size());
        assertEquals(task, history.getFirst());
    }
}
