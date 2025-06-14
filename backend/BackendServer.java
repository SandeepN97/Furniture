import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class BackendServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/products", new ProductsHandler());
        server.setExecutor(null); // default executor
        server.start();
        System.out.println("Server started on port 8080");
    }

    static class ProductsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String json = "[" +
                    "{\"id\":1,\"name\":\"Modern Chair\",\"price\":540}," +
                    "{\"id\":2,\"name\":\"Wooden Table\",\"price\":300}," +
                    "{\"id\":3,\"name\":\"Office Desk\",\"price\":450}" +
                    "]";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            byte[] response = json.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, response.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }
        }
    }
}
