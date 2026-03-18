import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lab3.Matrix;
import lab2.Point;
import lab2.Triangle;
import lab4.Container;

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
        Point[] points = new Point[3];
        for (int i = 0; i < 3; i++) {
            String dataRaw = params.getOrDefault("p" + i, "0,0,0");

            String[] values = dataRaw.split(",");
            points[i] = new Point(Double.parseDouble(values[0].trim()),
                    Double.parseDouble(values[1].trim()),
                    Double.parseDouble(values[2].trim()));
        }
        return new Triangle(points[0], points[1], points[2]);
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

class MatrixMapper {

    static Matrix fromParams(Map<String, String> params, int n, int k) {
        Matrix matrix = new Matrix(n);
        String dataRaw = params.getOrDefault("m" + k, "");

        String[] values = dataRaw.split(",");
        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (idx < values.length) {
                    matrix.repl(i, j, Integer.parseInt(values[idx].trim()));
                    idx++;
                }
            }
        }
        return matrix;
    }
}

class MatrixJsonSerializer {

    static String toJson(Matrix[] mats) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < mats.length; i++) {
            s.append(mats[i].toString());
            if (i < mats.length - 1) {
                s.append(",\n");
            }
        }
        return String.format("{\"matrices\": \n%s}", s.toString());
    }

    private static String escape(String s) {
        return s.replace("\"", "\\\"");
    }
}

class ListMapper {

    static List<Integer> fromParams(Map<String, String> params, int n, int k) {
        List<Integer> ls = new ArrayList<>();
        String dataRaw = params.getOrDefault("x" + k, "");

        String[] values = dataRaw.split(",");
        for (int i = 0; i < n && i < values.length; i++) {
            ls.add(Integer.parseInt(values[i].trim()));
        }
        return ls;
    }
}

class ContainerJsonSerializer {
    static String toJson(Container container) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean first = true;
        for (Object obj : container) {
            if (!first) sb.append(",");
            sb.append(obj);
            first = false;
        }
        sb.append("]");
        return String.format("{\"Result concat\": \n%s}", sb.toString());
    }
}

public class Test1 {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(5678), 0);
        Router router = new Router(server);
        server.createContext("/lab2", new MyHandlerLab2());
        server.createContext("/lab3", new MyHandlerLab3());
        server.createContext("/lab4", new MyHandlerLab4());
        server.createContext("/menu", new MyHandlerMenu());

        server.setExecutor(null);
        server.start();
    }

    static class MyHandlerMenu implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            File file = new File("./src/menu.html");

            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, file.length());

            try (FileInputStream fis = new FileInputStream(file);
                 OutputStream os = exchange.getResponseBody()) {
                fis.transferTo(os);
            }
        }
    }

    static class MyHandlerLab2 implements HttpHandler {
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

    static class MyHandlerLab3 implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            String query = exchange.getRequestURI().getQuery();
            Map<String, String> params = QueryParser.parse(query);
            int ln = Integer.parseInt(params.getOrDefault("len", "0"));

            Matrix[] matrixs = new Matrix[ln];
            for (int i = 0; i < ln; i++) {
                try {
                    int n = Integer.parseInt(params.getOrDefault("n" + i, "0"));
                    matrixs[i] = MatrixMapper.fromParams(params, n, i);
                } catch (NumberFormatException e) {
                    exchange.sendResponseHeaders(400, -1);
                    return;
                }
            }
            Arrays.sort(matrixs);
            String json = MatrixJsonSerializer.toJson(matrixs);

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, json.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(json.getBytes());
            }
        }
    }

    static class MyHandlerLab4 implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            String query = exchange.getRequestURI().getQuery();
            Map<String, String> params = QueryParser.parse(query);
            int ln = Integer.parseInt(params.getOrDefault("len", "0"));

            Iterable<?>[] arr = new Iterable[ln];
            for (int i = 0; i < ln; i++) {
                try {
                    int l = Integer.parseInt(params.getOrDefault("l" + i, "0"));
                    arr[i] = ListMapper.fromParams(params, l, i);
                } catch (NumberFormatException e) {
                    exchange.sendResponseHeaders(400, -1);
                    return;
                }
            }
            Container container = new Container(arr);
            String json = ContainerJsonSerializer.toJson(container);

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, json.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(json.getBytes());
            }
        }
    }

}
