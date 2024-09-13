package de.codergames.minecloudvelocity.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import de.codergames.minecloudvelocity.Service;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CloudCommand implements SimpleCommand {

    private Service service;

    public CloudCommand(Service service) {
        this.service = service;
    }


    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (args.length == 0) {
            source.sendMessage(Component.text("MineCloud Plugin v1.0"));
            return;
        }

        String arg1 = invocation.arguments()[0];

        if (arg1.equals("getTryServer")) {
            GetTryServer.execute(service, source);
            return;
        }
        if (arg1 == "help") {

        }


    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return SimpleCommand.super.suggest(invocation);
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        return SimpleCommand.super.suggestAsync(invocation);
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return SimpleCommand.super.hasPermission(invocation);
    }
}
