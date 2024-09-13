package de.codergames.minecloudvelocity.core.network.handler;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.codergames.minecloudvelocity.Service;
import de.codergames.minecloudvelocity.core.network.WebHookServer;
import de.codergames.minecloudvelocity.core.network.responds.RespondsBody;

import java.io.IOException;
import java.io.OutputStream;

public class ShutdownHandler implements HttpHandler {

    private Service service;

    public ShutdownHandler(Service service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            RespondsBody respondsBody = handle(WebHookServer.getJson(exchange));
            OutputStream os = exchange.getResponseBody();
            os.write(respondsBody.getResponse().getBytes());
            exchange.sendResponseHeaders(respondsBody.getCode(), respondsBody.getResponse().length());
            os.close();
        }
    }

    private RespondsBody handle(JsonObject json) {
        if (json.has("shutdown")) {
            String msg = String.valueOf(json.get("shutdown"));
            if (msg.equals("")) {
                service.shutdown();
                return new RespondsBody(200, "Shutdown Service: " + service.getServiceConfig().getName());
            }
            service.shutdown(msg);
            return new RespondsBody(200, "Shutdown Service: " + service.getServiceConfig().getName() + " with msg: " + msg);
        }
        return new RespondsBody(400, "Ungültige Shutdown Anfrage");
    }

}
