package com.amap.api.services.help;

public class InputtipsQuery implements Cloneable {
    private String f1212a;
    private String f1213b;
    private boolean f1214c = false;
    private String f1215d = null;

    public InputtipsQuery(String str, String str2) {
        this.f1212a = str;
        this.f1213b = str2;
    }

    public String getKeyword() {
        return this.f1212a;
    }

    public String getCity() {
        return this.f1213b;
    }

    public void setType(String str) {
        this.f1215d = str;
    }

    public String getType() {
        return this.f1215d;
    }

    public void setCityLimit(boolean z) {
        this.f1214c = z;
    }

    public boolean getCityLimit() {
        return this.f1214c;
    }
}
