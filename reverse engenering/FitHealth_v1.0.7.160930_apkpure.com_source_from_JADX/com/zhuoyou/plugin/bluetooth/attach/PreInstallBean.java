package com.zhuoyou.plugin.bluetooth.attach;

import java.util.ArrayList;
import java.util.List;

public class PreInstallBean {
    private String mCategory = null;
    private String mName = null;
    private List<String> mPlugNames = new ArrayList();
    private List<String> mPlugPackageNames = new ArrayList();

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

    public List<String> getPlugPackageNames() {
        return this.mPlugPackageNames;
    }

    public void addPlugPackageName(String PlugPackageName) {
        this.mPlugPackageNames.add(PlugPackageName);
    }

    public List<String> getPlugNames() {
        return this.mPlugNames;
    }

    public void addPlugName(String mPlugName) {
        this.mPlugNames.add(mPlugName);
    }
}
