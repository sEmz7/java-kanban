package api.handlers;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;
import tasks.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class TaskHttpHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager manager;
    private final Gson gson;

    public TaskHttpHandler(TaskManager manager) {
        this.manager = manager;
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
                .create();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET":
                handleGetTasks(exchange);
                break;
            case "POST":
                handlePostTasks(exchange);
                // post
                break;
            case "DELETE":
                // delete
                break;
            default:

                break;

        }
    }

    private void handleGetTasks(HttpExchange exchange) throws IOException {
        String[] urlParts = exchange.getRequestURI().getPath().split("/");
        if (urlParts.length == 2) {
            List<Task> listOfTasks =  manager.getAllTasks();
            super.sendText(exchange, gson.toJson(listOfTasks));
        } else if (urlParts.length == 3) {
            int taskId;
            try {
                taskId = Integer.parseInt(urlParts[2]);
                Task task = manager.getTaskByID(taskId);
                if (task != null) {
                    String taskJson = gson.toJson(task);
                    super.sendText(exchange, taskJson);
                    return;
                }
                super.sendNotFound(exchange, "Нет задачи с таким id.");
            } catch (NumberFormatException e) {
                super.sendNotFound(exchange, "Нет задачи с таким id.");
            }
        }
    }

    private void handlePostTasks(HttpExchange exchange) throws IOException {
        String[] urlParts = exchange.getRequestURI().getPath().split("/");
        if (urlParts.length == 2) {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

            try {
                Task task = gson.fromJson(body, Task.class);
                System.out.println(task);
                manager.createTask(task);
                super.sendText(exchange, "Задача добавлена.");
            } catch (JsonSyntaxException e) {
                super.sendNotFound(exchange, "Неверный формат ввода.");
            }
        }
        // TODO: Обработать POST /tasks/id
    }
}
