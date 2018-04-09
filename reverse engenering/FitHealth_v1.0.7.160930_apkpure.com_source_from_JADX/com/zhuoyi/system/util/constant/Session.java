package com.zhuoyi.system.util.constant;

import com.zhuoyi.system.network.object.NetworkAddr;
import java.io.Serializable;
import java.util.UUID;

public final class Session implements Serializable {
    private static final long serialVersionUID = 1044237039063420007L;
    private static Session session = null;
    private String UUId = UUID.randomUUID().toString();
    private NetworkAddr promNetworkAddr = null;
    private NetworkAddr statisNetworkAddr = null;
    private NetworkAddr updateNetworkAddr = null;

    private Session() {
    }

    public NetworkAddr getPromNetworkAddr() {
        return getInstance().promNetworkAddr;
    }

    public void setPromNetworkAddr(NetworkAddr promNetworkAddr) {
        getInstance().promNetworkAddr = promNetworkAddr;
    }

    public NetworkAddr getStatisNetworkAddr() {
        return this.statisNetworkAddr;
    }

    public void setStatisNetworkAddr(NetworkAddr statisNetworkAddr) {
        getInstance().statisNetworkAddr = statisNetworkAddr;
    }

    public NetworkAddr getUpdateNetworkAddr() {
        return this.updateNetworkAddr;
    }

    public void setUpdateNetworkAddr(NetworkAddr updateNetworkAddr) {
        getInstance().updateNetworkAddr = updateNetworkAddr;
    }

    public String toString() {
        return "Session [promNetworkAddr=" + this.promNetworkAddr + ", statisNetworkAddr=" + this.statisNetworkAddr + ", updateNetworkAddr=" + this.updateNetworkAddr + "]";
    }

    public boolean isEmpty() {
        if (this.promNetworkAddr == null || this.statisNetworkAddr == null || this.updateNetworkAddr == null) {
            return true;
        }
        return false;
    }

    public static synchronized Session getInstance() {
        Session session;
        synchronized (Session.class) {
            if (session == null) {
                session = new Session();
            }
            session = session;
        }
        return session;
    }

    public String getUUId() {
        return this.UUId;
    }
}
