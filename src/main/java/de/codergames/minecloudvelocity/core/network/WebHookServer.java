package de.codergames.minecloudvelocity.core.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import de.codergames.minecloudvelocity.Service;
import de.codergames.minecloudvelocity.core.network.handler.MovePlayerHandler;
import de.codergames.minecloudvelocity.core.network.handler.RegisterServerHandler;
import de.codergames.minecloudvelocity.core.network.handler.ShutdownHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class WebHookServer {

    private Service service;

    public WebHookServer(Service service) {
        this.service = service;
    }

    public void start() {
        try {
            HttpServer server = HttpServer.create(service.getServiceConfig().getPluginListener().getInetSocketAddress(), 0);
            String url = "/cloud/service/" + service.getServiceConfig().getName();

            server.createContext(url + "/registerService", new RegisterServerHandler(service));
            server.createContext(url + "/shutdown", new ShutdownHandler(service));
            server.createContext(url + "/movePlayer", new MovePlayerHandler(service));

            server.setExecutor(null);
            server.start();

            service.getLogger().info("WebHook Server started on port: {}", server.getAddress().getPort());
        } catch (IOException e) {
            service.getLogger().error("WebHook Server could not be started. {}", e);
        }
    }

    public static JsonObject getJson(HttpExchange exchange) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder requestBodyBuilder = new StringBuilder();
        String line;
        Gson gson = new Gson();
        while ((line = bufferedReader.readLine()) != null) {
            requestBodyBuilder.append(line);
        }
        return gson.fromJson(requestBodyBuilder.toString(), JsonObject.class);
    }
}
