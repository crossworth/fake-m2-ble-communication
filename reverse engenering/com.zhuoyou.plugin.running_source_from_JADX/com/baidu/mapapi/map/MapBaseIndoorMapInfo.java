package com.baidu.mapapi.map;

import java.util.ArrayList;

public final class MapBaseIndoorMapInfo {
    private static final String f1134d = MapBaseIndoorMapInfo.class.getSimpleName();
    String f1135a;
    String f1136b;
    ArrayList<String> f1137c;

    public enum SwitchFloorError {
        SWITCH_OK,
        FLOOR_INFO_ERROR,
        FLOOR_OVERLFLOW,
        FOCUSED_ID_ERROR,
        SWITCH_ERROR
    }

    public MapBaseIndoorMapInfo(MapBaseIndoorMapInfo mapBaseIndoorMapInfo) {
        this.f1135a = mapBaseIndoorMapInfo.f1135a;
        this.f1136b = mapBaseIndoorMapInfo.f1136b;
        this.f1137c = mapBaseIndoorMapInfo.f1137c;
    }

    public MapBaseIndoorMapInfo(String str, String str2, ArrayList<String> arrayList) {
        this.f1135a = str;
        this.f1136b = str2;
        this.f1137c = arrayList;
    }

    public String getCurFloor() {
        return this.f1136b;
    }

    public ArrayList<String> getFloors() {
        return this.f1137c;
    }

    public String getID() {
        return this.f1135a;
    }
}
