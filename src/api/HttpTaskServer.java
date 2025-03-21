package api;

import api.handlers.EpicHttpHandler;
import api.handlers.SubtaskHttpHandler;
import api.handlers.TaskHttpHandler;
import com.sun.net.httpserver.HttpServer;
import manager.Managers;
import manager.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpTaskServer {
    private static final int serverPort = 8080;
    private final TaskManager manager;

    public HttpTaskServer(TaskManager manager) {
        this.manager = manager;
    }

    public HttpTaskServer() {
        this.manager = Managers.getDefaultTaskManager();
    }

    public void start() {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(serverPort), 0);
            httpServer.createContext("/tasks", new TaskHttpHandler(manager));
            httpServer.createContext("/epics", new EpicHttpHandler(manager));
            httpServer.createContext("/subtasks", new SubtaskHttpHandler(manager));
            httpServer.start();
            System.out.println("Сервер запущен на порту: " + serverPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
