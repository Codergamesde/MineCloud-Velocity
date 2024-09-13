package de.codergames.minecloudvelocity.core.network.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import de.codergames.minecloudvelocity.Service;
import de.codergames.minecloudvelocity.core.network.WebHookServer;
import de.codergames.minecloudvelocity.core.network.responds.MovePlayerResponds;
import de.codergames.minecloudvelocity.core.network.responds.RespondsBody;
import de.codergames.minecloudvelocity.core.network.responds.data.MovePlayerData;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

public class MovePlayerHandler implements HttpHandler {

    private Service service;

    public MovePlayerHandler(Service service) {
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
        Gson gson = new Gson();
        if (!json.has("move_player")) {
            MovePlayerResponds movePlayerResponds = gson.fromJson(json, MovePlayerResponds.class);
            MovePlayerData movePlayerData = movePlayerResponds.getMovePlayer();
            Optional<Player> optionalPlayer = service.getProxyServer().getPlayer(movePlayerData.getPlayerName());
            Optional<RegisteredServer> optionalRegisteredServer = service.getProxyServer().getServer(movePlayerData.getServerName());

            if (optionalRegisteredServer.isEmpty()) {
                service.getLogger().info("Server " + movePlayerData.getServerName() + " nicht gefunden");
                return new RespondsBody(404, "Server " + movePlayerData.getServerName() + " nicht gefunden");
            }
            if (optionalPlayer.isEmpty()) {
                service.getLogger().info("player_move objekt passt nicht");
                return new RespondsBody(400, "player_move objekt passt nicht");
            }

            RegisteredServer registeredServer = optionalRegisteredServer.get();
            Player player = optionalPlayer.get();

            player.createConnectionRequest(registeredServer).connect();

            return new RespondsBody(200, "Move Player Anfrage Erfolgreich verarbietet");
        }
        return new RespondsBody(400, "Ungültige Anfrage");
    }
}
