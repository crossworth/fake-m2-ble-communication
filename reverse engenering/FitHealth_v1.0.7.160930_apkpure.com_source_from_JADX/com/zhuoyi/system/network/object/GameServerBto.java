package com.zhuoyi.system.network.object;

import com.zhuoyi.system.network.serializer.ByteField;

public class GameServerBto {
    @ByteField(index = 2)
    private String host;
    @ByteField(description = "模块标识符", index = 1)
    private short moduleId;
    @ByteField(index = 3)
    private int port;

    public short getModuleId() {
        return this.moduleId;
    }

    public void setModuleId(short moduleId) {
        this.moduleId = moduleId;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String toString() {
        return "GameServerBto [moduleId=" + this.moduleId + ", host=" + this.host + ", port=" + this.port + "]";
    }
}
