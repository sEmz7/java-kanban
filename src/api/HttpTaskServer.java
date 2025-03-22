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
    private HttpServer httpServer;
    private boolean isStarted;

    public HttpTaskServer(TaskManager manager) {
        this.manager = manager;
    }

    public void start() {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(serverPort), 0);
            httpServer.createContext("/tasks", new TaskHttpHandler(manager, historyManager));
            httpServer.createContext("/epics", new EpicHttpHandler(manager, historyManager));
            httpServer.createContext("/subtasks", new SubtaskHttpHandler(manager, historyManager));
            httpServer.createContext("/history", new HistoryHttpHandler(historyManager));
            httpServer.createContext("/prioritized", new PrioritizedHttpHandler(manager));
            httpServer.start();
            isStarted = true;
            System.out.println("Сервер запущен на порту: " + serverPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        if (isStarted) {
            httpServer.stop(0);
        }
    }
}
