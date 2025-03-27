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
    private static final String TASKS_ENDPOINT = "/tasks";
    private static final String EPICS_ENDPOINT = "/epics";
    private static final String SUBTASKS_ENDPOINT = "/subtasks";
    private static final String HISTORY_ENDPOINT = "/history";
    private static final String PRIORITIZED_ENDPOINT = "/prioritized";

    public HttpTaskServer(TaskManager manager) {
        this.manager = manager;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(serverPort), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        httpServer.createContext(TASKS_ENDPOINT, new TaskHttpHandler(manager, historyManager));
        httpServer.createContext(EPICS_ENDPOINT, new EpicHttpHandler(manager, historyManager));
        httpServer.createContext(SUBTASKS_ENDPOINT, new SubtaskHttpHandler(manager, historyManager));
        httpServer.createContext(HISTORY_ENDPOINT, new HistoryHttpHandler(historyManager));
        httpServer.createContext(PRIORITIZED_ENDPOINT, new PrioritizedHttpHandler(manager));
        httpServer.start();
        isStarted = true;
        System.out.println("Сервер запущен на порту: " + serverPort);
    }

    public void stop() {
        if (isStarted) {
            httpServer.stop(0);
        }
    }
}
