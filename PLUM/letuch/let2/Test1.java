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

class HttpStatusCode {
    private final String reason;
    private final int code;

    HttpStatusCode(String reason, int code) {
        this.reason = reason;
        this.code = code;
    }

    String getReason() {
        return reason;
    }

    int getCode() {
        return code;
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
            params.put(key, value);
        }
        return params;
    }
}

class HttpStatusCodeMapper {

    static HttpStatusCode fromParams(Map<String, String> params) {
        String reason = params.getOrDefault("reason", "Ok");
        int code = Integer.parseInt(params.getOrDefault("code", "200"));
        return new HttpStatusCode(reason, code);
    }
}

class PersonJsonSerializer {

    static String toJson(HttpStatusCode p) {
        int id = System.identityHashCode(p);

        return """
        {
          "code": "%d",
          "reason": %s,
          "objectId": "%s"
        }
        """.formatted(
                p.getCode(),
                escape(p.getReason()),
                Integer.toHexString(id));
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

            HttpStatusCode person;
            try {
                person = HttpStatusCodeMapper.fromParams(params);
            } catch (NumberFormatException e) {
                exchange.sendResponseHeaders(400, -1);
                return;
            }

            String json = PersonJsonSerializer.toJson(person);

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, json.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(json.getBytes());
            }
        }
    }

}
