package com.zhuoyou.plugin.running.bean;

public class EventStep {
    private boolean noAnim = true;

    public EventStep(boolean noAnim) {
        this.noAnim = noAnim;
    }

    public boolean isNoAnim() {
        return this.noAnim;
    }
}
