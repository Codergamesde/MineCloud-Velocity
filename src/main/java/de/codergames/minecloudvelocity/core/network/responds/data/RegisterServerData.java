package de.codergames.minecloudvelocity.core.network.responds.data;

import de.codergames.minecloudvelocity.core.lib.Address;

public class RegisterServerData {
    private String name;
    private Address address;
    private boolean try_to_connect;

    public String getName() {
        return this.name;
    }
    public Address getAddress() {
        return this.address;
    }
    public boolean isTryToConnect() {
        return this.try_to_connect;
    }
}

