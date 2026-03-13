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
            params.put(key, value);
        }
        return params;
    }
}

class PointMapper {

    static Point[] fromParams(Map<String, String> params) {
        double x1 = Double.parseDouble(params.getOrDefault("x1", "0"));
        double y1 = Double.parseDouble(params.getOrDefault("y1", "0"));
        double x2 = Double.parseDouble(params.getOrDefault("x2", "0"));
        double y2 = Double.parseDouble(params.getOrDefault("y2", "0"));
        return  new Point[] {new Point(x1, y1), new Point(x2, y2)};
    }
}

class PointJsonSerializer {

    static String toJson(Point p1, Point p2, String way, Ruler lin) {
        if (way.equals("ruler")) {
            return """
                    {
                      "distance": %.2f
                      "way": %s
                      "work_time_ms": %s
                    }
                    """.formatted(
                    lin.dist(p1, p2),
                    "By ruler",
                    lin.gtTime());
        } else {
            return """
                    {
                      "distance": %.2f
                      "way": %s
                      "work_time_ms": %s
                    }
                    """.formatted(
                    Point.dist(p1, p2),
                    "By points",
                    p1.gtTime());
        }
    }

    private static String escape(String s) {
        return s.replace("\"", "\\\"");
    }
}

public class Test {

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
            String way = params.getOrDefault("way", "points");
            Point[] points;
            try {
                points = PointMapper.fromParams(params);
            } catch (NumberFormatException e) {
                exchange.sendResponseHeaders(400, -1);
                return;
            }
            Ruler lin = new Ruler();

            String json = PointJsonSerializer.toJson(points[0], points[1], way, lin);

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, json.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(json.getBytes());
            }
        }
    }

}

