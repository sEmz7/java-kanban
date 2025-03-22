package api.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;

import java.io.IOException;

public class PrioritizedHttpHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager manager;

    public PrioritizedHttpHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if (method.equals("GET")) {
            handleGetPrioritized(exchange);
        }
    }

    private void handleGetPrioritized(HttpExchange exchange) throws IOException {
        String prioritizedJson = gson.toJson(manager.getPrioritizedTasks());
        super.sendText(exchange, prioritizedJson);
    }
}
