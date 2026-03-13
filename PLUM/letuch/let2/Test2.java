import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

class Vector {
    private final String vector;

    Vector(String vector) {
        this.vector = vector;
    }

    String getVector() {
        return vector;
    }
}

class QueryParser {

    static Map<String, String> parse(String query) {
        Map<String, String> params = new HashMap<>();
        if (query == null || query.isEmpty())
            return params;

        for (String pair : query.split("&")) {
            String[] parts = pair.split("=", 2);
            if (parts.length != 2)
                continue;

            String key = URLDecoder.decode(parts[0], StandardCharsets.UTF_8);
            String value = URLDecoder.decode(parts[1], StandardCharsets.UTF_8);
            params.merge(key, value, (oldVal, newVal) -> oldVal + ", " + newVal);
        }
        return params;
    }
}

class VectorMapper {

    static Vector fromParams(Map<String, String> params) {
        String reason = params.getOrDefault("vector", "0");
        return new Vector(reason);
    }
}

class VectorJsonSerializer {

    static String toJson(Vector p) {
        int id = System.identityHashCode(p);

        return """
        {
          "vector": "(%s)",
          "objectId": "%s"
        }
        """.formatted(
                p.getVector(),
                Integer.toHexString(id));
    }

    private static String escape(String s) {
        return s.replace("\"", "\\\"");
    }
}

public class Test2 {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(5678), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            String query = exchange.getRequestURI().getQuery();
            Map<String, String> params = QueryParser.parse(query);

            Vector vector;
            try {vector = VectorMapper.fromParams(params);
            } catch (NumberFormatException e) {
                exchange.sendResponseHeaders(400, -1);
                return;
            }

            String json = VectorJsonSerializer.toJson(vector);

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, json.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(json.getBytes());
            }
        }
    }

}
