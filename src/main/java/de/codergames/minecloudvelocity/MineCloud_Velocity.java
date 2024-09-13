package de.codergames.minecloudvelocity;

import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(
        id = "minecloud-velocity",
        name = "MineCloud-Velocity",
        version = BuildConstants.VERSION
)
public class MineCloud_Velocity {

    private Service service;

    @Inject
    public MineCloud_Velocity(ProxyServer proxyServer, Logger logger, PluginContainer pluginContainer) {
        this.service = new Service(proxyServer, logger, pluginContainer);
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        service.start();
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        service.stop();
    }

}
