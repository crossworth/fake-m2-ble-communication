package com.zhuoyou.plugin.running.bean;

public class EventFirmware {
    private boolean hasUpdate = false;

    public EventFirmware(boolean hasUpdate) {
        this.hasUpdate = hasUpdate;
    }

    public boolean isHasUpdate() {
        return this.hasUpdate;
    }
}
