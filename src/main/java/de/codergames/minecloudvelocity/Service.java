package de.codergames.minecloudvelocity;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;

import java.util.ArrayList;
import java.util.List;

import de.codergames.minecloudvelocity.core.network.responds.data.RegisterServerData;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

import de.codergames.minecloudvelocity.command.CloudCommand;
import de.codergames.minecloudvelocity.core.config.ServiceConfig;
import de.codergames.minecloudvelocity.core.ServiceInfo;
import de.codergames.minecloudvelocity.event.PlayerJoin;
import de.codergames.minecloudvelocity.core.network.WebHookServer;


public class Service {

    private Logger logger;
    private ProxyServer proxyServer;
    private ServiceConfig serviceConfig;
    private PluginContainer pluginContainer;
    private Cloud cloud;
    private List<RegisteredServer> tryServer;


    public Service(ProxyServer proxyServer, Logger logger, PluginContainer pluginContainer) {
        this.proxyServer = proxyServer;
        this.logger = logger;
        this.pluginContainer = pluginContainer;
        this.serviceConfig = new ServiceConfig();
        this.cloud = new Cloud(this);
    }

    public void start() {
        logger.info("Start the MineCloud Plugin ...");
        cloud.setOnlineMode();
        this.tryServer = new ArrayList<>();

        registerEvents();
        registerCommands();
        startWebHookServer();


        logger.info("MineCloud Plugin Erfolgreich geladen");
        cloud.getOnlineService();
    }

    public void stop() {
        logger.info("Stop the MineCloud Plugin");
        proxyServer.shutdown(Component.text("Stop By Cloud"));
    }

    private void startWebHookServer() {
        WebHookServer webHookServer = new WebHookServer(this);
        webHookServer.start();
    }

    private void registerEvents() {
        proxyServer.getEventManager().register(pluginContainer, new PlayerJoin(this));
    }

    private void registerCommands() {
        CommandManager commandManager = proxyServer.getCommandManager();

        CommandMeta commandMeta = commandManager.metaBuilder("cloud")
                .aliases("minecloud")
                .plugin(pluginContainer)
                .build();

        SimpleCommand simpleCommand = new CloudCommand(this);

        commandManager.register(commandMeta, simpleCommand);
    }

    public ServiceConfig getServiceConfig() {
        return this.serviceConfig;
    }
    public Logger getLogger() {
        return this.logger;
    }
    public List<RegisteredServer> getTryServer() {
        return this.tryServer;
    }
    public ProxyServer getProxyServer(){
        return this.proxyServer;
    }
    public void addServer(ServiceInfo serviceInfo) {
       RegisteredServer registeredServer = proxyServer.registerServer(new ServerInfo(serviceInfo.getName(), serviceInfo.getServerAddress().getInetSocketAddress()));
       tryServer.add(registeredServer);
    }
    public void addServer(RegisterServerData server) {
        RegisteredServer registeredServer = proxyServer.registerServer(new ServerInfo(server.getName(), server.getAddress().getInetSocketAddress()));
        if (server.isTryToConnect()) {
            tryServer.add(registeredServer);
        }
    }

    public void shutdown() {
        proxyServer.shutdown();
    }

    public void shutdown(String msg) {
        proxyServer.shutdown(Component.text(msg));
    }

}
