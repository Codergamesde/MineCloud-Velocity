package de.codergames.minecloudvelocity.core.lib;

import java.net.InetSocketAddress;

public class Address {

    private String ip;
    private int port;


    public Address(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String toString() {
        return (this.ip + ":" + this.port);
    }

    public InetSocketAddress getInetSocketAddress() {
        return new InetSocketAddress(this.ip, this.port);
    }

}
