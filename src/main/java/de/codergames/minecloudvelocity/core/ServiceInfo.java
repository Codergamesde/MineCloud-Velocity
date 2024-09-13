package de.codergames.minecloudvelocity.core;

import de.codergames.minecloudvelocity.core.lib.Address;

public class ServiceInfo {

    private String name;
    private Address server_address;


    public ServiceInfo(String name, Address address) {
        this.name = name;
        this.server_address = address;
    }

    public String getName() {
        return name;
    }

    public Address getServerAddress() {
        return server_address;
    }
}
