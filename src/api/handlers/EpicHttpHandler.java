package api.handlers;

import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.HistoryManager;
import manager.TaskManager;
import tasks.Epic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EpicHttpHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager manager;
    private final HistoryManager historyManager;

    public EpicHttpHandler(TaskManager manager, HistoryManager historyManager) {
        this.manager = manager;
        this.historyManager = historyManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET":
                handleGetEpics(exchange);
                break;
            case "POST":
                handlePostEpics(exchange);
                break;
            case "DELETE":
                handleDeleteEpics(exchange);
                break;
            default:
                super.sendNotFound(exchange, "Нет эндпоинта для метода " + exchange.getRequestMethod());
                break;
        }
    }

    private void handleGetEpics(HttpExchange exchange) throws IOException {
        String[] urlParts = exchange.getRequestURI().getPath().split("/");

        if (urlParts.length == 2) {
            super.sendText(exchange, gson.toJson(manager.getAllEpics()));
        } else if (urlParts.length == 3) {
            int epicId = -1;
            try {
                epicId = Integer.parseInt(urlParts[2]);
            } catch (NumberFormatException e) {
                super.sendNotFound(exchange, "ID эпика - целое число.");
                return;
            }
            if (manager.epicIsExist(epicId)) {
                Epic epic = manager.getEpicByID(epicId);
                String epicJson = gson.toJson(epic);
                historyManager.addTask(epic);
                super.sendText(exchange, epicJson);
                return;
            }
            super.sendNotFound(exchange, "Нет эпика с таким ID.");
        } else if (urlParts.length == 4 && urlParts[3].equals("subtasks")) {
            int epicId;
            try {
                epicId = Integer.parseInt(urlParts[2]);
            } catch (NumberFormatException e) {
                super.sendNotFound(exchange, "ID эпика - целое число.");
                return;
            }
            if (manager.epicIsExist(epicId)) {
                String epicSubtasksJson = gson.toJson(manager.getEpicByID(epicId).getSubTasks());
                super.sendText(exchange, epicSubtasksJson);
                return;
            }
            super.sendNotFound(exchange, "Нет эпика с таким ID.");
        }
    }

    private void handlePostEpics(HttpExchange exchange) throws IOException {
        String[] urlParts = exchange.getRequestURI().getPath().split("/");
        if (urlParts.length == 2) {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Epic epic;
            try {
                epic = gson.fromJson(body, Epic.class);
                if (epic.getTaskID() == 0) {
                    if (manager.getAllEpics().stream()
                            .anyMatch(epic1 -> !epic1.equals(epic) && manager.checkIntersection(epic1, epic))) {
                        super.sendHasInteractions(exchange, "Эпик пересекается с существующими.");
                        return;
                    }
                    manager.createEpic(epic);
                    super.sendText(exchange, "Эпик добавлен.");
                    return;
                } else if (!manager.epicIsExist(epic.getTaskID())) {
                    super.sendNotFound(exchange, "Нет задачи с таким ID.");
                    return;
                }
                if (manager.getAllEpics().stream()
                        .anyMatch(epic1 -> !epic1.equals(epic) && manager.checkIntersection(epic1, epic))) {
                    super.sendHasInteractions(exchange, "Эпик пересекается с существующими.");
                    return;
                }
                manager.updateEpic(epic);
                super.sendText(exchange, "Эпик обновлен.");
            } catch (JsonSyntaxException e) {
                super.sendNotFound(exchange, "Неверный формат ввода эпика.");
            }
        }
    }

    private void handleDeleteEpics(HttpExchange exchange) throws IOException {
        String[] urlParts = exchange.getRequestURI().getPath().split("/");

        if (urlParts.length == 3) {
            int epicId;
            try {
                epicId = Integer.parseInt(urlParts[2]);
            } catch (NumberFormatException e) {
                super.sendNotFound(exchange, "ID эпика - целое число.");
                return;
            }
            if (manager.epicIsExist(epicId)) {
                manager.removeEpicByID(epicId);
                historyManager.remove(epicId);
                super.sendText(exchange, "Эпик удален.");
                return;
            }
            super.sendNotFound(exchange, "Нет эпика с таким ID.");
        }
    }
}
