package com.zhuoyou.plugin.bluetooth.product;

import java.util.ArrayList;
import java.util.List;

public class Grandpa {
    private List<Father> mFathers = new ArrayList();
    private String mModifyTime;

    public List<Father> getFather() {
        return this.mFathers;
    }

    public void addFather(Father father) {
        this.mFathers.add(father);
    }

    public String getModifyTime() {
        return this.mModifyTime;
    }

    public void setModifyTime(String mModifyTime) {
        this.mModifyTime = mModifyTime;
    }
}
