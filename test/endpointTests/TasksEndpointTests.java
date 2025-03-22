package endpointTests;

import api.typeToken.TaskListTypeToken;
import org.junit.jupiter.api.Test;
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

class TasksEndpointTests extends BaseEndpointTests {

    @Test
    void shouldReturnTasks() throws IOException, InterruptedException {
        Task task = new Task("Task 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(15), LocalDateTime.of(2022, 12, 12, 12, 12));
        manager.createTask(task);
        Task task2 = new Task("Task 2", "Desc 2", TaskStatus.NEW,
                Duration.ofMinutes(15), LocalDateTime.of(2025, 12, 12, 12, 12));
        manager.createTask(task2);

        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        String tasksJson = response.body();

        List<Task> tasks = gson.fromJson(tasksJson, new TaskListTypeToken().getType());

        assertNotNull(tasks, "Задачи не вернулись");
        assertEquals(2, tasks.size());
        assertEquals(task2, tasks.getLast());
    }

    @Test
    void shouldReturnTaskById() throws IOException, InterruptedException {
        Task task = new Task("Task 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(15), LocalDateTime.of(2022, 12, 12, 12, 12));
        manager.createTask(task);
        Task task2 = new Task("Task 2", "Desc 2", TaskStatus.NEW,
                Duration.ofMinutes(15), LocalDateTime.of(2025, 12, 12, 12, 12));
        manager.createTask(task2);

        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        String taskJson = response.body();
        Task returnedTask = gson.fromJson(taskJson, Task.class);

        assertNotNull(returnedTask, "Задача не вернулась");
        assertEquals(task, returnedTask, "Вернулась другая задача");
    }

    @Test
    void shouldNotReturnTaskById() throws IOException, InterruptedException {
        Task task = new Task("Task 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(15), LocalDateTime.of(2022, 12, 12, 12, 12));
        manager.createTask(task);

        URI url = URI.create("http://localhost:8080/tasks/2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(404, response.statusCode(), "Задача вернулась, но ее нет в менеджере");
    }

    @Test
    void shouldNotReturnTaskByInvalidInput() throws IOException, InterruptedException {
        Task task = new Task("Task 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(15), LocalDateTime.of(2022, 12, 12, 12, 12));
        manager.createTask(task);

        URI url = URI.create("http://localhost:8080/tasks/INVALIDINPUT");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(404, response.statusCode());
    }

    @Test
    void shouldCreateTask() throws IOException, InterruptedException {
        Task task = new Task("Task 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(15), LocalDateTime.of(2022, 12, 12, 12, 12));
        String taskJson = gson.toJson(task);

        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Task> tasks = manager.getAllTasks();

        assertNotNull(task, "Задачи не возвращаются");
        assertEquals(1, tasks.size(), "Некорректное кол-во задач");
        assertEquals("Task 1", tasks.getFirst().getTaskName());
    }

    @Test
    void shouldUpdateTask() throws IOException, InterruptedException {
        Task task = new Task("Task 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(15), LocalDateTime.of(2022, 12, 12, 12, 12));
        manager.createTask(task);
        Task task2 = new Task("Task 1 updated", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(15), LocalDateTime.of(2022, 12, 12, 12, 12));
        task2.setTaskID(1);

        String taskJson = gson.toJson(task2);

        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Task> tasks = manager.getAllTasks();

        assertNotNull(task, "Задачи не возвращаются");
        assertEquals(1, tasks.size(), "Некорректное кол-во задач");
        assertEquals("Task 1 updated", tasks.getFirst().getTaskName());
    }

    @Test
    void shouldDeleteTask() throws IOException, InterruptedException {
        Task task = new Task("Task 1", "Desc 1", TaskStatus.NEW,
                Duration.ofMinutes(15), LocalDateTime.of(2022, 12, 12, 12, 12));
        manager.createTask(task);

        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Task> tasks = manager.getAllTasks();

        assertNotNull(task, "Задачи не возвращаются");
        assertEquals(0, tasks.size(), "Некорректное кол-во задач");
    }
}
