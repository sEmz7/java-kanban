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
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PrioritizedEndpointTests extends BaseEndpointTests {

    @Test
    void shouldGetPrioritizedTasks() throws IOException, InterruptedException {
        Task task = new Task("Task 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(15), LocalDateTime.of(2099, 12, 12, 12, 12));
        manager.createTask(task);
        manager.savePrioritizedTask(task);

        Epic epic = new Epic("Epic 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(150),
                LocalDateTime.of(2020, 12, 12, 12, 12),
                LocalDateTime.of(2021, 12, 12, 12, 12));
        manager.createEpic(epic);

        SubTask subTask = new SubTask("Sub 1", "Desc1", TaskStatus.NEW,
                Duration.ofMinutes(15),
                LocalDateTime.of(2014, 12, 12, 12, 12),
                LocalDateTime.of(2015, 12, 12, 12, 12));
        subTask.setEpicID(epic.getTaskID());
        manager.createSubtask(subTask);
        manager.savePrioritizedTask(subTask);

        URI url = URI.create("http://localhost:8080/prioritized");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Task> prioritizedTasks = gson.fromJson(response.body(), new TaskListTypeToken().getType());

        assertNotNull(prioritizedTasks);
        assertEquals(2, prioritizedTasks.size());
    }
}
