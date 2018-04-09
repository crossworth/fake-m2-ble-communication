package com.zhuoyou.plugin.bluetooth.product;

import java.util.ArrayList;
import java.util.List;

public class Father {
    private String mCategory;
    private String mName;
    private List<Son> mSons = new ArrayList();

    public List<Son> getSons() {
        return this.mSons;
    }

    public void addSon(Son son) {
        this.mSons.add(son);
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getCategory() {
        return this.mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }
}
