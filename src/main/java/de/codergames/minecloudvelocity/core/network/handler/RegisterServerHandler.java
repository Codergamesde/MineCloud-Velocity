package de.codergames.minecloudvelocity.core.network.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import de.codergames.minecloudvelocity.Service;
import de.codergames.minecloudvelocity.core.ServiceInfo;
import de.codergames.minecloudvelocity.core.network.WebHookServer;
import de.codergames.minecloudvelocity.core.network.responds.RegisterServerResponds;
import de.codergames.minecloudvelocity.core.network.responds.RespondsBody;

import java.io.IOException;
import java.io.OutputStream;

public class RegisterServerHandler implements HttpHandler {

    private Service service;

    public RegisterServerHandler(Service service) {
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

    public RespondsBody handle(JsonObject json) {
        Gson gson = new Gson();
        if (json.has("register_server")) {
            RegisterServerResponds registerServerResponds = gson.fromJson(json, RegisterServerResponds.class);
            ServiceInfo serviceInfo = new ServiceInfo(registerServerResponds.getRegisterServer().getName(), registerServerResponds.getRegisterServer().getAddress());
            service.addServer(serviceInfo);
            service.getLogger().info("Service {} add to try server list", serviceInfo.getName());
            return new RespondsBody(200, "ok");
        }



        return new RespondsBody(400, "Keine Gültige Anfrage");
    }
}
