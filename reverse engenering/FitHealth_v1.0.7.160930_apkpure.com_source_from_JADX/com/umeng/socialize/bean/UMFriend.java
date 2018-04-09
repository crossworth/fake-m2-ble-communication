package com.umeng.socialize.bean;

import java.io.Serializable;

public class UMFriend implements Serializable {
    private static final long f3244a = 1;
    private int f3245b;
    private String f3246c;
    private String f3247d;
    private String f3248e;
    private String f3249f;
    private String f3250g;
    private PinYin f3251h;
    private long f3252i;
    private boolean f3253j = true;
    public char mGroup;

    public static class PinYin implements Serializable {
        private static final long serialVersionUID = 1;
        public String mInitial;
        public String mTotalPinyin;
    }

    public int getId() {
        return this.f3245b;
    }

    public void setId(int i) {
        this.f3245b = i;
    }

    public String getFid() {
        return this.f3246c;
    }

    public String getLinkName() {
        return this.f3248e;
    }

    public void setLinkName(String str) {
        this.f3248e = str;
    }

    public void setFid(String str) {
        this.f3246c = str;
    }

    public String getName() {
        return this.f3247d;
    }

    public void setName(String str) {
        this.f3247d = str;
    }

    public String getIcon() {
        return this.f3249f;
    }

    public void setIcon(String str) {
        this.f3249f = str;
    }

    public String getUsid() {
        return this.f3250g;
    }

    public void setUsid(String str) {
        this.f3250g = str;
    }

    public long getLastAtTime() {
        return this.f3252i;
    }

    public void setLastAtTime(long j) {
        this.f3252i = j;
    }

    public boolean isAlive() {
        return this.f3253j;
    }

    public void setAlive(boolean z) {
        this.f3253j = z;
    }

    public PinYin getPinyin() {
        return this.f3251h;
    }

    public void setPinyin(PinYin pinYin) {
        this.f3251h = pinYin;
        if (pinYin != null) {
            this.mGroup = pinYin.mInitial.charAt(0);
        }
    }

    public final boolean isUpdate(UMFriend uMFriend) {
        if (uMFriend == null) {
            return false;
        }
        if (uMFriend.getLastAtTime() > this.f3252i) {
            return true;
        }
        if (uMFriend.isAlive() != isAlive()) {
            return true;
        }
        if (!uMFriend.getName().equals(this.f3247d)) {
            return true;
        }
        if (uMFriend.getIcon() == null || uMFriend.getIcon().equals(this.f3249f)) {
            return false;
        }
        return true;
    }

    public char upGroup() {
        if (this.mGroup == '\u0000' && this.f3251h != null) {
            this.mGroup = this.f3251h.mInitial.charAt(0);
        }
        if (this.mGroup != '\u0000') {
            char c = this.mGroup;
            if ('@' < c && c < '[') {
                return c;
            }
            if (('`' < c && c < '{') || c == "常".charAt(0)) {
                return c;
            }
        }
        return "符".charAt(0);
    }

    public boolean isEquals(String str) {
        if (str == null || !str.equals(Character.valueOf(this.mGroup))) {
            return false;
        }
        return true;
    }
}
