package com.zhuoyi.system.network.object;

import com.zhuoyi.system.network.serializer.ByteField;
import java.util.ArrayList;

public class ZoneServerBto {
    @ByteField(index = 3)
    private int gameId;
    @ByteField(index = 7)
    private ArrayList<GameServerBto> gameServerList;
    @ByteField(description = "1：New 2：火爆， 3：拥挤", index = 6)
    private short iconId;
    @ByteField(index = 5)
    private String zoneDesc;
    @ByteField(index = 2)
    private int zoneId;
    @ByteField(index = 4)
    private String zoneName;

    public int getZoneId() {
        return this.zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public int getGameId() {
        return this.gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getZoneName() {
        return this.zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getZoneDesc() {
        return this.zoneDesc;
    }

    public void setZoneDesc(String zoneDesc) {
        this.zoneDesc = zoneDesc;
    }

    public short getIconId() {
        return this.iconId;
    }

    public void setIconId(short iconId) {
        this.iconId = iconId;
    }

    public ArrayList<GameServerBto> getGameServerList() {
        return this.gameServerList;
    }

    public void setGameServerList(ArrayList<GameServerBto> gameServerList) {
        this.gameServerList = gameServerList;
    }

    public String toString() {
        return "ZoneServerBto [zoneId=" + this.zoneId + ", gameId=" + this.gameId + ", zoneName=" + this.zoneName + ", zoneDesc=" + this.zoneDesc + ", iconId=" + this.iconId + ", gameServerList=" + this.gameServerList + "]";
    }
}
