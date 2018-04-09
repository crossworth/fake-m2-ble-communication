package com.zhuoyou.plugin.running.bean;

public class WXGuide {
    private int hint;
    private int img;

    public WXGuide(int img, int hint) {
        this.img = img;
        this.hint = hint;
    }

    public int getImg() {
        return this.img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getHint() {
        return this.hint;
    }

    public void setHint(int hint) {
        this.hint = hint;
    }
}
