package api.handlers;

import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.HistoryManager;
import manager.TaskManager;
import tasks.SubTask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SubtaskHttpHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager manager;
    private final HistoryManager historyManager;

    public SubtaskHttpHandler(TaskManager manager, HistoryManager historyManager) {
        this.manager = manager;
        this.historyManager = historyManager;
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
            int subtaskId = -1;
            try {
                subtaskId = Integer.parseInt(urlParts[2]);
            } catch (NumberFormatException e) {
                super.sendNotFound(exchange, "ID подзадачи - целое число.");
                return;
            }
            if (manager.subtaskIsExist(subtaskId)) {
                SubTask subTask = manager.getSubtaskByID(subtaskId);
                String subtaskJson = gson.toJson(subTask);
                historyManager.addTask(subTask);
                super.sendText(exchange, subtaskJson);
                return;
            }
            super.sendNotFound(exchange, "Нет подзадачи с таким ID.");
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
                    return;
                }
                manager.createSubtask(subTask);
                manager.savePrioritizedTask(subTask);
                super.sendText(exchange, "Подзадача добавлена в epic с ID: " + subTask.getEpicID());
                return;
            }
            if (!manager.subtaskIsExist(subTask.getTaskID())) {
                super.sendNotFound(exchange, "Нет подзадачи с таким ID.");
                return;
            }
            if (manager.getAllSubtasks().stream()
                    .anyMatch(subTask1 -> !subTask1.equals(subTask) &&
                            manager.checkIntersection(subTask1, subTask))) {
                super.sendHasInteractions(exchange, "Подзадача пересекается с существующими.");
                return;
            }
            manager.updateSubtask(subTask);
            super.sendText(exchange, "Подзадача обновлена.");
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
                historyManager.remove(subtaskId);
                manager.removePrioritizedTask(manager.getSubtaskByID(subtaskId));
                super.sendText(exchange, "Подзадача с ID: " + subtaskId + " была удалена.");
                return;
            }
            super.sendNotFound(exchange, "Нет подзадачи с таким ID.");
        }
    }
}
