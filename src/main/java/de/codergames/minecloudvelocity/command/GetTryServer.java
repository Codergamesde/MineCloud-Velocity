package de.codergames.minecloudvelocity.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import de.codergames.minecloudvelocity.Service;
import net.kyori.adventure.text.Component;

import java.util.List;

public class GetTryServer {

    public static void execute(Service service, CommandSource source) {
        List<RegisteredServer> tryServer = service.getTryServer();
        if (tryServer.isEmpty()) {
            source.sendMessage(Component.text("There are no servers to try!"));
            return;
        }
        source.sendMessage(Component.text("Servers: "));
        for (RegisteredServer server : tryServer) {
            source.sendMessage(Component.text(server.getServerInfo().getName() + " - " + server.getServerInfo().getAddress() + " -  " + server.getPlayersConnected().toString()));
        }

    }

}
