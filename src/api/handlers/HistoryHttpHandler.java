package api.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.HistoryManager;

import java.io.IOException;

public class HistoryHttpHandler extends BaseHttpHandler implements HttpHandler {
    private final HistoryManager historyManager;

    public HistoryHttpHandler(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if (method.equals("GET")) {
            handleGetHistory(exchange);
        }
    }

    private void handleGetHistory(HttpExchange exchange) throws IOException {
        String historyJson = gson.toJson(historyManager.getHistory());
        super.sendText(exchange, historyJson);
    }
}
