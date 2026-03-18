import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class StaticFileHandler implements HttpHandler {
    private final String rootDirectory;
    private final Map<String, String> mimeTypes;

    public StaticFileHandler(String rootDirectory) {
        this.rootDirectory = rootDirectory;
        this.mimeTypes = new HashMap<>();

        mimeTypes.put("html", "text/html");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (path.equals("/menu")) {
            path = "/menu.html";
        }

        File file = new File(rootDirectory + path);

        if (!file.exists() || file.isDirectory()) {
            sendNotFound(exchange);
            return;
        }

        String contentType = getContentType(file.getName());

        try (FileInputStream fis = new FileInputStream(file)) {
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, file.length());

            try (OutputStream os = exchange.getResponseBody()) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, count);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            sendInternalError(exchange);
        }
    }

    private String getContentType(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1).toLowerCase();
        }

        return mimeTypes.getOrDefault(extension, "application/octet-stream");
    }

    private void sendNotFound(HttpExchange exchange) throws IOException {
        String response = "404 Not Found";
        exchange.sendResponseHeaders(404, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void sendInternalError(HttpExchange exchange) throws IOException {
        String response = "500 Internal Server Error";
        exchange.sendResponseHeaders(500, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}

