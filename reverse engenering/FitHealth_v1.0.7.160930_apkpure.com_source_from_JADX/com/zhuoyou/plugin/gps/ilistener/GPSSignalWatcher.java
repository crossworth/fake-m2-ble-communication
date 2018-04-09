package com.zhuoyou.plugin.gps.ilistener;

public interface GPSSignalWatcher {
    void addWatcher(GPSSignalListener gPSSignalListener);

    void notifyWatchers(int i);

    void removeWatcher(GPSSignalListener gPSSignalListener);
}
