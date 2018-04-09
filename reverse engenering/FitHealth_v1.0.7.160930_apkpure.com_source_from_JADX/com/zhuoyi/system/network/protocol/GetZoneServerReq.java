package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.TerminalInfo;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;

@SignalCode(encrypt = true, messageCode = 198001)
public class GetZoneServerReq {
    @ByteField(index = 2)
    private int gameId;
    @ByteField(index = 3)
    private TerminalInfo terminalInfo;
    @ByteField(index = 1)
    private String token;
    @ByteField(index = 0)
    private int userId;

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getGameId() {
        return this.gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public TerminalInfo getTerminalInfo() {
        return this.terminalInfo;
    }

    public void setTerminalInfo(TerminalInfo terminalInfo) {
        this.terminalInfo = terminalInfo;
    }

    public String toString() {
        return "GetZoneServerReq [userId=" + this.userId + ", token=" + this.token + ", gameId=" + this.gameId + ", terminalInfo=" + this.terminalInfo + "]";
    }
}
