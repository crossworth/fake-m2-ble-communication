package com.weibo.net;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class WeiboParameters {
    private List<String> mKeys = new ArrayList();
    private Bundle mParameters = new Bundle();

    public void add(String key, String value) {
        if (this.mKeys.contains(key)) {
            this.mParameters.putString(key, value);
            return;
        }
        this.mKeys.add(key);
        this.mParameters.putString(key, value);
    }

    public void remove(String key) {
        this.mKeys.remove(key);
        this.mParameters.remove(key);
    }

    public void remove(int i) {
        String key = (String) this.mKeys.get(i);
        this.mParameters.remove(key);
        this.mKeys.remove(key);
    }

    public int getLocation(String key) {
        if (this.mKeys.contains(key)) {
            return this.mKeys.indexOf(key);
        }
        return -1;
    }

    public String getKey(int location) {
        if (location < 0 || location >= this.mKeys.size()) {
            return "";
        }
        return (String) this.mKeys.get(location);
    }

    public String getValue(String key) {
        return this.mParameters.getString(key);
    }

    public String getValue(int location) {
        return this.mParameters.getString((String) this.mKeys.get(location));
    }

    public int size() {
        return this.mKeys.size();
    }

    public void addAll(WeiboParameters parameters) {
        for (int i = 0; i < parameters.size(); i++) {
            add(parameters.getKey(i), parameters.getValue(i));
        }
    }

    public void clear() {
        this.mKeys.clear();
        this.mParameters.clear();
    }
}
