package com.zhuoyi.system.network.object;

public class NetworkAddr {
    private String serverAddress;

    public NetworkAddr(String serverAddress) {
        setServerAddress(serverAddress);
    }

    public String getServerAddress() {
        return this.serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        if (serverAddress == null || !serverAddress.toLowerCase().startsWith("http://")) {
            this.serverAddress = "http://" + serverAddress;
        } else {
            this.serverAddress = serverAddress;
        }
    }

    public String toString() {
        return "NetworkAddr [serverAddress=" + this.serverAddress + "]";
    }
}
