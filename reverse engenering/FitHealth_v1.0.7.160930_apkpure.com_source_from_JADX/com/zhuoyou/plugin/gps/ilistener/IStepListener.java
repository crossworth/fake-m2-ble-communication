package com.zhuoyou.plugin.gps.ilistener;

public interface IStepListener {
    void onHadRunStep(int i);

    void onStateChanged(int i);

    void onStepCount(int i);
}
