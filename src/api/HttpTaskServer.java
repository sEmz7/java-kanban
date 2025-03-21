package api;

import api.handlers.*;
import com.sun.net.httpserver.HttpServer;
import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int serverPort = 8080;
    private final TaskManager manager;
    private final HistoryManager historyManager = Managers.getDefaultHistoryManager();

    public HttpTaskServer(TaskManager manager) {
        this.manager = manager;
    }

    public HttpTaskServer() {
        this.manager = Managers.getDefaultTaskManager();
    }

    public void start() {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(serverPort), 0);
            httpServer.createContext("/tasks", new TaskHttpHandler(manager, historyManager));
            httpServer.createContext("/epics", new EpicHttpHandler(manager, historyManager));
            httpServer.createContext("/subtasks", new SubtaskHttpHandler(manager, historyManager));
            httpServer.createContext("/history", new HistoryHttpHandler(historyManager));
            httpServer.createContext("/prioritized", new PrioritizedHttpHandler(manager));
            httpServer.start();
            System.out.println("Сервер запущен на порту: " + serverPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
