package api.handlers;

import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.HistoryManager;
import manager.TaskManager;
import tasks.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TaskHttpHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager manager;
    private final HistoryManager historyManager;


    public TaskHttpHandler(TaskManager manager, HistoryManager historyManager) {
        this.manager = manager;
        this.historyManager = historyManager;
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
                break;
            case "DELETE":
                handleDeleteTasks(exchange);
                break;
            default:
                super.sendNotFound(exchange, "Нет эндпоинта для метода " + exchange.getRequestMethod());
                break;
        }
    }

    private void handleGetTasks(HttpExchange exchange) throws IOException {
        String[] urlParts = exchange.getRequestURI().getPath().split("/");
        if (urlParts.length == 2) {
            List<Task> listOfTasks = manager.getAllTasks();
            super.sendText(exchange, gson.toJson(listOfTasks));
        } else if (urlParts.length == 3) {
            int taskId;
            try {
                taskId = Integer.parseInt(urlParts[2]);
                Task task = manager.getTaskByID(taskId);
                if (task != null) {
                    String taskJson = gson.toJson(task);
                    historyManager.addTask(task);
                    super.sendText(exchange, taskJson);
                    return;
                }
                super.sendNotFound(exchange, "Нет задачи с таким id.");
            } catch (NumberFormatException e) {
                super.sendNotFound(exchange, "ID задачи - целое число.");
            }
        }
    }

    private void handlePostTasks(HttpExchange exchange) throws IOException {
        String[] urlParts = exchange.getRequestURI().getPath().split("/");
        if (urlParts.length == 2) {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            try {
                Task task = gson.fromJson(body, Task.class);
                if (task.getTaskID() == 0) {
                    if (manager.getAllTasks().stream()
                            .anyMatch(existingTask -> !existingTask.equals(task) && manager.checkIntersection(existingTask, task))) {
                        super.sendHasInteractions(exchange, "Задача пересекается с существующими.");
                        return;
                    }
                    manager.createTask(task);
                    manager.savePrioritizedTask(task);
                    super.sendText(exchange, "Задача добавлена.");
                    return;
                }
                if (!manager.taskIsExist(task.getTaskID())) {
                    super.sendNotFound(exchange, "Нет задачи с таким ID.");
                    return;
                }
                if (manager.getAllTasks().stream()
                        .anyMatch(task1 -> !task1.equals(task) && manager.checkIntersection(task1, task))) {
                    super.sendHasInteractions(exchange, "Задача пересекается с существующими.");
                    return;
                }
                manager.updateTask(task);
                super.sendText(exchange, "Задача обновлена.");
            } catch (JsonSyntaxException e) {
                super.sendNotFound(exchange, "Неверный формат ввода задачи.");
            }
        }
    }

    private void handleDeleteTasks(HttpExchange exchange) throws IOException {
        String[] urlParts = exchange.getRequestURI().getPath().split("/");

        if (urlParts.length == 3) {
            int taskId = -1;
            try {
                taskId = Integer.parseInt(urlParts[2]);
            } catch (NumberFormatException e) {
                super.sendNotFound(exchange, "ID задачи - целое число.");
                return;
            }
            if (manager.taskIsExist(taskId)) {
                manager.removeTaskByID(taskId);
                historyManager.remove(taskId);
                manager.removePrioritizedTask(manager.getTaskByID(taskId));
                super.sendText(exchange, "Задача с ID: " + taskId + " была удалена.");
                return;
            }
            super.sendNotFound(exchange, "Нет задачи с таким ID.");
        }
    }
}
