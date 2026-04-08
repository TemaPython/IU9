import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.text.ParseException;


public class Http {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(5678), 0);
        server.createContext("/lab11", new MyHandlerLab11());
        server.setExecutor(null);
        server.start();
    }

    static class MyHandlerLab11 implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            InputStream body = exchange.getRequestBody();
            String response;
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

            try {
                Parser parser = new Parser();
                Tree tree = parser.parse(body);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos, true, "UTF-8");
                tree.print(ps, "", true);
                response = baos.toString("UTF-8");

            } catch (ParseException e) {
                response = e.getMessage();
            }

            byte[] bytes = response.getBytes("UTF-8");
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }
}
