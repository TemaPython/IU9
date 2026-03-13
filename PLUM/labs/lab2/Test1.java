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

class TriangleMapper {

  static Triangle fromParams(Map<String, String> params) {
    double x1 = Double.parseDouble(params.getOrDefault("p1x", "0"));
    double y1 = Double.parseDouble(params.getOrDefault("p1y", "0"));
    double z1 = Double.parseDouble(params.getOrDefault("p1z", "0"));
    double x2 = Double.parseDouble(params.getOrDefault("p2x", "0"));
    double y2 = Double.parseDouble(params.getOrDefault("p2y", "0"));
    double z2 = Double.parseDouble(params.getOrDefault("p2z", "0"));
    double x3 = Double.parseDouble(params.getOrDefault("p3x", "0"));
    double y3 = Double.parseDouble(params.getOrDefault("p3y", "0"));
    double z3 = Double.parseDouble(params.getOrDefault("p3z", "0"));
    return new Triangle(new Point(x1, y1, z1),
                        new Point(x2, y2, z2),
                        new Point(x3, y3, z3));
  }
}

class TriangleJsonSerializer {

  static String toJson(Triangle t) {
    return """
        {
          "point1": %s,
          "point2": %s,
          "point3": %s,
          "square": %.2f
        }
        """.formatted(
            t.getP1().toString(),
            t.getP2().toString(),
            t.getP3().toString(),
            t.getSquare());
  }

  private static String escape(String s) {
    return s.replace("\"", "\\\"");
  }
}

public class Test1 {

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

      Triangle triangle;
      try {
        triangle = TriangleMapper.fromParams(params);
      } catch (NumberFormatException e) {
        exchange.sendResponseHeaders(400, -1);
        return;
      }

      String json = TriangleJsonSerializer.toJson(triangle);

      exchange.getResponseHeaders().add("Content-Type", "application/json");
      exchange.sendResponseHeaders(200, json.getBytes().length);

      try (OutputStream os = exchange.getResponseBody()) {
        os.write(json.getBytes());
      }
    }
  }

}
