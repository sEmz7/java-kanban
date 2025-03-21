package api.handlers;

import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;
import tasks.SubTask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SubtaskHttpHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager manager;

    public SubtaskHttpHandler(TaskManager manager) {
        this.manager = manager;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET":
                handleGetSubtasks(exchange);
                break;
            case "POST":
                handlePostSubtasks(exchange);
                break;
            case "DELETE":
                handleDeleteSubtasks(exchange);
                break;
            default:
                super.sendNotFound(exchange, "Нет эндпоинта для метода " + exchange.getRequestMethod());
                break;
        }
    }

    private void handleGetSubtasks(HttpExchange exchange) throws IOException {
        String[] urlParts = exchange.getRequestURI().getPath().split("/");
        if (urlParts.length == 2) {
            String subtasksJson = gson.toJson(manager.getAllSubtasks());
            super.sendText(exchange, subtasksJson);
        } else if (urlParts.length == 3) {
            int subtaskId;
            try {
                subtaskId = Integer.parseInt(urlParts[2]);
            } catch (NumberFormatException e) {
                super.sendNotFound(exchange, "ID подзадачи - целое число.");
                return;
            }
            if (manager.subtaskIsExist(subtaskId)) {
                String subtaskJson = gson.toJson(manager.getSubtaskByID(subtaskId));
                super.sendText(exchange, subtaskJson);
            } else {
                super.sendNotFound(exchange, "Нет подзадачи с таким ID.");
            }
        }
    }

    private void handlePostSubtasks(HttpExchange exchange) throws IOException {
        String[] urlParts = exchange.getRequestURI().getPath().split("/");
        if (urlParts.length == 2) {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            SubTask subTask;
            try {
                subTask = gson.fromJson(body, SubTask.class);
            } catch (JsonSyntaxException e) {
                super.sendNotFound(exchange, "Неверный формат ввода подзадачи.");
                return;
            }
            if (subTask.getTaskID() == 0) {
                if (manager.getAllSubtasks().stream()
                        .anyMatch(subTask1 -> !subTask1.equals(subTask) &&
                                manager.checkIntersection(subTask1, subTask))) {
                    super.sendHasInteractions(exchange, "Подзадача пересекается с существующими.");
                } else {
                    manager.createSubtask(subTask);
                    super.sendText(exchange, "Подзадача добавлена в epic с ID: " + subTask.getEpicID());
                }
            } else {
                if (!manager.subtaskIsExist(subTask.getTaskID())) {
                    super.sendNotFound(exchange, "Нет подзадачи с таким ID.");
                } else {
                    if (manager.getAllSubtasks().stream()
                            .anyMatch(subTask1 -> !subTask1.equals(subTask) &&
                                    manager.checkIntersection(subTask1, subTask))) {
                        super.sendHasInteractions(exchange, "Подзадача пересекается с существующими.");
                    } else {
                        manager.updateSubtask(subTask);
                        super.sendText(exchange, "Подзадача обновлена.");
                    }
                }
            }
        }
    }

    private void handleDeleteSubtasks(HttpExchange exchange) throws IOException {
        String[] urlParts = exchange.getRequestURI().getPath().split("/");
        if (urlParts.length == 3) {
            int subtaskId;
            try {
                subtaskId = Integer.parseInt(urlParts[2]);
            } catch (NumberFormatException e) {
                super.sendNotFound(exchange, "ID подзадачи - целое число.");
                return;
            }
            if (manager.subtaskIsExist(subtaskId)) {
                manager.removeSubtaskByID(subtaskId);
                super.sendText(exchange, "Подзадача с ID: " + subtaskId + " была удалена.");
            } else {
                super.sendNotFound(exchange, "Нет подзадачи с таким ID.");
            }
        }
    }
}
