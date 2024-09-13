package de.codergames.minecloudvelocity.event;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.proxy.Player;
import de.codergames.minecloudvelocity.Service;
import net.kyori.adventure.text.Component;

import java.util.Random;

public class PlayerJoin {

    private Service service;

    public PlayerJoin(Service service) {
        this.service = service;
    }

    @Subscribe(order = PostOrder.FIRST)
    public void PlayerJoin(PlayerChooseInitialServerEvent event) {
        // connect to try Server
        Random random = new Random();
        int randomIndex = random.nextInt(service.getTryServer().size());
        event.setInitialServer(service.getTryServer().get(randomIndex));

        // send message to player
        Player player = event.getPlayer();
        player.sendMessage(Component.text("Hello " + player.getUsername()));
    }

}
