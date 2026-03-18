import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.HashMap;

public class Router {
    private final HttpServer server;
    private final Map<String, Map<String, HttpHandler>> routes = new HashMap<>();

    public Router(HttpServer server) {
        this.server = server;
    }

    public void addRoute(String path, String method, HttpHandler handler) {
        routes.computeIfAbsent(path, k -> new HashMap<>()).put(method, handler);
    }

    public void handleRequest(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        Map<String, HttpHandler> methodHandlers = routes.get(path);
        if (methodHandlers == null) {
            sendError(exchange, 404, "404 Not Found");
            return;
        }

        HttpHandler handler = methodHandlers.get(method);
        if (handler == null) {
            sendError(exchange, 405, "405 Method Not Allowed");
            return;
        }

        try {
            handler.handle(exchange);
        } catch (Exception e) {
            e.printStackTrace();
            sendError(exchange, 500, "500 Internal Server Error");
        }
    }

    private void sendError(HttpExchange exchange, int code, String message) throws IOException {
        exchange.sendResponseHeaders(code, message.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(message.getBytes());
        }
    }
}